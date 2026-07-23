package com.timelordmod.gallifrey.item;

import com.timelordmod.gallifrey.GallifreyMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


/**
 * Central registry for all items added by the Regeneration mod.
 *
 * To add new items in the future:
 *  1. Create the Item or subclass.
 *  2. Add a static field here.
 *  3. Register it in the register() block below.
 *  4. Add a model JSON in assets/regeneration/models/item/
 *  5. Add a texture in assets/regeneration/textures/item/
 */
public class GallifreyModItems {
    // -----------------------------------------------------------------------
    // Item constants
    // -----------------------------------------------------------------------

    public static final WhitePointStarItem WHITE_POINT_STAR = new WhitePointStarItem(
            new FabricItemSettings()
    );

    // -----------------------------------------------------------------------
    // Registration
    // -----------------------------------------------------------------------
    public static void register() {
        registerItem("white_point_star", WHITE_POINT_STAR);

        GallifreyMod.LOGGER.debug("[Regeneration] Items registered.");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name), item);
    }
}
