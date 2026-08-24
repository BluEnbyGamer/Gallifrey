package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GallifreyModItems {

    public static final WhitePointStarItem WHITE_POINT_STAR = new WhitePointStarItem(
            new FabricItemSettings()
    );
    public static final VortexManipulator VORTEX_MANIPULATOR = new VortexManipulator(
            new FabricItemSettings()
    );

    public static void register() {
        registerItem("white_point_star", WHITE_POINT_STAR);
        registerItem("vortex_manipulator", VORTEX_MANIPULATOR);

        GallifreyMod.LOGGER.debug("[Regeneration] Items registered.");
    }

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name), item);
    }
}

