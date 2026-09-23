package com.timelordmod.gallifrey.world.dimension;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

/**
 * Registry keys for Gallifrey's runtime dimensions.
 *
 * The dimensions themselves use vanilla dimension types in their datapack JSONs.
 * This keeps them available during the server's dynamic-registry load and avoids
 * custom dimension-type ordering problems with worldgen mods such as Biolith.
 */
public final class ModDimensions {
    private ModDimensions() {}

    public static final RegistryKey<World> MARS_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("mars"));

    public static final RegistryKey<World> GALL_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("gallifrey"));
}
