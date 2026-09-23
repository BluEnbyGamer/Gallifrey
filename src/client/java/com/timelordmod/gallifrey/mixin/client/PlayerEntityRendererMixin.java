package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.client.HatFeatureRenderer;
import com.timelordmod.gallifrey.client.LivingEntityRendererAccessor;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addHeadwearFeature(CallbackInfo ci) {
        LivingEntityRendererAccessor accessor = (LivingEntityRendererAccessor) (Object) this;
        accessor.gallifrey$getFeatures().add(
                new HatFeatureRenderer((PlayerEntityRenderer) (Object) this)
        );
    }
}
