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

public class GallifreyModBlocks {
   public static final Block GALLIFREY_GRASS_BLOCK = registerBlock("gallifrey_grass_block",
           new TieredMiningBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).requiresTool(),
                   List.of(),
                   List.of("minecraft:wooden_shovel"),
                   List.of("minecraft:stone_shovel"),
                   List.of("minecraft:gold_shovel"),
                   List.of("minecraft:iron_shovel"),
                   List.of("minecraft:diamond_shovel"),
                   List.of("minecraft:netherite_shovel")));




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
