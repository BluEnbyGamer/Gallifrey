package com.timelordmod.gallifrey.sonic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.block.TrapdoorBlock;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.TntEntity;

import net.minecraft.registry.Registries;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.minecraft.text.Text;

import net.minecraft.util.Identifier;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;


public class SonicHandler {

    public static void use(
            PlayerEntity player,
            World world,
            HitResult target,
            SonicMode mode
    ) {

        playSonicSound(player, world);

        switch (mode) {

            case SCAN -> scan(
                    player,
                    world,
                    target
            );

            case ACTIVATE -> activate(
                    player,
                    world,
                    target
            );
        }
    }


    // =========================================================
    // SONIC SOUND
    // =========================================================

    private static void playSonicSound(
            PlayerEntity player,
            World world
    ) {

        world.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                SoundCategory.PLAYERS,
                1.0F,
                1.5F
        );
    }


    // =========================================================
    // SCAN
    // =========================================================

    private static void scan(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        // -----------------------------------------------------
        // BLOCK
        // -----------------------------------------------------

        if (target.getType() == HitResult.Type.BLOCK) {

            BlockHitResult blockHit =
                    (BlockHitResult) target;

            BlockState state =
                    world.getBlockState(
                            blockHit.getBlockPos()
                    );

            Identifier blockId =
                    Registries.BLOCK.getId(
                            state.getBlock()
                    );

            String blockName =
                    state.getBlock()
                            .getName()
                            .getString();

            player.sendMessage(
                    Text.literal(
                            "§bSONIC SCAN\n" +
                                    "§fTarget: §e" +
                                    blockName +
                                    "\n" +
                                    "§fID: §7" +
                                    blockId
                    ),
                    false
            );

            return;
        }


        // -----------------------------------------------------
        // ENTITY
        // -----------------------------------------------------

        if (target.getType() == HitResult.Type.ENTITY) {

            EntityHitResult entityHit =
                    (EntityHitResult) target;

            Entity entity =
                    entityHit.getEntity();

            Identifier entityId =
                    Registries.ENTITY_TYPE.getId(
                            entity.getType()
                    );

            String entityName =
                    entity.getName()
                            .getString();


            if (entity instanceof LivingEntity livingEntity) {

                float health =
                        livingEntity.getHealth();

                float maxHealth =
                        livingEntity.getMaxHealth();

                player.sendMessage(
                        Text.literal(
                                "§bSONIC SCAN\n" +
                                        "§fTarget: §e" +
                                        entityName +
                                        "\n" +
                                        "§fType: §7" +
                                        entityId +
                                        "\n" +
                                        "§fHealth: §a" +
                                        String.format(
                                                "%.1f",
                                                health
                                        ) +
                                        " §7/ §a" +
                                        String.format(
                                                "%.1f",
                                                maxHealth
                                        )
                        ),
                        false
                );

            } else {

                player.sendMessage(
                        Text.literal(
                                "§bSONIC SCAN\n" +
                                        "§fTarget: §e" +
                                        entityName +
                                        "\n" +
                                        "§fType: §7" +
                                        entityId
                        ),
                        false
                );
            }

            return;
        }


        // -----------------------------------------------------
        // NOTHING
        // -----------------------------------------------------

        player.sendMessage(
                Text.literal(
                        "§7SONIC SCAN: Nothing detected."
                ),
                true
        );
    }


    // =========================================================
    // ACTIVATE
    // =========================================================

    private static void activate(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        if (target.getType() != HitResult.Type.BLOCK) {

            player.sendMessage(
                    Text.literal(
                            "§cSONIC: No compatible technology detected."
                    ),
                    true
            );

            return;
        }


        BlockHitResult blockHit =
                (BlockHitResult) target;

        BlockPos pos =
                blockHit.getBlockPos();

        BlockState state =
                world.getBlockState(pos);

        Block block =
                state.getBlock();


        // =====================================================
        // LEVER
        // =====================================================

        if (block instanceof LeverBlock) {

            boolean powered =
                    state.get(LeverBlock.POWERED);

            world.setBlockState(
                    pos,
                    state.with(
                            LeverBlock.POWERED,
                            !powered
                    ),
                    Block.NOTIFY_ALL
            );

            player.sendMessage(
                    Text.literal(
                            powered
                                    ? "§bSONIC: Lever deactivated."
                                    : "§bSONIC: Lever activated."
                    ),
                    true
            );

            return;
        }


        // =====================================================
        // BUTTON
        // =====================================================

        if (block instanceof ButtonBlock button) {

            button.powerOn(
                    state,
                    world,
                    pos
            );

            player.sendMessage(
                    Text.literal(
                            "§bSONIC: Button activated."
                    ),
                    true
            );

            return;
        }


        // =====================================================
        // IRON DOOR
        // =====================================================

        if (block instanceof DoorBlock) {

            Identifier doorId =
                    Registries.BLOCK.getId(block);

            if (doorId.equals(
                    new Identifier(
                            "minecraft",
                            "iron_door"
                    )
            )) {

                boolean open =
                        state.get(DoorBlock.OPEN);

                world.setBlockState(
                        pos,
                        state.with(
                                DoorBlock.OPEN,
                                !open
                        ),
                        Block.NOTIFY_ALL
                );

                player.sendMessage(
                        Text.literal(
                                open
                                        ? "§bSONIC: Door closed."
                                        : "§bSONIC: Door opened."
                        ),
                        true
                );

                return;
            }
        }


        // =====================================================
        // IRON TRAPDOOR
        // =====================================================

        if (block instanceof TrapdoorBlock) {

            Identifier trapdoorId =
                    Registries.BLOCK.getId(block);

            if (trapdoorId.equals(
                    new Identifier(
                            "minecraft",
                            "iron_trapdoor"
                    )
            )) {

                boolean open =
                        state.get(TrapdoorBlock.OPEN);

                world.setBlockState(
                        pos,
                        state.with(
                                TrapdoorBlock.OPEN,
                                !open
                        ),
                        Block.NOTIFY_ALL
                );

                player.sendMessage(
                        Text.literal(
                                open
                                        ? "§bSONIC: Trapdoor closed."
                                        : "§bSONIC: Trapdoor opened."
                        ),
                        true
                );

                return;
            }
        }


        // =====================================================
        // REDSTONE LAMP
        // =====================================================

        if (block instanceof RedstoneLampBlock) {

            boolean lit =
                    state.get(RedstoneLampBlock.LIT);

            world.setBlockState(
                    pos,
                    state.with(
                            RedstoneLampBlock.LIT,
                            !lit
                    ),
                    Block.NOTIFY_ALL
            );

            player.sendMessage(
                    Text.literal(
                            lit
                                    ? "§bSONIC: Lamp deactivated."
                                    : "§bSONIC: Lamp activated."
                    ),
                    true
            );

            return;
        }


        // =====================================================
        // NOTE BLOCK
        // =====================================================

        if (block instanceof NoteBlock) {

            world.addSyncedBlockEvent(
                    pos,
                    block,
                    0,
                    0
            );

            player.sendMessage(
                    Text.literal(
                            "§bSONIC: Note block activated."
                    ),
                    true
            );

            return;
        }


        // =====================================================
        // TNT
        // =====================================================

        if (block instanceof TntBlock) {

            igniteTnt(
                    world,
                    pos
            );

            player.sendMessage(
                    Text.literal(
                            "§cSONIC: TNT activated!"
                    ),
                    true
            );

            return;
        }


        // =====================================================
        // UNSUPPORTED
        // =====================================================

        player.sendMessage(
                Text.literal(
                        "§7SONIC: Target cannot be activated."
                ),
                true
        );
    }


    // =========================================================
    // TNT
    // =========================================================

    private static void igniteTnt(
            World world,
            BlockPos pos
    ) {

        world.removeBlock(
                pos,
                false
        );

        TntEntity tnt =
                new TntEntity(
                        world,
                        pos.getX() + 0.5D,
                        pos.getY(),
                        pos.getZ() + 0.5D,
                        null
                );

        tnt.setFuse(80);

        world.spawnEntity(tnt);

        world.playSound(
                null,
                pos,
                SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.BLOCKS,
                1.0F,
                1.0F
        );
    }
}