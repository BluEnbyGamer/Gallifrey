package com.timelordmod.gallifrey.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.entity.ModBoats;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;
import com.timelordmod.gallifrey.item.custom.HeadwearItem;

public class GallifreyModItems {

    public static final WhitePointStarItem WHITE_POINT_STAR = new WhitePointStarItem(
            new FabricItemSettings()
    );

    public static final SonicCrystalItem SONIC_CRYSTAL = new SonicCrystalItem(
            new FabricItemSettings()
    );

    //public static final ChrononCoreItem CHRONON_CORE = new ChrononCoreItem(
            //new FabricItemSettings()
    //);

    public static final VortexManipulator VORTEX_MANIPULATOR = new VortexManipulator(
            new FabricItemSettings()
    );

    public static final BlankCircuitItem BLANK_CIRCUIT = new BlankCircuitItem(
            new FabricItemSettings()
    );

    public static final LocationCircuitItem LOCATION_CIRCUIT = new LocationCircuitItem(
            new FabricItemSettings()
    );

    public static final DimensionCircuitItem DIMENSION_CIRCUIT = new DimensionCircuitItem(
            new FabricItemSettings()
    );

    public static final Item TARDIS_SIGN = registerItem("tardis_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_TARDIS_SIGN, GallifreyModBlocks.WALL_TARDIS_SIGN));
    public static final Item HANGING_TARDIS_SIGN = registerItem("tardis_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_TARDIS_SIGN, GallifreyModBlocks.WALL_HANGING_TARDIS_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item TARDIS_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TARDIS_BOAT_ID, ModBoats.TARDIS_BOAT_KEY, false);
    public static final Item TARDIS_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TARDIS_CHEST_BOAT_ID, ModBoats.TARDIS_BOAT_KEY, true);

    public static final Item ULANDA_SIGN = registerItem("ulanda_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_ULANDA_SIGN, GallifreyModBlocks.WALL_ULANDA_SIGN));
    public static final Item HANGING_ULANDA_SIGN = registerItem("ulanda_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_ULANDA_SIGN, GallifreyModBlocks.WALL_HANGING_ULANDA_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item ULANDA_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ULANDA_BOAT_ID, ModBoats.ULANDA_BOAT_KEY, false);
    public static final Item ULANDA_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ULANDA_CHEST_BOAT_ID, ModBoats.ULANDA_BOAT_KEY, true);

    public static final Item TREEBORG_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TREEBORG_BOAT_ID, ModBoats.TREEBORG_BOAT_KEY, false);
    public static final Item TREEBORG_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TREEBORG_CHEST_BOAT_ID, ModBoats.TREEBORG_BOAT_KEY, true);

    public static final Item TREEBORG_SIGN = registerItem("treeborg_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_TREEBORG_SIGN, GallifreyModBlocks.WALL_TREEBORG_SIGN));
    public static final Item HANGING_TREEBORG_SIGN = registerItem("treeborg_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_TREEBORG_SIGN, GallifreyModBlocks.WALL_HANGING_TREEBORG_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item ASH_SIGN = registerItem("ash_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_ASH_SIGN, GallifreyModBlocks.WALL_ASH_SIGN));
    public static final Item HANGING_ASH_SIGN = registerItem("ash_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_ASH_SIGN, GallifreyModBlocks.WALL_HANGING_ASH_SIGN, new FabricItemSettings().maxCount(16)));
    public static final Item ASH_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ASH_BOAT_ID, ModBoats.ASH_BOAT_KEY, false);
    public static final Item ASH_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ASH_CHEST_BOAT_ID, ModBoats.ASH_BOAT_KEY, true);



    // AWT clothing port: wearable without Trinkets (uses the vanilla head equipment slot).
    public static final HeadwearItem FEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem FANCYFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem PURPLEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem GREENFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem ORANGEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem BLUEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem DARKBLUEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem PINKFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem GREYFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem YELLOWFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem TRUSTABLE_HAT = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem EYESTALK = new HeadwearItem(new FabricItemSettings());

    public static final SonicScrewdriver SONIC_SCREWDRIVER =
            new SonicScrewdriver(
                    new FabricItemSettings()
                            .maxCount(1)
                            .maxDamage(100)
            );





    public static void register() {
        registerItem("white_point_star", WHITE_POINT_STAR);
        //registerItem("chronon_core", CHRONON_CORE);
        registerItem("vortex_manipulator", VORTEX_MANIPULATOR);
        registerItem("blank_circuit", BLANK_CIRCUIT);
        registerItem("location_circuit",LOCATION_CIRCUIT);
        registerItem("dimension_circuit",DIMENSION_CIRCUIT);
        registerItem("sonic_crystal",SONIC_CRYSTAL);

        registerItem("sonic_screwdriver", SONIC_SCREWDRIVER);
        registerItem("fez", FEZ);
        registerItem("fancyfez", FANCYFEZ);
        registerItem("purplefez", PURPLEFEZ);
        registerItem("greenfez", GREENFEZ);
        registerItem("orangefez", ORANGEFEZ);
        registerItem("bluefez", BLUEFEZ);
        registerItem("darkbluefez", DARKBLUEFEZ);
        registerItem("pinkfez", PINKFEZ);
        registerItem("greyfez", GREYFEZ);
        registerItem("yellowfez", YELLOWFEZ);
        registerItem("trustable_hat", TRUSTABLE_HAT);
        registerItem("eyestalk", EYESTALK);

        GallifreyMod.LOGGER.debug("[Gallifrey] Items registered.");
    }

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name), item);
    }
}

