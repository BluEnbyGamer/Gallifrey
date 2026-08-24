package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.screens.VortexManipulatorScreen;
import com.timelordmod.gallifrey.world.dimension.ModDimensions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;

public class GallifreyModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // ---------------------------------------------------------
        // DIMENSION SKY
        // ---------------------------------------------------------

        DimensionRenderingRegistry.registerSkyRenderer(
                ModDimensions.GALL_LEVEL_KEY,
                new TwinSunSkyRenderer()
        );

        // ---------------------------------------------------------
        // ULANDA RENDER LAYER
        // ---------------------------------------------------------

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        // ---------------------------------------------------------
// VORTEX MANIPULATOR
// ---------------------------------------------------------

        UseItemCallback.EVENT.register(
                (player, world, hand) -> {

                    ItemStack stack = player.getStackInHand(hand);

                    if (stack.getItem() instanceof VortexManipulator) {

                        if (world.isClient()) {

                            MinecraftClient.getInstance().setScreen(
                                    new VortexManipulatorScreen()
                            );

                        }

                        return TypedActionResult.success(
                                stack,
                                world.isClient()
                        );
                    }

                    return TypedActionResult.pass(stack);
                }
        );

    }
}


