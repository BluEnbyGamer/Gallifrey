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
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_SAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_HANGING_LEAVES, RenderLayer.getCutoutMipped());
    }
}

