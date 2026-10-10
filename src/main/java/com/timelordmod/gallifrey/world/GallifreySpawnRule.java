package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;


public final class GallifreySpawnRule {
    private GallifreySpawnRule() {}

    public static final GameRules.Key<GameRules.BooleanRule> SPAWN_ON_GALLIFREY =
            GameRuleRegistry.register("spawnOnGallifrey", GameRules.Category.SPAWNING,
                    GameRuleFactory.createBooleanRule(false));

    private static final int SEARCH_RADIUS = 256;
    private static final int SEARCH_STEP = 8;
    private static BlockPos cachedSpawn = null;

    public static void register() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTED
                .register(server -> cachedSpawn = null);

        // First join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (!isEnabled(player)) return;

            int playTime = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME));
            boolean firstJoin = playTime == 0 && player.getSpawnPointPosition() == null;
            if (!firstJoin) return;

            // Run next tick so the player is fully added to the world before moving dimension
            server.execute(() -> sendToGallifrey(player, true));
        });

        // Respawn after death with no bed
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (alive) return; // returning from the End, not a death
            if (!isEnabled(newPlayer)) return;
            if (newPlayer.getSpawnPointPosition() != null) return; // has a working bed/anchor/spawn

            sendToGallifrey(newPlayer, true);
        });
    }

    private static boolean isEnabled(ServerPlayerEntity player) {
        return player.getServer() != null
                && player.getServer().getGameRules().getBoolean(SPAWN_ON_GALLIFREY);
    }

    private static void sendToGallifrey(ServerPlayerEntity player, boolean setSpawn) {
        if (player.getServer() == null) return;
        ServerWorld gallifrey = player.getServer().getWorld(ModDimensions.GALL_LEVEL_KEY);
        if (gallifrey == null) return;

        BlockPos pos = getSpawnPos(gallifrey);

        if (setSpawn) {
            // forced = true so it works without a bed; sendMessage = false
            player.setSpawnPoint(ModDimensions.GALL_LEVEL_KEY, pos, 0.0F, true, false);
        }
        player.teleport(gallifrey, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
        player.fallDistance = 0;
    }

    private static BlockPos getSpawnPos(ServerWorld world) {
        if (cachedSpawn != null) return cachedSpawn;

        // Spiral outward from 0,0 looking for dry, solid ground
        for (int r = 0; r <= SEARCH_RADIUS; r += SEARCH_STEP) {
            for (int dx = -r; dx <= r; dx += SEARCH_STEP) {
                for (int dz = -r; dz <= r; dz += SEARCH_STEP) {
                    if (Math.abs(dx) != r && Math.abs(dz) != r) continue; // only the ring edge
                    BlockPos found = safeSurface(world, dx, dz);
                    if (found != null) {
                        cachedSpawn = found;
                        return found;
                    }
                }
            }
        }

        // Fallback: top block at 0,0 even if it's wet
        int y = surfaceY(world, Heightmap.Type.MOTION_BLOCKING, 0, 0);
        cachedSpawn = new BlockPos(0, y, 0);
        return cachedSpawn;
    }

    private static int surfaceY(ServerWorld world, Heightmap.Type type, int x, int z) {
        Chunk chunk = world.getChunk(x >> 4, z >> 4);
        return chunk.sampleHeightmap(type, x & 15, z & 15) + 1;
    }

    private static BlockPos safeSurface(ServerWorld world, int x, int z) {
        int y = surfaceY(world, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        if (y <= world.getBottomY() + 1) return null;

        BlockPos feet = new BlockPos(x, y, z);
        BlockPos ground = feet.down();
        BlockState groundState = world.getBlockState(ground);

        if (!groundState.getFluidState().isEmpty()) return null;            // water / lava
        if (!groundState.isSolidBlock(world, ground)) return null;          // must stand on something
        if (!world.getBlockState(feet).isAir()) return null;                // room for feet
        if (!world.getBlockState(feet.up()).isAir()) return null;           // room for head
        return feet;
    }
}