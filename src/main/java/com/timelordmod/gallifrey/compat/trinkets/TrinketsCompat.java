package com.timelordmod.gallifrey.compat.trinkets;

import com.timelordmod.gallifrey.GallifreyMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * Trinkets support (Fabric 1.20.1, Trinkets 3.7).
 *
 *  - The Vortex Manipulator can be worn in either glove slot (it shows on your wrist), and still
 *    works from there: the Vortex Manipulator key (K by default) opens it, and the server finds
 *    it there for every jump. See TrinketsClientCompat for the key and the rendering.
 *  - The Sonic Screwdriver can be clipped into the belt slot (it hangs on your hip).
 *  - The slots are switched on for players in data/trinkets/entities/gallifrey.json, and the
 *    items allowed in them are in data/trinkets/tags/items/... Those files do nothing without
 *    Trinkets. (The Sonic Shades and fezzes already have Gallifrey's own slots, so they don't
 *    take up Trinkets slots too.)
 *
 * No compile-time dependency: the few Trinkets calls are made by reflection, and nothing here
 * runs unless Trinkets is installed. Checked against Trinkets' 1.20.1 source.
 */
public final class TrinketsCompat {
    private TrinketsCompat() {}

    public static final String MOD_ID = "trinkets";

    private static boolean loaded;
    private static Method getTrinketComponent;   // TrinketsApi.getTrinketComponent(LivingEntity) -> Optional<TrinketComponent>
    private static Method getEquipped;           // TrinketComponent.getEquipped(Item) -> List<Pair<SlotReference, ItemStack>>

    public static boolean isLoaded() {
        return loaded;
    }

    public static void register() {
        if (!FabricLoader.getInstance().isModLoaded(MOD_ID)) return;
        try {
            Class<?> api = Class.forName("dev.emi.trinkets.api.TrinketsApi");
            Class<?> component = Class.forName("dev.emi.trinkets.api.TrinketComponent");
            getTrinketComponent = api.getMethod("getTrinketComponent", LivingEntity.class);
            getEquipped = component.getMethod("getEquipped", Item.class);
            loaded = true;
            GallifreyMod.LOGGER.info("[Gallifrey] Trinkets found: the Vortex Manipulator and sonic can be worn in trinket slots");
        } catch (ReflectiveOperationException e) {
            GallifreyMod.LOGGER.warn("[Gallifrey] Trinkets is installed but its API has changed; trinket slot support is off", e);
        }
    }

    /** The first stack of {@code item} worn in any trinket slot, or EMPTY. */
    public static ItemStack findWorn(LivingEntity entity, Item item) {
        if (!loaded || entity == null) return ItemStack.EMPTY;
        try {
            Optional<?> component = (Optional<?>) getTrinketComponent.invoke(null, entity);
            if (component.isEmpty()) return ItemStack.EMPTY;
            List<?> equipped = (List<?>) getEquipped.invoke(component.get(), item);
            for (Object entry : equipped) {
                if (entry instanceof Pair<?, ?> pair && pair.getRight() instanceof ItemStack stack && !stack.isEmpty()) {
                    return stack;
                }
            }
        } catch (ReflectiveOperationException | RuntimeException e) {
            // Treat any Trinkets hiccup as "not worn" rather than crashing.
        }
        return ItemStack.EMPTY;
    }
}
