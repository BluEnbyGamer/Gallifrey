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
    public static final RegistryKey<Biome> GALLIFREY_ULANDA_FOREST = RegistryKey.of(
            RegistryKeys.BIOME, new Identifier(GallifreyMod.MOD_ID, "ulanda_forest"));

    public static void bootstrap(Registerable<Biome> context) {
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        var carvers = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        context.register(GALLIFREY_ULANDA_FOREST, new Biome.Builder()
                .precipitation(false)
                .temperature(2.0F)
                .downfall(0.0F)
                .effects(new BiomeEffects.Builder()
                        .fogColor(10638337)
                        .skyColor(15105551)
                        .waterColor(4159204)
                        .waterFogColor(2635560)
                        .grassColor(14641191)
                        .foliageColor(15109680)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build())
                .spawnSettings(spawnSettings.build())
                .build());

    }
}
