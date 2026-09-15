package com.timelordmod.gallifrey.entity;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBoats {
    public static final Identifier TARDIS_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "tardis_boat");
    public static final Identifier TARDIS_CHEST_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "tardis_chest_boat");

    public static final Identifier ULANDA_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "ulanda_boat");
    public static final Identifier ULANDA_CHEST_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "ulanda_chest_boat");

    public static final Identifier TREEBORG_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "treeborg_boat");
    public static final Identifier TREEBORG_CHEST_BOAT_ID = new Identifier(GallifreyMod.MOD_ID, "treeborg_chest_boat");

    public static final RegistryKey<TerraformBoatType> TARDIS_BOAT_KEY = TerraformBoatTypeRegistry.createKey(TARDIS_BOAT_ID);
    public static final RegistryKey<TerraformBoatType> ULANDA_BOAT_KEY = TerraformBoatTypeRegistry.createKey(ULANDA_BOAT_ID);
    public static final RegistryKey<TerraformBoatType> TREEBORG_BOAT_KEY = TerraformBoatTypeRegistry.createKey(TREEBORG_BOAT_ID);

    public static void registerBoats() {
        TerraformBoatType tardisBoat = new TerraformBoatType.Builder()
                .item(GallifreyModItems.TARDIS_BOAT)
                .chestItem(GallifreyModItems.TARDIS_CHEST_BOAT)
                .planks(GallifreyModBlocks.TARDIS_PLANKS.asItem())
                .build();

        TerraformBoatType ulandaBoat = new TerraformBoatType.Builder()
                .item(GallifreyModItems.ULANDA_BOAT)
                .chestItem(GallifreyModItems.ULANDA_CHEST_BOAT)
                .planks(GallifreyModBlocks.ULANDA_PLANKS.asItem())
                .build();

        TerraformBoatType treeborgBoat = new TerraformBoatType.Builder()
                .item(GallifreyModItems.TREEBORG_BOAT)
                .chestItem(GallifreyModItems.TREEBORG_CHEST_BOAT)
                .planks(GallifreyModBlocks.TREEBORG_PLANKS.asItem())
                .build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, ULANDA_BOAT_KEY, ulandaBoat);

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, TARDIS_BOAT_KEY, tardisBoat);

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, TREEBORG_BOAT_KEY, treeborgBoat);
    }
}
