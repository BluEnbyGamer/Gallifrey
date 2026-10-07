package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.tardis.TardisDimensionManager;
import com.timelordmod.gallifrey.tardis.TardisRegistryState;
import com.timelordmod.gallifrey.tardis.TardisExteriorCatalog;
import com.timelordmod.gallifrey.block.entity.TardisInteriorDoorBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.UUID;

/** Persistent state for one physical TARDIS exterior. */
public class TardisExteriorBlockEntity extends BlockEntity {
    public static final int MAX_FUEL = 1000;
    public static final int FLIGHT_COST = 10;
    private static final int FLIGHT_TIME = 60;
    private static final int MATERIALIZATION_TIME = 20;

    private UUID tardisId;
    private UUID owner;
    private boolean locked;
    private int fuel = 100;
    private BlockPos interiorOrigin = new BlockPos(0, 64, 0);
    private boolean interiorGenerated;
    private String interiorStructure = "tardis_platform";
    private String exteriorStyle = "policebox";
    private Vec3i interiorSize = new Vec3i(5, 5, 5);
    private BlockPos consolePos;
    private BlockPos interiorDoorPos;

    private boolean flightPending;
    private int flightTicks;
    private Identifier flightWorld;
    private BlockPos flightPos;
    private int flightRotation;
    private int materializationTicks;

    public TardisExteriorBlockEntity(BlockPos pos, BlockState state) {
        super(GallifreyModBlockEntities.TARDIS_EXTERIOR, pos, state);
    }

    public void ensureInitialized(ServerPlayerEntity firstOwner) {
        if (tardisId == null) {
            UUID newId = UUID.randomUUID();
            // Every newly created TARDIS gets a real, isolated pocket world.
            // The legacy shared dimension remains available only for old saves.
            ServerWorld interior = TardisDimensionManager.ensureInteriorWorld(firstOwner.getServer(), newId);
            if (interior == null) {
                firstOwner.sendMessage(net.minecraft.text.Text.literal("The TARDIS could not create its pocket dimension."), true);
                return;
            }
            tardisId = newId;
            owner = firstOwner.getUuid();
            TardisRegistryState registry = TardisRegistryState.get(firstOwner.getServer());
            interiorOrigin = new BlockPos(0, 64, 0);
            String interiorDimension = interior.getRegistryKey().getValue().toString();
            registry.register(tardisId, owner, firstOwner.getServerWorld(), pos.asLong(),
                    interiorOrigin.asLong(), locked, interiorDimension);
            markDirty();
        }
    }

    public UUID getTardisId() { return tardisId; }
    public UUID getOwner() { return owner; }
    public boolean isLocked() { return locked; }
    public int getFuel() { return fuel; }
    public BlockPos getInteriorOrigin() { return interiorOrigin; }
    public String getInteriorStructure() { return interiorStructure; }
    public String getExteriorStyle() { return TardisExteriorCatalog.get(exteriorStyle).id(); }
    public String getExteriorStyleName() { return TardisExteriorCatalog.get(exteriorStyle).displayName(); }
    public void setExteriorStyle(String style) {
        exteriorStyle = TardisExteriorCatalog.get(style).id();
        updateInteriorDoorStyle();
        markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }
    public Vec3i getInteriorSize() { return interiorSize; }
    public BlockPos getConsolePos() { return consolePos; }
    public BlockPos getInteriorDoorPos() { return interiorDoorPos; }

    public void setConsolePos(BlockPos pos) {
        consolePos = pos == null ? null : pos.toImmutable();
        markDirty();
    }

    public void setInteriorDoorPos(BlockPos pos) {
        interiorDoorPos = pos == null ? null : pos.toImmutable();
        markDirty();
    }

    private void updateInteriorDoorStyle() {
        if (world == null || world.isClient || world.getServer() == null || tardisId == null || interiorDoorPos == null) return;
        TardisRegistryState.Record record = TardisRegistryState.get(world.getServer()).get(tardisId);
        if (record == null) return;
        ServerWorld interior = TardisDimensionManager.getInterior(world.getServer(), record);
        if (interior != null && interior.getBlockEntity(interiorDoorPos) instanceof TardisInteriorDoorBlockEntity door) {
            door.setExteriorStyle(exteriorStyle);
        }
    }

    public boolean canAccess(UUID player) {
        return !locked || (owner != null && owner.equals(player));
    }

    public boolean canPilot(UUID player) {
        return owner != null && owner.equals(player);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        if (world != null && world.getServer() != null && tardisId != null) {
            TardisRegistryState.get(world.getServer()).updateLock(tardisId, locked);
        }
        markDirty();
    }

    public int addFuel(int amount) {
        int old = fuel;
        fuel = Math.min(MAX_FUEL, fuel + Math.max(0, amount));
        markDirty();
        return fuel - old;
    }

    public boolean consumeFuel(int amount) {
        if (fuel < amount) return false;
        fuel -= amount;
        markDirty();
        return true;
    }

