package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.tardis.TardisExteriorCatalog;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

/** The in-room police-box door used as the reliable TARDIS exit control. */
public class TardisInteriorDoorBlockEntity extends BlockEntity {
    private UUID tardisId;
    private String exteriorStyle = "policebox";

    public TardisInteriorDoorBlockEntity(BlockPos pos, BlockState state) {
        super(GallifreyModBlockEntities.TARDIS_INTERIOR_DOOR, pos, state);
    }

    public UUID getTardisId() { return tardisId; }

    public void setTardisId(UUID id) {
        tardisId = id;
        markDirty();
    }

    public String getExteriorStyle() {
        return TardisExteriorCatalog.get(exteriorStyle).id();
    }

    public void setExteriorStyle(String style) {
        exteriorStyle = TardisExteriorCatalog.get(style).id();
        markDirty();
        if (world != null) world.updateListeners(pos, getCachedState(), getCachedState(), 3);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (tardisId != null) nbt.putUuid("TardisId", tardisId);
        nbt.putString("ExteriorStyle", exteriorStyle);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.containsUuid("TardisId")) tardisId = nbt.getUuid("TardisId");
        exteriorStyle = TardisExteriorCatalog.get(nbt.getString("ExteriorStyle")).id();
    }
}
