package com.timelordmod.gallifrey.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

/**
 * Intentionally generates nothing.
 *
 * Datagen always writes textures as "gallifrey:block/<block id>", in one flat folder.
 * The textures are now sorted into sub-folders (block/ulanda_wood/, item/steel/, ...),
 * so every generated model pointed at a file that is no longer there.
 *
 * All blockstates and models are now ordinary hand-edited files in
 *   src/main/resources/assets/gallifrey/blockstates
 *   src/main/resources/assets/gallifrey/models
 * To add a new block, copy the files of a similar block there and change the names.
 *
 * Do NOT add generator calls back in here unless the textures for that block
 * sit directly in textures/block/ or textures/item/ again.
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
