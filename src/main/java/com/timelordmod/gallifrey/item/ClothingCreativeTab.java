package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ClothingCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModItems.FEZ))
            .displayName(Text.translatable("itemGroup.gallifrey.clothing"))
            .entries((context, entries) -> {
                entries.add(GallifreyModItems.FEZ);
                entries.add(GallifreyModItems.FANCYFEZ);
                entries.add(GallifreyModItems.PURPLEFEZ);
                entries.add(GallifreyModItems.GREENFEZ);
                entries.add(GallifreyModItems.ORANGEFEZ);
                entries.add(GallifreyModItems.BLUEFEZ);
                entries.add(GallifreyModItems.DARKBLUEFEZ);
                entries.add(GallifreyModItems.PINKFEZ);
                entries.add(GallifreyModItems.GREYFEZ);
                entries.add(GallifreyModItems.YELLOWFEZ);
                entries.add(GallifreyModItems.TRUSTABLE_HAT);
                entries.add(GallifreyModItems.EYESTALK);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GallifreyMod.id("clothing"), TAB);
        GallifreyMod.LOGGER.debug("[Gallifrey] Clothing creative tab registered.");
    }
}
