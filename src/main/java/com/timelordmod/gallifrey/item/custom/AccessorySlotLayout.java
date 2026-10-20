package com.timelordmod.gallifrey.item.custom;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Where the Sonic Shades and fez slots sit in the inventory screens (x/y relative to the screen's
 * top-left corner, as Minecraft's Slot uses).
 *
 * Normally they're in the column between the player preview and the crafting grid, above the
 * offhand slot. Trinkets puts its own slot groups in that same column (and, in the creative tab,
 * right of the armour), so when Trinkets is installed both slots move somewhere Trinkets never
 * uses: side by side under the crafting grid, right of the recipe book button, and in the
 * creative tab, under Trinkets' row. Trinkets isn't needed for any of this; it's only checked
 * for.
 */
public final class AccessorySlotLayout {
    private AccessorySlotLayout() {}

    public static final boolean TRINKETS = FabricLoader.getInstance().isModLoaded("trinkets");

    // Survival inventory.
    public static final int SHADES_X = TRINKETS ? 134 : 77;
    public static final int SHADES_Y = TRINKETS ? 62 : 8;
    public static final int FEZ_X = TRINKETS ? 152 : 77;
    public static final int FEZ_Y = TRINKETS ? 62 : 26;

    // Creative inventory tab.
    public static final int CREATIVE_SHADES_X = 127;
    public static final int CREATIVE_SHADES_Y = TRINKETS ? 37 : 6;
    public static final int CREATIVE_FEZ_X = TRINKETS ? 146 : 127;
    public static final int CREATIVE_FEZ_Y = TRINKETS ? 37 : 33;
}
