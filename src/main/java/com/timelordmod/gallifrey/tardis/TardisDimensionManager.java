package com.timelordmod.gallifrey.tardis;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.TardisExteriorBlock;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.UUID;

public final class TardisDimensionManager {
    public static final Identifier INTERIOR_DIMENSION_ID = new Identifier(GallifreyMod.MOD_ID, "tardis");
    private static final BlockPos DEFAULT_ENTRY_OFFSET = new BlockPos(0, 1, 0);

    private TardisDimensionManager() {}

    public static Identifier getDimensionId(long tardisId) {
        return INTERIOR_DIMENSION_ID;
    }

    public static RegistryKey<World> interiorKey() {
        return RegistryKey.of(RegistryKeys.WORLD, INTERIOR_DIMENSION_ID);
    }

    public static ServerWorld getInterior(MinecraftServer server) {
        return server.getWorld(interiorKey());
    }

    public static Vec3d interiorEntry(TardisExteriorBlockEntity tardis) {
        BlockPos o = tardis.getInteriorOrigin();
        return new Vec3d(o.getX() + 0.5, o.getY() + 1.0, o.getZ() + 0.5);
    }

    public static boolean enter(ServerPlayerEntity player, TardisExteriorBlockEntity tardis) {
        MinecraftServer server = player.getServer();
        if (server == null) return false;

        if (!tardis.canAccess(player.getUuid())) {
            player.sendMessage(Text.literal("The TARDIS is locked."), true);
            return false;
        }

        ServerWorld interior = getInterior(server);
        if (interior == null) {
            player.sendMessage(Text.literal("The TARDIS interior dimension is unavailable."), true);
            return false;
        }

        tardis.ensureInitialized(player);
        tardis.generateInterior(interior);

        TardisRegistryState registry = TardisRegistryState.get(server);
        registry.register(tardis.getTardisId(), tardis.getOwner(), player.getServerWorld(),
                tardis.getPos().asLong(), tardis.getInteriorOrigin().asLong(), tardis.isLocked());
        registry.setActive(player, tardis.getTardisId());

        ChunkPos chunk = new ChunkPos(tardis.getInteriorOrigin());
        interior.getChunkManager().addTicket(net.minecraft.server.world.ChunkTicketType.POST_TELEPORT, chunk, 2, player.getId());
        FabricDimensions.teleport(player, interior,
                new TeleportTarget(interiorEntry(tardis), Vec3d.ZERO,
                        tardis.getCachedState().get(TardisExteriorBlock.ROTATION) * 45.0f, 0.0f));
        return true;
    }

