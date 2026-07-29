package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.GallifreyMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;


/**
 * All the blocks for the mod get registered here.
 *
 * Adding a new one? do this:
 *  1. Make the block class
 *  2. Register it below
 *  3. Block model json in assets/regeneration/models/block/
 *  4. Texture in assets/regeneration/textures/block/
 *  5. Item model json in assets/regeneration/models/item/
 *  6. Add it to the creative tab
 */


public class GallifreyModBlocks {
    public static final Block GALLIFREY_GRASS_BLOCK = registerBlock("gallifrey_grass_block",
            new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)));

    public static final Block GALLIFREY_GRASS = registerBlock("gallifrey_grass",
            new Block(FabricBlockSettings.copyOf(Blocks.GRASS)));

    public static final Block GALLIFREY_FERN = registerBlock("gallifrey_fern",
            new Block(FabricBlockSettings.copyOf(Blocks.FERN)));

    public static final Block ULANDA_SAP = registerBlock("ulanda_sapling",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    public static final Block ULANDA_LEAVES = registerBlock("ulanda_leaves",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block ULANDA_HANGING_LEAVES = registerBlock("ulanda_hanging_leaves",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));


    private static Block registerBlock(String name, Block block) {
        registerBlockitem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, name), block);
    }
    private static Item registerBlockitem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name),
                new BlockItem(block,new FabricItemSettings()));
    }

    public static void register(){
        GallifreyMod.LOGGER.info("Registering ModBlocks for" + GallifreyMod.MOD_ID );
    }
}
