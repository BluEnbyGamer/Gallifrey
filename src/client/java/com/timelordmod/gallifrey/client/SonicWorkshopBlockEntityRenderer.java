package com.timelordmod.gallifrey.client.render;

import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class SonicWorkshopBlockEntityRenderer
        implements BlockEntityRenderer<SonicWorkshopBlockEntity> {

    public SonicWorkshopBlockEntityRenderer(
            BlockEntityRendererFactory.Context context
    ) {
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

        // The Sonic Workshop no longer stores a Sonic.
        // Nothing needs to be rendered here.
    }
}
