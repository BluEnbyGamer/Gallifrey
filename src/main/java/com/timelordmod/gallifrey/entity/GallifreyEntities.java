package com.timelordmod.gallifrey.entity;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.entity.custom.LaserEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GallifreyEntities {

    public static final EntityType<LaserEntity> LASER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GallifreyMod.MOD_ID, "laser"),
            FabricEntityTypeBuilder.<LaserEntity>create(SpawnGroup.MISC, LaserEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(1)
                    .build());
}
