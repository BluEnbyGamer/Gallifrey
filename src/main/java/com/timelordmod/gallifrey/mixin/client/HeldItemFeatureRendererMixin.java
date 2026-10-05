package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.SonicShadesItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Headwear is still present in the player's inventory/hand in creative mode
 * after it is equipped.  Vanilla then renders that same stack as a held item,
 * producing the large duplicate seen on the body.  The actual head feature
 * renderer is already responsible for the wearable copy, so suppress the held
 * copy while the same headwear is equipped.
 */
@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void gallifrey$hideEquippedHeadwearInHands(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            LivingEntity entity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch,
            CallbackInfo ci) {

        if (!(entity instanceof AbstractClientPlayerEntity player)) {
            return;
        }

        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();
        ItemStack equipped = player.getEquippedStack(EquipmentSlot.HEAD);

        if (equipped.isEmpty()) {
            return;
        }

        boolean isGallifreyHeadwear = equipped.getItem() instanceof HeadwearItem
                || equipped.getItem() instanceof SonicShadesItem;

        if (!isGallifreyHeadwear) {
            return;
        }

        if ((!mainHand.isEmpty() && mainHand.getItem() == equipped.getItem())
                || (!offHand.isEmpty() && offHand.getItem() == equipped.getItem())) {
            ci.cancel();
        }
    }
}
