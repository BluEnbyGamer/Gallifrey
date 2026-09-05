package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class TardisExteriorBlockEntity extends BlockEntity {

    public TardisExteriorBlockEntity(BlockPos pos, BlockState state) {
        super(GallifreyModBlockEntities.TARDIS_EXTERIOR, pos, state);
    }
}
