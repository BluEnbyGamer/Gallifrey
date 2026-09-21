package com.timelordmod.gallifrey.sonic;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
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

            case UNLOCK -> unlock(
                    player,
                    world,
                    target
            );

            case DISABLE -> disable(
                    player,
                    world,
                    target
            );

            case REPAIR -> repair(
                    player,
                    world,
                    target
            );

            case REMOTE -> remote(
                    player,
                    world,
                    target
            );
        }
    }

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

    /**
     * Scans the targeted block or entity and reports
     * exactly what the Sonic Screwdriver detected.
     */
    private static void scan(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        if (target.getType() == HitResult.Type.BLOCK) {

            BlockHitResult blockHit = (BlockHitResult) target;

            BlockState state = world.getBlockState(
                    blockHit.getBlockPos()
            );

            Identifier blockId = Registries.BLOCK.getId(
                    state.getBlock()
            );

            String blockName = state
                    .getBlock()
                    .getName()
                    .getString();

            player.sendMessage(
                    Text.literal(
                            "§bSONIC SCAN\n" +
                                    "§fTarget: §e" + blockName + "\n" +
                                    "§fID: §7" + blockId
                    ),
                    false
            );

            return;
        }

        if (target.getType() == HitResult.Type.ENTITY) {

            EntityHitResult entityHit =
                    (EntityHitResult) target;

            Entity entity = entityHit.getEntity();

            Identifier entityId =
                    Registries.ENTITY_TYPE.getId(
                            entity.getType()
                    );

            String entityName =
                    entity.getName().getString();

            if (entity instanceof LivingEntity livingEntity) {

                float health =
                        livingEntity.getHealth();

                float maxHealth =
                        livingEntity.getMaxHealth();

                player.sendMessage(
                        Text.literal(
                                "§bSONIC SCAN\n" +
                                        "§fTarget: §e" + entityName + "\n" +
                                        "§fType: §7" + entityId + "\n" +
                                        "§fHealth: §a" +
                                        String.format("%.1f", health) +
                                        " §7/ §a" +
                                        String.format("%.1f", maxHealth)
                        ),
                        false
                );

            } else {

                player.sendMessage(
                        Text.literal(
                                "§bSONIC SCAN\n" +
                                        "§fTarget: §e" + entityName + "\n" +
                                        "§fType: §7" + entityId
                        ),
                        false
                );
            }

            return;
        }

        player.sendMessage(
                Text.literal(
                        "§7SONIC SCAN: Nothing detected."
                ),
                true
        );
    }

    private static void unlock(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        player.sendMessage(
                Text.literal("§bSONIC: Unlock mode."),
                true
        );
    }

    private static void disable(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        player.sendMessage(
                Text.literal("§bSONIC: Disable mode."),
                true
        );
    }

    private static void repair(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        player.sendMessage(
                Text.literal("§bSONIC: Repair mode."),
                true
        );
    }

    private static void remote(
            PlayerEntity player,
            World world,
            HitResult target
    ) {

        player.sendMessage(
                Text.literal("§bSONIC: Remote mode."),
                true
        );
    }
}