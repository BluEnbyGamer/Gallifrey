package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.datagen.*;
import com.timelordmod.gallifrey.world.ModConfiguredFeatures;
import com.timelordmod.gallifrey.world.ModPlacedFeatures;
import com.timelordmod.gallifrey.world.biome.ModBiomes;
import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class GallifreyModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		// Exports whatever buildRegistry() below populates - biomes, configured
		// features, placed features, dimension type - to actual JSON. Without this,
		// buildRegistry() alone still wouldn't produce any files.
		pack.addProvider(ModWorldGenerator::new);
	}

	// Populates the dynamic registries from their bootstrap functions. This is the
	// piece that was missing entirely before - ModBiomes.bootstrap, ModConfiguredFeatures.bootstrap,
	// ModPlacedFeatures.boostrap (existing typo, not renamed here - renaming it would
	// require also renaming the method itself, which is out of scope for this fix),
	// and ModDimensions.bootstrapType were all real, correct code that nothing ever called.
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::boostrap);
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ModDimensions::bootstrapType);
	}
}