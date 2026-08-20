package com.timelordmod.gallifrey;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;


public class GallifreyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_LEAVES, RenderLayer.getCutoutMipped());
    }
}

