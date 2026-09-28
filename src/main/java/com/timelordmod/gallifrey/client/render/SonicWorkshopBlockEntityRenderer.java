package com.timelordmod.gallifrey.client.render;

import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;
import com.timelordmod.gallifrey.client.SonicWorkshopModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SonicWorkshopBlockEntityRenderer extends GeoBlockRenderer<SonicWorkshopBlockEntity> {
    public SonicWorkshopBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new SonicWorkshopModel());
    }

    @Override
    public void renderRecursively(
            MatrixStack matrices,
            SonicWorkshopBlockEntity blockEntity,
            GeoBone bone,
            RenderLayer renderType,
            VertexConsumerProvider vertexConsumers,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {

        // The supplied workshop texture is authored as a bright panel texture.
        // Keep it from becoming almost black in normal daylight because of the
        // model's steeply angled faces.
        int workshopLight = LightmapTextureManager.MAX_LIGHT_COORDINATE;
        super.renderRecursively(matrices, blockEntity, bone, renderType, vertexConsumers, buffer,
                isReRender, partialTick, workshopLight, packedOverlay, red, green, blue, alpha);

        if (!"sonic_holder".equals(bone.getName())) {
            return;
        }

        ItemStack sonic = blockEntity.getSonic();
        if (sonic.isEmpty()) {
            return;
        }

        matrices.push();
        // sonic_holder's pivot is the centre of the gold socket. The casing
        // models are authored as handheld items, so shrink them to the socket
        // size and rotate them onto the workshop's 53-degree sloped holder.
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(53.0F));
        matrices.translate(0.0D, -0.02D, 0.0D);
        matrices.scale(0.24F, 0.24F, 0.24F);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(
                sonic,
                ModelTransformationMode.FIXED,
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                packedOverlay,
                matrices,
                vertexConsumers,
                blockEntity.getWorld(),
                (int) blockEntity.getPos().asLong());

        matrices.pop();
    }
}
