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
                .add(GallifreyModBlocks.TARDIS_LOG)
                .add(GallifreyModBlocks.TARDIS_WOOD)
                .add(GallifreyModBlocks.STRIP_TARDIS_WOOD)
                .add(GallifreyModBlocks.STRIP_TARDIS_WOOD);

        //getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                //.add(ModBlocks.RUBY_BLOCK);

        //getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                //.add(ModBlocks.RAW_RUBY_BLOCK)
                //.add(ModBlocks.RUBY_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(GallifreyModBlocks.TARDIS_FENCE)
                .add(GallifreyModBlocks.ULANDA_FENCE);

        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(GallifreyModBlocks.TARDIS_FENCE_GATE)
                .add(GallifreyModBlocks.ULANDA_FENCE_GATE);

        //getOrCreateTagBuilder(BlockTags.WALLS)
                //.add(ModBlocks.RUBY_WALL);



        //getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                //.add(ModBlocks.END_STONE_RUBY_ORE);
    }
}