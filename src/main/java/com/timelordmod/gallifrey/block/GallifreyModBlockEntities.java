package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class GallifreyModBlockEntities {

    public static final BlockEntityType<TardisExteriorBlockEntity> TARDIS_EXTERIOR =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    GallifreyMod.id("tardis_exterior"),
                    FabricBlockEntityTypeBuilder.create(
                            TardisExteriorBlockEntity::new,
                            GallifreyModBlocks.TARDIS_EXTERIOR
                    ).build()
            );

    public static void register() {
        GallifreyMod.LOGGER.info("Registering Block Entities for " + GallifreyMod.MOD_ID);
    }
}
