package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.client.HatFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Invoker("addFeature")
    protected abstract boolean gallifrey$addFeature(
            FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> feature);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addHeadwearFeature(CallbackInfo ci) {
        gallifrey$addFeature(new HatFeatureRenderer((PlayerEntityRenderer) (Object) this));
    }
}
