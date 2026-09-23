package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.item.custom.HeadwearItem;
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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class HatFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HatFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       AbstractClientPlayerEntity player, float limbAngle, float limbDistance,
                       float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.HEAD);
        if (!(stack.getItem() instanceof HeadwearItem)) return;

        matrices.push();
        this.getContextModel().head.rotate(matrices);

        if (stack.isOf(GallifreyModItems.EYESTALK)) {
            matrices.translate(0.0D, 0.175D, 0.5D);
        } else {
            matrices.translate(0.0D, 0.15D, 0.3D);
        }
        matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180.0F));

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(player, stack, ModelTransformationMode.HEAD, false, matrices,
                vertexConsumers, player.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }
}
