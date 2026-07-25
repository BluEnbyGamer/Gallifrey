package com.timelordmod.gallifrey;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;


public class GallifreyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.GALLIFREY_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.GALLIFREY_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.GALLIFREY_SAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.GALLIFREY_LEAVES, RenderLayer.getCutoutMipped());
    }
}

