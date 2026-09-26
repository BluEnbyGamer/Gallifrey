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
            }
    );

    public static final CreativeSection TOOLS = CreativeSection.of(
            "tools",
            () -> new ItemStack(GallifreyModItems.SONIC_SCREWDRIVER),
            entries -> {
                // Vortex Manipulator and its parts
                entries.add(GallifreyModItems.VORTEX_MANIPULATOR);
                entries.add(GallifreyModItems.WHITE_POINT_STAR);
                entries.add(GallifreyModItems.BLANK_CIRCUIT);
                entries.add(GallifreyModItems.LOCATION_CIRCUIT);
                entries.add(GallifreyModItems.DIMENSION_CIRCUIT);

                // Tapping
                entries.add(GallifreyModBlocks.TREE_TAPPER);
                entries.add(GallifreyModItems.TREEBORG_PASTE);

                // Sonics
                entries.add(GallifreyModItems.SONIC_SCREWDRIVER);
                entries.add(GallifreyModBlocks.SONIC_WORKSHOP);
                entries.add(GallifreyModItems.SONIC_CRYSTAL);
                entries.add(GallifreyModBlocks.SONIC_CRYSTAL_ORE);
                entries.add(GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE);
                entries.add(GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE);

                // TARDIS
                entries.add(GallifreyModBlocks.TARDIS_EXTERIOR);
                entries.add(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);
            }
    );

    public static final CreativeSection ROUNDELS = CreativeSection.of(
            "roundels",
            () -> new ItemStack(GallifreyModBlocks.STRUCTURE_ROUNDEL),
            entries -> {
                entries.add(GallifreyModBlocks.BASALT_ROUNDEL);
                entries.add(GallifreyModBlocks.BONE_ROUNDEL);
                entries.add(GallifreyModBlocks.STRUCTURE_ROUNDEL);
                entries.add(GallifreyModBlocks.LODESTONE_ROUNDEL);
                entries.add(GallifreyModBlocks.QUARTZ_ROUNDEL);
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
            }
    );

    public static final CreativeSection MARS = CreativeSection.of(
            "mars",
            () -> new ItemStack(GallifreyModBlocks.MARS_STONE),
            entries -> {
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
            }
    );

    /** Sidebar order. */
    public static final List<CreativeSection> ALL = List.of(
            WOOD_TYPES,
            TOOLS,
            ROUNDELS,
            CLOTHING,
            MARS,
            SKARO
    );

    private GallifreyTabSections() {}
}
