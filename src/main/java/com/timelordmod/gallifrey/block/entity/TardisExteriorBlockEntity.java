package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.tardis.TardisDimensionManager;
import com.timelordmod.gallifrey.tardis.TardisRegistryState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.UUID;

/** Persistent state for one physical TARDIS exterior. */
public class TardisExteriorBlockEntity extends BlockEntity {
    public static final int MAX_FUEL = 1000;
    public static final int FLIGHT_COST = 10;
    private static final int FLIGHT_TIME = 60;

    private UUID tardisId;
    private UUID owner;
    private boolean locked;
    private int fuel = 100;
    private BlockPos interiorOrigin = new BlockPos(0, 64, 0);
    private boolean interiorGenerated;

    private boolean flightPending;
    private int flightTicks;
    private Identifier flightWorld;
    private BlockPos flightPos;
    private int flightRotation;

    public TardisExteriorBlockEntity(BlockPos pos, BlockState state) {
        super(GallifreyModBlockEntities.TARDIS_EXTERIOR, pos, state);
    }

    public void ensureInitialized(ServerPlayerEntity firstOwner) {
        if (tardisId == null) {
            tardisId = UUID.randomUUID();
            owner = firstOwner.getUuid();
            TardisRegistryState registry = TardisRegistryState.get(firstOwner.getServer());
            int slot = registry.allocateInteriorSlot();
            int x = (slot % 100) * 128;
            int z = (slot / 100) * 128;
            interiorOrigin = new BlockPos(x, 64, z);
            registry.register(tardisId, owner, firstOwner.getServerWorld(), pos.asLong(),
                    interiorOrigin.asLong(), locked);
            markDirty();
        }
    }

    public UUID getTardisId() { return tardisId; }
    public UUID getOwner() { return owner; }
    public boolean isLocked() { return locked; }
    public int getFuel() { return fuel; }
    public BlockPos getInteriorOrigin() { return interiorOrigin; }

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

    public void generateInterior(ServerWorld world) {
        if (interiorGenerated) return;

        Identifier structureId = new Identifier("gallifrey", "interiors/tardis_platform");
        java.util.Optional<net.minecraft.structure.StructureTemplate> template =
                world.getStructureTemplateManager().getTemplate(structureId);
        if (template.isEmpty()) return;

        BlockPos placement = interiorOrigin.add(-2, 0, -2);
        ChunkPos chunk = new ChunkPos(placement);
        world.getChunkManager().addTicket(net.minecraft.server.world.ChunkTicketType.POST_TELEPORT,
                chunk, 2, chunk.getStartPos().asLong());

        template.get().place(world, placement, placement,
                new net.minecraft.structure.StructurePlacementData(), world.getRandom(), 2);
        interiorGenerated = true;
        markDirty();
    }

    public void beginFlight(Identifier targetWorld, BlockPos targetPos, int rotation) {
        flightPending = true;
        flightTicks = FLIGHT_TIME;
        flightWorld = targetWorld;
        flightPos = targetPos.toImmutable();
        flightRotation = rotation;
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
        cancelFlight();
    }

    public Identifier getFlightWorld() { return flightWorld; }
    public BlockPos getFlightPos() { return flightPos; }
    public int getFlightRotation() { return flightRotation; }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (tardisId != null) nbt.putUuid("TardisId", tardisId);
        if (owner != null) nbt.putUuid("Owner", owner);
        nbt.putBoolean("Locked", locked);
        nbt.putInt("Fuel", fuel);
        nbt.putLong("InteriorOrigin", interiorOrigin.asLong());
        nbt.putBoolean("InteriorGenerated", interiorGenerated);

        nbt.putBoolean("FlightPending", flightPending);
        nbt.putInt("FlightTicks", flightTicks);
        if (flightWorld != null) nbt.putString("FlightWorld", flightWorld.toString());
        if (flightPos != null) nbt.putLong("FlightPos", flightPos.asLong());
        nbt.putInt("FlightRotation", flightRotation);
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

        flightPending = nbt.getBoolean("FlightPending");
        flightTicks = nbt.getInt("FlightTicks");
        if (nbt.contains("FlightWorld")) flightWorld = new Identifier(nbt.getString("FlightWorld"));
        if (nbt.contains("FlightPos")) flightPos = BlockPos.fromLong(nbt.getLong("FlightPos"));
        flightRotation = nbt.getInt("FlightRotation");
    }
}
