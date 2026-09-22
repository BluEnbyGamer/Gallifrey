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

        ItemStack heldStack = player.getStackInHand(hand);

        // -----------------------------------------------------
        // Client
        // -----------------------------------------------------

        if (world.isClient) {
            return heldStack.getItem() instanceof SonicScrewdriver
                    ? ActionResult.SUCCESS
                    : ActionResult.PASS;
        }

        // -----------------------------------------------------
        // Must be holding a Sonic
        // -----------------------------------------------------

        if (!(heldStack.getItem() instanceof SonicScrewdriver)) {
            return ActionResult.PASS;
        }

        // -----------------------------------------------------
        // Recharge Sonic to full
        // -----------------------------------------------------

        SonicScrewdriver.recharge(
                heldStack,
                SonicScrewdriver.MAX_POWER
        );

        // -----------------------------------------------------
        // Change casing
        // -----------------------------------------------------

        SonicScrewdriver.changeCasing(heldStack);

        // -----------------------------------------------------
        // Tell the client the item changed
        // -----------------------------------------------------

        player.setStackInHand(hand, heldStack);

        return ActionResult.SUCCESS;
    }
}
