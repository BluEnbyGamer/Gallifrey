package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Five-second space-suit jetpack with a thirty-second recharge. */
public final class SpaceSuitJetpackHandler {
    private static final int FLIGHT_TICKS = 5 * 20;
    private static final int COOLDOWN_TICKS = 30 * 20;
    private static final Map<UUID, Integer> ACTIVE = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> COOLDOWN = new ConcurrentHashMap<>();

    private SpaceSuitJetpackHandler() {}

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(SpaceSuitJetpackHandler::tickWorld);
    }

    public static void activate(PlayerEntity player) {
        UUID id = player.getUuid();
        if (!hasFullSuit(player)) {
            player.sendMessage(Text.literal("§cYou need the full Space Suit to use the jetpack."), true);
            return;
        }
        if (ACTIVE.containsKey(id)) return;
        int remaining = COOLDOWN.getOrDefault(id, 0);
        if (remaining > 0) {
            player.sendMessage(Text.literal("§eJetpack recharging: " + ((remaining + 19) / 20) + "s"), true);
            return;
        }
        ACTIVE.put(id, FLIGHT_TICKS);
        COOLDOWN.put(id, COOLDOWN_TICKS);
        player.sendMessage(Text.literal("§bJetpack activated!"), true);
    }

    private static void tickWorld(ServerWorld world) {
        for (PlayerEntity player : world.getPlayers()) {
            UUID id = player.getUuid();
            int cooldown = COOLDOWN.getOrDefault(id, 0);
            if (cooldown > 0) COOLDOWN.put(id, cooldown - 1);
            else COOLDOWN.remove(id);

            Integer active = ACTIVE.get(id);
            if (active == null) continue;
            if (!hasFullSuit(player) || player.isSpectator() || player.isCreative()) {
                ACTIVE.remove(id);
                continue;
            }

            // Sustain upward thrust while active; horizontal movement remains player-controlled.
            var velocity = player.getVelocity();
            player.setVelocity(velocity.x, Math.max(velocity.y, 0.42), velocity.z);
            player.velocityDirty = true;
            if (active <= 1) {
                ACTIVE.remove(id);
                player.sendMessage(Text.literal("§eJetpack depleted. Recharging for 30 seconds."), true);
            } else {
                ACTIVE.put(id, active - 1);
            }
        }
    }

    private static boolean hasFullSuit(PlayerEntity p) {
        return p.getEquippedStack(EquipmentSlot.HEAD).isOf(GallifreyModItems.SPACE_SUIT_HELMET)
                && p.getEquippedStack(EquipmentSlot.CHEST).isOf(GallifreyModItems.SPACE_SUIT_CHESTPLATE)
                && p.getEquippedStack(EquipmentSlot.LEGS).isOf(GallifreyModItems.SPACE_SUIT_LEGGINGS)
                && p.getEquippedStack(EquipmentSlot.FEET).isOf(GallifreyModItems.SPACE_SUIT_BOOTS);
    }
}
