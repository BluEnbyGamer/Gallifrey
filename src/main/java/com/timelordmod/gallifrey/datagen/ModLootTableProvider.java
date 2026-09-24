package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        //TARDIS WOOD SET BLOCK DROPS
        addDrop(GallifreyModBlocks.TARDIS_SAPLING);
        addDrop(GallifreyModBlocks.TARDIS_LEAVES, leavesDrops(GallifreyModBlocks.TARDIS_LEAVES, GallifreyModBlocks.TARDIS_SAPLING, 0.0025f));
        addDrop(GallifreyModBlocks.TARDIS_LOG);
        addDrop(GallifreyModBlocks.STRIP_TARDIS_LOG);
        addDrop(GallifreyModBlocks.TARDIS_WOOD);
        addDrop(GallifreyModBlocks.STRIP_TARDIS_WOOD);
        addDrop(GallifreyModBlocks.TARDIS_PLANKS);
        addDrop(GallifreyModBlocks.TARDIS_SLAB, slabDrops(GallifreyModBlocks.TARDIS_SLAB));
        addDrop(GallifreyModBlocks.TARDIS_BUTTON);
        addDrop(GallifreyModBlocks.TARDIS_FENCE);
        addDrop(GallifreyModBlocks.TARDIS_FENCE_GATE);
        addDrop(GallifreyModBlocks.TARDIS_WOOD_DOOR, doorDrops(GallifreyModBlocks.TARDIS_WOOD_DOOR));
        addDrop(GallifreyModBlocks.TARDIS_TRAPDOOR);
        addDrop(GallifreyModBlocks.TARDIS_PRESSURE_PLATE);
        addDrop(GallifreyModBlocks.STANDING_TARDIS_SIGN);
        addDrop(GallifreyModBlocks.WALL_TARDIS_SIGN);
        addDrop(GallifreyModBlocks.HANGING_TARDIS_SIGN);
        addDrop(GallifreyModBlocks.WALL_HANGING_TARDIS_SIGN);

        //ULANDA WOOD SET BLOCK DROPS
        addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        addDrop(GallifreyModBlocks.ULANDA_LOG);
        addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        addDrop(GallifreyModBlocks.ULANDA_WOOD);
        addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        addDrop(GallifreyModBlocks.ULANDA_PLANKS);
        addDrop(GallifreyModBlocks.ULANDA_SLAB, slabDrops(GallifreyModBlocks.ULANDA_SLAB));
        addDrop(GallifreyModBlocks.ULANDA_BUTTON);
        addDrop(GallifreyModBlocks.ULANDA_FENCE);
        addDrop(GallifreyModBlocks.ULANDA_FENCE_GATE);
        addDrop(GallifreyModBlocks.ULANDA_DOOR, doorDrops(GallifreyModBlocks.ULANDA_DOOR));
        addDrop(GallifreyModBlocks.ULANDA_TRAPDOOR);
        addDrop(GallifreyModBlocks.ULANDA_PRESSURE_PLATE);
        addDrop(GallifreyModBlocks.STANDING_ULANDA_SIGN);
        addDrop(GallifreyModBlocks.WALL_ULANDA_SIGN);
        addDrop(GallifreyModBlocks.HANGING_ULANDA_SIGN);
        addDrop(GallifreyModBlocks.WALL_HANGING_ULANDA_SIGN);

        //TREE-BORG WOOD SET BLOCK DROPS
        addDrop(GallifreyModBlocks.TREEBORG_SAPLING);
        addDrop(GallifreyModBlocks.TREEBORG_LEAVES, leavesDrops(GallifreyModBlocks.TREEBORG_LEAVES, GallifreyModBlocks.TREEBORG_SAPLING, 0.0025f));
        addDrop(GallifreyModBlocks.TREEBORG_LOG);
        addDrop(GallifreyModBlocks.STRIP_TREEBORG_LOG);
        addDrop(GallifreyModBlocks.TREEBORG_WOOD);
        addDrop(GallifreyModBlocks.STRIP_TREEBORG_WOOD);
        addDrop(GallifreyModBlocks.TREEBORG_PLANKS);
        addDrop(GallifreyModBlocks.TREEBORG_SLAB, slabDrops(GallifreyModBlocks.TREEBORG_SLAB));
        addDrop(GallifreyModBlocks.TREEBORG_BUTTON);
        addDrop(GallifreyModBlocks.TREEBORG_FENCE);
        addDrop(GallifreyModBlocks.TREEBORG_FENCE_GATE);
        addDrop(GallifreyModBlocks.TREEBORG_DOOR, doorDrops(GallifreyModBlocks.TREEBORG_DOOR));
        addDrop(GallifreyModBlocks.TREEBORG_TRAPDOOR);
        addDrop(GallifreyModBlocks.TREEBORG_PRESSURE_PLATE);
        addDrop(GallifreyModBlocks.STANDING_TREEBORG_SIGN);
        addDrop(GallifreyModBlocks.WALL_TREEBORG_SIGN);
        addDrop(GallifreyModBlocks.HANGING_TREEBORG_SIGN);
        addDrop(GallifreyModBlocks.WALL_HANGING_TREEBORG_SIGN);

        //ASH WOOD SET BLOCK DROPS
        //addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        //addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        //ddDrop(GallifreyModBlocks.ULANDA_LOG);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        //addDrop(GallifreyModBlocks.ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.ULANDA_PLANKS);

        //MAPLE WOOD SET BLOCK DROPS
        //addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        //addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        //addDrop(GallifreyModBlocks.ULANDA_LOG);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        //addDrop(GallifreyModBlocks.ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.ULANDA_PLANKS);

        //WILLOW WOOD SET BLOCK DROPS
        //addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        //addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        //addDrop(GallifreyModBlocks.ULANDA_LOG);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        //addDrop(GallifreyModBlocks.ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.ULANDA_PLANKS);

        //MOON-PINE WOOD SET BLOCK DROPS
        //addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        //addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        //addDrop(GallifreyModBlocks.ULANDA_LOG);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        //addDrop(GallifreyModBlocks.ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.ULANDA_PLANKS);

        // MISC BLOCK DROPS
        addDrop(GallifreyModBlocks.REINFORCED_STEEL_BLOCK);

        // Skaro blocks
        addDrop(GallifreyModBlocks.EXQUISITE_CAT);
        addDrop(GallifreyModBlocks.GOOD_HEAVENS);
        addDrop(GallifreyModBlocks.COBBLED_KALETITE);
        addDrop(GallifreyModBlocks.KALETITE);
        addDrop(GallifreyModBlocks.KALETITE_BRICKS);
        addDrop(GallifreyModBlocks.WASTED_DIRT);
        addDrop(GallifreyModBlocks.WASTED_GRASS);
        addDrop(GallifreyModBlocks.WASTED_LEAVES);
        addDrop(GallifreyModBlocks.WASTED_LOG);
        addDrop(GallifreyModBlocks.WASTED_PLANKS);
        addDrop(GallifreyModBlocks.WASTED_PLANK_SLAB, slabDrops(GallifreyModBlocks.WASTED_PLANK_SLAB));
        addDrop(GallifreyModBlocks.DALEKANIUM_BLOCK);
        addDrop(GallifreyModBlocks.DALEKANIUM_ORE);
        addDrop(GallifreyModBlocks.DEEPSLATE_DALEKANIUM_ORE);

    }
}