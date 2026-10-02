package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(GallifreyModBlocks.ULANDA_LOG, GallifreyModBlocks.ULANDA_WOOD,
                        GallifreyModBlocks.STRIP_ULANDA_LOG, GallifreyModBlocks.STRIP_ULANDA_WOOD)
                .add(GallifreyModBlocks.TREEBORG_LOG, GallifreyModBlocks.TREEBORG_WOOD,
                        GallifreyModBlocks.STRIP_TREEBORG_LOG, GallifreyModBlocks.STRIP_TREEBORG_WOOD)
                .add(GallifreyModBlocks.TARDIS_LOG, GallifreyModBlocks.TARDIS_WOOD,
                        GallifreyModBlocks.STRIP_TARDIS_LOG, GallifreyModBlocks.STRIP_TARDIS_WOOD)
                .add(GallifreyModBlocks.ASH_LOG, GallifreyModBlocks.ASH_WOOD,
                        GallifreyModBlocks.STRIP_ASH_LOG, GallifreyModBlocks.STRIP_ASH_WOOD)
                .add(GallifreyModBlocks.MAPLE_LOG, GallifreyModBlocks.MAPLE_WOOD,
                        GallifreyModBlocks.STRIP_MAPLE_LOG, GallifreyModBlocks.STRIP_MAPLE_WOOD)
                // Skaro blocks are intentionally axe-mineable as requested.
                .add(GallifreyModBlocks.DALEKANIUM_BLOCK, GallifreyModBlocks.DALEKANIUM_ORE,
                        GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE,
                        GallifreyModBlocks.EXQUISITE_CAT, GallifreyModBlocks.GOOD_HEAVENS,
                        GallifreyModBlocks.COBBLED_KALETITE, GallifreyModBlocks.KALETITE,
                        GallifreyModBlocks.KALETITE_BRICKS, GallifreyModBlocks.WASTED_DIRT,
                        GallifreyModBlocks.WASTED_GRASS, GallifreyModBlocks.WASTED_LEAVES,
                        GallifreyModBlocks.WASTED_LOG, GallifreyModBlocks.WASTED_PLANKS,
                        GallifreyModBlocks.WASTED_SLAB);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(GallifreyModBlocks.MARS_STONE, GallifreyModBlocks.MARS_COBBLESTONE,
                        GallifreyModBlocks.MARS_ANDESITE, GallifreyModBlocks.MARS_DIORITE,
                        GallifreyModBlocks.MARS_GRANITE, GallifreyModBlocks.POLISHED_MARS_STONE,
                        GallifreyModBlocks.MARS_POLISHED_ANDESITE, GallifreyModBlocks.MARS_POLISHED_DIORITE,
                        GallifreyModBlocks.MARS_POLISHED_GRANITE, GallifreyModBlocks.MARS_STONE_BRICKS,
                        GallifreyModBlocks.MARS_STONE_BRICKS_CRACKED, GallifreyModBlocks.MARS_CHISELED_STONE_BRICKS,
                        GallifreyModBlocks.MARS_IRON_ORE, GallifreyModBlocks.PISS_CRYSTAL)
                .add(GallifreyModBlocks.DALEKANIUM_BLOCK, GallifreyModBlocks.DALEKANIUM_ORE,
                        GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE,
                        GallifreyModBlocks.EXQUISITE_CAT, GallifreyModBlocks.GOOD_HEAVENS,
                        GallifreyModBlocks.COBBLED_KALETITE, GallifreyModBlocks.KALETITE,
                        GallifreyModBlocks.KALETITE_BRICKS)
                .add(GallifreyModBlocks.SONIC_CRYSTAL_ORE, GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE,
                        GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE)
                .add(GallifreyModBlocks.WHITE_POINT_ORE, GallifreyModBlocks.DEEPSLATE_WHITE_POINT_ORE,
                        GallifreyModBlocks.NETHER_WHITE_POINT_ORE);

        // Mars blocks and ordinary Skaro stone blocks can be harvested with a stone pickaxe.
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(GallifreyModBlocks.MARS_STONE, GallifreyModBlocks.MARS_COBBLESTONE,
                        GallifreyModBlocks.MARS_ANDESITE, GallifreyModBlocks.MARS_DIORITE,
                        GallifreyModBlocks.MARS_GRANITE, GallifreyModBlocks.POLISHED_MARS_STONE,
                        GallifreyModBlocks.MARS_POLISHED_ANDESITE, GallifreyModBlocks.MARS_POLISHED_DIORITE,
                        GallifreyModBlocks.MARS_POLISHED_GRANITE, GallifreyModBlocks.MARS_STONE_BRICKS,
                        GallifreyModBlocks.MARS_STONE_BRICKS_CRACKED, GallifreyModBlocks.MARS_CHISELED_STONE_BRICKS,
                        GallifreyModBlocks.MARS_IRON_ORE, GallifreyModBlocks.PISS_CRYSTAL,
                        GallifreyModBlocks.EXQUISITE_CAT, GallifreyModBlocks.GOOD_HEAVENS,
                        GallifreyModBlocks.COBBLED_KALETITE, GallifreyModBlocks.KALETITE,
                        GallifreyModBlocks.KALETITE_BRICKS);

        // Sonic crystal and Dalekanium ores require an iron pickaxe.
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(GallifreyModBlocks.SONIC_CRYSTAL_ORE, GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE,
                        GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE,
                        GallifreyModBlocks.DALEKANIUM_ORE, GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE,
                        GallifreyModBlocks.DALEKANIUM_BLOCK);

        // White Point Star ores require a diamond pickaxe.
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK,
                        GallifreyModBlocks.WHITE_POINT_ORE, GallifreyModBlocks.DEEPSLATE_WHITE_POINT_ORE,
                        GallifreyModBlocks.NETHER_WHITE_POINT_ORE);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(GallifreyModBlocks.TARDIS_FENCE, GallifreyModBlocks.TREEBORG_FENCE,
                        GallifreyModBlocks.ASH_FENCE, GallifreyModBlocks.MAPLE_FENCE,
                        GallifreyModBlocks.ULANDA_FENCE);

        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(GallifreyModBlocks.TARDIS_FENCE_GATE, GallifreyModBlocks.TREEBORG_FENCE_GATE,
                        GallifreyModBlocks.ASH_FENCE_GATE, GallifreyModBlocks.MAPLE_FENCE_GATE,
                        GallifreyModBlocks.ULANDA_FENCE_GATE);
    }
}
