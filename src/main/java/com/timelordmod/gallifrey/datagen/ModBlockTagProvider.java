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
                        GallifreyModBlocks.WASTED_SLAB)

        .add(GallifreyModBlocks.MOONPINE_LOG, GallifreyModBlocks.MOONPINE_WOOD,
                GallifreyModBlocks.STRIP_MOONPINE_LOG, GallifreyModBlocks.STRIP_MOONPINE_WOOD)
        .add(GallifreyModBlocks.WASTED_WOOD,
                GallifreyModBlocks.STRIP_WASTED_LOG, GallifreyModBlocks.STRIP_WASTED_WOOD)
        .add(GallifreyModBlocks.PREHISTORIC_LOG, GallifreyModBlocks.PREHISTORIC_WOOD,
                GallifreyModBlocks.STRIP_PREHISTORIC_LOG,
                GallifreyModBlocks.PREHISTORIC_PLANKS);

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
                        GallifreyModBlocks.NETHER_WHITE_POINT_ORE,
                        GallifreyModBlocks.PREHISTORIC_ORE, GallifreyModBlocks.DEEPSLATE_PREHISTORIC_ORE);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(GallifreyModBlocks.PREHISTORIC_ORE, GallifreyModBlocks.DEEPSLATE_PREHISTORIC_ORE);

        // Roundels: mined with the same tool as the vanilla block each one is made from.
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(
                        GallifreyModBlocks.STRIPPED_ACACIA_LOG_ROUNDEL, GallifreyModBlocks.STRIPPED_BIRCH_LOG_ROUNDEL,
                        GallifreyModBlocks.STRIPPED_CHERRY_LOG_ROUNDEL, GallifreyModBlocks.STRIPPED_DARK_OAK_LOG_ROUNDEL,
                        GallifreyModBlocks.STRIPPED_JUNGLE_LOG_ROUNDEL, GallifreyModBlocks.STRIPPED_MANGROVE_LOG_ROUNDEL,
                        GallifreyModBlocks.STRIPPED_OAK_LOG_ROUNDEL, GallifreyModBlocks.STRIPPED_SPRUCE_LOG_ROUNDEL);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(
                        GallifreyModBlocks.BASALT_ROUNDEL, GallifreyModBlocks.BONE_ROUNDEL,
                        GallifreyModBlocks.VANILLA_QUATZ_ROUNDEL,
                        GallifreyModBlocks.STRUCTURE_ROUNDEL, GallifreyModBlocks.LODESTONE_ROUNDEL,
                        GallifreyModBlocks.QUARTZ_ROUNDEL, GallifreyModBlocks.AMBQUARTZ_ROUNDEL,
                        GallifreyModBlocks.BLUEQUARTZ_ROUNDEL, GallifreyModBlocks.BLACK_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.BLUE_CONCRETE_ROUNDEL, GallifreyModBlocks.BROWN_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.COPPER_ROUNDEL, GallifreyModBlocks.CRIMQUARTZ_ROUNDEL,
                        GallifreyModBlocks.OBSIQUARTZ_ROUNDEL, GallifreyModBlocks.VERDQUARTZ_ROUNDEL,
                        GallifreyModBlocks.VIOQUARTZ_ROUNDEL, GallifreyModBlocks.CYQUARTZ_ROUNDEL,
                        GallifreyModBlocks.END_STONE_BRICKS_ROUNDEL, GallifreyModBlocks.EXPOSED_COPPER_ROUNDEL,
                        GallifreyModBlocks.GRAY_CONCRETE_ROUNDEL, GallifreyModBlocks.GREEN_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.LIGHT_BLUE_CONCRETE_ROUNDEL, GallifreyModBlocks.LIGHT_GRAY_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.LIME_CONCRETE_ROUNDEL, GallifreyModBlocks.MAGENTA_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.ORANGE_CONCRETE_ROUNDEL, GallifreyModBlocks.OXIDIZED_COPPER_ROUNDEL,
                        GallifreyModBlocks.POLISHED_DIORITE_ROUNDEL, GallifreyModBlocks.POLISHED_ANDESITE_ROUNDEL,
                        GallifreyModBlocks.POLISHED_DEEPSLATE_ROUNDEL, GallifreyModBlocks.PINK_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.POLISHED_GRANITE_ROUNDEL, GallifreyModBlocks.PURPLE_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.RED_CONCRETE_ROUNDEL, GallifreyModBlocks.SANDSTONE_ROUNDEL,
                        GallifreyModBlocks.WEATHERED_COPPER_ROUNDEL, GallifreyModBlocks.WHITE_CONCRETE_ROUNDEL,
                        GallifreyModBlocks.YELLOW_CONCRETE_ROUNDEL, GallifreyModBlocks.HARTNELL_ROUNDEL);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(
                        GallifreyModBlocks.DIRT_ROUNDEL);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(
                        GallifreyModBlocks.MOSS_ROUNDEL);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(GallifreyModBlocks.TARDIS_FENCE, GallifreyModBlocks.TREEBORG_FENCE,
                        GallifreyModBlocks.ASH_FENCE, GallifreyModBlocks.MAPLE_FENCE,
                        GallifreyModBlocks.MOONPINE_FENCE, GallifreyModBlocks.ULANDA_FENCE,
                        GallifreyModBlocks.WASTED_FENCE, GallifreyModBlocks.PREHISTORIC_FENCE);

        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(GallifreyModBlocks.TARDIS_FENCE_GATE, GallifreyModBlocks.TREEBORG_FENCE_GATE,
                        GallifreyModBlocks.ASH_FENCE_GATE, GallifreyModBlocks.MAPLE_FENCE_GATE,
                        GallifreyModBlocks.MOONPINE_FENCE_GATE,GallifreyModBlocks.ULANDA_FENCE_GATE,
                        GallifreyModBlocks.WASTED_FENCE_GATE, GallifreyModBlocks.PREHISTORIC_FENCE_GATE);

        // Logs tag: leaves only stay alive next to blocks in this tag,
        // so every tree's logs must be here or its leaves decay.
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
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
                .add(GallifreyModBlocks.MOONPINE_LOG, GallifreyModBlocks.MOONPINE_WOOD,
                        GallifreyModBlocks.STRIP_MOONPINE_LOG, GallifreyModBlocks.STRIP_MOONPINE_WOOD)
                .add(GallifreyModBlocks.WASTED_LOG, GallifreyModBlocks.WASTED_WOOD,
                        GallifreyModBlocks.STRIP_WASTED_LOG, GallifreyModBlocks.STRIP_WASTED_WOOD)
                .add(GallifreyModBlocks.PREHISTORIC_LOG, GallifreyModBlocks.PREHISTORIC_WOOD,
                        GallifreyModBlocks.STRIP_PREHISTORIC_LOG);
    }
}
