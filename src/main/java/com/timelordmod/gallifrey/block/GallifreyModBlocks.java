package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.world.tree.UlandaSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


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

    //ULANDA WOOD SET BLOCK REGISTRY
    public static final Block REINFORCED_STEEL_BLOCK = registerBlock("reinforced_steel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).sounds(BlockSoundGroup.METAL)));

    public static final Block ULANDA_LEAVES = registerBlock("ulanda_leaves",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block ULANDA_WOOD = registerBlock("ulanda_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_LOG = registerBlock("ulanda_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block   STRIP_ULANDA_LOG = registerBlock("stripped_ulanda_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block STRIP_ULANDA_WOOD = registerBlock("stripped_ulanda_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_PLANKS = registerBlock("ulanda_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_SAPLING = registerBlock("ulanda_sapling",
            new SaplingBlock(new UlandaSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    //TREE-BORG WOOD SET BLOCK REGISTRY


    //ASH WOOD SET BLOCK REGISTRY


    //MAPLE WOOD SET BLOCK REGISTRY


    //WILLOW WOOD SET BLOCK REGISTRY


    //MOON-PINE WOOD SET BLOCK REGISTRY


    // MISC BLOCKS REGISTRY


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
        registerFlammable();
    }

    private static void registerFlammable() {
        FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
        //Ulanda wood set

        flammable.add(ULANDA_LOG, 5, 5);
        flammable.add(STRIP_ULANDA_LOG, 5, 5);
        flammable.add(ULANDA_WOOD, 5, 5);
        flammable.add(STRIP_ULANDA_WOOD, 5, 5);
        flammable.add(ULANDA_PLANKS, 5, 20);
        flammable.add(ULANDA_LEAVES, 30, 60);

        //Tree-borg wood set
        //flammable.add(TREEBORRG_LOG, 5, 5);
        //flammable.add(STRIP_TREEBORRG_LOG, 5, 5);
        //flammable.add(TREEBORRG_WOOD, 5, 5);
        //flammable.add(STRIP_TREEBORRG_WOOD, 5, 5);
        //flammable.add(TREEBORRG_PLANKS, 5, 20);
        //flammable.add(TREEBORRG_LEAVES, 30, 60);

        //Ash wood set
        //flammable.add(ASH_LOG, 5, 5);
        //flammable.add(STRIP_ASH_LOG, 5, 5);
        //flammable.add(ASH_WOOD, 5, 5);
        //flammable.add(STRIP_ASH_WOOD, 5, 5);
        //flammable.add(ASH_PLANKS, 5, 20);
        //flammable.add(ASH_LEAVES, 30, 60);

        //Maple wood set
        //flammable.add(MAPLE_LOG, 5, 5);
        //flammable.add(STRIP_MAPLE_LOG, 5, 5);
        //flammable.add(MAPLE_WOOD, 5, 5);
        //flammable.add(STRIP_MAPLE_WOOD, 5, 5);
        //flammable.add(MAPLE_PLANKS, 5, 20);
        //flammable.add(MAPLE_LEAVES, 30, 60);

        //Willow wood set
        //flammable.add(WILLOW_LOG, 5, 5);
        //flammable.add(STRIP_WILLOW_LOG, 5, 5);
        //flammable.add(WILLOW_WOOD, 5, 5);
        //flammable.add(STRIP_WILLOW_WOOD, 5, 5);
        //flammable.add(WILLOW_PLANKS, 5, 20);
        //flammable.add(WILLOW_LEAVES, 30, 60);

        //Moon-pine wood set
        //flammable.add(MOONPINE_LOG, 5, 5);
        //flammable.add(STRIP_MOONPINE_LOG, 5, 5);
        //flammable.add(MOONPINE_WOOD, 5, 5);
        //flammable.add(STRIP_MOONPINE_WOOD, 5, 5);
        //flammable.add(MOONPINE_PLANKS, 5, 20);
        //flammable.add(MOONPINE_LEAVES, 30, 60);

        //Misc flammables

    }
}
