package com.timelordmod.gallifrey.block.custom;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicWorkshopBlock extends Block
        implements BlockEntityProvider {

    public SonicWorkshopBlock(Settings settings) {
        super(settings);
    }

// =========================================================
// BLOCK ENTITY
// =========================================================

    @Override
    public BlockEntity createBlockEntity(
            BlockPos pos,
            BlockState state
    ) {

        return new SonicWorkshopBlockEntity(
                pos,
                state
        );
    }

// =========================================================
// TICKER
// =========================================================

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type
    ) {

        if (type == GallifreyModBlockEntities.SONIC_WORKSHOP_BLOCK_ENTITY) {

            return (world1, pos, state1, blockEntity) ->
                    SonicWorkshopBlockEntity.tick(
                            world1,
                            pos,
                            state1,
                            (SonicWorkshopBlockEntity) blockEntity
                    );
        }

        return null;
    }

// =========================================================
// RIGHT CLICK
// =========================================================

    @Override
    public ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {

        // Client handles the interaction visually.
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        BlockEntity blockEntity =
                world.getBlockEntity(pos);

        if (!(blockEntity instanceof SonicWorkshopBlockEntity workshop)) {
            return ActionResult.PASS;
        }

        ItemStack heldStack =
                player.getStackInHand(hand);

        // =====================================================
        // TAKE SONIC OUT
        // =====================================================

        /*
         * IMPORTANT:
         *
         * This happens FIRST.
         *
         * Therefore you can take the Sonic out at ANY
         * charge level.
         *
         * It does NOT have to be fully charged.
         */

        if (heldStack.isEmpty()) {

            ItemStack sonic =
                    workshop.getSonic();

            if (sonic.isEmpty()) {
                return ActionResult.PASS;
            }

            player.setStackInHand(
                    hand,
                    sonic
            );

            workshop.setSonic(
                    ItemStack.EMPTY
            );

            world.updateListeners(
                    pos,
                    state,
                    state,
                    3
            );

            return ActionResult.SUCCESS;
        }

        // =====================================================
        // PUT SONIC IN
        // =====================================================

        if (heldStack.getItem()
                instanceof SonicScrewdriver) {

            // Workshop already contains a Sonic.
            if (!workshop.getSonic().isEmpty()) {
                return ActionResult.PASS;
            }

            /*
             * Make a copy so the workshop owns its own
             * ItemStack.
             */
            ItemStack sonic =
                    heldStack.copy();

            // Only put ONE Sonic into the workshop.
            sonic.setCount(1);

            workshop.setSonic(
                    sonic
            );

            // Remove one Sonic from the player's hand.
            heldStack.decrement(1);

            world.updateListeners(
                    pos,
                    state,
                    state,
                    3
            );

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }


}




