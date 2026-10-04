package com.timelordmod.gallifrey.item.section;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Every section of the Gallifrey creative tab.
 *
 * The order of {@link #ALL} is the order of the sidebar buttons AND the order the
 * items appear in when no section is selected.
 *
 * To add a section: declare it below, add it to ALL, and add a lang line
 * "creative_section.gallifrey.<id>". Six fit in the sidebar as-is.
 */
public final class GallifreyTabSections {

    public static final CreativeSection WOOD_TYPES = CreativeSection.of(
            "wood_types",
            () -> new ItemStack(GallifreyModBlocks.TARDIS_LOG),
            entries -> {
                // TARDIS wood set
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

                // Ulanda wood set
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

                // Treeborg wood set
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

                // Ash wood set
                entries.add(GallifreyModBlocks.ASH_SAPLING);
                entries.add(GallifreyModBlocks.ASH_LEAVES);
                entries.add(GallifreyModBlocks.ASH_LOG);
                entries.add(GallifreyModBlocks.ASH_WOOD);
                entries.add(GallifreyModBlocks.STRIP_ASH_LOG);
                entries.add(GallifreyModBlocks.STRIP_ASH_WOOD);
                entries.add(GallifreyModBlocks.ASH_PLANKS);
                entries.add(GallifreyModBlocks.ASH_STAIRS);
                entries.add(GallifreyModBlocks.ASH_SLAB);
                entries.add(GallifreyModBlocks.ASH_FENCE);
                entries.add(GallifreyModBlocks.ASH_FENCE_GATE);
                entries.add(GallifreyModBlocks.ASH_DOOR);
                entries.add(GallifreyModBlocks.ASH_TRAPDOOR);
                entries.add(GallifreyModBlocks.ASH_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.ASH_BUTTON);
                entries.add(GallifreyModItems.ASH_SIGN);
                entries.add(GallifreyModItems.HANGING_ASH_SIGN);
                entries.add(GallifreyModItems.ASH_BOAT);
                entries.add(GallifreyModItems.ASH_CHEST_BOAT);

                // Maple wood set
                entries.add(GallifreyModBlocks.MAPLE_SAPLING);
                entries.add(GallifreyModBlocks.MAPLE_LEAVES);
                entries.add(GallifreyModBlocks.MAPLE_LOG);
                entries.add(GallifreyModBlocks.MAPLE_WOOD);
                entries.add(GallifreyModBlocks.STRIP_MAPLE_LOG);
                entries.add(GallifreyModBlocks.STRIP_MAPLE_WOOD);
                entries.add(GallifreyModBlocks.MAPLE_PLANKS);
                entries.add(GallifreyModBlocks.MAPLE_STAIRS);
                entries.add(GallifreyModBlocks.MAPLE_SLAB);
                entries.add(GallifreyModBlocks.MAPLE_FENCE);
                entries.add(GallifreyModBlocks.MAPLE_FENCE_GATE);
                entries.add(GallifreyModBlocks.MAPLE_DOOR);
                entries.add(GallifreyModBlocks.MAPLE_TRAPDOOR);
                entries.add(GallifreyModBlocks.MAPLE_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.MAPLE_BUTTON);
                entries.add(GallifreyModItems.MAPLE_SIGN);
                entries.add(GallifreyModItems.HANGING_MAPLE_SIGN);
                entries.add(GallifreyModItems.MAPLE_BOAT);
                entries.add(GallifreyModItems.MAPLE_CHEST_BOAT);

                // Moonpine wood set
                entries.add(GallifreyModBlocks.MOONPINE_SAPLING);
                entries.add(GallifreyModBlocks.MOONPINE_LEAVES);
                entries.add(GallifreyModBlocks.MOONPINE_LOG);
                entries.add(GallifreyModBlocks.MOONPINE_WOOD);
                entries.add(GallifreyModBlocks.STRIP_MOONPINE_LOG);
                entries.add(GallifreyModBlocks.STRIP_MOONPINE_WOOD);
                entries.add(GallifreyModBlocks.MOONPINE_PLANKS);
                entries.add(GallifreyModBlocks.MOONPINE_STAIRS);
                entries.add(GallifreyModBlocks.MOONPINE_SLAB);
                entries.add(GallifreyModBlocks.MOONPINE_FENCE);
                entries.add(GallifreyModBlocks.MOONPINE_FENCE_GATE);
                entries.add(GallifreyModBlocks.MOONPINE_DOOR);
                entries.add(GallifreyModBlocks.MOONPINE_TRAPDOOR);
                entries.add(GallifreyModBlocks.MOONPINE_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.MOONPINE_BUTTON);
                entries.add(GallifreyModItems.MOONPINE_SIGN);
                entries.add(GallifreyModItems.HANGING_MOONPINE_SIGN);
                entries.add(GallifreyModItems.MOONPINE_BOAT);
                entries.add(GallifreyModItems.MOONPINE_CHEST_BOAT);

                // Prehistoric wood set
            }
    );

    public static final CreativeSection PREHISTORIC = CreativeSection.of(
            "prehistoric",
            () -> new ItemStack(GallifreyModItems.PREHISTORIC_INGOT),
            entries -> {
                entries.add(GallifreyModBlocks.PREHISTORIC_SAPLING);
                entries.add(GallifreyModBlocks.POTTED_PREHISTORIC_SAPLING);
                entries.add(GallifreyModBlocks.PREHISTORIC_VINE);
                entries.add(GallifreyModBlocks.PREHISTORIC_LEAVES);
                entries.add(GallifreyModBlocks.PREHISTORIC_LOG);
                entries.add(GallifreyModBlocks.STRIP_PREHISTORIC_LOG);
                entries.add(GallifreyModBlocks.PREHISTORIC_PLANKS);
                entries.add(GallifreyModBlocks.PREHISTORIC_STAIRS);
                entries.add(GallifreyModBlocks.PREHISTORIC_SLAB);
                entries.add(GallifreyModBlocks.PREHISTORIC_FENCE);
                entries.add(GallifreyModBlocks.PREHISTORIC_FENCE_GATE);
                entries.add(GallifreyModBlocks.PREHISTORIC_DOOR);
                entries.add(GallifreyModBlocks.PREHISTORIC_TRAPDOOR);
                entries.add(GallifreyModBlocks.PREHISTORIC_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.PREHISTORIC_BUTTON);
                entries.add(GallifreyModBlocks.PREHISTORIC_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_PREHISTORIC_ORE);
                entries.add(GallifreyModBlocks.PREHISTORIC_BLOCK);
                entries.add(GallifreyModItems.PREHISTORIC_INGOT);
                entries.add(GallifreyModItems.PREHISTORIC_UPGRADE_SMITHING_TEMPLATE);
                entries.add(GallifreyModItems.PREHISTORIC_SWORD);
                entries.add(GallifreyModItems.PREHISTORIC_PICKAXE);
                entries.add(GallifreyModItems.PREHISTORIC_AXE);
                entries.add(GallifreyModItems.PREHISTORIC_SHOVEL);
                entries.add(GallifreyModItems.PREHISTORIC_HOE);
                entries.add(GallifreyModItems.PREHISTORIC_HELMET);
                entries.add(GallifreyModItems.PREHISTORIC_CHESTPLATE);
                entries.add(GallifreyModItems.PREHISTORIC_LEGGINGS);
                entries.add(GallifreyModItems.PREHISTORIC_BOOTS);
            }
    );

    public static final CreativeSection MISC = CreativeSection.of(
            "misc",
            () -> new ItemStack(GallifreyModItems.SONIC_SCREWDRIVER),
            entries -> {
                // Vortex Manipulator and its parts
                entries.add(GallifreyModItems.VORTEX_MANIPULATOR);
                entries.add(GallifreyModBlocks.WHITE_POINT_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_WHITE_POINT_ORE);
                entries.add(GallifreyModBlocks.NETHER_WHITE_POINT_ORE);
                entries.add(GallifreyModItems.WHITE_POINT_STAR);
                entries.add(GallifreyModItems.BLANK_CIRCUIT);
                entries.add(GallifreyModItems.LOCATION_CIRCUIT);
                entries.add(GallifreyModItems.INTERFACE_CIRCUIT);
                entries.add(GallifreyModItems.DIMENSION_CIRCUIT);
                entries.add(GallifreyModItems.DW_XIV_MUSIC_DISC);
                entries.add(GallifreyModItems.GALLIFREY_MUSIC_DISC);

                // Tapping
                entries.add(GallifreyModBlocks.TREE_TAPPER);
                entries.add(GallifreyModItems.SILICONE);
                entries.add(GallifreyModItems.TREEBORG_PASTE);
                entries.add(GallifreyModItems.MAPLE_SYRUP);

                // Sonics
                entries.add(GallifreyModItems.SONIC_SCREWDRIVER);
                entries.add(GallifreyModBlocks.SONIC_WORKSHOP);
                entries.add(GallifreyModItems.SONIC_CRYSTAL);
                entries.add(GallifreyModBlocks.SONIC_CRYSTAL_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE);
                entries.add(GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE);

                // TARDIS
                entries.add(GallifreyModBlocks.TARDIS_EXTERIOR);
                entries.add(GallifreyModItems.RAW_STEEL);
                entries.add(GallifreyModItems.STEEL_INGOT);
                entries.add(GallifreyModBlocks.RAW_STEEL_BLOCK);
                entries.add(GallifreyModBlocks.STEEL_BLOCK);
                entries.add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);

                // Prehistoric ore and gear
            }
    );

    public static final CreativeSection MISC_BLOCKS = CreativeSection.of(
            "misc_blocks",
            () -> new ItemStack(GallifreyModBlocks.HARTNELL_WALL),
            entries -> {
                entries.add(GallifreyModBlocks.HARTNELL_WALL);
                entries.add(GallifreyModBlocks.GOOD_HEAVENS);
                entries.add(GallifreyModBlocks.EXQUISITE_CAT);
            }
    );

    public static final CreativeSection ROUNDELS = CreativeSection.of(
            "roundels",
            () -> new ItemStack(GallifreyModBlocks.STRUCTURE_ROUNDEL),
            entries -> {
                entries.add(GallifreyModBlocks.MOSS_ROUNDEL);
                entries.add(GallifreyModBlocks.DIRT_ROUNDEL);
                entries.add(GallifreyModBlocks.END_STONE_BRICKS_ROUNDEL);
                entries.add(GallifreyModBlocks.SANDSTONE_ROUNDEL);
                entries.add(GallifreyModBlocks.HARTNELL_ROUNDEL);
                entries.add(GallifreyModBlocks.BASALT_ROUNDEL);
                entries.add(GallifreyModBlocks.BONE_ROUNDEL);
                entries.add(GallifreyModBlocks.STRUCTURE_ROUNDEL);
                entries.add(GallifreyModBlocks.LODESTONE_ROUNDEL);
                entries.add(GallifreyModBlocks.VANILLA_QUATZ_ROUNDEL);
                entries.add(GallifreyModBlocks.QUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.BLUEQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.CRIMQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.AMBQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.OBSIQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.VERDQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.VIOQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.CYQUARTZ_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_ACACIA_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_BIRCH_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_CHERRY_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_DARK_OAK_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_JUNGLE_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_MANGROVE_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_OAK_LOG_ROUNDEL);
                entries.add(GallifreyModBlocks.STRIPPED_SPRUCE_LOG_ROUNDEL);;
                entries.add(GallifreyModBlocks.COPPER_ROUNDEL);
                entries.add(GallifreyModBlocks.EXPOSED_COPPER_ROUNDEL);
                entries.add(GallifreyModBlocks.WEATHERED_COPPER_ROUNDEL);
                entries.add(GallifreyModBlocks.OXIDIZED_COPPER_ROUNDEL);
                //CONCRETE ROUNDELS
                entries.add(GallifreyModBlocks.GRAY_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.GREEN_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.LIGHT_BLUE_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.LIGHT_GRAY_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.LIME_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.MAGENTA_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.ORANGE_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.PINK_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.PURPLE_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.RED_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.WHITE_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.YELLOW_CONCRETE_ROUNDEL);
                entries.add(GallifreyModBlocks.POLISHED_ANDESITE_ROUNDEL);
                entries.add(GallifreyModBlocks.POLISHED_DEEPSLATE_ROUNDEL);
                entries.add(GallifreyModBlocks.POLISHED_DIORITE_ROUNDEL);
                entries.add(GallifreyModBlocks.POLISHED_GRANITE_ROUNDEL);
            }
    );

    public static final CreativeSection CLOTHING = CreativeSection.of(
            "clothing",
            () -> new ItemStack(GallifreyModItems.FEZ),
            entries -> {
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
                entries.add(GallifreyModItems.OMEGA_HELMET);
            }
    );

    public static final CreativeSection MARS = CreativeSection.of(
            "mars",
            () -> new ItemStack(GallifreyModBlocks.MARS_STONE),
            entries -> {
                entries.add(GallifreyModBlocks.MARS_SAND);
                entries.add(GallifreyModBlocks.MARS_SANDSTONE);
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
            }
    );

    public static final CreativeSection SKARO = CreativeSection.of(
            "skaro",
            () -> new ItemStack(GallifreyModBlocks.KALETITE),
            entries -> {
                entries.add(GallifreyModBlocks.WASTED_DIRT);
                entries.add(GallifreyModBlocks.WASTED_GRASS);
                // Wasted wood set
                entries.add(GallifreyModBlocks.WASTED_SAPLING);
                entries.add(GallifreyModBlocks.WASTED_LEAVES);
                entries.add(GallifreyModBlocks.WASTED_LOG);
                entries.add(GallifreyModBlocks.WASTED_WOOD);
                entries.add(GallifreyModBlocks.STRIP_WASTED_LOG);
                entries.add(GallifreyModBlocks.STRIP_WASTED_WOOD);
                entries.add(GallifreyModBlocks.WASTED_PLANKS);
                entries.add(GallifreyModBlocks.WASTED_STAIRS);
                entries.add(GallifreyModBlocks.WASTED_SLAB);
                entries.add(GallifreyModBlocks.WASTED_FENCE);
                entries.add(GallifreyModBlocks.WASTED_FENCE_GATE);
                entries.add(GallifreyModBlocks.WASTED_DOOR);
                entries.add(GallifreyModBlocks.WASTED_TRAPDOOR);
                entries.add(GallifreyModBlocks.WASTED_PRESSURE_PLATE);
                entries.add(GallifreyModBlocks.WASTED_BUTTON);
                entries.add(GallifreyModItems.WASTED_SIGN);
                entries.add(GallifreyModItems.HANGING_WASTED_SIGN);
                entries.add(GallifreyModItems.WASTED_BOAT);
                entries.add(GallifreyModItems.WASTED_CHEST_BOAT);
                entries.add(GallifreyModBlocks.KALETITE);
                entries.add(GallifreyModBlocks.COBBLED_KALETITE);
                entries.add(GallifreyModBlocks.KALETITE_BRICKS);
                entries.add(GallifreyModBlocks.EXQUISITE_CAT);
                entries.add(GallifreyModBlocks.GOOD_HEAVENS);
                entries.add(GallifreyModBlocks.DALEKANIUM_BLOCK);
                entries.add(GallifreyModBlocks.DALEKANIUM_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE);
                entries.add(GallifreyModItems.DALEKANIUM_INGOT);
            }
    );

    /** Sidebar order. */
    public static final List<CreativeSection> ALL = List.of(
            WOOD_TYPES,
            PREHISTORIC,
            MISC,
            MISC_BLOCKS,
            ROUNDELS,
            CLOTHING,
            MARS,
            SKARO
    );

    private GallifreyTabSections() {}
}
