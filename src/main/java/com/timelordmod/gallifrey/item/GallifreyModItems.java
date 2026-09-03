package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GallifreyModItems {

    public static final WhitePointStarItem WHITE_POINT_STAR = new WhitePointStarItem(
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

    public static final Item ULANDA_SIGN = registerItem("ulanda_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_ULANDA_SIGN, GallifreyModBlocks.WALL_ULANDA_SIGN));
    public static final Item HANGING_ULANDA_SIGN = registerItem("ulanda_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_ULANDA_SIGN, GallifreyModBlocks.WALL_HANGING_ULANDA_SIGN, new FabricItemSettings().maxCount(16)));



    public static void register() {
        registerItem("white_point_star", WHITE_POINT_STAR);
        //registerItem("chronon_core", CHRONON_CORE);
        registerItem("vortex_manipulator", VORTEX_MANIPULATOR);
        registerItem("blank_circuit", BLANK_CIRCUIT);
        registerItem("location_circuit",LOCATION_CIRCUIT);
        registerItem("dimension_circuit",DIMENSION_CIRCUIT);

        GallifreyMod.LOGGER.debug("[Gallifrey] Items registered.");
    }

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name), item);
    }
}

