package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Lightweight standalone oxygen system for the Gallifrey mod's airless worlds.
 * A full suit (Gallifrey's own, or Ad Astra's when installed) stores 10 minutes of oxygen
 * in the chestplate's NBT. Oxygen refills
 * in the Overworld and the breathable Gallifrey/Skaro/prehistoric worlds.
 */
public final class SpaceSuitOxygenHandler {
    private static final String OXYGEN_KEY = "GallifreyOxygenTicks";
    private static final int MAX_OXYGEN_TICKS = 20 * 60 * 10;
    private SpaceSuitOxygenHandler() {}

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(SpaceSuitOxygenHandler::tickWorld);
    }

    private static void tickWorld(ServerWorld world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (isMoonWorld(world) && !hasFullSuit(player)) {
                if (world.getTime() % 20L == 0L) {
                    player.damage(world.getDamageSources().drown(), 4.0F);
                }
                continue;
            }
            if (!hasFullSuit(player)) continue;
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            var nbt = chest.getOrCreateNbt();
            if (!isVacuumWorld(world)) {
                // Refill the suit automatically whenever the player is outside a vacuum world.
                nbt.putInt(OXYGEN_KEY, MAX_OXYGEN_TICKS);
                continue;
            }
            int oxygen = nbt.contains(OXYGEN_KEY) ? nbt.getInt(OXYGEN_KEY) : MAX_OXYGEN_TICKS;
            if (oxygen > 0) player.setAir(Math.max(player.getAir(), 300));
            if (world.getTime() % 20L != 0L) continue;
            if (oxygen > 0) {
                oxygen--;
                nbt.putInt(OXYGEN_KEY, oxygen);
                player.setAir(300);
            } else {
                // No oxygen remains: the sealed suit no longer protects its wearer.
                player.setAir(0);
                player.damage(world.getDamageSources().drown(), 1.0F);
            }
        }
    }

    private static boolean hasFullSuit(PlayerEntity p) {
        // The Gallifrey suit, or any Ad Astra suit (see SpaceSuits).
        return SpaceSuits.hasFullSuit(p);
    }

    private static boolean isMoonWorld(ServerWorld world) {
        Identifier id = world.getRegistryKey().getValue();
        return "gallifrey".equals(id.getNamespace()) && id.getPath().equals("moon");
    }

    private static boolean isVacuumWorld(ServerWorld world) {
        Identifier id = world.getRegistryKey().getValue();
        if (!"gallifrey".equals(id.getNamespace())) return false;
        return id.getPath().equals("mars") || id.getPath().equals("mondas") || id.getPath().equals("moon");
    }
}
