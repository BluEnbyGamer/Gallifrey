package com.timelordmod.gallifrey.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

/**
 * Gallifrey's two extra wearable slots, shown in the player inventory:
 *  - the Sonic Shades slot (Sonic Shades only)
 *  - the fez slot (anything in the gallifrey:fezzes item tag)
 *
 * The stacks live on the player (SonicShadesSlotMixin), are synced to every client so other
 * players see them, are saved with the player, drop on death (kept with keepInventory), and
 * render on the head exactly like a helmet-slot item (AccessoryFeatureRenderer).
 */
public final class GallifreyAccessories {
    private GallifreyAccessories() {}

    public static final TagKey<Item> FEZZES = TagKey.of(RegistryKeys.ITEM, new Identifier("gallifrey", "fezzes"));

    public static boolean fitsShadesSlot(ItemStack stack) {
        return stack.getItem() instanceof SonicShadesItem;
    }

    public static boolean fitsFezSlot(ItemStack stack) {
        return stack.isIn(FEZZES);
    }

    public static ItemStack getShades(PlayerEntity player) {
        return ((SonicShadesSlotAccess) player).gallifrey$getSonicShades();
    }

    public static void setShades(PlayerEntity player, ItemStack stack) {
        ((SonicShadesSlotAccess) player).gallifrey$setSonicShades(stack);
    }

    public static ItemStack getFez(PlayerEntity player) {
        return ((FezSlotAccess) player).gallifrey$getFez();
    }

    public static void setFez(PlayerEntity player, ItemStack stack) {
        ((FezSlotAccess) player).gallifrey$setFez(stack);
    }

    /**
     * Call after changing a worn stack in place (its NBT, say), so the change is sent to clients.
     * Setting the slot again is what tells Minecraft it changed.
     */
    public static void resyncShades(PlayerEntity player) {
        setShades(player, getShades(player));
    }

    public static void register() {
        // Keep the slots across respawning (with keepInventory) and returning from the End.
        // Without keepInventory they were already dropped on death (SonicShadesSlotMixin).
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (alive || newPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
                setShades(newPlayer, getShades(oldPlayer));
                setFez(newPlayer, getFez(oldPlayer));
            }
        });
    }
}