    public static boolean exit(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return false;
        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(player.getUuid());
        if (id == null) {
            player.sendMessage(Text.literal("You are not inside a registered TARDIS."), true);
            return false;
        }

        TardisRegistryState.Record record = registry.get(id);
        if (record == null) {
            registry.clearActive(player);
            player.sendMessage(Text.literal("The TARDIS link could not be restored."), true);
            return false;
        }

        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world()));
        ServerWorld world = server.getWorld(key);
        if (world == null) {
            player.sendMessage(Text.literal("The TARDIS exterior world is unavailable."), true);
            return false;
        }

        BlockPos pos = BlockPos.fromLong(record.pos());
        world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
        if (!(world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis)
                || !id.equals(tardis.getTardisId())) {
            player.sendMessage(Text.literal("The TARDIS exterior could not be located."), true);
            return false;
        }

        registry.clearActive(player);
        FabricDimensions.teleport(player, world,
                new TeleportTarget(Vec3d.ofCenter(pos).add(0, 0.15, 0),
                        Vec3d.ZERO,
                        tardis.getCachedState().get(TardisExteriorBlock.ROTATION) * 45.0f, 0.0f));
        return true;
    }

    public static boolean travel(ServerPlayerEntity pilot, ServerWorld targetWorld, BlockPos targetPos, float yaw) {
        MinecraftServer server = pilot.getServer();
        if (server == null) return false;

        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(pilot.getUuid());
        if (id == null) {
            pilot.sendMessage(Text.literal("Enter a TARDIS before initiating flight."), true);
            return false;
        }

        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return false;

        ServerWorld currentWorld = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (currentWorld == null) return false;
        BlockPos currentPos = BlockPos.fromLong(record.pos());
        currentWorld.getChunk(currentPos.getX() >> 4, currentPos.getZ() >> 4);

        if (!(currentWorld.getBlockEntity(currentPos) instanceof TardisExteriorBlockEntity tardis)) {
            pilot.sendMessage(Text.literal("TARDIS exterior is missing."), true);
            return false;
        }

        if (!tardis.canPilot(pilot.getUuid())) {
            pilot.sendMessage(Text.literal("The TARDIS controls are locked to its pilot."), true);
            return false;
        }
        if (tardis.getFuel() < TardisExteriorBlockEntity.FLIGHT_COST) {
            pilot.sendMessage(Text.literal("Insufficient artron energy. Refuel the TARDIS first."), true);
            return false;
        }

        targetWorld.getChunk(targetPos.getX() >> 4, targetPos.getZ() >> 4);
        if (!isSafeLandingSpace(targetWorld, targetPos, currentWorld == targetWorld && currentPos.equals(targetPos))) {
            pilot.sendMessage(Text.literal("The TARDIS cannot materialise there: the landing block is occupied."), true);
            return false;
        }

        tardis.consumeFuel(TardisExteriorBlockEntity.FLIGHT_COST);
        pilot.sendMessage(Text.literal("TARDIS flight initiated. Engines engaging..."), true);
        currentWorld.playSound(null, currentPos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.5f, 0.65f);
        targetWorld.playSound(null, targetPos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.2f, 0.8f);

        // Store the target on the TARDIS. A server tick will perform the actual
        // dematerialisation/rematerialisation, keeping the interior occupied during flight.
        tardis.beginFlight(targetWorld.getRegistryKey().getValue(), targetPos, rotationFromYaw(yaw));
        return true;
    }

    public static void tickFlight(MinecraftServer server) {
        TardisRegistryState registry = TardisRegistryState.get(server);
        for (TardisRegistryState.Record record : new java.util.ArrayList<>(registryRecords(registry))) {
            ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
            if (world == null) continue;
            BlockPos pos = BlockPos.fromLong(record.pos());
            world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof TardisExteriorBlockEntity tardis && tardis.isFlightPending()) {
                if (tardis.tickFlight()) {
                    materialize(server, registry, world, pos, tardis);
                }
            }
        }
    }

    private static java.util.Collection<TardisRegistryState.Record> registryRecords(TardisRegistryState state) {
        return state.records();
    }

    private static void materialize(MinecraftServer server, TardisRegistryState registry,
                                    ServerWorld sourceWorld, BlockPos sourcePos,
                                    TardisExteriorBlockEntity tardis) {
        Identifier targetId = tardis.getFlightWorld();
        ServerWorld targetWorld = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, targetId));
        if (targetWorld == null) {
            tardis.cancelFlight();
            return;
        }

        BlockPos targetPos = tardis.getFlightPos();
        if (!isSafeLandingSpace(targetWorld, targetPos, sourceWorld == targetWorld && sourcePos.equals(targetPos))) {
            tardis.cancelFlight();
            return;
        }

        BlockState state = tardis.getCachedState().with(TardisExteriorBlock.ROTATION, tardis.getFlightRotation());
        NbtCompound nbt = tardis.createNbt();

        sourceWorld.setBlockState(sourcePos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
        targetWorld.setBlockState(targetPos, state, 3);
        if (targetWorld.getBlockEntity(targetPos) instanceof TardisExteriorBlockEntity newTardis) {
            nbt.remove("x");
            nbt.remove("y");
            nbt.remove("z");
            nbt.remove("id");
            newTardis.readNbt(nbt);
            newTardis.finishFlight();
            registry.updateLocation(newTardis.getTardisId(), targetWorld, targetPos.asLong());
            newTardis.markDirty();
        }
        targetWorld.playSound(null, targetPos, SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.BLOCKS, 2.0f, 0.55f);
        targetWorld.spawnParticles(net.minecraft.particle.ParticleTypes.REVERSE_PORTAL,
                targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5,
                80, 0.5, 1.0, 0.5, 0.04);
    }

    private static boolean isSafeLandingSpace(ServerWorld world, BlockPos pos, boolean allowCurrent) {
        if (allowCurrent) return true;
        return world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isAir();
    }

    public static int rotationFromYaw(float yaw) {
        return net.minecraft.util.math.MathHelper.floor((double)((yaw + 180.0F) * 8.0F / 360.0F) + 0.5D) & 7;
    }

    public static void refuel(ServerPlayerEntity player, int amount) {
        MinecraftServer server = player.getServer();
        if (server == null) return;
        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(player.getUuid());
        if (id == null) return;
        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return;
        ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (world == null) return;
        BlockPos pos = BlockPos.fromLong(record.pos());
        if (world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis) {
            int added = tardis.addFuel(amount);
            player.sendMessage(Text.literal("Artron reserves: " + tardis.getFuel() + "/" + TardisExteriorBlockEntity.MAX_FUEL +
                    " (+" + added + ")"), true);
        }
    }
}
