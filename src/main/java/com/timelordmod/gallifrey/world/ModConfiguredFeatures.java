package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    //public static final RegistryKey<ConfiguredFeature<?, ?>> ****_ORE_KEY = registerKey("****_ore");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_****_ORE_KEY = registerKey("nether_****_ore");
    //public static final RegistryKey<ConfiguredFeature<?, ?>> END_****_ORE_KEY = registerKey("end_****_ore");

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
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(GallifreyMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}