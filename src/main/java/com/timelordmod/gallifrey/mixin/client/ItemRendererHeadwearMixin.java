package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.SonicShadesItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Headwear is drawn by HatFeatureRenderer only when worn.
 *
 * In particular, do not let another item-render path apply the model's
 * Blockbench `head` transform a second time.  That is what produces the
 * oversized/inverted copy seen on Fezzes, the Eye Stalk and Sonic Shades.
 * The custom head renderer uses ModelTransformationMode.NONE deliberately.
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererHeadwearMixin {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void gallifrey$hideDuplicateHeadwearRender(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel bakedModel,
            CallbackInfo ci) {

        if (!gallifrey$isHeadwear(stack)) {
            return;
        }

        // HatFeatureRenderer is the sole renderer for worn headwear.  It uses
        // NONE, so cancelling HEAD and third-person hand transforms cannot
        // affect the compact model on the player's head or the GUI icon.
        if (renderMode == ModelTransformationMode.HEAD
                || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND) {
            ci.cancel();
        }
    }

    private static boolean gallifrey$isHeadwear(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof HeadwearItem
                || stack.getItem() instanceof SonicShadesItem);
    }
}
