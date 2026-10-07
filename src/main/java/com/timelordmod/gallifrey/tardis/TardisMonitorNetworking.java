package com.timelordmod.gallifrey.tardis;

import com.timelordmod.gallifrey.GallifreySounds;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;

/** Server-side protocol for the in-world TARDIS console. */
public final class TardisMonitorNetworking {
    private TardisMonitorNetworking() {}

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.TARDIS_MONITOR_ACTION,
                (server, player, handler, buf, responseSender) -> {
                    PacketByteBuf copy = new PacketByteBuf(buf.copy());
                    String action = copy.readString(32);
                    server.execute(() -> handle(player, action, copy));
                });
    }

    public static boolean openFor(ServerPlayerEntity player) {
        if (!sendState(player)) {
            player.sendMessage(Text.literal("The console cannot find your active TARDIS."), true);
            return false;
        }
        return true;
    }

    private static void handle(ServerPlayerEntity player, String action, PacketByteBuf buf) {
        try {
            switch (action) {
                case "REQUEST_STATE" -> sendState(player);
                case "FLIGHT" -> {
                    String dimension = buf.readString(128);
                    double x = buf.readDouble();
                    double y = buf.readDouble();
                    double z = buf.readDouble();
                    Identifier id = new Identifier(dimension);
                    ServerWorld target = player.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, id));
                    if (target == null) {
                        player.sendMessage(Text.literal("Unknown dimension: " + id), true);
                    } else {
                        TardisDimensionManager.travel(player, target, BlockPos.ofFloored(x, y, z), player.getYaw());
                    }
                    sendState(player);
                }
                case "REFUEL" -> {
                    int added = TardisDimensionManager.refuel(player);
                    if (added > 0) {
                        player.sendMessage(Text.literal("Refuelled TARDIS by " + added + " artron energy."), true);
                    }
                    sendState(player);
                }
                case "INTERIOR" -> {
                    String name = buf.readString(64);
                    TardisDimensionManager.swapInterior(player, name);
                    sendState(player);
                }
                case "EXTERIOR_VARIANT" -> {
                    int variant = buf.readVarInt();
                    setExteriorVariant(player, variant);
                    sendState(player);
                }
                case "LOCK" -> { setLock(player, true); sendState(player); }
                case "UNLOCK" -> { setLock(player, false); sendState(player); }
                case "PLAY_DRWHO_VALE" -> playDrWhoVale(player);
                default -> player.sendMessage(Text.literal("Unknown TARDIS console action."), true);
            }
        } catch (Exception e) {
            player.sendMessage(Text.literal("TARDIS console command could not be completed."), true);
            sendState(player);
        }
    }

    private static void playDrWhoVale(ServerPlayerEntity player) {
        TardisExteriorBlockEntity tardis = findActive(player);
        if (tardis == null || !tardis.canPilot(player.getUuid())) {
            player.sendMessage(Text.literal("The console cannot find your active TARDIS."), true);
            return;
        }
        ServerWorld world = player.getServerWorld();
        world.playSound(null, player.getBlockPos(), GallifreySounds.DRWHOVALE, SoundCategory.MUSIC, 1.0f, 1.0f);
        player.sendMessage(Text.literal("Now playing: Doctor Who Vale"), true);
    }

    private static boolean setLock(ServerPlayerEntity player, boolean locked) {
        TardisExteriorBlockEntity tardis = findActive(player);
        if (tardis == null || !tardis.canPilot(player.getUuid())) {
            player.sendMessage(Text.literal("Only the TARDIS owner can change security."), true);
            return false;
        }
        tardis.setLocked(locked);
        player.sendMessage(Text.literal(locked ? "TARDIS security locked." : "TARDIS security open."), true);
        return true;
    }

    private static boolean setExteriorVariant(ServerPlayerEntity player, int variant) {
        TardisExteriorBlockEntity tardis = findActive(player);
        if (tardis == null || !tardis.canPilot(player.getUuid())) return false;
        tardis.setExteriorVariant(variant);
        return true;
    }

    public static boolean sendState(ServerPlayerEntity player) {
        TardisExteriorBlockEntity tardis = findActive(player);
        if (tardis == null) return false;

        TardisRegistryState registry = TardisRegistryState.get(player.getServer());
        UUID id = registry.getActiveTardis(player.getUuid());
        if (id == null) return false;

        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return false;

        ServerWorld interior = TardisDimensionManager.getInterior(player.getServer(), record);
        if (interior == null && !TardisDimensionManager.INTERIOR_DIMENSION_ID.toString().equals(record.interiorDimension())) {
            interior = TardisDimensionManager.ensureInteriorWorld(player.getServer(), id);
        }
        if (interior != null) TardisDimensionManager.sendInteriorDimensionKey(player, interior.getRegistryKey().getValue());

        PacketByteBuf out = PacketByteBufs.create();
        out.writeBoolean(true);
        out.writeInt(tardis.getFuel());
        out.writeInt(TardisExteriorBlockEntity.MAX_FUEL);
        out.writeVarInt(tardis.getExteriorVariant());
        out.writeString(tardis.getExteriorVariantName(), 32);
        out.writeString(tardis.getInteriorStructure(), 64);
        out.writeVarInt(TardisInteriorCatalog.names().size());
        for (String name : TardisInteriorCatalog.names()) out.writeString(name, 64);
        out.writeString(record.world(), 128);
        out.writeString(record.interiorDimension(), 128);
        BlockPos pos = BlockPos.fromLong(record.pos());
        out.writeDouble(pos.getX());
        out.writeDouble(pos.getY());
        out.writeDouble(pos.getZ());
        out.writeBoolean(tardis.isFlightPending());
        out.writeBoolean(tardis.isLocked());

        ServerPlayNetworking.send(player, ModPackets.TARDIS_MONITOR_STATE, out);
        return true;
    }

    private static TardisExteriorBlockEntity findActive(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return null;
        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(player.getUuid());
        if (id == null) return null;
        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return null;
        ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (world == null) return null;
        BlockPos pos = BlockPos.fromLong(record.pos());
        world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
        if (world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis
                && id.equals(tardis.getTardisId())) return tardis;
        return null;
    }
}
