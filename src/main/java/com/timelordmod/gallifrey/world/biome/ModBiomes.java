package com.timelordmod.gallifrey.world.biome;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.SpawnSettings;

public class ModBiomes {

    //declare all keys here
    public static final RegistryKey<Biome> GALLIFREYAN_PLAINS = registerKey("gallifreyan_plains");
    public static final RegistryKey<Biome> GALLIFREYAN_BIRCH_FOREST = registerKey("gallifreyan_birch_forest");
    public static final RegistryKey<Biome> GALLIFREYAN_TAIGA = registerKey("gallifreyan_taiga");

    private static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(GallifreyMod.MOD_ID, name));
    }

    public static void bootstrap(Registerable<Biome> context) {
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        var carvers = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        //Register all biomes easily
        context.register(GALLIFREYAN_PLAINS, createBiome(false, 0.8F, 0.4F, 10638337, 15105551, 14641191, 15109680));
        context.register(GALLIFREYAN_BIRCH_FOREST, createBiome(false, 0.6F, 0.6F, 10638337, 15105551, 14641191, 15109680));
        context.register(GALLIFREYAN_TAIGA, createBiome(false, 0.25F, 0.8F, 10638337, 15105551, 14641191, 15109680));
    }

    // Helper method to eliminate copy-pasted boilerplate
    private static Biome createBiome(boolean precipitation, float temp, float downfall, int fog, int sky, int grass, int foliage) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

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
                .build();
    }
}