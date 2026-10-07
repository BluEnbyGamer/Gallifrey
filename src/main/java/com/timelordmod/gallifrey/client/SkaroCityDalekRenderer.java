package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.entity.custom.SkaroCityDalekEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/** GeckoLib renderer for the supplied Skaro City Dalek geometry. */
public class SkaroCityDalekRenderer extends GeoEntityRenderer<SkaroCityDalekEntity> {
    public SkaroCityDalekRenderer(EntityRendererFactory.Context context) {
        super(context, new DefaultedEntityGeoModel<>(GallifreyMod.id("skaro_city_dalek"), "head"));
        this.shadowRadius = 0.8F;
    }
}
