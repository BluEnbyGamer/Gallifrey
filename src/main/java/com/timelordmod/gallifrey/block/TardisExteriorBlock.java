package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class TardisExteriorBlock extends Block implements BlockEntityProvider {

    public TardisExteriorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TardisExteriorBlockEntity(pos, state);
    }
}