    public boolean generateInterior(ServerWorld world) {
        if (interiorGenerated) {
            if (interiorDoorPos == null
                    || !(world.getBlockEntity(interiorDoorPos) instanceof TardisInteriorDoorBlockEntity)) {
                installInteriorDoor(world);
            }
            return true;
        }
        Identifier structureId = TardisInteriorCatalog.id(interiorStructure);
        if (structureId == null) return false;

        java.util.Optional<net.minecraft.structure.StructureTemplate> template =
                world.getStructureTemplateManager().getTemplate(structureId);
        if (template.isEmpty()) return false;

        interiorSize = template.get().getSize();
        BlockPos placement = interiorOrigin.add(-2, 0, -2);
        ChunkPos chunk = new ChunkPos(placement);
        // Structure placement needs the target chunk loaded. POST_TELEPORT is
        // an entity ticket and requires an Integer ticket key, so it must not
        // be used with a chunk-position long.
        world.getChunk(chunk.x, chunk.z);
        template.get().place(world, placement, placement,
                new net.minecraft.structure.StructurePlacementData(), world.getRandom(), 2);
        installConsole(world);
        installInteriorDoor(world);
        interiorGenerated = true;
        markDirty();
        return true;
    }

    public boolean replaceInterior(ServerWorld world, String structureName) {
        Identifier structureId = TardisInteriorCatalog.id(structureName);
        if (structureId == null) return false;
        java.util.Optional<net.minecraft.structure.StructureTemplate> template =
                world.getStructureTemplateManager().getTemplate(structureId);
        if (template.isEmpty()) return false;

        // Older TARDIS saves predate the stored structure footprint. Recover it
        // from the currently selected template before clearing the old interior.
        if (interiorSize.getX() <= 1 && interiorSize.getY() <= 1 && interiorSize.getZ() <= 1) {
            Identifier oldId = TardisInteriorCatalog.id(interiorStructure);
            if (oldId != null) {
                java.util.Optional<net.minecraft.structure.StructureTemplate> oldTemplate =
                        world.getStructureTemplateManager().getTemplate(oldId);
                oldTemplate.ifPresent(value -> interiorSize = value.getSize());
            }
        }
        if (interiorDoorPos != null) {
            world.setBlockState(interiorDoorPos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
            interiorDoorPos = null;
        }
        clearInterior(world);
        interiorStructure = structureName.toLowerCase(java.util.Locale.ROOT);
        interiorSize = template.get().getSize();
        BlockPos placement = interiorOrigin.add(-2, 0, -2);
        ChunkPos chunk = new ChunkPos(placement);
        // Ensure the target chunk is loaded before placing the structure.
        world.getChunk(chunk.x, chunk.z);
        template.get().place(world, placement, placement,
                new net.minecraft.structure.StructurePlacementData(), world.getRandom(), 2);
        installConsole(world);
        installInteriorDoor(world);
        interiorGenerated = true;
        markDirty();
        return true;
    }

    /** Places the physical police-box interior exit door. */
    private void installInteriorDoor(ServerWorld world) {
        BlockPos base = interiorOrigin.add(0, 1, -5);
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos candidate = base.add(dx, 0, dz);
                if (world.getBlockState(candidate).isAir()
                        && world.getBlockState(candidate.down()).isSolidBlock(world, candidate.down())) {
                    world.setBlockState(candidate, com.timelordmod.gallifrey.block.GallifreyModBlocks.TARDIS_INTERIOR_DOOR.getDefaultState(), 3);
                    if (world.getBlockEntity(candidate) instanceof TardisInteriorDoorBlockEntity door) {
                        door.setTardisId(tardisId);
                        door.setExteriorStyle(exteriorStyle);
                    }
                    interiorDoorPos = candidate.toImmutable();
                    markDirty();
                    return;
                }
            }
        }
    }

    /** Places the physical Hartnell TARDIS console in a clear central floor position. */
    private void installConsole(ServerWorld world) {
        BlockPos base = interiorOrigin.add(0, 1, 2);
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                BlockPos candidate = base.add(dx, 0, dz);
                if (world.getBlockState(candidate).isAir()
                        && world.getBlockState(candidate.down()).isSolidBlock(world, candidate.down())) {
                    world.setBlockState(candidate, com.timelordmod.gallifrey.block.GallifreyModBlocks.TARDIS_CONSOLE.getDefaultState(), 3);
                    consolePos = candidate.toImmutable();
                    markDirty();
                    return;
                }
            }
        }
    }

    private void clearInterior(ServerWorld world) {
        BlockPos placement = interiorOrigin.add(-2, 0, -2);
        int maxX = Math.max(1, interiorSize.getX());
        int maxY = Math.max(1, interiorSize.getY());
        int maxZ = Math.max(1, interiorSize.getZ());
        for (int x = -1; x < maxX + 1; x++) {
            for (int y = -1; y < maxY + 1; y++) {
                for (int z = -1; z < maxZ + 1; z++) {
                    world.setBlockState(placement.add(x, y, z), net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
                }
            }
        }
    }

    public void beginFlight(Identifier targetWorld, BlockPos targetPos, int rotation) {
        flightPending = true;
        flightTicks = FLIGHT_TIME;
        flightWorld = targetWorld;
        flightPos = targetPos.toImmutable();
        flightRotation = rotation;
        materializationTicks = 0;
        markDirty();
    }

    public boolean isFlightPending() { return flightPending; }

    public boolean tickFlight() {
        if (!flightPending) return false;
        flightTicks--;
        markDirty();
        return flightTicks <= 0;
    }

    public void cancelFlight() {
        flightPending = false;
        flightTicks = 0;
        flightWorld = null;
        flightPos = null;
        markDirty();
    }

    public void finishFlight() {
        flightPending = false;
        flightTicks = 0;
        flightWorld = null;
        flightPos = null;
        materializationTicks = MATERIALIZATION_TIME;
        markDirty();
    }

    public boolean tickMaterialization() {
        if (materializationTicks <= 0) return false;
        materializationTicks--;
        markDirty();
        return materializationTicks > 0;
    }

    public Identifier getFlightWorld() { return flightWorld; }
    public BlockPos getFlightPos() { return flightPos; }
    public int getFlightRotation() { return flightRotation; }
    public int getFlightTicks() { return flightTicks; }
    public int getFlightTime() { return FLIGHT_TIME; }
    public int getMaterializationTicks() { return materializationTicks; }
    public int getMaterializationTime() { return MATERIALIZATION_TIME; }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (tardisId != null) nbt.putUuid("TardisId", tardisId);
        if (owner != null) nbt.putUuid("Owner", owner);
        nbt.putBoolean("Locked", locked);
        nbt.putInt("Fuel", fuel);
        nbt.putLong("InteriorOrigin", interiorOrigin.asLong());
        nbt.putBoolean("InteriorGenerated", interiorGenerated);
        nbt.putString("InteriorStructure", interiorStructure);
        nbt.putString("ExteriorStyle", exteriorStyle);
        if (interiorDoorPos != null) nbt.putLong("InteriorDoorPos", interiorDoorPos.asLong());
        nbt.putInt("InteriorSizeX", interiorSize.getX());
        nbt.putInt("InteriorSizeY", interiorSize.getY());
        nbt.putInt("InteriorSizeZ", interiorSize.getZ());

        nbt.putBoolean("FlightPending", flightPending);
        nbt.putInt("FlightTicks", flightTicks);
        if (flightWorld != null) nbt.putString("FlightWorld", flightWorld.toString());
        if (flightPos != null) nbt.putLong("FlightPos", flightPos.asLong());
        nbt.putInt("FlightRotation", flightRotation);
        nbt.putInt("MaterializationTicks", materializationTicks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.containsUuid("TardisId")) tardisId = nbt.getUuid("TardisId");
        if (nbt.containsUuid("Owner")) owner = nbt.getUuid("Owner");
        locked = nbt.getBoolean("Locked");
        fuel = Math.max(0, Math.min(MAX_FUEL, nbt.getInt("Fuel")));
        if (nbt.contains("InteriorOrigin")) interiorOrigin = BlockPos.fromLong(nbt.getLong("InteriorOrigin"));
        interiorGenerated = nbt.getBoolean("InteriorGenerated");
        if (nbt.contains("InteriorStructure")) interiorStructure = nbt.getString("InteriorStructure");
        if (nbt.contains("ExteriorStyle")) {
            exteriorStyle = TardisExteriorCatalog.get(nbt.getString("ExteriorStyle")).id();
        } else if (nbt.contains("ExteriorVariant")) {
            exteriorStyle = TardisExteriorCatalog.migrateLegacyIndex(nbt.getInt("ExteriorVariant"));
        } else {
            exteriorStyle = "policebox";
        }
        if (nbt.contains("InteriorDoorPos")) interiorDoorPos = BlockPos.fromLong(nbt.getLong("InteriorDoorPos"));
        interiorSize = new Vec3i(Math.max(1, nbt.getInt("InteriorSizeX")), Math.max(1, nbt.getInt("InteriorSizeY")), Math.max(1, nbt.getInt("InteriorSizeZ")));

        flightPending = nbt.getBoolean("FlightPending");
        flightTicks = nbt.getInt("FlightTicks");
        if (nbt.contains("FlightWorld")) flightWorld = new Identifier(nbt.getString("FlightWorld"));
        if (nbt.contains("FlightPos")) flightPos = BlockPos.fromLong(nbt.getLong("FlightPos"));
        flightRotation = nbt.getInt("FlightRotation");
        materializationTicks = nbt.getInt("MaterializationTicks");
    }
}
