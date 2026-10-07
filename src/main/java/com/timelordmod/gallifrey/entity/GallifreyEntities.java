package com.timelordmod.gallifrey.entity;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.entity.custom.LaserEntity;
import com.timelordmod.gallifrey.entity.custom.SkaroCityDalekEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GallifreyEntities {

    public static final EntityType<SkaroCityDalekEntity> SKARO_CITY_DALEK = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "skaro_city_dalek"),
            FabricEntityTypeBuilder.<SkaroCityDalekEntity>create(SpawnGroup.MONSTER, SkaroCityDalekEntity::new)
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
