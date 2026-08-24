package com.timelordmod.gallifrey.world.dimension;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class ModDimensions {

    public static final RegistryKey<World> GALL_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, new Identifier(GallifreyMod.MOD_ID, "gallifrey"));
    public static final RegistryKey<DimensionType> GALL_DIM_TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, new Identifier(GallifreyMod.MOD_ID, "gallifrey_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(GALL_DIM_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0D,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.OVERWORLD_ID,
                0.0F,
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 7), 0)
        ));
    }
}
