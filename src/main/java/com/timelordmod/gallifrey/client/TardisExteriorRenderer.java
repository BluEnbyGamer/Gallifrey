package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.block.TardisExteriorBlock;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.model.TardisModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class TardisExteriorRenderer implements BlockEntityRenderer<TardisExteriorBlockEntity> {

    // Temporary texture - the UV template hasn't been painted yet, this is
    // the same file the block's own (now-unused) JSON model pointed at
    private static final Identifier TEXTURE =
            new Identifier("gallifrey", "textures/block/tardis/tardis_exterior.png");

    public static final EntityModelLayer TARDIS_EXTERIOR_LAYER =
            new EntityModelLayer(new Identifier("gallifrey", "tardis_exterior"), "main");

    private final TardisModel model;

    public TardisExteriorRenderer(BlockEntityRendererFactory.Context context) {
        this.model = new TardisModel(context.getLayerModelPart(TARDIS_EXTERIOR_LAYER));
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
            // First third: fade/phase out. Middle third: unstable vortex.
            // Final third: the exterior is almost fully phased out.
            float progress = 1.0F - (blockEntity.getFlightTicks() / (float) blockEntity.getFlightTime());
            phase = progress < 0.34F ? 1.0F - progress / 0.34F : 0.04F;
            pulse = 1.0F + (float) Math.sin(progress * Math.PI * 20.0F) * 0.035F;
        } else if (blockEntity.getMaterializationTicks() > 0) {
            float progress = 1.0F - (blockEntity.getMaterializationTicks() / (float) blockEntity.getMaterializationTime());
            // Stronger fade at the beginning, then settle into the normal shell.
            phase = Math.min(1.0F, progress * 1.35F);
            pulse = 1.0F + (float) Math.sin((1.0F - progress) * Math.PI * 12.0F) * 0.04F;
        }

        matrices.translate(0.0F, (1.0F - phase) * 0.08F, 0.0F);
        matrices.scale(-pulse, -pulse, pulse);

        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;
        switch (blockEntity.getExteriorVariant()) {
            case 1 -> { red = 0.78F; green = 0.92F; blue = 1.0F; }
            case 2 -> { red = 0.62F; green = 0.72F; blue = 0.78F; }
            case 3 -> { red = 0.55F; green = 0.68F; blue = 0.82F; }
            default -> { }
        }

        // A subtle blue-white time-vortex tint during the unstable phase.
        if (blockEntity.isFlightPending() && blockEntity.getFlightTicks() < 40) {
            float vortex = 0.16F * (1.0F - blockEntity.getFlightTicks() / 40.0F);
            red = Math.min(1.0F, red + vortex * 0.35F);
            green = Math.min(1.0F, green + vortex * 0.55F);
            blue = Math.min(1.0F, blue + vortex);
        }

        model.render(
                matrices,
                vertexConsumers.getBuffer(model.getLayer(TEXTURE)),
                light,
                overlay,
                red, green, blue, phase
        );

        matrices.pop();
    }
}