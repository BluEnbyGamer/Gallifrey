package com.timelordmod.gallifrey.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MondasFogMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void gallifrey$mondasFog(Camera camera, BackgroundRenderer.FogType fogType,
                                            float viewDistance, boolean thickFog, float tickDelta,
                                            CallbackInfo ci) {
        if (camera.getFocusedEntity() == null) return;
        World world = camera.getFocusedEntity().getWorld();
        if ("mondas".equals(world.getRegistryKey().getValue().getPath())) {
            // Mondas is now deliberately hostile and near-whiteout in its blizzard.
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(Math.min(viewDistance, 9.0F));
            RenderSystem.setShaderFogColor(0.78F, 0.82F, 0.86F);
            ci.cancel();
        }
    }
}
