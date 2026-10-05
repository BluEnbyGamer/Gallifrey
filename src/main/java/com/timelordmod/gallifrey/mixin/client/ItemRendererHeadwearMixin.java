package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.SonicShadesItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Final safety net for third-person hand rendering. Headwear has its own
 * player-head renderer; allowing ItemRenderer to draw the item in a hand
 * would create the large/inverted duplicate model. GUI, ground and HEAD
 * transforms are deliberately left untouched.
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererHeadwearMixin {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void gallifrey$hideHeadwearThirdPersonItem(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel bakedModel,
            CallbackInfo ci) {

        if (renderMode != ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                && renderMode != ModelTransformationMode.THIRD_PERSON_LEFT_HAND) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !gallifrey$isHeadwear(stack)) {
            return;
        }

        if (client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(stack.getItem())) {
            ci.cancel();
        }
    }

    private static boolean gallifrey$isHeadwear(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof HeadwearItem
                || stack.getItem() instanceof SonicShadesItem);
    }
}
