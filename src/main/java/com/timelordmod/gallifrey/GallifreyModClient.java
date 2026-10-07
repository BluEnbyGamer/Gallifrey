package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.block.custom.SonicWorkshopBlock;
import com.timelordmod.gallifrey.client.CreativeSectionSidebar;
import com.timelordmod.gallifrey.client.PlanetWeatherClient;
import com.timelordmod.gallifrey.client.SonicShadesClient;
import com.timelordmod.gallifrey.client.TardisExteriorRenderer;
import com.timelordmod.gallifrey.client.render.SonicWorkshopBlockEntityRenderer;
import com.timelordmod.gallifrey.fluid.GallifreyFluids;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;
import com.timelordmod.gallifrey.model.TardisModel;
import com.timelordmod.gallifrey.screens.SonicWorkshopScreen;
import com.timelordmod.gallifrey.screens.VortexManipulatorScreen;
import com.timelordmod.gallifrey.sonic.SonicCasing;
import com.timelordmod.gallifrey.world.dimension.ModDimensions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;

public class GallifreyModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), GallifreyFluids.STILL_RADIATION, GallifreyFluids.FLOWING_RADIATION);
        FluidRenderHandlerRegistry.INSTANCE.register(GallifreyFluids.STILL_RADIATION, GallifreyFluids.FLOWING_RADIATION,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0x2EFF2E
                ));

        com.timelordmod.gallifrey.client.ModClientPackets.register();;
        SonicShadesClient.register();
        ClientTickEvents.END_CLIENT_TICK.register(PlanetWeatherClient::tick);

        // =========================================================
        // SONIC SCREWDRIVER - CASING MODEL
        // =========================================================

        ModelPredicateProviderRegistry.register(
                GallifreyModItems.SONIC_SCREWDRIVER,
                new Identifier("gallifrey", "sonic_casing"),
                (stack, world, entity, seed) -> {

                    SonicCasing casing =
                            SonicScrewdriver.getCasing(stack);

                    return casing.ordinal() / 15.0F;
                }
        );

        // =========================================================
        // SONIC SCREWDRIVER - ON/OFF MODEL
        // =========================================================

        ModelPredicateProviderRegistry.register(
                GallifreyModItems.SONIC_SCREWDRIVER,
                new Identifier(
                        "gallifrey",
                        "sonic_on"
                ),
                (stack, world, entity, seed) ->
                        SonicScrewdriver.isOn(stack)
                                ? 1.0F
                                : 0.0F
        );

        // =========================================================
        // VORTEX MANIPULATOR
        // =========================================================

        UseItemCallback.EVENT.register(
                (player, world, hand) -> {

                    ItemStack stack =
                            player.getStackInHand(hand);

                    if (
                            stack.getItem()
                                    instanceof VortexManipulator
                    ) {

                        if (world.isClient()) {

                            MinecraftClient
                                    .getInstance()
                                    .setScreen(
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

        // =========================================================
        // DIMENSION SKY
        // =========================================================

        DimensionRenderingRegistry.registerSkyRenderer(
                ModDimensions.GALL_LEVEL_KEY,
                new TwinSunSkyRenderer()
        );

        DimensionRenderingRegistry.registerSkyRenderer(
                ModDimensions.SKARO_LEVEL_KEY,
                new SkaroSkyRenderer()
        );

        DimensionRenderingRegistry.registerSkyRenderer(
                ModDimensions.MONDAS_LEVEL_KEY,
                new MondasSkyRenderer()
        );

        // =========================================================
        // MARS CRYSTAL RENDER LAYER
        // =========================================================
        // The crystal texture contains transparent pixels.  Cutout rendering
        // prevents those transparent pixels from appearing as black planes.
        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.PISS_CRYSTAL,
                RenderLayer.getCutout()
        );

        // =========================================================
        // Creative tab sections register
        // =========================================================

        CreativeSectionSidebar.register();

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TREE_TAPPER,
                RenderLayer.getCutout()
        );
        // =========================================================
        // TARDIS RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TARDIS_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_TARDIS_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TARDIS_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TARDIS_WOOD_DOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/tardis_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/tardis_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                TardisExteriorRenderer.TARDIS_EXTERIOR_LAYER,
                TardisModel::getTexturedModelData
        );

        BlockEntityRendererRegistry.register(
                GallifreyModBlockEntities.TARDIS_EXTERIOR,
                TardisExteriorRenderer::new
        );

        BlockEntityRendererRegistry.register(
                GallifreyModBlockEntities.SONIC_WORKSHOP_BLOCK_ENTITY,
                SonicWorkshopBlockEntityRenderer::new
        );

        // =========================================================
        // ULANDA RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_ULANDA_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_DOOR,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ULANDA_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/ulanda_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/ulanda_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        // =========================================================
        // TREEBORG RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TREEBORG_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_TREEBORG_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TREEBORG_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TREEBORG_DOOR,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.TREEBORG_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/treeborg_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/treeborg_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        // =========================================================
        // ASH RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ASH_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_ASH_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ASH_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ASH_DOOR,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.ASH_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/ash_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/ash_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        // =========================================================
        // MAPLE RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MAPLE_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_MAPLE_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MAPLE_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MAPLE_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/maple_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/maple_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        // =========================================================
        // MOONPINE RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MOONPINE_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_MOONPINE_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MOONPINE_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.MOONPINE_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "boat/moonpine_boat"
                        ),
                        "main"
                ),
                BoatEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                new EntityModelLayer(
                        new Identifier(
                                "gallifrey",
                                "chest_boat/moonpine_boat"
                        ),
                        "main"
                ),
                ChestBoatEntityModel::getTexturedModelData
        );

        // =========================================================
        // WASTED RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.WASTED_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.WASTED_DOOR,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.WASTED_TRAPDOOR,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.WASTED_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_WASTED_SAPLING,
                RenderLayer.getCutout()
        );


        // =========================================================
        // CLASSIC RENDER LAYER
        // =========================================================

        // Classic saplings and flowers use transparent cross-plane textures.
        // They must be rendered with cutout rather than the default solid layer
        // or the transparent pixels appear as black squares in-world.
        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.CLASSIC_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.CLASSIC_RED_FLOWER,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.CLASSIC_YELLOW_FLOWER,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.CLASSIC_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        // =========================================================
        // PREHISTORIC RENDER LAYER
        // =========================================================

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.PREHISTORIC_LEAVES,
                RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.PREHISTORIC_VINE,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
        GallifreyModBlocks.PREHISTORIC_DOOR,
        RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
        GallifreyModBlocks.PREHISTORIC_TRAPDOOR,
        RenderLayer.getCutoutMipped()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.PREHISTORIC_SAPLING,
                RenderLayer.getCutout()
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                GallifreyModBlocks.POTTED_PREHISTORIC_SAPLING,
                RenderLayer.getCutout()
        );

    }


}
