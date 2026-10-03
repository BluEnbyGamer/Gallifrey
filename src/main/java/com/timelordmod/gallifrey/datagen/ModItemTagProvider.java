package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(GallifreyModBlocks.TARDIS_PLANKS.asItem())
                .add(GallifreyModBlocks.TREEBORG_PLANKS.asItem())
                .add(GallifreyModBlocks.ULANDA_PLANKS.asItem())
                .add(GallifreyModBlocks.ASH_PLANKS.asItem())
                .add(GallifreyModBlocks.MAPLE_PLANKS.asItem())
                .add(GallifreyModBlocks.MOONPINE_PLANKS.asItem())
                .add(GallifreyModBlocks.WASTED_PLANKS.asItem())
                .add(GallifreyModBlocks.PREHISTORIC_PLANKS.asItem());

        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                .add(GallifreyModItems.DW_XIV_MUSIC_DISC)
                .add(GallifreyModItems.GALLIFREY_MUSIC_DISC);

        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(GallifreyModItems.DW_XIV_MUSIC_DISC)
                .add(GallifreyModItems.GALLIFREY_MUSIC_DISC);
    }
}