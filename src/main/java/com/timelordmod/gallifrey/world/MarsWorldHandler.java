package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;

/** Runtime low-gravity behaviour for Mars and the Moon. */
public final class MarsWorldHandler {
    private MarsWorldHandler() {}

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            boolean isMars = world.getRegistryKey().equals(ModDimensions.MARS_LEVEL_KEY);
            boolean isMoon = world.getRegistryKey().equals(ModDimensions.MOON_LEVEL_KEY);
            if (!isMars && !isMoon) return;

            // Approximate gravity: Mars ~38% of Earth, Moon ~16.5% of Earth.
            // Vanilla gravity is roughly -0.08 blocks/tick^2. Counteract most of
            // that acceleration while airborne to create longer, floatier jumps.
            double gravityCompensation = isMoon ? 0.067 : 0.05;
            for (PlayerEntity player : world.getPlayers()) {
                if (!player.isOnGround() && !player.isClimbing()
                        && !player.isSwimming() && !player.hasNoGravity()) {
                    var velocity = player.getVelocity();
                    player.setVelocity(velocity.x, velocity.y + gravityCompensation, velocity.z);
                    player.velocityDirty = true;
                }
            }
        });
    }
}
