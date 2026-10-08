package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.tardis.TardisMonitorNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** A functional TARDIS console monitor. Clicking it opens the TARDIS control panel. */
public class TardisMonitorBlock extends Block {
    public TardisMonitorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;

        if (!TardisMonitorNetworking.openFor(serverPlayer)) {
            return ActionResult.FAIL;
        }
        return ActionResult.SUCCESS;
    }
}
