package com.timelordmod.gallifrey;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class GallifreySounds {

    public static final SoundEvent VM_TAKE_OFF = registerSound("vm_take_off");
    public static final SoundEvent VM_LAND = registerSound("vm_land");
    public static final SoundEvent SONIC = registerSound("sonic");

    private static SoundEvent registerSound(String name) {
        Identifier id = new Identifier(GallifreyMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        // Registers the sounds
    }
}

