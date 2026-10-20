package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.custom.TimelordRobeArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TimelordRobeArmorRenderer extends GeoArmorRenderer<TimelordRobeArmorItem> {
    public TimelordRobeArmorRenderer() {
        super(new DefaultedItemGeoModel<>(GallifreyMod.id("armor/timelord_robes")));
    }
}
