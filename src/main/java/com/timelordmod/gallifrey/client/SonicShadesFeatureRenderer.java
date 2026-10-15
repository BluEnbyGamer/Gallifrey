package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import com.timelordmod.gallifrey.item.custom.SonicShadesSlotAccess;
import net.minecraft.item.ItemStack;

/**
 * Renders the supplied Blockbench Sonic Shades model on the player's face.
 * The model is an item model with a HEAD display transform, so it is rendered
 * through Minecraft's normal item-model pipeline instead of being treated as
 * armor geometry. This preserves the exact supplied texture/model.
 */
public final class SonicShadesFeatureRenderer
        extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public SonicShadesFeatureRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            AbstractClientPlayerEntity player,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch) {

        ItemStack stack = ((SonicShadesSlotAccess) player).gallifrey$getSonicShades();
        if (!stack.isOf(GallifreyModItems.SONIC_SHADES)) {
            return;
        }

        matrices.push();

        // Follow the player's actual head rotation/pitch.
        this.getContextModel().head.rotate(matrices);

        // Position the shades across the eyes. The item HEAD transform is
        // applied by Minecraft, so use a modest local rotation and scale here
        // rather than rotating the whole player head or using the oversized
        // original display scale.
        // Nudge forward to the face plane and slightly down to align with the eyes.
        matrices.translate(0.0D, 0.005D, -0.22D);
        // The source item geometry is inverted for a head-mounted render; correct
        // that without changing the player's head orientation.
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        // Larger than the previous pass, but still scaled down from the item model.
        matrices.scale(0.68F, 0.68F, 0.68F);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(
                player,
                stack,
                ModelTransformationMode.HEAD,
                false,
                matrices,
                vertexConsumers,
                player.getWorld(),
                light,
                OverlayTexture.DEFAULT_UV,
                0
        );

        matrices.pop();
    }
}
