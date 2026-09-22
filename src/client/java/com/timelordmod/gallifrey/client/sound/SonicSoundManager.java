package com.timelordmod.gallifrey.client.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class SonicSoundManager {

    private static SonicSoundInstance activeSound;

    public static void start(PlayerEntity player) {

        if (activeSound != null) {
            return;
        }

        activeSound =
                new SonicSoundInstance(player);

        MinecraftClient
                .getInstance()
                .getSoundManager()
                .play(activeSound);
    }

    public static void stop() {

        if (activeSound == null) {
            return;
        }

        MinecraftClient
                .getInstance()
                .getSoundManager()
                .stop(activeSound);

        activeSound = null;
    }

    public static boolean isPlaying() {

        return activeSound != null;
    }
}


