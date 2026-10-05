package com.timelordmod.gallifrey.world.dimension;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

/**
 * Registry keys for Gallifrey's runtime dimensions.
 *
 * Registry keys used by the custom dimensions. Dimension types and generators
 * are supplied by the datapack resources in data/gallifrey.
 */
public final class ModDimensions {
    private ModDimensions() {}

    public static final RegistryKey<World> MARS_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("mars"));

    public static final RegistryKey<World> GALL_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("gallifrey"));

    public static final RegistryKey<World> SKARO_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("skaro"));

    public static final RegistryKey<World> PREHISTORIC_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("prehistoric"));

    public static final RegistryKey<World> MONDAS_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("mondas"));

    /** Pete's World: a vanilla-style Overworld that uses its own, derived seed. */
    public static final RegistryKey<World> PETES_WORLD_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, GallifreyMod.id("petes_world"));
}