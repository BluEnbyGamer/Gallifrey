package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.sonic.SonicHandler;
import com.timelordmod.gallifrey.sonic.SonicMode;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class SonicScrewdriver extends Item {

    private static final double SONIC_RANGE = 16.0D;

    public static final int MAX_POWER = 100;

    private static final String POWER_KEY = "SonicPower";
    private static final String MODE_KEY = "SonicMode";

    public SonicScrewdriver(Settings settings) {
        super(settings);
    }

    // =========================================================
    // POWER
    // =========================================================

    public static int getPower(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(POWER_KEY);
    }

    public static void setPower(ItemStack stack, int power) {

        power = Math.max(
                0,
                Math.min(MAX_POWER, power)
        );

        stack.getOrCreateNbt().putInt(
                POWER_KEY,
                power
        );
    }

    public static void consumePower(ItemStack stack) {

        int power = getPower(stack);

        if (power <= 0) {
            return;
        }

        setPower(
                stack,
                power - 1
        );
    }

    // =========================================================
    // MODEL STATE
    // =========================================================

    /*
     * The Sonic does NOT have a manually controlled ON/OFF state.
     *
     * The model is considered:
     *
     * ON  = has power
     * OFF = power is zero
     *
     * This means the Sonic automatically appears "off"
     * when its power runs out.
     */

    public static boolean isOn(ItemStack stack) {
        return getPower(stack) > 0;
    }

    // =========================================================
    // MODE
    // =========================================================

    public static SonicMode getMode(ItemStack stack) {

        int mode = stack
                .getOrCreateNbt()
                .getInt(MODE_KEY);

        if (mode == 1) {
            return SonicMode.ACTIVATE;
        }

        return SonicMode.SCAN;
    }

    public static void setMode(
            ItemStack stack,
            SonicMode mode
    ) {

        int value =
                mode == SonicMode.ACTIVATE
                        ? 1
                        : 0;

        stack.getOrCreateNbt().putInt(
                MODE_KEY,
                value
        );
    }

    public static void toggleMode(ItemStack stack) {

        if (getMode(stack) == SonicMode.SCAN) {

            setMode(
                    stack,
                    SonicMode.ACTIVATE
            );

        } else {

            setMode(
                    stack,
                    SonicMode.SCAN
            );
        }
    }

    // =========================================================
    // INITIAL DATA
    // =========================================================

    @Override
    public void inventoryTick(
            ItemStack stack,
            World world,
            Entity entity,
            int slot,
            boolean selected
    ) {

        super.inventoryTick(
                stack,
                world,
                entity,
                slot,
                selected
        );

        /*
         * Newly-created Sonic Screwdrivers start with:
         *
         * Power = 100
         * Mode = SCAN
         */

        if (!stack.getOrCreateNbt().contains(POWER_KEY)) {

            setPower(
                    stack,
                    MAX_POWER
            );
        }

        if (!stack.getOrCreateNbt().contains(MODE_KEY)) {

            setMode(
                    stack,
                    SonicMode.SCAN
            );
        }
    }

    // =========================================================
    // TARGETING
    // =========================================================

    private static BlockHitResult getBlockTarget(
            PlayerEntity player
    ) {

        Vec3d start =
                player.getCameraPosVec(1.0F);

        Vec3d direction =
                player.getRotationVec(1.0F);

        Vec3d end =
                start.add(
                        direction.multiply(
                                SONIC_RANGE
                        )
                );

        return player.getWorld().raycast(
                new RaycastContext(
                        start,
                        end,
                        RaycastContext.ShapeType.OUTLINE,
                        RaycastContext.FluidHandling.NONE,
                        player
                )
        );
    }

    private static EntityHitResult getEntityTarget(
            PlayerEntity player
    ) {

        World world =
                player.getWorld();

        Vec3d start =
                player.getCameraPosVec(1.0F);

        Vec3d direction =
                player.getRotationVec(1.0F);

        Vec3d end =
                start.add(
                        direction.multiply(
                                SONIC_RANGE
                        )
                );

        Box searchBox =
                player
                        .getBoundingBox()
                        .stretch(
                                direction.multiply(
                                        SONIC_RANGE
                                )
                        )
                        .expand(1.0D);

        List<Entity> entities =
                world.getOtherEntities(
                        player,
                        searchBox,
                        entity ->
                                entity.isAlive()
                                        && entity.isAttackable()
                );

        Entity closestEntity = null;

        double closestDistance =
                SONIC_RANGE * SONIC_RANGE;

        for (Entity entity : entities) {

            Box box =
                    entity
                            .getBoundingBox()
                            .expand(0.3D);

            Optional<Vec3d> hit =
                    box.raycast(
                            start,
                            end
                    );

            if (hit.isPresent()) {

                double distance =
                        start.squaredDistanceTo(
                                hit.get()
                        );

                if (distance < closestDistance) {

                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity == null) {
            return null;
        }

        return new EntityHitResult(
                closestEntity
        );
    }

    private static HitResult getSonicTarget(
            PlayerEntity player
    ) {

        BlockHitResult blockHit =
                getBlockTarget(player);

        EntityHitResult entityHit =
                getEntityTarget(player);

        double blockDistance =
                SONIC_RANGE * SONIC_RANGE;

        double entityDistance =
                SONIC_RANGE * SONIC_RANGE;

        if (
                blockHit.getType()
                        != HitResult.Type.MISS
        ) {

            blockDistance =
                    player
                            .getCameraPosVec(1.0F)
                            .squaredDistanceTo(
                                    blockHit.getPos()
                            );
        }

        if (entityHit != null) {

            entityDistance =
                    player
                            .getCameraPosVec(1.0F)
                            .squaredDistanceTo(
                                    entityHit
                                            .getEntity()
                                            .getPos()
                            );
        }

        if (
                entityHit != null
                        && entityDistance < blockDistance
        ) {

            return entityHit;
        }

        return blockHit;
    }

    // =========================================================
    // USE
    // =========================================================

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity player,
            Hand hand
    ) {

        ItemStack stack =
                player.getStackInHand(hand);

        // =====================================================
        // SNEAK + RIGHT CLICK
        // CHANGE MODE
        // =====================================================

        if (player.isSneaking()) {

            if (!world.isClient) {

                toggleMode(stack);

                SonicMode mode =
                        getMode(stack);

                player.sendMessage(
                        Text.literal(
                                "§bSONIC MODE: §e"
                                        + mode.getDisplayName()
                        ),
                        true
                );
            }

            return TypedActionResult.success(
                    stack,
                    world.isClient
            );
        }

        // =====================================================
        // NO POWER
        // =====================================================

        if (getPower(stack) <= 0) {

            if (!world.isClient) {

                player.sendMessage(
                        Text.literal(
                                "§cSONIC: Power depleted."
                        ),
                        true
                );
            }

            return TypedActionResult.success(
                    stack,
                    world.isClient
            );
        }

        // =====================================================
        // USE SONIC
        // =====================================================

        if (!world.isClient) {

            HitResult target =
                    getSonicTarget(player);

            SonicMode mode =
                    getMode(stack);

            SonicHandler.use(
                    player,
                    world,
                    target,
                    mode
            );

            // Every Sonic use consumes one power.
            consumePower(stack);

            player.sendMessage(
                    Text.literal(
                            "§bSONIC POWER: §f"
                                    + getPower(stack)
                                    + "§7/§f"
                                    + MAX_POWER
                    ),
                    true
            );
        }

        return TypedActionResult.success(
                stack,
                world.isClient
        );
    }
}