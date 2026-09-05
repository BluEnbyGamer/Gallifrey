package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class TardisExteriorRenderer implements BlockEntityRenderer<TardisExteriorBlockEntity> {

    public TardisExteriorRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(
            TardisExteriorBlockEntity blockEntity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        // TARDIS model will be rendered here.
    }
}
