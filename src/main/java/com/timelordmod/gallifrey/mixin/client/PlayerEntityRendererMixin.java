package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.client.HatFeatureRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addHeadwearFeature(CallbackInfo ci) {
        LivingEntityRendererAccessor accessor = (LivingEntityRendererAccessor) (Object) this;
        accessor.gallifrey$getFeatures().add(
                new HatFeatureRenderer((PlayerEntityRenderer) (Object) this)
        );
    }
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void gallifrey$forceDefaultSkin(
            AbstractClientPlayerEntity player,
            CallbackInfoReturnable<Identifier> cir) {
        cir.setReturnValue(new Identifier("gallifrey", "textures/entity/player/stevelord.png"));
    }

}
