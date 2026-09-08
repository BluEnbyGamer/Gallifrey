package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class RoundelsCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModBlocks.BONE_ROUNDEL))
            .displayName(Text.translatable("itemGroup.gallifrey.roundels"))
            .entries((context, entries) -> {
                //ROUNDELS GO HERE
                entries.add(GallifreyModBlocks.BASALT_ROUNDEL);
                entries.add(GallifreyModBlocks.BONE_ROUNDEL);
                entries.add(GallifreyModBlocks.STRUCTURE_ROUNDEL);

            })
            .build();


    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                GallifreyMod.id("roundels"),
                TAB
        );
        GallifreyMod.LOGGER.debug("[Gallifrey] Roundels Creative tab registered.");
    }
}