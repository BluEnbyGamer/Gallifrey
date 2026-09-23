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
            .displayName(Text.translatable("itemGroup.gallifrey.gallifrey"))
            .entries((context, entries) -> {
                //ITEMS GO HERE
                entries.add(GallifreyModItems.VORTEX_MANIPULATOR);
                entries.add(GallifreyModItems.WHITE_POINT_STAR);
                entries.add(GallifreyModItems.BLANK_CIRCUIT);
                entries.add(GallifreyModItems.LOCATION_CIRCUIT);
                entries.add(GallifreyModItems.DIMENSION_CIRCUIT);
                entries.add(GallifreyModItems.SONIC_SCREWDRIVER);
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
                entries.add(GallifreyModBlocks.SONIC_WORKSHOP);

                //TARDIS
                entries.add(GallifreyModBlocks.TARDIS_EXTERIOR);

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
                entries.add(GallifreyModItems.TARDIS_SIGN);
                entries.add(GallifreyModItems.HANGING_TARDIS_SIGN);
                entries.add(GallifreyModItems.TARDIS_BOAT);
                entries.add(GallifreyModItems.TARDIS_CHEST_BOAT);

                //ULANDA WOODSET
                entries.add(GallifreyModBlocks.ULANDA_SAPLING);
                entries.add(GallifreyModBlocks.ULANDA_LEAVES);
                entries.add(GallifreyModBlocks.ULANDA_LOG);
                entries.add(GallifreyModBlocks.ULANDA_WOOD);
                entries.add(GallifreyModBlocks.STRIP_ULANDA_LOG);
                entries.add(GallifreyModBlocks.STRIP_ULANDA_WOOD);
                entries.add(GallifreyModBlocks.ULANDA_PLANKS);
                entries.add(GallifreyModBlocks.ULANDA_STAIRS);
                entries.add(GallifreyModBlocks.ULANDA_SLAB);
                entries.add(GallifreyModBlocks.ULANDA_FENCE);
                entries.add(GallifreyModBlocks.ULANDA_FENCE_GATE);
                entries.add(GallifreyModBlocks.ULANDA_DOOR);
                entries.add(GallifreyModBlocks.ULANDA_TRAPDOOR);
                entries.add(GallifreyModBlocks.ULANDA_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.ULANDA_BUTTON);
                entries.add(GallifreyModItems.ULANDA_SIGN);
                entries.add(GallifreyModItems.HANGING_ULANDA_SIGN);
                entries.add(GallifreyModItems.ULANDA_BOAT);
                entries.add(GallifreyModItems.ULANDA_CHEST_BOAT);

                //TREEOBORG_WOODSET
                entries.add(GallifreyModBlocks.TREEBORG_SAPLING);
                entries.add(GallifreyModBlocks.TREEBORG_LEAVES);
                entries.add(GallifreyModBlocks.TREEBORG_LOG);
                entries.add(GallifreyModBlocks.TREEBORG_WOOD);
                entries.add(GallifreyModBlocks.STRIP_TREEBORG_LOG);
                entries.add(GallifreyModBlocks.STRIP_TREEBORG_WOOD);
                entries.add(GallifreyModBlocks.TREEBORG_PLANKS);
                entries.add(GallifreyModBlocks.TREEBORG_STAIRS);
                entries.add(GallifreyModBlocks.TREEBORG_SLAB);
                entries.add(GallifreyModBlocks.TREEBORG_FENCE);
                entries.add(GallifreyModBlocks.TREEBORG_FENCE_GATE);
                entries.add(GallifreyModBlocks.TREEBORG_DOOR);
                entries.add(GallifreyModBlocks.TREEBORG_TRAPDOOR);
                entries.add(GallifreyModBlocks.TREEBORG_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.TREEBORG_BUTTON);
                entries.add(GallifreyModItems.TREEBORG_SIGN);
                entries.add(GallifreyModItems.HANGING_TREEBORG_SIGN);
                entries.add(GallifreyModItems.TREEBORG_BOAT);
                entries.add(GallifreyModItems.TREEBORG_CHEST_BOAT);

            })
            .build();


    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                GallifreyMod.id("gallifrey"),
                TAB
        );
        GallifreyMod.LOGGER.debug("[Gallifrey] Gallifrey Creative tab registered.");
    }
}