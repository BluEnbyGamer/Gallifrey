package com.timelordmod.gallifrey.client.render;

import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class SonicWorkshopBlockEntityRenderer
        implements BlockEntityRenderer<SonicWorkshopBlockEntity> {

    private final ItemRenderer itemRenderer;

    public SonicWorkshopBlockEntityRenderer(
            BlockEntityRendererFactory.Context context
    ) {

        this.itemRenderer =
                context.getItemRenderer();
    }

    @Override
    public void render(
            SonicWorkshopBlockEntity blockEntity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {

        // Nothing to render if there is no Sonic
        if (blockEntity.getSonic().isEmpty()) {
            return;
        }

        matrices.push();

        // =====================================================
        // POSITION
        // =====================================================

        matrices.translate(
                0.5D,
                1.05D,
                0.5D
        );

        // =====================================================
        // SIZE
        // =====================================================

        matrices.scale(
                0.75F,
                0.75F,
                0.75F
        );

        // =====================================================
        // ROTATION
        // =====================================================

        matrices.multiply(
                RotationAxis.POSITIVE_Y
                        .rotationDegrees(90.0F)
        );

        // =====================================================
        // RENDER SONIC
        // =====================================================

        itemRenderer.renderItem(
                blockEntity.getSonic(),
                ModelTransformationMode.GROUND,
                light,
                overlay,
                matrices,
                vertexConsumers,
                blockEntity.getWorld(),
                0
        );

        matrices.pop();
    }


}