package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.sonic.SonicHandler;
import com.timelordmod.gallifrey.sonic.SonicMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class SonicScrewdriver extends Item {

    private static final double SONIC_RANGE = 16.0D;

    public SonicScrewdriver(Settings settings) {
        super(settings);
    }

    /**
     * Finds the block the player is looking at.
     */
    private static BlockHitResult getBlockTarget(PlayerEntity player) {

        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(SONIC_RANGE));

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

    /**
     * Finds the entity the player is looking at.
     */
    private static EntityHitResult getEntityTarget(PlayerEntity player) {

        World world = player.getWorld();

        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(SONIC_RANGE));

        Box searchBox = player
                .getBoundingBox()
                .stretch(direction.multiply(SONIC_RANGE))
                .expand(1.0D);

        List<Entity> entities = world.getOtherEntities(
                player,
                searchBox,
                entity -> entity.isAlive() && entity.isAttackable()
        );

        Entity closestEntity = null;
        double closestDistance = SONIC_RANGE * SONIC_RANGE;

        for (Entity entity : entities) {

            Box box = entity.getBoundingBox().expand(0.3D);

            java.util.Optional<Vec3d> hit = box.raycast(
                    start,
                    end
            );

            if (hit.isPresent()) {

                double distance = start.squaredDistanceTo(hit.get());

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity == null) {
            return null;
        }

        return new EntityHitResult(closestEntity);
    }

    /**
     * Gets whichever target is closest to the player.
     */
    private static HitResult getSonicTarget(PlayerEntity player) {

        BlockHitResult blockHit = getBlockTarget(player);
        EntityHitResult entityHit = getEntityTarget(player);

        double blockDistance = SONIC_RANGE * SONIC_RANGE;
        double entityDistance = SONIC_RANGE * SONIC_RANGE;

        if (blockHit.getType() != HitResult.Type.MISS) {
            blockDistance = player
                    .getCameraPosVec(1.0F)
                    .squaredDistanceTo(blockHit.getPos());
        }

        if (entityHit != null) {
            entityDistance = player
                    .getCameraPosVec(1.0F)
                    .squaredDistanceTo(entityHit.getEntity().getPos());
        }

        if (entityHit != null && entityDistance < blockDistance) {
            return entityHit;
        }

        return blockHit;
    }

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity player,
            Hand hand
    ) {

        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {

            HitResult target = getSonicTarget(player);

            SonicHandler.use(
                    player,
                    world,
                    target,
                    SonicMode.SCAN
            );
        }

        return TypedActionResult.success(
                stack,
                world.isClient
        );
    }
}
