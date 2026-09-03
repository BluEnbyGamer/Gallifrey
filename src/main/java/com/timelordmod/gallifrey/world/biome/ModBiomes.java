package com.timelordmod.gallifrey.world.biome;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.world.ModPlacedFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Consumer;

public class ModBiomes {

    //declare all keys here
    public static final RegistryKey<Biome> GALLIFREYAN_PLAINS = registerKey("gallifreyan_plains");
    public static final RegistryKey<Biome> GALLIFREYAN_BIRCH_FOREST = registerKey("gallifreyan_birch_forest");
    public static final RegistryKey<Biome> GALLIFREYAN_TAIGA = registerKey("gallifreyan_taiga");
    public static final RegistryKey<Biome> GALLIFREYAN_ULANDA_FOREST = registerKey("gallifreyan_ulanda_forest");
    public static final RegistryKey<Biome> GALLIFREYAN_TARDIS_BIOME = registerKey("gallifreyan_tardis_biome");

    private static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(GallifreyMod.MOD_ID, name));
    }

    public static void bootstrap(Registerable<Biome> context) {
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        var carvers = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        //Register all biomes easily
        context.register(GALLIFREYAN_PLAINS, createBiome(false, 0.8F, 0.4F, 10638337, 15105551, 14641191, 15109680,
                placedFeatures, carvers, generationSettings -> {
                    DefaultBiomeFeatures.addPlainsTallGrass(generationSettings);
                    DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
                    DefaultBiomeFeatures.addDefaultGrass(generationSettings);
                }));

        context.register(GALLIFREYAN_BIRCH_FOREST, createBiome(false, 0.6F, 0.6F, 10638337, 15105551, 14641191, 15109680,
                placedFeatures, carvers, generationSettings -> {
                    DefaultBiomeFeatures.addBirchTrees(generationSettings);
                    DefaultBiomeFeatures.addLargeFerns(generationSettings);
                    DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
                    DefaultBiomeFeatures.addDefaultGrass(generationSettings);
                }));

        context.register(GALLIFREYAN_TAIGA, createBiome(false, 0.25F, 0.8F, 10638337, 15105551, 14641191, 15109680,
                placedFeatures, carvers, generationSettings -> {
                    DefaultBiomeFeatures.addTaigaTrees(generationSettings);
                    DefaultBiomeFeatures.addSweetBerryBushes(generationSettings);
                    DefaultBiomeFeatures.addDefaultGrass(generationSettings);
                }));

        // New: Ulanda Forest - dense Ulanda tree cover via the ULANDA_PLACED_KEY placed
        // feature (registered in ModPlacedFeatures), not a DefaultBiomeFeatures call,
        // since this is our own custom tree, not a vanilla default
        context.register(GALLIFREYAN_ULANDA_FOREST, createBiome(false, 0.5F, 0.6F, 10638337, 15105551, 14641191, 15109680,
                placedFeatures, carvers, generationSettings -> {
                    generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ULANDA_PLACED_KEY);
                    DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
                    DefaultBiomeFeatures.addDefaultGrass(generationSettings);
                }));

        // New: Tardis biome - placed at an extreme continentalness value in the
        // dimension's biome source (see gallifrey.json), matching real mushroom
        // fields rarity rather than approximating it with a placement chance
        context.register(GALLIFREYAN_TARDIS_BIOME, createBiome(false, 0.5F, 0.5F, 10638337, 15105551, 15215, 15215,
                placedFeatures, carvers, generationSettings -> {
                    generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TARDIS_PLACED_KEY);
                    DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
                    DefaultBiomeFeatures.addDefaultGrass(generationSettings);
                }));
    }

    // Helper method to eliminate copy-pasted boilerplate. featureCustomizer supplies
    // whatever's unique to that biome (its trees, its extra plants); the shared
    // defaults (carvers, ores, mushrooms) are added here once for all 4 biomes.
    private static Biome createBiome(boolean precipitation, float temp, float downfall, int fog, int sky, int grass, int foliage,
                                     RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> carvers,
                                     Consumer<GenerationSettings.LookupBackedBuilder> featureCustomizer) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(placedFeatures, carvers);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        featureCustomizer.accept(generationSettings);

        return new Biome.Builder()
                .precipitation(precipitation)
                .temperature(temp)
                .downfall(downfall)
                .effects(new BiomeEffects.Builder()
                        .fogColor(fog)
                        .skyColor(sky)
                        .waterColor(4159204)
                        .waterFogColor(2635560)
                        .grassColor(grass)
                        .foliageColor(foliage)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}