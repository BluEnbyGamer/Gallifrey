package com.timelordmod.gallifrey.block.entity;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicWorkshopBlockEntity extends BlockEntity {

    private ItemStack sonic = ItemStack.EMPTY;

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
    // SONIC
    // =========================================================

    public ItemStack getSonic() {
        return sonic;
    }

    public void setSonic(ItemStack stack) {

        sonic = stack;

        markDirty();
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

        if (world.isClient) {
            return;
        }

        if (blockEntity.sonic.isEmpty()) {
            return;
        }

        if (!(blockEntity.sonic.getItem()
                instanceof SonicScrewdriver)) {

            return;
        }

        // Recharge every 10 ticks = 0.5 seconds
        if (world.getTime() % 10 == 0) {

            int power =
                    SonicScrewdriver.getPower(
                            blockEntity.sonic
                    );

            if (power < SonicScrewdriver.MAX_POWER) {

                SonicScrewdriver.recharge(
                        blockEntity.sonic,
                        1
                );

                blockEntity.markDirty();
            }
        }
    }

    // =========================================================
    // SAVE
    // =========================================================

    @Override
    public void writeNbt(
            NbtCompound nbt
    ) {

        super.writeNbt(nbt);

        if (!sonic.isEmpty()) {

            nbt.put(
                    "Sonic",
                    sonic.writeNbt(
                            new NbtCompound()
                    )
            );
        }
    }

    // =========================================================
    // LOAD
    // =========================================================

    @Override
    public void readNbt(
            NbtCompound nbt
    ) {

        super.readNbt(nbt);

        if (nbt.contains("Sonic")) {

            sonic = ItemStack.fromNbt(
                    nbt.getCompound("Sonic")
            );

        } else {

            sonic = ItemStack.EMPTY;
        }
    }
}

