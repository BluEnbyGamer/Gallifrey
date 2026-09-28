package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.screens.SonicWorkshopScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public final class SonicWorkshopClientHooks {
    private SonicWorkshopClientHooks() {}
    public static void open(BlockPos pos) {
        MinecraftClient.getInstance().setScreen(new SonicWorkshopScreen(pos));
    }
}
