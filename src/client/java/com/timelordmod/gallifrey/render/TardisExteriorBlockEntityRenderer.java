package com.timelordmod.gallifrey.render;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.model.TardisModel;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TardisExteriorBlockEntityRenderer
        implements BlockEntityRenderer<TardisExteriorBlockEntity> {

    private static final Identifier TEXTURE =
            new Identifier(
                    GallifreyMod.MOD_ID,
                    "textures/tardis/tardis.png"
            );

    private final TardisModel model;

    public TardisExteriorBlockEntityRenderer(
            BlockEntityRendererFactory.Context context
    ) {
        TexturedModelData modelData =
                TardisModel.getTexturedModelData();

        this.model = new TardisModel(
                modelData.createModel()
        );
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
        matrices.push();

        // Move model to the centre of the block
        matrices.translate(
                0.5D,
                1.5D,
                0.5D
        );

        // Blockbench model uses pixel coordinates
        matrices.scale(
                1.0F / 16.0F,
                1.0F / 16.0F,
                1.0F / 16.0F
        );

        VertexConsumer vertices =
                vertexConsumers.getBuffer(
                        RenderLayer.getEntityCutoutNoCull(TEXTURE)
                );

        model.render(
                matrices,
                vertices,
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                overlay,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        matrices.pop();
    }
}