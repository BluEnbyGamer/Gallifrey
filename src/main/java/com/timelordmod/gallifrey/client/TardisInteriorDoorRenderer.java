package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.block.entity.TardisInteriorDoorBlockEntity;
import com.timelordmod.gallifrey.model.TardisInteriorDoorModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class TardisInteriorDoorRenderer implements BlockEntityRenderer<TardisInteriorDoorBlockEntity> {
    public static final EntityModelLayer LAYER =
            new EntityModelLayer(new Identifier("gallifrey", "tardis_interior_door"), "main");

    private final TardisInteriorDoorModel model;

    public TardisInteriorDoorRenderer(BlockEntityRendererFactory.Context context) {
        this.model = new TardisInteriorDoorModel(context.getLayerModelPart(LAYER));
    }

    @Override
    public void render(
            TardisInteriorDoorBlockEntity blockEntity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        matrices.push();
        matrices.translate(0.5F, 1.5F, 0.5F);
        matrices.scale(-1.0F, -1.0F, 1.0F);

        String style = blockEntity.getExteriorStyle();
        Identifier texture = TardisExteriorRenderer.texture(style);
        Identifier emission = TardisExteriorRenderer.emissionTexture(style);

        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture)),
                light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(emission)),
                light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }
}
