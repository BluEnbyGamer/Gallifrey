package com.timelordmod.gallifrey.item;


import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class GallifreyCreativeTab {
    public static final ItemGroup TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GallifreyModBlocks.TARDIS_SAPLING))
            .displayName(Text.translatable("itemGroup.gallifrey.main"))
            .entries((context, entries) -> {
                //ITEMS GO HERE
                entries.add(GallifreyModItems.VORTEX_MANIPULATOR);
                entries.add(GallifreyModItems.WHITE_POINT_STAR);
                entries.add(GallifreyModItems.BLANK_CIRCUIT);
                entries.add(GallifreyModItems.LOCATION_CIRCUIT);
                entries.add(GallifreyModItems.DIMENSION_CIRCUIT);

                //MISC BLOCKS
                entries.add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);

                //TARDIS WOODSET
                entries.add(GallifreyModBlocks.TARDIS_SAPLING);
                entries.add(GallifreyModBlocks.TARDIS_LEAVES);
                entries.add(GallifreyModBlocks.TARDIS_LOG);
                entries.add(GallifreyModBlocks.TARDIS_WOOD);
                entries.add(GallifreyModBlocks.STRIP_TARDIS_LOG);
                entries.add(GallifreyModBlocks.STRIP_TARDIS_WOOD);
                entries.add(GallifreyModBlocks.TARDIS_PLANKS);
                entries.add(GallifreyModBlocks.TARDIS_STAIRS);
                entries.add(GallifreyModBlocks.TARDIS_SLAB);
                entries.add(GallifreyModBlocks.TARDIS_FENCE);
                entries.add(GallifreyModBlocks.TARDIS_FENCE_GATE);
                entries.add(GallifreyModBlocks.TARDIS_WOOD_DOOR);
                entries.add(GallifreyModBlocks.TARDIS_TRAPDOOR);
                entries.add(GallifreyModBlocks.TARDIS_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.TARDIS_BUTTON);

                //ULANDA WOODSET
                entries.add(GallifreyModBlocks.ULANDA_SAPLING);
                entries.add(GallifreyModBlocks.ULANDA_LEAVES);
                entries.add(GallifreyModBlocks.ULANDA_LOG);
                entries.add(GallifreyModBlocks.ULANDA_WOOD);
                entries.add(GallifreyModBlocks.STRIP_ULANDA_LOG);
                entries.add(GallifreyModBlocks.STRIP_ULANDA_WOOD);
                entries.add(GallifreyModBlocks.ULANDA_PLANKS);

                //TREEOBORG_WOODSET
                //entries.add(GallifreyModBlocks.ULANDA_SAPLING);
                //entries.add(GallifreyModBlocks.ULANDA_LEAVES);
                //entries.add(GallifreyModBlocks.ULANDA_LOG);
                //entries.add(GallifreyModBlocks.ULANDA_WOOD);
                //entries.add(GallifreyModBlocks.STRIP_ULANDA_LOG);
                //entries.add(GallifreyModBlocks.STRIP_ULANDA_WOOD);
                //entries.add(GallifreyModBlocks.ULANDA_PLANKS);


            })
            .build();


    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                GallifreyMod.id("main"),
                TAB
        );
        GallifreyMod.LOGGER.debug("[Gallifrey] Creative tab registered.");
    }
}