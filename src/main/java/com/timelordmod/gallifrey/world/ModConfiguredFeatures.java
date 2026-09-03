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
    //public static final RegistryKey<ConfiguredFeature<?, ?>> ****_ORE_KEY = registerKey("****_ore");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_****_ORE_KEY = registerKey("nether_****_ore");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> END_****_ORE_KEY = registerKey("end_****_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> TARDIS_TREE_KEY =registerKey("tardis_tree");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ULANDA_KEY =registerKey("ulanda");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        // RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        // RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        // RuleTest netherReplacables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        // RuleTest endReplacables = new BlockMatchRuleTest(Blocks.END_STONE);

        // List<OreFeatureConfig.Target> overworldRubyOres =
        // List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.****_ORE.getDefaultState()),
        // OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_****_ORE.getDefaultState()));

        // List<OreFeatureConfig.Target> nether****Ores =
        //  List.of(OreFeatureConfig.createTarget(netherReplacables, ModBlocks.NETHER_****_ORE.getDefaultState()));

        // List<OreFeatureConfig.Target> end****Ores =
        // List.of(OreFeatureConfig.createTarget(endReplacables, ModBlocks.END_STONE_****_ORE.getDefaultState()));

        // register(context, ****_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworld****Ores, 12));
        // register(context, NETHER_****_ORE_KEY, Feature.ORE, new OreFeatureConfig(nether****Ores, 12));
        // register(context, END_****_ORE_KEY, Feature.ORE, new OreFeatureConfig(end****Ores, 12));
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
                                UniformIntProvider.create(4, 6),
                                0.25F,
                                0.5F,
                                0.16666667F,
                                0.33333334F
                        ),

                        new TwoLayersFeatureSize(1, 0, 2)
                )
                        .build()
        );

        register(context, TARDIS_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(GallifreyModBlocks.TARDIS_LOG),
                new StraightTrunkPlacer(5, 4, 3),

                BlockStateProvider.of(GallifreyModBlocks.TARDIS_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),

                new TwoLayersFeatureSize(1, 0, 2)).build());
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(GallifreyMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}