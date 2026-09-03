package com.timelordmod.gallifrey.datagen;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        //WOOD TYPE POOLS
        BlockStateModelGenerator.BlockTexturePool ulandaPool = blockStateModelGenerator.registerCubeAllModelTexturePool(GallifreyModBlocks.ULANDA_PLANKS);
        BlockStateModelGenerator.BlockTexturePool tardiswoodPool = blockStateModelGenerator.registerCubeAllModelTexturePool(GallifreyModBlocks.TARDIS_PLANKS);
        tardiswoodPool.family(GallifreyModBlocks.TARDIS_FAMILY);
        ulandaPool.family(GallifreyModBlocks.ULANDA_FAMILY);

        //TARDIS WOOD TYPE
        blockStateModelGenerator.registerLog(GallifreyModBlocks.TARDIS_LOG).log(GallifreyModBlocks.TARDIS_LOG).wood(GallifreyModBlocks.TARDIS_WOOD);
        blockStateModelGenerator.registerLog(GallifreyModBlocks.STRIP_TARDIS_LOG).log(GallifreyModBlocks.STRIP_TARDIS_LOG).wood(GallifreyModBlocks.STRIP_TARDIS_WOOD);
        blockStateModelGenerator.registerDoor(GallifreyModBlocks.TARDIS_WOOD_DOOR);
        blockStateModelGenerator.registerTrapdoor(GallifreyModBlocks.TARDIS_TRAPDOOR);
        tardiswoodPool.stairs(GallifreyModBlocks.TARDIS_STAIRS);
        tardiswoodPool.slab(GallifreyModBlocks.TARDIS_SLAB);
        tardiswoodPool.button(GallifreyModBlocks.TARDIS_BUTTON);
        tardiswoodPool.pressurePlate(GallifreyModBlocks.TARDIS_PRESSURE_PLATE);
        tardiswoodPool.fence(GallifreyModBlocks.TARDIS_FENCE);
        tardiswoodPool.fenceGate(GallifreyModBlocks.TARDIS_FENCE_GATE);

        //ULANDA WOOD TYPE
        blockStateModelGenerator.registerLog(GallifreyModBlocks.ULANDA_LOG).log(GallifreyModBlocks.ULANDA_LOG).wood(GallifreyModBlocks.ULANDA_WOOD);
        blockStateModelGenerator.registerLog(GallifreyModBlocks.STRIP_ULANDA_LOG).log(GallifreyModBlocks.STRIP_ULANDA_LOG).wood(GallifreyModBlocks.STRIP_ULANDA_WOOD);
        blockStateModelGenerator.registerDoor(GallifreyModBlocks.ULANDA_DOOR);
        blockStateModelGenerator.registerTrapdoor(GallifreyModBlocks.ULANDA_TRAPDOOR);
        ulandaPool.stairs(GallifreyModBlocks.ULANDA_STAIRS);
        ulandaPool.slab(GallifreyModBlocks.ULANDA_SLAB);
        ulandaPool.button(GallifreyModBlocks.ULANDA_BUTTON);
        ulandaPool.pressurePlate(GallifreyModBlocks.ULANDA_PRESSURE_PLATE);
        ulandaPool.fence(GallifreyModBlocks.ULANDA_FENCE);
        ulandaPool.fenceGate(GallifreyModBlocks.ULANDA_FENCE_GATE);

        //TREE-BORG WOOD TYPE


        //ASH WOOD TYPE


        //MAPLE WOOD TYPE


        //WILLOW WOOD TYPE


        //MOON-PINE WOOD TYPE


        // MISC BLOCKS REGISTRY
        blockStateModelGenerator.registerSingleton(GallifreyModBlocks.TARDIS_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerSingleton(GallifreyModBlocks.ULANDA_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerFlowerPotPlant(GallifreyModBlocks.TARDIS_SAPLING, GallifreyModBlocks.POTTED_TARDIS_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(GallifreyModBlocks.ULANDA_SAPLING, GallifreyModBlocks.POTTED_ULANDA_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(GallifreyModItems.HANGING_TARDIS_SIGN, Models.GENERATED);
        itemModelGenerator.register(GallifreyModItems.HANGING_ULANDA_SIGN, Models.GENERATED);
    }
}