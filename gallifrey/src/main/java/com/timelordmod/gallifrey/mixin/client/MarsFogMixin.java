package com.timelordmod.gallifrey.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.timelordmod.gallifrey.client.PlanetWeatherClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MarsFogMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void gallifrey$marsFog(Camera camera, BackgroundRenderer.FogType fogType,
                                           float viewDistance, boolean thickFog, float tickDelta,
                                           CallbackInfo ci) {
        if (camera.getFocusedEntity() == null) return;
        World world = camera.getFocusedEntity().getWorld();
        if (!"mars".equals(world.getRegistryKey().getValue().getPath())) return;

        if (PlanetWeatherClient.isMarsSandstorm()) {
            float intensity = PlanetWeatherClient.marsStormIntensity(tickDelta);
            float end = 18.0F - (8.0F * intensity);
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(Math.min(viewDistance, end));
            RenderSystem.setShaderFogColor(
                    0.52F + 0.10F * intensity,
                    0.20F + 0.04F * intensity,
                    0.07F + 0.01F * intensity
            );
        } else {
            RenderSystem.setShaderFogStart(4.0F);
            RenderSystem.setShaderFogEnd(Math.min(viewDistance, 96.0F));
        }
        ci.cancel();
    }
}
