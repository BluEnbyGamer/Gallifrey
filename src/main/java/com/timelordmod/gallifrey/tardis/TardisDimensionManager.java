package com.timelordmod.gallifrey.tardis;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.TardisExteriorBlock;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.GallifreySounds;
import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
        if (!tardis.generateInterior(interior)) {
            player.sendMessage(Text.literal("The TARDIS interior structure could not be loaded."), true);
            return false;
        }

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
        world.playSound(null, pos, GallifreySounds.POLICE_BOX_DOOR_CLOSE, SoundCategory.BLOCKS, 0.65F, 1.0F);
        FabricDimensions.teleport(player, world,
                new TeleportTarget(Vec3d.ofCenter(pos).add(0, 0.15, 0),
                        Vec3d.ZERO,
                        tardis.getCachedState().get(TardisExteriorBlock.ROTATION) * 45.0f, 0.0f));
        return true;
    }

    public static boolean swapInterior(ServerPlayerEntity pilot, String structureName) {
        MinecraftServer server = pilot.getServer();
        if (server == null) return false;
        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(pilot.getUuid());
        if (id == null) {
            pilot.sendMessage(Text.literal("Enter a TARDIS before changing its interior."), true);
            return false;
        }
        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return false;
        ServerWorld interior = getInterior(server);
        if (interior == null) return false;

        ServerWorld exteriorWorld = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (exteriorWorld == null) return false;
        BlockPos exteriorPos = BlockPos.fromLong(record.pos());
        if (!(exteriorWorld.getBlockEntity(exteriorPos) instanceof TardisExteriorBlockEntity tardis)) return false;
        if (!tardis.canPilot(pilot.getUuid())) {
            pilot.sendMessage(Text.literal("Only the TARDIS owner can change its interior."), true);
            return false;
        }
        if (tardis.isFlightPending()) {
            pilot.sendMessage(Text.literal("You cannot change the interior during flight."), true);
            return false;
        }
        if (TardisInteriorCatalog.id(structureName) == null) {
            pilot.sendMessage(Text.literal("Unknown interior. Use /tardis interiors to list the available designs."), true);
            return false;
        }

        // Changing the room invalidates the current interior layout. Safely eject
        // everybody first, including the pilot who requested the change.
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (registry.isInside(player, id) && player.getServerWorld() == interior) {
                closeConsole(player);
                exit(player);
            }
        }

        if (!tardis.replaceInterior(interior, structureName)) {
            pilot.sendMessage(Text.literal("That interior could not be loaded."), true);
            return false;
        }
        pilot.sendMessage(Text.literal("TARDIS interior changed to " + structureName + "."), true);
        interior.playSound(null, tardis.getInteriorOrigin(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.BLOCKS, 1.0f, 1.2f);
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

        int minY = targetWorld.getBottomY();
        int maxY = targetWorld.getTopY() - 2;
        if (targetPos.getY() < minY || targetPos.getY() > maxY) {
            pilot.sendMessage(Text.literal("The TARDIS cannot materialise outside this dimension's build height."), true);
            return false;
        }

        targetWorld.getChunk(targetPos.getX() >> 4, targetPos.getZ() >> 4);
        if (!isSafeLandingSpace(targetWorld, targetPos, currentWorld == targetWorld && currentPos.equals(targetPos))) {
            pilot.sendMessage(Text.literal("The TARDIS cannot materialise there: the landing block is occupied."), true);
            return false;
        }

        tardis.consumeFuel(TardisExteriorBlockEntity.FLIGHT_COST);
        pilot.sendMessage(Text.literal("TARDIS flight initiated. Engines engaging..."), true);

        // Classic TARDIS sequence: dematerialise at the old location, ride the
        // flight sound through the vortex, then materialise at the destination.
        currentWorld.playSound(null, currentPos, GallifreySounds.TYPE70DEMAT, SoundCategory.BLOCKS, 1.8f, 1.0f);
        currentWorld.playSound(null, currentPos, GallifreySounds.TYPE70FLIGHT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        ServerWorld interior = getInterior(server);
        if (interior != null) {
            interior.playSound(null, tardis.getInteriorOrigin(), GallifreySounds.TYPE70FLIGHT, SoundCategory.AMBIENT, 0.75f, 1.0f);
        }

        // Store the target on the TARDIS. A server tick performs the actual
        // rematerialisation while players remain safely inside the TARDIS dimension.
        tardis.beginFlight(targetWorld.getRegistryKey().getValue(), targetPos, rotationFromYaw(yaw));
        return true;
    }

    public static void tickFlight(MinecraftServer server) {
        TardisRegistryState registry = TardisRegistryState.get(server);
        tickInteriorSafety(server, registry);
        for (TardisRegistryState.Record record : new java.util.ArrayList<>(registryRecords(registry))) {
            ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
            if (world == null) continue;
            BlockPos pos = BlockPos.fromLong(record.pos());
            world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof TardisExteriorBlockEntity tardis) {
                if (tardis.isFlightPending()) {
                    int before = tardis.getFlightTicks();
                    if (before % 3 == 0) {
                        spawnPhaseParticles(world, pos, before);
                    }
                    if (tardis.tickFlight()) {
                        materialize(server, registry, world, pos, tardis);
                    }
                } else if (tardis.getMaterializationTicks() > 0) {
                    if (tardis.getMaterializationTicks() % 2 == 0) {
                        spawnMaterializationParticles(world, pos);
                    }
                    tardis.tickMaterialization();
                }
            }
        }
    }

    /** Keeps players from falling into the void of the shared TARDIS pocket dimension. */
    private static void tickInteriorSafety(MinecraftServer server, TardisRegistryState registry) {
        ServerWorld interior = getInterior(server);
        if (interior == null) return;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.getServerWorld() != interior) continue;
            UUID id = registry.getActiveTardis(player.getUuid());
            if (id == null) continue;
            TardisRegistryState.Record record = registry.get(id);
            if (record == null) continue;

            BlockPos origin = BlockPos.fromLong(record.origin());
            // Interior templates are built around Y=64. If a player falls more
            // than eight blocks below their TARDIS floor, return them above the
            // physical console rather than letting them reach the world void.
            if (player.getY() >= origin.getY() - 8) continue;

            BlockPos console = tardisConsolePosition(interior, origin, record, server);
            Vec3d target = console == null
                    ? new Vec3d(origin.getX() + 0.5, origin.getY() + 2.0, origin.getZ() + 0.5)
                    : new Vec3d(console.getX() + 0.5, console.getY() + 1.25, console.getZ() + 0.5);
            player.teleport(interior, target.x, target.y, target.z, player.getYaw(), player.getPitch());
            player.setVelocity(Vec3d.ZERO);
            player.sendMessage(Text.literal("The TARDIS catches you and returns you to the console."), true);
        }
    }

    /** Finds the registered console, falling back to a modest search only when needed. */
    private static BlockPos tardisConsolePosition(ServerWorld interior, BlockPos origin, TardisRegistryState.Record record, MinecraftServer server) {
        ServerWorld exterior = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (exterior != null) {
            BlockPos exteriorPos = BlockPos.fromLong(record.pos());
            if (exterior.getBlockEntity(exteriorPos) instanceof TardisExteriorBlockEntity tardis) {
                BlockPos stored = tardis.getConsolePos();
                if (stored != null && interior.getBlockState(stored).isOf(GallifreyModBlocks.TARDIS_CONSOLE)) {
                    return stored;
                }
            }
        }

        // The console may have been broken in an older save before consolePos
        // existed. Search only around the normal central console area.
        for (int x = origin.getX() - 12; x <= origin.getX() + 12; x++) {
            for (int y = Math.max(interior.getBottomY(), origin.getY() - 2); y <= origin.getY() + 8; y++) {
                for (int z = origin.getZ() - 12; z <= origin.getZ() + 12; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (interior.getBlockState(pos).isOf(GallifreyModBlocks.TARDIS_CONSOLE)) return pos;
                }
            }
        }
        return null;
    }

    /** Permanently removes a TARDIS and its pocket interior. */
    public static boolean deleteTardis(MinecraftServer server, UUID id) {
        TardisRegistryState registry = TardisRegistryState.get(server);
        TardisRegistryState.Record record = registry.get(id);
        if (record == null) return false;

        ServerWorld exteriorWorld = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        BlockPos exteriorPos = BlockPos.fromLong(record.pos());

        // Eject linked players before deleting the record so exit() can still
        // resolve the physical exterior.
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (registry.isInside(player, id)) {
                closeConsole(player);
                if (exteriorWorld != null) {
                    player.teleport(exteriorWorld, exteriorPos.getX() + 0.5, exteriorPos.getY() + 0.15,
                            exteriorPos.getZ() + 0.5, player.getYaw(), player.getPitch());
                }
                registry.clearActive(player);
            }
        }

        ServerWorld interior = getInterior(server);
        if (interior != null) {
            // The registry stores the origin but not the latest footprint. Clear
            // a generous area around it so the generated interior is removed.
            clearDeletedInterior(interior, BlockPos.fromLong(record.origin()));
        }

        if (exteriorWorld != null) {
            exteriorWorld.getChunk(exteriorPos.getX() >> 4, exteriorPos.getZ() >> 4);
            if (exteriorWorld.getBlockState(exteriorPos).isOf(GallifreyModBlocks.TARDIS_EXTERIOR)) {
                exteriorWorld.setBlockState(exteriorPos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
            }
        }
        registry.remove(id);
        return true;
    }

    private static void clearDeletedInterior(ServerWorld world, BlockPos origin) {
        // Current interiors occupy a modest footprint. A 96x32x96 cleanup box
        // also removes layouts whose saved footprint was larger in older builds.
        for (int x = -48; x <= 48; x++) {
            for (int y = -2; y <= 32; y++) {
                for (int z = -48; z <= 48; z++) {
                    world.setBlockState(origin.add(x, y, z), net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
                }
            }
        }
    }

    public static void closeConsole(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, ModPackets.TARDIS_CLOSE_CONSOLE, net.fabricmc.fabric.api.networking.v1.PacketByteBufs.create());
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
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (registry.isInside(player, newTardis.getTardisId())) {
                    TardisMonitorNetworking.sendState(player);
                }
            }
        }
        targetWorld.playSound(null, targetPos, GallifreySounds.TYPE70MAT, SoundCategory.BLOCKS, 1.8f, 1.0f);
        ServerWorld interior = getInterior(server);
        if (interior != null) {
            interior.playSound(null, tardis.getInteriorOrigin(), GallifreySounds.TYPE70MAT, SoundCategory.AMBIENT, 0.75f, 1.0f);
        }
        spawnMaterializationParticles(targetWorld, targetPos);
    }

    private static void spawnPhaseParticles(ServerWorld world, BlockPos pos, int remainingTicks) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;
        float pulse = 0.35F + (float)Math.sin(remainingTicks * 0.9F) * 0.12F;
        world.spawnParticles(net.minecraft.particle.ParticleTypes.PORTAL, x, y, z,
                18, 0.55, 1.0, 0.55, pulse);
        if (remainingTicks < 45) {
            world.spawnParticles(net.minecraft.particle.ParticleTypes.END_ROD, x, y, z,
                    3, 0.35, 0.7, 0.35, 0.015);
        }
    }

    private static void spawnMaterializationParticles(ServerWorld world, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;
        world.spawnParticles(net.minecraft.particle.ParticleTypes.REVERSE_PORTAL, x, y, z,
                45, 0.55, 1.0, 0.55, 0.04);
        world.spawnParticles(net.minecraft.particle.ParticleTypes.END_ROD, x, y, z,
                8, 0.45, 0.9, 0.45, 0.02);
    }

    private static boolean isSafeLandingSpace(ServerWorld world, BlockPos pos, boolean allowCurrent) {
        if (allowCurrent) return true;
        return world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isAir();
    }

    public static int rotationFromYaw(float yaw) {
        return net.minecraft.util.math.MathHelper.floor((double)((yaw + 180.0F) * 8.0F / 360.0F) + 0.5D) & 7;
    }

    /** Refuels from Atrium Fuel items in the pilot inventory and returns energy added. */
    public static int refuel(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return 0;
        TardisRegistryState registry = TardisRegistryState.get(server);
        UUID id = registry.getActiveTardis(player.getUuid());
        if (id == null) return 0;
        TardisRegistryState.Record record = registry.get(id);
        if (record == null || record.owner() == null || !record.owner().equals(player.getUuid())) return 0;
        ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(record.world())));
        if (world == null) return 0;
        BlockPos pos = BlockPos.fromLong(record.pos());
        if (!(world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis)) return 0;

        int added = 0;
        for (int slot = 0; slot < player.getInventory().size()
                && tardis.getFuel() < TardisExteriorBlockEntity.MAX_FUEL; slot++) {
            net.minecraft.item.ItemStack stack = player.getInventory().getStack(slot);
            if (!stack.isOf(com.timelordmod.gallifrey.item.GallifreyModItems.ATRIUM_FUEL)) continue;
            stack.decrement(1);
            added += tardis.addFuel(100);
        }
        return added;
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
