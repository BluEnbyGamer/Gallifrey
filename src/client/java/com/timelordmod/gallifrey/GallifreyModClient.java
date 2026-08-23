package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.RenderLayer;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;



public class GallifreyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DimensionRenderingRegistry.registerSkyRenderer(
                ModDimensions.GALL_LEVEL_KEY,
                new TwinSunSkyRenderer()
        );

        //ULANDA RENDER LAYER
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GallifreyModBlocks.ULANDA_LEAVES, RenderLayer.getCutoutMipped());

        //TREE-BORG RENDER LAYER


        //ASH RENDER LAYER


        //MAPLE RENDER LAYER


        //WILLOW RENDER LAYER


        //MOON-PINE RENDER LAYER


    }
}

