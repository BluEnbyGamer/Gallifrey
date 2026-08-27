package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.datagen.ModItemTagProvider;
import com.timelordmod.gallifrey.datagen.ModLootTableProvider;
import com.timelordmod.gallifrey.datagen.ModWorldGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GallifreyModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModWorldGenerator::new);
	}
}
