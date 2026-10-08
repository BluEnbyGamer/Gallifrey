package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.entity.TardisConsoleBlockEntity;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

/** GeckoLib renderer for the supplied Hartnell console model. */
public class TardisConsoleBlockEntityRenderer extends GeoBlockRenderer<TardisConsoleBlockEntity> {
    public TardisConsoleBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new DefaultedBlockGeoModel<>(GallifreyMod.id("tardis_console")));
    }
}
