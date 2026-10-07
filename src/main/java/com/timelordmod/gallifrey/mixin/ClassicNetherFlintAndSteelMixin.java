package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
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

        // Vanilla portals can be aligned on either horizontal axis.
        for (Direction.Axis axis : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) {
            Optional<NetherPortal> portal = NetherPortal.getNewPortal(serverWorld, firePos, axis);
            if (portal.isPresent()) {
                portal.get().createPortal();
                return;
            }
        }
    }

    private static boolean isClassicPortalDimension(ServerWorld world) {
        return world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)
                || world.getRegistryKey().equals(ModDimensions.CLASSIC_NETHER_LEVEL_KEY);
    }
}
