package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    //public static final RegistryKey<PlacedFeature> ****_ORE_PLACED_KEY = registerKey("****_ore_placed");
    //public static final RegistryKey<PlacedFeature> NETHER_****_ORE_PLACED_KEY = registerKey("nether_****_ore_placed");
    //public static final RegistryKey<PlacedFeature> END_****_ORE_PLACED_KEY = registerKey("end_****_ore_placed");

    // Dense forest placement - 10 attempts per chunk, unlike a rare single-tree feature
    public static final RegistryKey<PlacedFeature> ULANDA_PLACED_KEY = registerKey("ulanda_placed");

    // Sparser than Ulanda - this tree lives in a biome that's already rare by design,
    // so a dense forest would undercut that "special, uncommon" feel
    public static final RegistryKey<PlacedFeature> TARDIS_PLACED_KEY = registerKey("tardis_placed");

    public static void boostrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, ULANDA_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ULANDA_KEY),
                List.of(
                        CountPlacementModifier.of(10),
                        SquarePlacementModifier.of(),
                        HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES),
                        PlacedFeatures.wouldSurvive(GallifreyModBlocks.ULANDA_SAPLING),
                        BiomePlacementModifier.of()
                ));

        register(context, TARDIS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.TARDIS_TREE_KEY),
                List.of(
                        CountPlacementModifier.of(2),
                        SquarePlacementModifier.of(),
                        HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES),
                        PlacedFeatures.wouldSurvive(GallifreyModBlocks.TARDIS_SAPLING),
                        BiomePlacementModifier.of()
                ));

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