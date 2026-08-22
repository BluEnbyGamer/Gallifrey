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

        //ULANDA WOOD SET BLOCK DROPS
        addDrop(GallifreyModBlocks.ULANDA_SAPLING);
        addDrop(GallifreyModBlocks.ULANDA_LEAVES, leavesDrops(GallifreyModBlocks.ULANDA_LEAVES, GallifreyModBlocks.ULANDA_SAPLING, 0.0025f));
        addDrop(GallifreyModBlocks.ULANDA_LOG);
        addDrop(GallifreyModBlocks.STRIP_ULANDA_LOG);
        addDrop(GallifreyModBlocks.ULANDA_WOOD);
        addDrop(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        addDrop(GallifreyModBlocks.ULANDA_PLANKS);

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
    }
}