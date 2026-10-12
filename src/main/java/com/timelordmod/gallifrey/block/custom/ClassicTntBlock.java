package com.timelordmod.gallifrey.block.custom;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Classic TNT with explicit support for the mod's Classic Flint and Steel. */
public class ClassicTntBlock extends TntBlock {
    public ClassicTntBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(GallifreyModItems.CLASSIC_FLINT_AND_STEEL)) {
            if (!world.isClient) {
                TntBlock.prime(world, pos, player);
                world.removeBlock(pos, false);
                if (!player.getAbilities().creativeMode) {
                    stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
