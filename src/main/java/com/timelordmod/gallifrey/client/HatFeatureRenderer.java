package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.SonicShadesSlotAccess;
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

/**
 * Restored headwear renderer: use the item's Blockbench head transform.
 * This replaces the newer hand-positioned/rotated renderer.
 */
public class HatFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HatFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       AbstractClientPlayerEntity player, float limbAngle, float limbDistance,
                       float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack hat = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack shades = ((SonicShadesSlotAccess) player).gallifrey$getSonicShades();
        if (!(hat.getItem() instanceof HeadwearItem) && shades.isEmpty()) return;

        matrices.push();
        this.getContextModel().head.rotate(matrices);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        if (hat.getItem() instanceof HeadwearItem) {
            itemRenderer.renderItem(player, hat, ModelTransformationMode.HEAD, false, matrices,
                    vertexConsumers, player.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
        }
        if (!shades.isEmpty()) {
            itemRenderer.renderItem(player, shades, ModelTransformationMode.HEAD, false, matrices,
                    vertexConsumers, player.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
        }
        matrices.pop();
    }

}
