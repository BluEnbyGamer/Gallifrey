package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.block.custom.SonicWorkshopBlock;
import com.timelordmod.gallifrey.client.TardisExteriorRenderer;
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
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;

public class GallifreyModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // =========================================================
        // SONIC SCREWDRIVER - CASING MODEL
        // =========================================================

        ModelPredicateProviderRegistry.register(
                GallifreyModItems.SONIC_SCREWDRIVER,
                new Identifier("gallifrey", "sonic_casing"),
                (stack, world, entity, seed) -> {

                    SonicCasing casing =
                            SonicScrewdriver.getCasing(stack);

                    return switch (casing) {

                        case THIRD_DOCTOR -> 0.0F;
                        case FOURTH_DOCTOR -> 1.0F;
                        case FIFTH_DOCTOR -> 2.0F;
                        case EIGHTH_DOCTOR -> 3.0F;
                        case WAR_DOCTOR -> 4.0F;
                        case WAR_MASTER -> 5.0F;
                        case ELEVENTH_CANE -> 6.0F;
                        case BLUE_SONIC -> 7.0F;
                        case RICKS_PORTAL_GUN -> 8.0F;
                        case VALVUE_GUN -> 9.0F;
                        case ALASTOR_STAFF -> 10.0F;
                        case DEOS_HAMMER -> 11.0F;
                        case MISSYS_UMBRELLA -> 12.0F;
                        case GAMBLERS_SONIC -> 13.0F;
                        case BLUNT_SONIC -> 14.0F;
                    };
                }
        );

        // =========================================================
        // SONIC SCREWDRIVER - ON/OFF MODEL
        // =========================================================

        ModelPredicateProviderRegistry.register(
                GallifreyModItems.SONIC_SCREWDRIVER,
                new Identifier("gallifrey", "sonic_on"),
                (stack, world, entity, seed) ->
                        SonicScrewdriver.isOn(stack)
                                ? 1.0F
                                : 0.0F
        );


        // =========================================================
        // SONIC WORKSHOP
        // =========================================================
        //
        // The Workshop is a BLOCK.
        //
        // Therefore we use UseBlockCallback rather than
        // UseItemCallback.
        //
        // The GUI is client-side, so this code belongs here.
        //

        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) -> {

                    // Only handle the client side.
                    if (!world.isClient) {
                        return ActionResult.PASS;
                    }

                    // Must be holding the Sonic Screwdriver.
                    ItemStack stack =
                            player.getStackInHand(hand);

                    if (!(stack.getItem()
                            instanceof SonicScrewdriver)) {

                        return ActionResult.PASS;
                    }

                    // Check that the block clicked is the
                    // Sonic Workshop.
                    if (!(world.getBlockState(
                            hitResult.getBlockPos()
                    ).getBlock() instanceof SonicWorkshopBlock)) {

                        return ActionResult.PASS;
                    }

                    // Open the Workshop GUI.
                    MinecraftClient.getInstance().setScreen(
                            new SonicWorkshopScreen()
                    );

                    return ActionResult.SUCCESS;
                }
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
    }
}
