package com.timelordmod.gallifrey.sonic;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class SonicModes {

    private static final String MODE_KEY = "SonicMode";

    public static SonicMode getMode(ItemStack stack) {

        int mode = stack.getOrCreateNbt().getInt(MODE_KEY);

        SonicMode[] modes = SonicMode.values();

        if (mode < 0 || mode >= modes.length) {
            return SonicMode.SCAN;
        }

        return modes[mode];
    }

    public static void nextMode(
            ItemStack stack,
            net.minecraft.entity.player.PlayerEntity player
    ) {

        SonicMode[] modes = SonicMode.values();

        SonicMode current = getMode(stack);

        int next = (current.ordinal() + 1) % modes.length;

        stack.getOrCreateNbt().putInt(MODE_KEY, next);

        SonicMode newMode = modes[next];

        player.sendMessage(
                Text.literal(
                        "§bSONIC MODE: §f" +
                                newMode.getDisplayName()
                ),
                true
        );
    }
}
