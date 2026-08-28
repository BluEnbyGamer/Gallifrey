package com.timelordmod.gallifrey.networking.packets;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VMPacket {
    private static final Map<UUID, PendingTeleportEffect> PENDING_EFFECTS = new ConcurrentHashMap<>();

    // assets/gallifrey/sounds/vm_take_off.ogg
    public static final SoundEvent VM_TAKE_OFF_SOUND = SoundEvent.of(new Identifier("gallifrey", "vm_take_off"));

    // assets/gallifrey/sounds/vm_land.ogg
    public static final SoundEvent VM_LAND_SOUND = SoundEvent.of(new Identifier("gallifrey", "vm_land"));

    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            Iterator<Map.Entry<UUID, PendingTeleportEffect>> iterator = PENDING_EFFECTS.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<UUID, PendingTeleportEffect> entry = iterator.next();
                PendingTeleportEffect pending = entry.getValue();
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());

                if (player == null || player.isRemoved()) {
                    iterator.remove();
                    continue;
                }

                // Phase 1: Source Warmup / Plays VM Sound
                if (pending.phase() == TeleportPhase.SOURCE_WARMUP) {
                    ServerWorld sourceWorld = server.getWorld(pending.sourceWorldKey());
                    if (sourceWorld == null) {
                        player.removeStatusEffect(StatusEffects.INVISIBILITY);
                        iterator.remove();
                        continue;
                    }

                    if (pending.ticksRemaining() > 0) {
                        entry.setValue(pending.tickDown());
                        continue;
                    }

                    // Play take-off sound at source position
                    sourceWorld.playSound(null, pending.sourcePosition().x, pending.sourcePosition().y, pending.sourcePosition().z,
                            VM_TAKE_OFF_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);

                    // Departure burst
                    sourceWorld.spawnParticles(ParticleTypes.PORTAL,
                            pending.sourcePosition().x, pending.sourcePosition().y + 1.0, pending.sourcePosition().z,
                            60, 0.4, 0.8, 0.4, 0.05);

                    // 19 ticks brings us to the 20-tick (1 second) mark from
                    // when the packet was received, where the player cloaks
                    entry.setValue(pending.nextPhase(TeleportPhase.PRE_CLOAK, 19));
                    continue;
                }

                // Phase 2: Pre-Cloak (player stays visible here, then goes invisible)
                if (pending.phase() == TeleportPhase.PRE_CLOAK) {
                    if (pending.ticksRemaining() > 0) {
                        entry.setValue(pending.tickDown());
                        continue;
                    }

                    // 1 second after take-off: go invisible now.
                    // Duration must outlast the remaining timeline (20 + 10 = 30 ticks)
                    // with room to spare, or it expires before the code removes it
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 40, 0, false, false, false));

                    // 20 ticks brings us to the original tick-40 teleport moment —
                    // same overall timing as before, just with a visible head start
                    entry.setValue(pending.nextPhase(TeleportPhase.TELEPORT, 20));
                    continue;
                }

                // Phase 3: Teleporting Player
                if (pending.phase() == TeleportPhase.TELEPORT) {
                    ServerWorld targetWorld = server.getWorld(pending.targetWorldKey());
                    if (targetWorld == null) {
                        player.removeStatusEffect(StatusEffects.INVISIBILITY);
                        iterator.remove();
                        continue;
                    }

                    if (pending.ticksRemaining() > 0) {
                        entry.setValue(pending.tickDown());
                        continue;
                    }

                    // Force load target chunk to prevent freezing in unloaded terrain
                    ChunkPos chunkPos = new ChunkPos(pending.targetChunkPos().x(), pending.targetChunkPos().z());
                    targetWorld.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());

                    FabricDimensions.teleport(player, targetWorld, new TeleportTarget(
                            pending.targetPosition(),
                            player.getVelocity(),
                            player.getYaw(),
                            player.getPitch()
                    ));
                    entry.setValue(pending.nextPhase(TeleportPhase.TARGET_ARRIVAL, 10));
                    continue;
                }

                // Phase 4: Arrival & Cleanup
                ServerWorld targetWorld = server.getWorld(pending.targetWorldKey());
                if (targetWorld == null || player.getServerWorld() != targetWorld) {
                    player.removeStatusEffect(StatusEffects.INVISIBILITY);
                    iterator.remove();
                    continue;
                }

                if (pending.ticksRemaining() > 0) {
                    entry.setValue(pending.tickDown());
                    continue;
                }

                // Play land sound at arrival position
                targetWorld.playSound(null, pending.targetPosition().x, pending.targetPosition().y, pending.targetPosition().z,
                        VM_LAND_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);

                // Arrival burst
                targetWorld.spawnParticles(ParticleTypes.REVERSE_PORTAL,
                        pending.targetPosition().x, pending.targetPosition().y + 1.0, pending.targetPosition().z,
                        60, 0.4, 0.8, 0.4, 0.05);

                player.removeStatusEffect(StatusEffects.INVISIBILITY);
                iterator.remove();
            }
        });
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        boolean targetPlayerMode = buf.readBoolean();
        String targetPlayerName = targetPlayerMode ? buf.readString() : "";
        Identifier dimensionId = !targetPlayerMode ? buf.readIdentifier() : null;
        double x = !targetPlayerMode ? buf.readDouble() : 0;
        double y = !targetPlayerMode ? buf.readDouble() : 0;
        double z = !targetPlayerMode ? buf.readDouble() : 0;

        server.execute(() -> {
            if (!gallifrey$hasVortexManipulator(player)) {
                player.sendMessage(Text.literal("You need a Vortex Manipulator."), true);
                return;
            }

            if (PENDING_EFFECTS.containsKey(player.getUuid())) {
                player.sendMessage(Text.literal("Vortex Manipulator sequence already active."), true);
                return;
            }

            ServerWorld targetWorld;
            Vec3d targetPos;

            if (targetPlayerMode) {
                ServerPlayerEntity targetPlayer = server.getPlayerManager().getPlayer(targetPlayerName);
                if (targetPlayer == null) {
                    player.sendMessage(Text.literal("Player not found: " + targetPlayerName), true);
                    return;
                }
                targetWorld = targetPlayer.getServerWorld();
                targetPos = targetPlayer.getPos();
            } else {
                RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
                targetWorld = server.getWorld(key);
                if (targetWorld == null) {
                    player.sendMessage(Text.literal("Unknown dimension: " + dimensionId), true);
                    return;
                }
                targetPos = new Vec3d(x, y, z);
            }

            ServerWorld sourceWorld = player.getServerWorld();
            Vec3d sourcePos = player.getPos();
            BlockPos targetBlockPos = BlockPos.ofFloored(targetPos);

            // Player stays visible here — invisibility gets applied
            // 1 second in, at the end of the PRE_CLOAK phase below

            PENDING_EFFECTS.put(player.getUuid(), new PendingTeleportEffect(
                    sourceWorld.getRegistryKey(),
                    sourcePos,
                    targetWorld.getRegistryKey(),
                    targetPos,
                    new ChunkPosKey(targetBlockPos.getX() >> 4, targetBlockPos.getZ() >> 4),
                    TeleportPhase.SOURCE_WARMUP,
                    1
            ));
        });
    }

    private static boolean gallifrey$hasVortexManipulator(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).isOf(GallifreyModItems.VORTEX_MANIPULATOR)) {
                return true;
            }
        }
        return false;
    }

    private record ChunkPosKey(int x, int z) {}

    private enum TeleportPhase {
        SOURCE_WARMUP,
        PRE_CLOAK,
        TELEPORT,
        TARGET_ARRIVAL
    }

    private record PendingTeleportEffect(RegistryKey<World> sourceWorldKey, Vec3d sourcePosition,
                                         RegistryKey<World> targetWorldKey, Vec3d targetPosition,
                                         ChunkPosKey targetChunkPos, TeleportPhase phase, int ticksRemaining) {
        private PendingTeleportEffect tickDown() {
            return new PendingTeleportEffect(sourceWorldKey, sourcePosition, targetWorldKey, targetPosition,
                    targetChunkPos, phase, ticksRemaining - 1);
        }

        private PendingTeleportEffect nextPhase(TeleportPhase nextPhase, int nextTicksRemaining) {
            return new PendingTeleportEffect(sourceWorldKey, sourcePosition, targetWorldKey, targetPosition,
                    targetChunkPos, nextPhase, nextTicksRemaining);
        }
    }
}