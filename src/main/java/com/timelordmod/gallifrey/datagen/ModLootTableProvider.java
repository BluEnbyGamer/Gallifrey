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
        //addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        //addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        //addDrop(GallifreyModBlocks.ULANDA_LOG);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        //addDrop(GallifreyModBlocks.ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        //addDrop(GallifreyModBlocks.ULANDA_PLANKS);

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
    }
}