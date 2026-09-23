package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class SkaroCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModBlocks.KALETITE))
            .displayName(Text.translatable("itemGroup.gallifrey.skaro"))
            .entries((context, entries) -> {
                entries.add(GallifreyModBlocks.WASTED_DIRT);
                entries.add(GallifreyModBlocks.WASTED_GRASS);
                entries.add(GallifreyModBlocks.WASTED_LOG);
                entries.add(GallifreyModBlocks.WASTED_LEAVES);
                entries.add(GallifreyModBlocks.WASTED_PLANKS);
                entries.add(GallifreyModBlocks.WASTED_PLANK_SLAB);
                entries.add(GallifreyModBlocks.KALETITE);
                entries.add(GallifreyModBlocks.COBBLED_KALETITE);
                entries.add(GallifreyModBlocks.KALETITE_BRICKS);
                entries.add(GallifreyModBlocks.EXQUISITE_CAT);
                entries.add(GallifreyModBlocks.GOOD_HEAVENS);
                entries.add(GallifreyModBlocks.DALEKANIUM_BLOCK);
                entries.add(GallifreyModBlocks.DALEKANIUM_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GallifreyMod.id("skaro"), TAB);
        GallifreyMod.LOGGER.debug("[Gallifrey] Skaro creative tab registered.");
    }
}
