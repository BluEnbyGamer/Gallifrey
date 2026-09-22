package com.timelordmod.gallifrey.client.sound;

import com.timelordmod.gallifrey.GallifreySounds;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;

public class SonicSoundInstance extends MovingSoundInstance {

    private final PlayerEntity player;

    public SonicSoundInstance(PlayerEntity player) {

        super(
                GallifreySounds.SONIC,
                SoundCategory.PLAYERS,
                SoundInstance.createRandom()
        );

        this.player = player;

        this.repeat = true;
        this.repeatDelay = 0;

        this.volume = 1.0F;
        this.pitch = 1.0F;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {

        // Stop if the player is gone.
        if (player.isRemoved()) {
            setDone();
            return;
        }

        // Keep the sound following the player.
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        // Stop when the Sonic is no longer active.
        boolean sonicOn =
                SonicScrewdriver.isOn(
                        player.getMainHandStack()
                )
                        ||
                        SonicScrewdriver.isOn(
                                player.getOffHandStack()
                        );

        if (!sonicOn) {
            setDone();
        }
    }

    /**
     * Public method used by SonicSoundManager
     * to stop the sound immediately.
     */
    public void stopSound() {
        setDone();
    }
}
