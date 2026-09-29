package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.CherryTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> SONIC_CRYSTAL_ORE_KEY = registerKey("sonic_crystal_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_SONIC_CRYSTAL_ORE_KEY = registerKey("nether_sonic_crystal_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_POINT_ORE_KEY = registerKey("white_point_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_WHITE_POINT_ORE_KEY = registerKey("nether_white_point_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MARS_IRON_ORE_KEY = registerKey("mars_iron_ore");


    public static final RegistryKey<ConfiguredFeature<?, ?>> ULANDA_KEY =registerKey("ulanda");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TARDIS_TREE_KEY =registerKey("tardis_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TREEBORG_KEY =registerKey("treeborg");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASH_KEY =registerKey("ash");;

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplacables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest marsReplacables = new BlockMatchRuleTest(GallifreyModBlocks.MARS_STONE);

        // --- Sonic Crystal ---
        List<OreFeatureConfig.Target> overworldSonicCrystalOres = List.of(
                OreFeatureConfig.createTarget(stoneReplacables, GallifreyModBlocks.SONIC_CRYSTAL_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplacables, GallifreyModBlocks.DEEPSLATE_SONIC_CRYSTAL_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherSonicCrystalOres = List.of(
                OreFeatureConfig.createTarget(netherReplacables, GallifreyModBlocks.NETHER_SONIC_CRYSTAL_ORE.getDefaultState()));

        register(context, SONIC_CRYSTAL_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSonicCrystalOres, 12));
        register(context, NETHER_SONIC_CRYSTAL_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherSonicCrystalOres, 12));

        // --- White Point ---
        List<OreFeatureConfig.Target> overworldWhitePointOres = List.of(
                OreFeatureConfig.createTarget(stoneReplacables, GallifreyModBlocks.WHITE_POINT_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplacables, GallifreyModBlocks.DEEPSLATE_WHITE_POINT_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherWhitePointOres = List.of(
                OreFeatureConfig.createTarget(netherReplacables, GallifreyModBlocks.NETHER_WHITE_POINT_ORE.getDefaultState()));

        register(context, WHITE_POINT_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldWhitePointOres, 12));
        register(context, NETHER_WHITE_POINT_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherWhitePointOres, 12));

        // --- Mars Iron ---
        List<OreFeatureConfig.Target> marsIronOres = List.of(
                OreFeatureConfig.createTarget(marsReplacables, GallifreyModBlocks.MARS_IRON_ORE.getDefaultState()));

        register(context, MARS_IRON_ORE_KEY, Feature.ORE, new OreFeatureConfig(marsIronOres, 8));

        register(context,ULANDA_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(GallifreyModBlocks.ULANDA_LOG),
                        new CherryTrunkPlacer(
                                7,1,2,
                                UniformIntProvider.create(1,3),
                                UniformIntProvider.create(4,6),
                                UniformIntProvider.create(-4,-2),
                                UniformIntProvider.create(-1,1)
                        ),
                        BlockStateProvider.of(GallifreyModBlocks.ULANDA_LEAVES),
                        new CherryFoliagePlacer(
                                ConstantIntProvider.create(4),
                                ConstantIntProvider.create(2),
                                UniformIntProvider.create(4, 5),
                                0.25F,
                                0.5F,
                                0.16666667F,
                                0.33333334F
                        ),

                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .build()
        );

        register(context, TREEBORG_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(GallifreyModBlocks.TREEBORG_LOG),
                new StraightTrunkPlacer(5, 4, 3),

                BlockStateProvider.of(GallifreyModBlocks.TREEBORG_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(2), 4),

                new TwoLayersFeatureSize(1, 0, 3)).build());

        register(context, ASH_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(GallifreyModBlocks.ASH_LOG),
                        new StraightTrunkPlacer(5, 4, 3),

                        BlockStateProvider.of(GallifreyModBlocks.ASH_LEAVES),
                        new CherryFoliagePlacer(
                                ConstantIntProvider.create(4),
                                ConstantIntProvider.create(2),
                                UniformIntProvider.create(4, 5),
                                0.25F,
                                0.5F,
                                0.16666667F,
                                0.33333334F
                        ),

                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .build()
        );

    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(GallifreyMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}