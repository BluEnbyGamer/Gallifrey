package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    //public static final RegistryKey<PlacedFeature> ****_ORE_PLACED_KEY = registerKey("****_ore_placed");
    //public static final RegistryKey<PlacedFeature> NETHER_****_ORE_PLACED_KEY = registerKey("nether_****_ore_placed");
    //public static final RegistryKey<PlacedFeature> END_****_ORE_PLACED_KEY = registerKey("end_****_ore_placed");

    public static void boostrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        //register(context, ****_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.****_ORE_KEY),
                //ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        //HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        //register(context, NETHER_****_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_****_ORE_KEY),
                //ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        //HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        //register(context, END_****_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_RUBY_ORE_KEY),
                //ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        //HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(GallifreyMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}