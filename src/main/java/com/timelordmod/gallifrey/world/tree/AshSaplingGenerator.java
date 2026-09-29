package com.timelordmod.gallifrey.world.tree;

import com.timelordmod.gallifrey.world.ModConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
;

public class AshSaplingGenerator extends SaplingGenerator {

    private static final RegistryKey<ConfiguredFeature<?, ?>> ASH_TREE =
            RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("gallifrey", "ash_tree"));

    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ASH_TREE;
    }
}
