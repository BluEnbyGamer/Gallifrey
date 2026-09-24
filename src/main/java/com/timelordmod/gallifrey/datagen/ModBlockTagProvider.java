package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)

                .add(GallifreyModBlocks.ULANDA_LOG)
                .add(GallifreyModBlocks.ULANDA_WOOD)
                .add(GallifreyModBlocks.STRIP_ULANDA_LOG)
                .add(GallifreyModBlocks.STRIP_ULANDA_WOOD)

                .add(GallifreyModBlocks.TREEBORG_LOG)
                .add(GallifreyModBlocks.TREEBORG_WOOD)
                .add(GallifreyModBlocks.STRIP_TREEBORG_LOG)
                .add(GallifreyModBlocks.STRIP_TREEBORG_WOOD)

                .add(GallifreyModBlocks.TARDIS_LOG)
                .add(GallifreyModBlocks.TARDIS_WOOD)
                .add(GallifreyModBlocks.STRIP_TARDIS_WOOD)
                .add(GallifreyModBlocks.STRIP_TARDIS_WOOD)

                .add(GallifreyModBlocks.ASH_LOG)
                .add(GallifreyModBlocks.ASH_WOOD)
                .add(GallifreyModBlocks.STRIP_ASH_LOG)
                .add(GallifreyModBlocks.STRIP_ASH_WOOD)

                .add(GallifreyModBlocks.WASTED_LOG)
                .add(GallifreyModBlocks.WASTED_PLANKS)
                .add(GallifreyModBlocks.WASTED_PLANK_SLAB)
                .add(GallifreyModBlocks.WASTED_LEAVES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(GallifreyModBlocks.EXQUISITE_CAT)
                .add(GallifreyModBlocks.GOOD_HEAVENS)
                .add(GallifreyModBlocks.COBBLED_KALETITE)
                .add(GallifreyModBlocks.KALETITE)
                .add(GallifreyModBlocks.KALETITE_BRICKS)
                .add(GallifreyModBlocks.DALEKANIUM_BLOCK)
                .add(GallifreyModBlocks.DALEKANIUM_ORE)
                .add(GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE)
                .add(GallifreyModBlocks.SONIC_CRYSTAL_ORE)
                .add(GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE)
                .add(GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(GallifreyModBlocks.COBBLED_KALETITE)
                .add(GallifreyModBlocks.KALETITE)
                .add(GallifreyModBlocks.KALETITE_BRICKS)
                .add(GallifreyModBlocks.DALEKANIUM_ORE)
                .add(GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(GallifreyModBlocks.DALEKANIUM_BLOCK)
                .add(GallifreyModBlocks.SONIC_CRYSTAL_ORE)
                .add(GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE)
                .add(GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(GallifreyModBlocks.TARDIS_FENCE)
                .add(GallifreyModBlocks.TREEBORG_FENCE)
                .add(GallifreyModBlocks.ASH_FENCE)
                .add(GallifreyModBlocks.ULANDA_FENCE);


        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(GallifreyModBlocks.TARDIS_FENCE_GATE)
                .add(GallifreyModBlocks.TREEBORG_FENCE_GATE)
                .add(GallifreyModBlocks.ASH_FENCE_GATE)
                .add(GallifreyModBlocks.ULANDA_FENCE_GATE);

        //getOrCreateTagBuilder(BlockTags.WALLS)
                //.add(ModBlocks.RUBY_WALL);



        //getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                //.add(ModBlocks.END_STONE_RUBY_ORE);
    }
}