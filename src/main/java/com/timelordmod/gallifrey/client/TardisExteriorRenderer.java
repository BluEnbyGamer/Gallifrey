package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.block.TardisExteriorBlock;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.model.TardisModel;
import com.timelordmod.gallifrey.tardis.TardisExteriorCatalog;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

/**
 * Renders the supplied Blockbench police-box model with the selected real
 * shell texture. Dematerialisation/materialisation is handled as an alpha
 * phase rather than by recolouring the model.
 */
public class TardisExteriorRenderer implements BlockEntityRenderer<TardisExteriorBlockEntity> {
    public static final EntityModelLayer TARDIS_EXTERIOR_LAYER =
            new EntityModelLayer(new Identifier("gallifrey", "tardis_exterior"), "main");

    private final TardisModel model;

    public TardisExteriorRenderer(BlockEntityRendererFactory.Context context) {
        this.model = new TardisModel(context.getLayerModelPart(TARDIS_EXTERIOR_LAYER));
    }

    public static Identifier texture(String style) {
        String id = TardisExteriorCatalog.get(style).id();
        return new Identifier("gallifrey", "textures/block/tardis/" + id + ".png");
    }

    public static Identifier emissionTexture(String style) {
        String id = TardisExteriorCatalog.get(style).id();
        return new Identifier("gallifrey", "textures/block/tardis/" + id + "_emission.png");
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
        matrices.translate(0.5, 1.5, 0.5);

        int rotation = blockEntity.getCachedState().get(TardisExteriorBlock.ROTATION);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation * 45.0F));

        float phase = 1.0F;
        float pulse = 1.0F;
        if (blockEntity.isFlightPending()) {
            float progress = 1.0F - (blockEntity.getFlightTicks() / (float) blockEntity.getFlightTime());
            phase = progress < 0.34F ? 1.0F - progress / 0.34F : 0.035F;
            pulse = 1.0F + (float) Math.sin(progress * Math.PI * 20.0F) * 0.035F;
        } else if (blockEntity.getMaterializationTicks() > 0) {
            float progress = 1.0F - (blockEntity.getMaterializationTicks() / (float) blockEntity.getMaterializationTime());
            phase = Math.min(1.0F, progress * 1.35F);
            pulse = 1.0F + (float) Math.sin((1.0F - progress) * Math.PI * 12.0F) * 0.04F;
        }

        float phaseScale = Math.max(0.06F, phase);
        matrices.translate(0.0F, (1.0F - phaseScale) * 0.08F, 0.0F);
        float baseScale = 0.94F;
        matrices.scale(-pulse * phaseScale * baseScale, -pulse * phaseScale * baseScale, pulse * phaseScale * baseScale);

        model.setDoorsOpen(blockEntity.getCachedState().get(TardisExteriorBlock.OPEN));
        String style = blockEntity.getExteriorStyle();
        Identifier texture = texture(style);
        Identifier emission = emissionTexture(style);

        model.render(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture)),
                light,
                overlay,
                1.0F, 1.0F, 1.0F, 1.0F
        );

        // Emission is deliberately a separate pass so it can be disabled when
        // the TARDIS has no artron power.
        if (blockEntity.isPowered()) {
            model.render(
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(emission)),
                    light,
                    overlay,
                    1.0F, 1.0F, 1.0F, 1.0F
            );
        }

        matrices.pop();
    }
}
