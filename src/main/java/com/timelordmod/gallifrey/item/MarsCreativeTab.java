package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class MarsCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModBlocks.MARS_STONE))
            .displayName(Text.translatable("itemGroup.gallifrey.mars"))
            .entries((context, entries) -> {
                entries.add(GallifreyModBlocks.MARS_STONE);
                entries.add(GallifreyModBlocks.MARS_COBBLESTONE);
                entries.add(GallifreyModBlocks.MARS_ANDESITE);
                entries.add(GallifreyModBlocks.MARS_DIORITE);
                entries.add(GallifreyModBlocks.MARS_GRANITE);
                entries.add(GallifreyModBlocks.POLISHED_MARS_STONE);
                entries.add(GallifreyModBlocks.MARS_POLISHED_ANDESITE);
                entries.add(GallifreyModBlocks.MARS_POLISHED_DIORITE);
                entries.add(GallifreyModBlocks.MARS_POLISHED_GRANITE);
                entries.add(GallifreyModBlocks.MARS_STONE_BRICKS);
                entries.add(GallifreyModBlocks.MARS_STONE_BRICKS_CRACKED);
                entries.add(GallifreyModBlocks.MARS_CHISELED_STONE_BRICKS);
                entries.add(GallifreyModBlocks.MARS_IRON_ORE);
                entries.add(GallifreyModBlocks.PISS_CRYSTAL);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GallifreyMod.id("mars"), TAB);
        GallifreyMod.LOGGER.debug("[Gallifrey] Mars creative tab registered.");
    }
}
