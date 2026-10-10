package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Lets Classic Obsidian count as a Nether portal frame.
 *
 * Without this, vanilla breaks a portal the moment a block next to it updates if the frame
 * isn't minecraft:obsidian, which is why Classic Obsidian portals vanished straight away.
 *
 * method_30487 is the frame-check lambda (state.isOf(Blocks.OBSIDIAN)) in 1.20.1.
 * Its name is the same in dev and in the built mod, so remap = false.
 */
@Mixin(NetherPortal.class)
public abstract class ClassicPortalFrameMixin {

    @Inject(method = "method_30487", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gallifrey$classicObsidianFrame(BlockState state, BlockView world, BlockPos pos,
                                                       CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && state.isOf(GallifreyModBlocks.CLASSIC_OBSIDIAN)) {
            cir.setReturnValue(true);
        }
    }
}
