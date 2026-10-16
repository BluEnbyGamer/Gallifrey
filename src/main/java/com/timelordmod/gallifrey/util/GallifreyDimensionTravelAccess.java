package com.timelordmod.gallifrey.util;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class GallifreyDimensionTravelAccess {
    private static final ThreadLocal<Map<UUID, Integer>> AUTHORIZED_PLAYERS =
            ThreadLocal.withInitial(HashMap::new);

    private GallifreyDimensionTravelAccess() {}

    public static boolean isAuthorized(ServerPlayerEntity player) {
        return AUTHORIZED_PLAYERS.get().containsKey(player.getUuid());
    }

    public static void beginAuthorization(ServerPlayerEntity player) {
        AUTHORIZED_PLAYERS.get().merge(player.getUuid(), 1, Integer::sum);
    }

    public static void endAuthorization(ServerPlayerEntity player) {
        Map<UUID, Integer> authorized = AUTHORIZED_PLAYERS.get();
        UUID playerId = player.getUuid();
        int depth = authorized.getOrDefault(playerId, 0);

        if (depth <= 1) {
            authorized.remove(playerId);
        } else {
            authorized.put(playerId, depth - 1);
        }

        if (authorized.isEmpty()) {
            AUTHORIZED_PLAYERS.remove();
        }
    }

    public static void runAuthorized(
            ServerPlayerEntity player, Runnable action) {
        beginAuthorization(player);
        try {
            action.run();
        } finally {
            endAuthorization(player);
        }
    }
}


