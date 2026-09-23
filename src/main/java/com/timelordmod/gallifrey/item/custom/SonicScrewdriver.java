package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.GallifreySounds;
import com.timelordmod.gallifrey.sonic.SonicCasing;
import com.timelordmod.gallifrey.sonic.SonicHandler;
import com.timelordmod.gallifrey.sonic.SonicMode;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
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

    // =========================================================
    // CONSTANTS
    // =========================================================

    private static final double SONIC_RANGE = 16.0D;

    public static final int MAX_POWER = 100;

    private static final String POWER_KEY = "SonicPower";
    private static final String MODE_KEY = "SonicMode";
    private static final String CASING_KEY = "SonicCasing";

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SonicScrewdriver(Settings settings) {
        super(settings);
    }

    // =========================================================
    // POWER
    // =========================================================

    public static int getPower(ItemStack stack) {
        return stack
                .getOrCreateNbt()
                .getInt(POWER_KEY);
    }

    public static void setPower(
            ItemStack stack,
            int power
    ) {

        power = Math.max(
                0,
                Math.min(
                        MAX_POWER,
                        power
                )
        );

        stack
                .getOrCreateNbt()
                .putInt(
                        POWER_KEY,
                        power
                );
    }

    public static void consumePower(
            ItemStack stack
    ) {

        int power = getPower(stack);

        if (power <= 0) {
            return;
        }

        setPower(
                stack,
                power - 1
        );
    }

    public static void recharge(
            ItemStack stack,
            int amount
    ) {

        int power = getPower(stack);

        if (power >= MAX_POWER) {
            return;
        }

        setPower(
                stack,
                power + amount
        );
    }

    // =========================================================
    // CASING
    // =========================================================

    public static SonicCasing getCasing(
            ItemStack stack
    ) {

        String casingId =
                stack
                        .getOrCreateNbt()
                        .getString(CASING_KEY);

        return SonicCasing.fromId(
                casingId
        );
    }

    public static void setCasing(
            ItemStack stack,
            SonicCasing casing
    ) {

        stack
                .getOrCreateNbt()
                .putString(
                        CASING_KEY,
                        casing.getId()
                );
    }

    // =========================================================
    // CHANGE CASING
    // =========================================================

    public static void changeCasing(
            ItemStack stack
    ) {

        SonicCasing currentCasing =
                getCasing(stack);

        SonicCasing[] casings =
                SonicCasing.values();

        int currentIndex = 0;

        for (int i = 0; i < casings.length; i++) {

            if (casings[i] == currentCasing) {
                currentIndex = i;
                break;
            }
        }

        int nextIndex =
                (currentIndex + 1)
                        % casings.length;

        setCasing(
                stack,
                casings[nextIndex]
        );
    }

    // =========================================================
    // ACTIVE STATE
    // =========================================================

    public static boolean isOn(
            ItemStack stack
    ) {

        return getPower(stack) > 0;
    }

    // =========================================================
    // MODE
    // =========================================================

    public static SonicMode getMode(
            ItemStack stack
    ) {

        int mode =
                stack
                        .getOrCreateNbt()
                        .getInt(MODE_KEY);

        SonicMode[] modes = SonicMode.values();
        if (mode < 0 || mode >= modes.length) {
            return SonicMode.INTERACTION;
        }
        return modes[mode];
    }

    public static void setMode(
            ItemStack stack,
            SonicMode mode
    ) {
        stack.getOrCreateNbt().putInt(MODE_KEY, mode.ordinal());
    }

    public static void toggleMode(ItemStack stack) {
        SonicMode[] modes = SonicMode.values();
        SonicMode current = getMode(stack);
        setMode(stack, modes[(current.ordinal() + 1) % modes.length]);
    }

    // =========================================================
    // INVENTORY TICK
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

        // -----------------------------------------------------
        // INITIAL POWER
        // -----------------------------------------------------

        if (
                !stack
                        .getOrCreateNbt()
                        .contains(POWER_KEY)
        ) {

            setPower(
                    stack,
                    MAX_POWER
            );
        }

        // -----------------------------------------------------
        // INITIAL MODE
        // -----------------------------------------------------

        if (
                !stack
                        .getOrCreateNbt()
                        .contains(MODE_KEY)
        ) {

            setMode(
                    stack,
                    SonicMode.INTERACTION
            );
        }

        // -----------------------------------------------------
        // INITIAL CASING
        // -----------------------------------------------------

        if (
                !stack
                        .getOrCreateNbt()
                        .contains(CASING_KEY)
        ) {

            setCasing(
                    stack,
                    SonicCasing.THIRD_DOCTOR
            );
        }
    }

    // =========================================================
    // BLOCK TARGET
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

        return player
                .getWorld()
                .raycast(
                        new RaycastContext(
                                start,
                                end,
                                RaycastContext.ShapeType.OUTLINE,
                                RaycastContext.FluidHandling.NONE,
                                player
                        )
                );
    }

    // =========================================================
    // ENTITY TARGET
    // =========================================================

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

        for (
                Entity entity :
                entities
        ) {

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

                if (
                        distance
                                < closestDistance
                ) {

                    closestDistance =
                            distance;

                    closestEntity =
                            entity;
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

    // =========================================================
    // SONIC TARGET
    // =========================================================

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

        // -----------------------------------------------------
        // BLOCK DISTANCE
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // ENTITY DISTANCE
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // CLOSEST TARGET
        // -----------------------------------------------------

        if (
                entityHit != null
                        && entityDistance
                        < blockDistance
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
                if (!isOn(stack)) {
                    player.sendMessage(Text.literal("§7SONIC: Inactive — recharge the sonic to use it."), true);
                    return TypedActionResult.success(stack, false);
                }

                toggleMode(stack);

                SonicMode mode =
                        getMode(stack);

                SonicCasing casing =
                        getCasing(stack);

                player.sendMessage(
                        Text.literal(
                                "§bSONIC MODE: §e"
                                        + mode.getDisplayName()
                                        + " §7| §bCASING: §e"
                                        + casing.getDisplayName()
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
        // NO POWER = INACTIVE
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
        // ACTUAL SONIC USE
        // =====================================================

        if (!world.isClient) {

            // -------------------------------------------------
            // GET TARGET
            // -------------------------------------------------

            HitResult target =
                    getSonicTarget(player);

            // -------------------------------------------------
            // GET MODE
            // -------------------------------------------------

            SonicMode mode =
                    getMode(stack);

            // -------------------------------------------------
            // GET CASING
            // -------------------------------------------------

            SonicCasing casing =
                    getCasing(stack);

            // -------------------------------------------------
            // USE SONIC
            // -------------------------------------------------

            SonicHandler.use(
                    player,
                    world,
                    target,
                    mode
            );

            // -------------------------------------------------
            // PLAY SOUND
            // -------------------------------------------------
            //
            // This plays ONLY when this method is called
            // by the player's right-click.
            //
            // It is NOT connected to inventoryTick().
            // It is NOT connected to isOn().
            // It does NOT loop.
            // -------------------------------------------------

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    GallifreySounds.SONIC,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            // -------------------------------------------------
            // CONSUME POWER
            // -------------------------------------------------

            consumePower(stack);

            // -------------------------------------------------
            // STATUS
            // -------------------------------------------------

            player.sendMessage(
                    Text.literal(
                            "§b"
                                    + casing.getDisplayName()
                                    + " §7| §bPOWER: §f"
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