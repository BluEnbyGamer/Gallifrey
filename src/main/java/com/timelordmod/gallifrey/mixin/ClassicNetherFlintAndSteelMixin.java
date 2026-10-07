package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Vanilla Flint and Steel places fire, but custom dimensions are not treated
 * as the normal Nether portal destination. Once the fire is placed inside a
 * valid obsidian frame in Classic/Classic Nether, explicitly build the normal
 * NetherPortal using Minecraft's own portal validator.
 */
@Mixin(FlintAndSteelItem.class)
public abstract class ClassicNetherFlintAndSteelMixin {

    @Inject(method = "useOnBlock", at = @At("RETURN"))
    private void gallifrey$igniteClassicPortal(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (!isClassicPortalDimension(serverWorld)) return;
        if (cir.getReturnValue() != ActionResult.SUCCESS) return;

        BlockPos firePos = context.getBlockPos().offset(context.getSide());
        BlockState fireState = serverWorld.getBlockState(firePos);
        if (!(fireState.getBlock() instanceof FireBlock)) return;

        // Vanilla's NetherPortal validator only accepts minecraft:obsidian.
        // Classic portals deliberately accept both vanilla and Gallifrey's
        // Classic Obsidian, while still producing the normal Nether Portal block.
        for (Direction.Axis axis : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) {
            if (gallifrey$createPortal(serverWorld, firePos, axis)) return;
        }
    }

    private static boolean gallifrey$createPortal(ServerWorld world, BlockPos firePos, Direction.Axis axis) {
        Direction horizontal = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction perpendicular = horizontal.rotateYClockwise();
        for (int dy = -4; dy <= 1; dy++) {
            for (int a = -3; a <= 3; a++) {
                for (int b = -3; b <= 3; b++) {
                    BlockPos bottomLeft = firePos.down(dy).offset(horizontal, a).offset(perpendicular, b);
                    if (!gallifrey$isFrame(world, bottomLeft, horizontal, perpendicular)) continue;
                    // 2x3 interior. The fire must be inside it.
                    boolean fireInside = false;
                    for (int y = 1; y <= 3; y++) {
                        for (int x = 1; x <= 2; x++) {
                            BlockPos p = bottomLeft.up(y).offset(horizontal, x);
                            if (p.equals(firePos)) fireInside = true;
                        }
                    }
                    if (!fireInside) continue;
                    for (int y = 1; y <= 3; y++) {
                        for (int x = 1; x <= 2; x++) {
                            world.setBlockState(bottomLeft.up(y).offset(horizontal, x),
                                    Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis), 3);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean gallifrey$isFrame(ServerWorld world, BlockPos bottomLeft, Direction horizontal, Direction perpendicular) {
        for (int x = 0; x <= 3; x++) {
            if (!gallifrey$isObsidian(world.getBlockState(bottomLeft.up(0).offset(horizontal, x)))
                    || !gallifrey$isObsidian(world.getBlockState(bottomLeft.up(4).offset(horizontal, x)))) return false;
        }
        for (int y = 0; y <= 4; y++) {
            if (!gallifrey$isObsidian(world.getBlockState(bottomLeft.up(y)))
                    || !gallifrey$isObsidian(world.getBlockState(bottomLeft.up(y).offset(horizontal, 3)))) return false;
        }
        for (int y = 1; y <= 3; y++) {
            for (int x = 1; x <= 2; x++) {
                BlockState state = world.getBlockState(bottomLeft.up(y).offset(horizontal, x));
                if (!state.isAir() && !(state.getBlock() instanceof FireBlock)) return false;
            }
        }
        return true;
    }

    private static boolean gallifrey$isObsidian(BlockState state) {
        return state.isOf(Blocks.OBSIDIAN) || state.isOf(GallifreyModBlocks.CLASSIC_OBSIDIAN);
    }

    private static boolean isClassicPortalDimension(ServerWorld world) {
        return world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)
                || world.getRegistryKey().equals(ModDimensions.CLASSIC_NETHER_LEVEL_KEY);
    }
}
