package com.timelordmod.gallifrey.entity;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.entity.custom.LaserEntity;
import com.timelordmod.gallifrey.entity.custom.SkaroCityDalekEntity;
import com.timelordmod.gallifrey.entity.custom.SkaroCityDalekAltEntity;
import com.timelordmod.gallifrey.entity.custom.SupremeCouncilDalekEntity;
import com.timelordmod.gallifrey.entity.custom.AlphaGhastEntity;
import com.timelordmod.gallifrey.entity.custom.AlphaZombiePigmanEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GallifreyEntities {

    public static final EntityType<AlphaGhastEntity> ALPHA_GHAST = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "alpha_ghast"),
            FabricEntityTypeBuilder.<AlphaGhastEntity>create(SpawnGroup.MONSTER, AlphaGhastEntity::new)
                    .dimensions(EntityDimensions.fixed(4.0f, 4.0f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(3)
                    .build());

    public static final EntityType<AlphaZombiePigmanEntity> ALPHA_ZOMBIE_PIGMAN = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "alpha_zombie_pigman"),
            FabricEntityTypeBuilder.<AlphaZombiePigmanEntity>create(SpawnGroup.MONSTER, AlphaZombiePigmanEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .trackRangeBlocks(48)
                    .trackedUpdateRate(3)
                    .build());

    public static final EntityType<SkaroCityDalekEntity> SKARO_CITY_DALEK = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "skaro_city_dalek"),
            FabricEntityTypeBuilder.<SkaroCityDalekEntity>create(SpawnGroup.MONSTER, SkaroCityDalekEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.9f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(2)
                    .build());

    public static final EntityType<SkaroCityDalekAltEntity> SKARO_CITY_DALEK_ALT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "skaro_city_dalek_alt"),
            FabricEntityTypeBuilder.<SkaroCityDalekAltEntity>create(SpawnGroup.MONSTER, SkaroCityDalekAltEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.9f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(2)
                    .build());

    public static final EntityType<SupremeCouncilDalekEntity> SUPREME_COUNCIL_DALEK = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "supreme_council_dalek"),
            FabricEntityTypeBuilder.<SupremeCouncilDalekEntity>create(SpawnGroup.MONSTER, SupremeCouncilDalekEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.9f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(2)
                    .build());

    public static final EntityType<LaserEntity> LASER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "laser"),
            FabricEntityTypeBuilder.<LaserEntity>create(SpawnGroup.MISC, LaserEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(1)
                    .build());
}
