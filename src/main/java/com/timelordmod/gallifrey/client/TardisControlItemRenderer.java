package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.custom.TardisControlItem;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/** Draws the TARDIS monitor/throttle items with their block's GeckoLib model. */
public class TardisControlItemRenderer extends GeoItemRenderer<TardisControlItem> {
    public TardisControlItemRenderer(String modelName) {
        super(new DefaultedBlockGeoModel<>(GallifreyMod.id(modelName)));
    }
}
