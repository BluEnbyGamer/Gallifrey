package com.timelordmod.gallifrey.block.custom;

import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class SonicWorkshopBlock extends Block {

    public SonicWorkshopBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(
            BlockState state,
            World world,
            net.minecraft.util.math.BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {

        ItemStack stack =
                player.getStackInHand(hand);

        if (stack.getItem()
                instanceof SonicScrewdriver) {

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}



