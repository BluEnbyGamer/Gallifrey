package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.entity.TardisMonitorBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

/** GeckoLib renderer for the TARDIS monitor (geo/block/tardis_monitor.geo.json). Faces the way it was placed. */
public class TardisMonitorRenderer extends GeoBlockRenderer<TardisMonitorBlockEntity> {
    public TardisMonitorRenderer(BlockEntityRendererFactory.Context context) {
        super(new DefaultedBlockGeoModel<>(GallifreyMod.id("tardis_monitor")));
    }
}
