package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.custom.GeoHeadwearItem;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Draws a {@link GeoHeadwearItem} on the wearer's head.
 *
 * Reads assets/gallifrey/geo/item/armor/<modelName>.geo.json
 * and   assets/gallifrey/textures/item/armor/<modelName>.png
 *
 * The model's parts must be inside the "armorHead" bone (Blockbench:
 * GeckoLib Animated Model -> Armor template) so they follow the head.
 *
 * headScale enlarges the whole hat around the neck pivot. A model built right
 * against the head (at +-4 pixels) clips through the head and through the
 * skin's hat layer (which sits at +-4.5); 1.2 pushes it clear of both without
 * touching the texture. Vanilla helmets are the equivalent of 1.25.
 */
public class GeoHeadwearRenderer extends GeoArmorRenderer<GeoHeadwearItem> {

    private final float headScale;

    public GeoHeadwearRenderer(String modelName, float headScale) {
        super(new DefaultedItemGeoModel<>(GallifreyMod.id("armor/" + modelName)));
        this.headScale = headScale;
    }

    @Override
    public void preRender(MatrixStack poseStack, GeoHeadwearItem animatable, BakedGeoModel model,
                          @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                          boolean isReRender, float partialTick, int packedLight, int packedOverlay,
                          float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);

        // Set every frame: GeckoLib resets un-animated bones to their original scale.
        if (this.headScale != 1.0F) {
            GeoBone head = this.getHeadBone();
            if (head != null) {
                head.setScaleX(this.headScale);
                head.setScaleY(this.headScale);
                head.setScaleZ(this.headScale);
            }
        }
    }
}
