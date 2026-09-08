package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TardisExteriorBlock extends Block implements BlockEntityProvider {

    // 8 steps around the compass, 45 degrees apart: 0=south, 2=west, 4=north, 6=east (matches vanilla sign convention)
    public static final IntProperty ROTATION = IntProperty.of("rotation", 0, 7);

    public TardisExteriorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(ROTATION, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        float yaw = ctx.getPlayerYaw();
        int rotation = MathHelper.floor((double) ((yaw + 180.0F) * 8.0F / 360.0F) + 0.5D) & 7;
        return this.getDefaultState().with(ROTATION, rotation);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TardisExteriorBlockEntity(pos, state);
    }
}

