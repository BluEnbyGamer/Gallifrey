package com.timelordmod.gallifrey.util;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public final class GallifreyDimensionTravelAccess {

    private static final ThreadLocal<UUID> AUTHORIZED_PLAYER =
            new ThreadLocal<>();

    private GallifreyDimensionTravelAccess() {
    }

    public static boolean isAuthorized(ServerPlayerEntity player) {
        UUID authorized = AUTHORIZED_PLAYER.get();
        return authorized != null && authorized.equals(player.getUuid());
    }

    public static void runAuthorized(
            ServerPlayerEntity player,
            Runnable action
    ) {
        UUID previous = AUTHORIZED_PLAYER.get();
        AUTHORIZED_PLAYER.set(player.getUuid());

        try {
            action.run();
        } finally {
            if (previous == null) {
                AUTHORIZED_PLAYER.remove();
            } else {
                AUTHORIZED_PLAYER.set(previous);
            }
        }
    }
}

