package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.section.CreativeSection;
import com.timelordmod.gallifrey.item.section.GallifreyTabSections;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;


public class GallifreyCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModBlocks.TARDIS_SAPLING))
            .displayName(Text.translatable("itemGroup.gallifrey.gallifrey"))
            .entries((context, entries) -> {
                for (CreativeSection section : GallifreyTabSections.ALL) {
                    section.addTo(entries);
                }
            })
            .build();

    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                GallifreyMod.id("gallifrey"),
                TAB
        );
        GallifreyMod.LOGGER.debug("[Gallifrey] Creative tab registered.");
    }
}
