package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicWorkshopBlockEntity extends BlockEntity {

    public SonicWorkshopBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                GallifreyModBlockEntities.SONIC_WORKSHOP_BLOCK_ENTITY,
                pos,
                state
        );
    }

    // =========================================================
    // TICK
    // =========================================================

    public static void tick(
            World world,
            BlockPos pos,
            BlockState state,
            SonicWorkshopBlockEntity blockEntity
    ) {
        // Nothing to do.
        //
        // The workshop no longer stores a Sonic.
        // Recharging happens instantly when the player
        // interacts with the workbench.
    }
}


