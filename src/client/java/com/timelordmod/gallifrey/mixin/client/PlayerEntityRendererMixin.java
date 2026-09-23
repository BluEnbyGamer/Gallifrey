package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.client.HatFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    /*
     * addFeature() is declared on LivingEntityRenderer, not PlayerEntityRenderer.
     * An @Invoker on this mixin therefore cannot resolve the method. Shadow the
     * inherited features list instead and add the custom feature directly.
     */
    @Shadow
    @Final
    protected List<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> features;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addHeadwearFeature(CallbackInfo ci) {
        features.add(new HatFeatureRenderer((PlayerEntityRenderer) (Object) this));
    }
}
