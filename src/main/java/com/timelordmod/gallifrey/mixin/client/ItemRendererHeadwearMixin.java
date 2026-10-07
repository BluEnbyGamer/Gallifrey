package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.SonicShadesItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Uses the supplied flat 2D texture for GUI/inventory and item-frame rendering,
 * while leaving the original Blockbench model untouched for hands and worn use.
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererHeadwearMixin {

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private BakedModel gallifrey$use2dHeadwearModel(
            BakedModel originalModel,
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel bakedModel) {

        if (!gallifrey$isHeadwear(stack)) {
            return originalModel;
        }

        // Inventory/creative screens use GUI; item frames use FIXED.
        // FIRST_PERSON_* and THIRD_PERSON_* remain the original 3D model,
        // and HEAD remains the original 3D model when worn.
        if (renderMode != ModelTransformationMode.GUI
                && renderMode != ModelTransformationMode.FIXED) {
            return originalModel;
        }

        Identifier itemId = Registries.ITEM.getId(stack.getItem());
        ModelIdentifier flatModelId = new ModelIdentifier(
                itemId.getNamespace(),
                "item/" + itemId.getPath() + "_2d",
                "inventory"
        );

        return MinecraftClient.getInstance()
                .getBakedModelManager()
                .getModel(flatModelId);
    }

    private static boolean gallifrey$isHeadwear(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof HeadwearItem
                || stack.getItem() instanceof SonicShadesItem);
    }
}
