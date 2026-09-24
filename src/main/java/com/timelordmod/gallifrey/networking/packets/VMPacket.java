package com.timelordmod.gallifrey.networking.packets;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.item.custom.VortexManipulatorData;
import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VMPacket {
    private static final Map<UUID, PendingTeleportEffect> PENDING_EFFECTS = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> SELF_DESTRUCTS = new ConcurrentHashMap<>();
    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final SoundEvent VM_TAKE_OFF_SOUND = SoundEvent.of(new Identifier("gallifrey", "vm_take_off"));
    public static final SoundEvent VM_LAND_SOUND = SoundEvent.of(new Identifier("gallifrey", "vm_land"));

    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickTeleport(server);
        });
    }

    private static void tickTeleport(MinecraftServer server) {
        tickSelfDestruct(server);
        Iterator<Map.Entry<UUID, PendingTeleportEffect>> iterator = PENDING_EFFECTS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, PendingTeleportEffect> entry = iterator.next();
            PendingTeleportEffect pending = entry.getValue();
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());
            if (player == null || player.isRemoved()) { SELF_DESTRUCTS.remove(entry.getKey()); continue; }

            if (pending.phase() == TeleportPhase.SOURCE_WARMUP) {
                ServerWorld sourceWorld = server.getWorld(pending.sourceWorldKey());
                if (sourceWorld == null) { cleanup(player, pending); iterator.remove(); continue; }
                if (pending.ticksRemaining() > 0) { entry.setValue(pending.tickDown()); continue; }
                sourceWorld.playSound(null, pending.sourcePosition().x, pending.sourcePosition().y, pending.sourcePosition().z, VM_TAKE_OFF_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
                sourceWorld.spawnParticles(ParticleTypes.PORTAL, pending.sourcePosition().x, pending.sourcePosition().y + 1.0, pending.sourcePosition().z, 60, 0.4, 0.8, 0.4, 0.05);
                entry.setValue(pending.nextPhase(TeleportPhase.PRE_CLOAK, 19));
                continue;
            }
            if (pending.phase() == TeleportPhase.PRE_CLOAK) {
                if (pending.ticksRemaining() > 0) { entry.setValue(pending.tickDown()); continue; }
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 40, 0, false, false, false));
                ItemStack mainHand = player.getMainHandStack();
                ItemStack offHand = player.getOffHandStack();
                ItemStack hiddenMainHand = ItemStack.EMPTY;
                ItemStack hiddenOffHand = ItemStack.EMPTY;
                if (mainHand.isOf(GallifreyModItems.VORTEX_MANIPULATOR)) { hiddenMainHand = mainHand.copy(); player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY); }
                if (offHand.isOf(GallifreyModItems.VORTEX_MANIPULATOR)) { hiddenOffHand = offHand.copy(); player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY); }
                Map<EquipmentSlot, ItemStack> hiddenArmor = new EnumMap<>(EquipmentSlot.class);
                for (EquipmentSlot slot : ARMOR_SLOTS) {
                    ItemStack piece = player.getEquippedStack(slot);
                    if (!piece.isEmpty()) { hiddenArmor.put(slot, piece.copy()); player.equipStack(slot, ItemStack.EMPTY); }
                }
                entry.setValue(pending.cloak(hiddenMainHand, hiddenOffHand, hiddenArmor, TeleportPhase.TELEPORT, 20));
                continue;
            }
            if (pending.phase() == TeleportPhase.TELEPORT) {
                ServerWorld targetWorld = server.getWorld(pending.targetWorldKey());
                if (targetWorld == null) { cleanup(player, pending); iterator.remove(); continue; }
                if (pending.ticksRemaining() > 0) { entry.setValue(pending.tickDown()); continue; }
                ChunkPos chunkPos = new ChunkPos(pending.targetChunkPos().x(), pending.targetChunkPos().z());
                targetWorld.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());
                FabricDimensions.teleport(player, targetWorld, new TeleportTarget(pending.targetPosition(), player.getVelocity(), player.getYaw(), player.getPitch()));
                entry.setValue(pending.nextPhase(TeleportPhase.TARGET_ARRIVAL, 10));
                continue;
            }
            ServerWorld targetWorld = server.getWorld(pending.targetWorldKey());
            if (targetWorld == null || player.getServerWorld() != targetWorld) { cleanup(player, pending); iterator.remove(); continue; }
            if (pending.ticksRemaining() > 0) { entry.setValue(pending.tickDown()); continue; }
            targetWorld.playSound(null, pending.targetPosition().x, pending.targetPosition().y, pending.targetPosition().z, VM_LAND_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
            targetWorld.spawnParticles(ParticleTypes.REVERSE_PORTAL, pending.targetPosition().x, pending.targetPosition().y + 1.0, pending.targetPosition().z, 60, 0.4, 0.8, 0.4, 0.05);
            cleanup(player, pending);
            iterator.remove();
        }
    }

    private static void cleanup(ServerPlayerEntity player, PendingTeleportEffect pending) {
        restoreHiddenItems(player, pending);
        player.removeStatusEffect(StatusEffects.INVISIBILITY);
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // The networking buffer is released after this callback returns. Copy it before
        // scheduling work on the server thread, otherwise multi-field commands such as
        // TELEPORT can fail with IllegalReferenceCountException while being decoded.
        PacketByteBuf copy = new PacketByteBuf(buf.copy());
        String action = copy.readString(32);
        server.execute(() -> handle(server, player, action, copy));
    }


    private static void tickSelfDestruct(MinecraftServer server) {
        for (Map.Entry<UUID, Integer> entry : SELF_DESTRUCTS.entrySet()) {
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());
            if (player == null || player.isRemoved()) { SELF_DESTRUCTS.remove(entry.getKey()); continue; }

            int ticks = entry.getValue() - 1;
            if (ticks > 0) {
                SELF_DESTRUCTS.put(entry.getKey(), ticks);
                if (ticks % 20 == 0) sendState(server, player);
                continue;
            }

            ItemStack vm = findVortexManipulator(player);
            if (!vm.isEmpty() && VortexManipulatorData.isOwner(vm, player.getUuid())) {
                ServerWorld world = player.getServerWorld();
                world.createExplosion(null, player.getX(), player.getY(), player.getZ(), 4.0F, false, World.ExplosionSourceType.TNT);
                vm.decrement(1);
                player.sendMessage(Text.literal("VORTEX MANIPULATOR SELF-DESTRUCT COMPLETE"), true);
            }
            SELF_DESTRUCTS.remove(entry.getKey());
            sendState(server, player);
        }
    }

    private static void handle(MinecraftServer server, ServerPlayerEntity player, String action, PacketByteBuf buf) {
        ItemStack vm = findVortexManipulator(player);
        if (vm.isEmpty()) { player.sendMessage(Text.literal("You need a Vortex Manipulator."), true); return; }
        VortexManipulatorData.ensureOwner(vm, player);

        switch (action) {
            case "REQUEST_STATE" -> sendState(server, player);
            case "TELEPORT" -> teleport(server, player, vm, buf);
            case "SAVE" -> saveLocation(player, vm, buf);
            case "DELETE" -> deleteLocation(server, player, vm, buf);
            case "GO" -> goLocation(server, player, vm, buf);
            case "ADD_PLAYER" -> addPlayer(server, player, vm, buf);
            case "REMOVE_PLAYER" -> removePlayer(server, player, vm, buf);
            case "SELF_DESTRUCT" -> armSelfDestruct(player, vm);
            case "CANCEL_SELF_DESTRUCT" -> cancelSelfDestruct(player, vm);
            default -> player.sendMessage(Text.literal("Unknown VM command."), true);
        }
    }

    private static void teleport(MinecraftServer server, ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isAuthorized(vm, player.getUuid())) { player.sendMessage(Text.literal("ISOMORPHIC LOCK ACTIVE."), true); return; }
        if (PENDING_EFFECTS.containsKey(player.getUuid())) { player.sendMessage(Text.literal("Vortex Manipulator sequence already active."), true); return; }
        boolean targetPlayerMode = buf.readBoolean();
        String targetPlayerName = targetPlayerMode ? buf.readString(64) : "";
        Identifier dimensionId = !targetPlayerMode ? buf.readIdentifier() : null;
        double x = !targetPlayerMode ? buf.readDouble() : 0;
        double y = !targetPlayerMode ? buf.readDouble() : 0;
        double z = !targetPlayerMode ? buf.readDouble() : 0;
        boolean surfaceMode = !targetPlayerMode && buf.readBoolean();
        ServerWorld targetWorld;
        Vec3d targetPos;
        if (targetPlayerMode) {
            ServerPlayerEntity targetPlayer = server.getPlayerManager().getPlayer(targetPlayerName);
            if (targetPlayer == null) { player.sendMessage(Text.literal("Player not found: " + targetPlayerName), true); return; }
            targetWorld = targetPlayer.getServerWorld();
            targetPos = targetPlayer.getPos();
        } else {
            RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
            targetWorld = server.getWorld(key);
            if (targetWorld == null) { player.sendMessage(Text.literal("Unknown dimension: " + dimensionId), true); return; }
            double targetY = surfaceMode ? targetWorld.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(x), (int) Math.floor(z)) : y;
            targetPos = new Vec3d(x, targetY, z);
        }
        ServerWorld sourceWorld = player.getServerWorld();
        Vec3d sourcePos = player.getPos();
        BlockPos targetBlockPos = BlockPos.ofFloored(targetPos);
        PENDING_EFFECTS.put(player.getUuid(), new PendingTeleportEffect(sourceWorld.getRegistryKey(), sourcePos, targetWorld.getRegistryKey(), targetPos,
                new ChunkPosKey(targetBlockPos.getX() >> 4, targetBlockPos.getZ() >> 4), TeleportPhase.SOURCE_WARMUP, 1, ItemStack.EMPTY, ItemStack.EMPTY, Map.of()));
    }

    private static void saveLocation(ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isAuthorized(vm, player.getUuid())) {
            player.sendMessage(Text.literal("ISOMORPHIC LOCK: you are not authorized to modify VM memory."), true);
            return;
        }
        String name = buf.readString(32).trim();
        if (name.isEmpty()) { player.sendMessage(Text.literal("Location name cannot be empty."), true); return; }
        if (VortexManipulatorData.locations(vm).size() >= 20 && VortexManipulatorData.findLocation(vm, name) == null) { player.sendMessage(Text.literal("VM memory full: maximum 20 saved locations."), true); return; }
        VortexManipulatorData.saveLocation(vm, name, player.getServerWorld().getRegistryKey().getValue().toString(), player.getX(), player.getY(), player.getZ());
        player.sendMessage(Text.literal("Saved VM location: " + name), true);
        sendState(null, player);
    }

    private static void deleteLocation(MinecraftServer server, ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isAuthorized(vm, player.getUuid())) {
            player.sendMessage(Text.literal("ISOMORPHIC LOCK: you are not authorized to modify VM memory."), true);
            return;
        }
        String name = buf.readString(32);
        player.sendMessage(Text.literal(VortexManipulatorData.deleteLocation(vm, name) ? "Deleted VM location: " + name : "Location not found: " + name), true);
        sendState(server, player);
    }

    private static void goLocation(MinecraftServer server, ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isAuthorized(vm, player.getUuid())) {
            player.sendMessage(Text.literal("ISOMORPHIC LOCK ACTIVE."), true);
            return;
        }
        String name = buf.readString(32);
        NbtCompound location = VortexManipulatorData.findLocation(vm, name);
        if (location == null) { player.sendMessage(Text.literal("Location not found: " + name), true); return; }
        PacketByteBuf fake = net.fabricmc.fabric.api.networking.v1.PacketByteBufs.create();
        fake.writeBoolean(false);
        fake.writeIdentifier(Identifier.tryParse(location.getString(VortexManipulatorData.DIMENSION)));
        fake.writeDouble(location.getDouble(VortexManipulatorData.X));
        fake.writeDouble(location.getDouble(VortexManipulatorData.Y));
        fake.writeDouble(location.getDouble(VortexManipulatorData.Z));
        fake.writeBoolean(false);
        teleport(server, player, vm, fake);
    }

    private static void addPlayer(MinecraftServer server, ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isOwner(vm, player.getUuid())) { player.sendMessage(Text.literal("Only the VM owner can change isomorphic users."), true); return; }
        String name = buf.readString(64);
        ServerPlayerEntity target = server.getPlayerManager().getPlayer(name);
        if (target == null) { player.sendMessage(Text.literal("Player must be online: " + name), true); return; }
        if (VortexManipulatorData.addUser(vm, target.getUuid())) player.sendMessage(Text.literal("Added " + target.getName().getString() + " to isomorphic controls."), true);
        else player.sendMessage(Text.literal("Player is already authorized."), true);
        sendState(server, player);
    }

    private static void removePlayer(MinecraftServer server, ServerPlayerEntity player, ItemStack vm, PacketByteBuf buf) {
        if (!VortexManipulatorData.isOwner(vm, player.getUuid())) { player.sendMessage(Text.literal("Only the VM owner can change isomorphic users."), true); return; }
        String name = buf.readString(64);
        ServerPlayerEntity target = server.getPlayerManager().getPlayer(name);
        UUID uuid = target != null ? target.getUuid() : findUuidByName(vm, name);
        if (uuid != null && VortexManipulatorData.removeUser(vm, uuid)) player.sendMessage(Text.literal("Removed " + name + " from isomorphic controls."), true);
        else player.sendMessage(Text.literal("Authorized player not found: " + name), true);
        sendState(server, player);
    }

    private static UUID findUuidByName(ItemStack vm, String name) {
        for (String id : VortexManipulatorData.userIds(vm)) {
            try {
                UUID uuid = UUID.fromString(id);
                // Names are not persisted intentionally; the online-name path above is preferred.
                // If offline, accept a UUID string as the removal key.
                if (id.equalsIgnoreCase(name)) return uuid;
            } catch (IllegalArgumentException ignored) {}
        }
        return null;
    }

    private static void armSelfDestruct(ServerPlayerEntity player, ItemStack vm) {
        if (!VortexManipulatorData.isOwner(vm, player.getUuid())) {
            player.sendMessage(Text.literal("SELF-DESTRUCT requires the VM owner."), true);
            return;
        }
        if (SELF_DESTRUCTS.containsKey(player.getUuid())) {
            player.sendMessage(Text.literal("SELF-DESTRUCT is already armed."), true);
            return;
        }
        SELF_DESTRUCTS.put(player.getUuid(), 200);
        player.sendMessage(Text.literal("SELF-DESTRUCT ARMED: 10 seconds. Use CANCEL to abort."), true);
        sendState(player.getServer(), player);
    }

    private static void cancelSelfDestruct(ServerPlayerEntity player, ItemStack vm) {
        if (!VortexManipulatorData.isOwner(vm, player.getUuid())) {
            player.sendMessage(Text.literal("SELF-DESTRUCT requires the VM owner."), true);
            return;
        }
        if (SELF_DESTRUCTS.remove(player.getUuid()) != null) {
            player.sendMessage(Text.literal("SELF-DESTRUCT CANCELLED."), true);
        } else {
            player.sendMessage(Text.literal("No self-destruct sequence is armed."), true);
        }
        sendState(player.getServer(), player);
    }

    private static ItemStack findVortexManipulator(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(GallifreyModItems.VORTEX_MANIPULATOR)) return stack;
        }
        return ItemStack.EMPTY;
    }

    public static void sendState(MinecraftServer server, ServerPlayerEntity player) {
        ItemStack vm = findVortexManipulator(player);
        if (vm.isEmpty()) return;
        VortexManipulatorData.ensureOwner(vm, player);
        PacketByteBuf buf = net.fabricmc.fabric.api.networking.v1.PacketByteBufs.create();
        buf.writeBoolean(VortexManipulatorData.isOwner(vm, player.getUuid()));
        buf.writeVarInt(SELF_DESTRUCTS.getOrDefault(player.getUuid(), 0));
        NbtList locations = VortexManipulatorData.locations(vm);
        buf.writeVarInt(locations.size());
        for (int i = 0; i < locations.size(); i++) {
            NbtCompound loc = locations.getCompound(i);
            buf.writeString(loc.getString(VortexManipulatorData.NAME), 32);
            buf.writeString(loc.getString(VortexManipulatorData.DIMENSION), 128);
            buf.writeDouble(loc.getDouble(VortexManipulatorData.X));
            buf.writeDouble(loc.getDouble(VortexManipulatorData.Y));
            buf.writeDouble(loc.getDouble(VortexManipulatorData.Z));
        }
        java.util.List<String> users = VortexManipulatorData.userIds(vm);
        buf.writeVarInt(users.size());
        for (String id : users) {
            UUID uuid = UUID.fromString(id);
            ServerPlayerEntity online = player.getServer().getPlayerManager().getPlayer(uuid);
            buf.writeUuid(uuid);
            buf.writeString(online == null ? id : online.getName().getString(), 64);
        }
        ServerPlayNetworking.send(player, ModPackets.VM_STATE, buf);
    }

    private static void restoreHiddenItems(ServerPlayerEntity player, PendingTeleportEffect pending) {
        if (!pending.hiddenMainHand().isEmpty()) player.setStackInHand(Hand.MAIN_HAND, pending.hiddenMainHand());
        if (!pending.hiddenOffHand().isEmpty()) player.setStackInHand(Hand.OFF_HAND, pending.hiddenOffHand());
        for (Map.Entry<EquipmentSlot, ItemStack> hidden : pending.hiddenArmor().entrySet()) player.equipStack(hidden.getKey(), hidden.getValue());
    }

    private record ChunkPosKey(int x, int z) {}
    private enum TeleportPhase { SOURCE_WARMUP, PRE_CLOAK, TELEPORT, TARGET_ARRIVAL }
    private record PendingTeleportEffect(RegistryKey<World> sourceWorldKey, Vec3d sourcePosition, RegistryKey<World> targetWorldKey,
                                         Vec3d targetPosition, ChunkPosKey targetChunkPos, TeleportPhase phase, int ticksRemaining,
                                         ItemStack hiddenMainHand, ItemStack hiddenOffHand, Map<EquipmentSlot, ItemStack> hiddenArmor) {
        private PendingTeleportEffect tickDown() { return new PendingTeleportEffect(sourceWorldKey, sourcePosition, targetWorldKey, targetPosition, targetChunkPos, phase, ticksRemaining - 1, hiddenMainHand, hiddenOffHand, hiddenArmor); }
        private PendingTeleportEffect nextPhase(TeleportPhase next, int ticks) { return new PendingTeleportEffect(sourceWorldKey, sourcePosition, targetWorldKey, targetPosition, targetChunkPos, next, ticks, hiddenMainHand, hiddenOffHand, hiddenArmor); }
        private PendingTeleportEffect cloak(ItemStack main, ItemStack off, Map<EquipmentSlot, ItemStack> armor, TeleportPhase next, int ticks) { return new PendingTeleportEffect(sourceWorldKey, sourcePosition, targetWorldKey, targetPosition, targetChunkPos, next, ticks, main, off, armor); }
    }
}
