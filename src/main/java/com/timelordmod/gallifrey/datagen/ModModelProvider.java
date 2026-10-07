package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Generates the blockstates and models for the Skaro stairs, slabs and walls.
 *
 * Everything else is still an ordinary hand-edited file in
 *   src/main/resources/assets/gallifrey/blockstates
 *   src/main/resources/assets/gallifrey/models
 *
 * Why only these: the stock generator helpers always write textures as
 * "gallifrey:block/<block id>", in one flat folder, but our textures are sorted
 * into sub-folders. The Skaro set below avoids that by passing the texture
 * path in by hand (see SKARO_TEXTURE_FOLDER). Any other block added here must
 * do the same, or its model will point at a texture that is not there.
 *
 * To add another Skaro stone type: register the four blocks in
 * GallifreyModBlocks, put <base id>.png in textures/block/skaro/, hand-write
 * the full block's own blockstate and model as usual, then add one StoneSet
 * line to SKARO_SETS and run runDatagen.
 */
public class ModModelProvider extends FabricModelProvider {

    /** Folder (inside textures/) that holds the Skaro stone textures. */
    private static final String SKARO_TEXTURE_FOLDER = "block/skaro/";

    /** A full block and the stairs, slab and wall made from it. */
    private record StoneSet(Block base, Block stairs, Block slab, Block wall) {}

    private static final List<StoneSet> SKARO_SETS = List.of(
            new StoneSet(GallifreyModBlocks.SKARO_STONE, GallifreyModBlocks.SKARO_STONE_STAIRS, GallifreyModBlocks.SKARO_STONE_SLAB, GallifreyModBlocks.SKARO_STONE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_COBBLESTONE, GallifreyModBlocks.SKARO_COBBLESTONE_STAIRS, GallifreyModBlocks.SKARO_COBBLESTONE_SLAB, GallifreyModBlocks.SKARO_COBBLESTONE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_ANDESITE, GallifreyModBlocks.SKARO_ANDESITE_STAIRS, GallifreyModBlocks.SKARO_ANDESITE_SLAB, GallifreyModBlocks.SKARO_ANDESITE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_DIORITE, GallifreyModBlocks.SKARO_DIORITE_STAIRS, GallifreyModBlocks.SKARO_DIORITE_SLAB, GallifreyModBlocks.SKARO_DIORITE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_GRANITE, GallifreyModBlocks.SKARO_GRANITE_STAIRS, GallifreyModBlocks.SKARO_GRANITE_SLAB, GallifreyModBlocks.SKARO_GRANITE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_POLISHED_ANDESITE, GallifreyModBlocks.SKARO_POLISHED_ANDESITE_STAIRS, GallifreyModBlocks.SKARO_POLISHED_ANDESITE_SLAB, GallifreyModBlocks.SKARO_POLISHED_ANDESITE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_POLISHED_DIORITE, GallifreyModBlocks.SKARO_POLISHED_DIORITE_STAIRS, GallifreyModBlocks.SKARO_POLISHED_DIORITE_SLAB, GallifreyModBlocks.SKARO_POLISHED_DIORITE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_POLISHED_GRANITE, GallifreyModBlocks.SKARO_POLISHED_GRANITE_STAIRS, GallifreyModBlocks.SKARO_POLISHED_GRANITE_SLAB, GallifreyModBlocks.SKARO_POLISHED_GRANITE_WALL),
            new StoneSet(GallifreyModBlocks.POLISHED_SKARO_STONE, GallifreyModBlocks.POLISHED_SKARO_STONE_STAIRS, GallifreyModBlocks.POLISHED_SKARO_STONE_SLAB, GallifreyModBlocks.POLISHED_SKARO_STONE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_DEEPSLATE, GallifreyModBlocks.SKARO_DEEPSLATE_STAIRS, GallifreyModBlocks.SKARO_DEEPSLATE_SLAB, GallifreyModBlocks.SKARO_DEEPSLATE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_COBBLED_DEEPSLATE, GallifreyModBlocks.SKARO_COBBLED_DEEPSLATE_STAIRS, GallifreyModBlocks.SKARO_COBBLED_DEEPSLATE_SLAB, GallifreyModBlocks.SKARO_COBBLED_DEEPSLATE_WALL),
            new StoneSet(GallifreyModBlocks.SKARO_DEEPSLATE_TILES, GallifreyModBlocks.SKARO_DEEPSLATE_TILES_STAIRS, GallifreyModBlocks.SKARO_DEEPSLATE_TILES_SLAB, GallifreyModBlocks.SKARO_DEEPSLATE_TILES_WALL)
    );

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (StoneSet set : SKARO_SETS) {
            // The texture is named after the full block, e.g. block/skaro/skaro_stone.png
            Identifier texture = new Identifier("gallifrey",
                    SKARO_TEXTURE_FOLDER + Registries.BLOCK.getId(set.base()).getPath());

            registerStairs(generator, set.stairs(), texture);
            registerSlab(generator, set.slab(), set.base(), texture);
            registerWall(generator, set.wall(), texture);
        }
    }

    private static TextureMap sides(Identifier texture) {
        return new TextureMap()
                .put(TextureKey.BOTTOM, texture)
                .put(TextureKey.TOP, texture)
                .put(TextureKey.SIDE, texture);
    }

    private static void registerStairs(BlockStateModelGenerator generator, Block stairs, Identifier texture) {
        TextureMap textures = sides(texture);
        Identifier inner = Models.INNER_STAIRS.upload(stairs, textures, generator.modelCollector);
        Identifier straight = Models.STAIRS.upload(stairs, textures, generator.modelCollector);
        Identifier outer = Models.OUTER_STAIRS.upload(stairs, textures, generator.modelCollector);
        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createStairsBlockState(stairs, inner, straight, outer));
        // The item model (parent: the straight stairs model) is generated automatically.
    }

    private static void registerSlab(BlockStateModelGenerator generator, Block slab, Block base, Identifier texture) {
        TextureMap textures = sides(texture);
        Identifier bottom = Models.SLAB.upload(slab, textures, generator.modelCollector);
        Identifier top = Models.SLAB_TOP.upload(slab, textures, generator.modelCollector);
        // A double slab just uses the full block's own (hand-written) model.
        Identifier full = ModelIds.getBlockModelId(base);
        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createSlabBlockState(slab, bottom, top, full));
        // The item model (parent: the bottom slab model) is generated automatically.
    }

    private static void registerWall(BlockStateModelGenerator generator, Block wall, Identifier texture) {
        TextureMap textures = new TextureMap().put(TextureKey.WALL, texture);
        Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, textures, generator.modelCollector);
        Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, textures, generator.modelCollector);
        Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, textures, generator.modelCollector);
        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createWallBlockState(wall, post, low, tall));
        // Walls have no single block model, so the item uses the special inventory model.
        Identifier inventory = Models.WALL_INVENTORY.upload(wall, textures, generator.modelCollector);
        generator.registerParentedItemModel(wall, inventory);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
