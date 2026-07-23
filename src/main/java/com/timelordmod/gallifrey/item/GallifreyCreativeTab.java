package com.timelordmod.gallifrey.item;


import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class GallifreyCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyMod.GALLIFREY_GRASS_BLOCK))
            .displayName(Text.translatable("itemGroup.gallifrey.main"))
            .entries((context, entries) -> {
                entries.add(GallifreyModItems.WHITE_POINT_STAR);
                entries.add(GallifreyModBlocks.GALLIFREY_GRASS_BLOCK);
            },


    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                GallifreyMod.id("main"),
                TAB
        );
        GallifreyMod.LOGGER.debug("[Gallifrey] Creative tab registered.");
    }
}