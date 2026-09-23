package com.timelordmod.gallifrey.world;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.WorldChunk;

public final class MarsWorldHandler {
    private MarsWorldHandler() {}

    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (!world.getRegistryKey().equals(ModDimensions.MARS_LEVEL_KEY)) return;
            terraformChunk(world, chunk);
        });

        // Mars gravity is roughly 38% of Earth's. Minecraft's normal gravity is
        // intentionally softened here rather than changing every entity globally.
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!world.getRegistryKey().equals(ModDimensions.MARS_LEVEL_KEY)) return;
            for (PlayerEntity player : world.getPlayers()) {
                if (!player.isOnGround() && !player.isClimbing() && !player.isSwimming()) {
                    var velocity = player.getVelocity();
                    if (velocity.y < 0.0) {
                        player.setVelocity(velocity.x, velocity.y + 0.045, velocity.z);
                    }
                }
            }
        });
    }

    private static void terraformChunk(net.minecraft.server.world.ServerWorld world, WorldChunk chunk) {
        int minY = world.getBottomY();
        int maxY = world.getBottomY() + world.getHeight() - 1;

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int x = chunk.getPos().getStartX() + lx;
                int z = chunk.getPos().getStartZ() + lz;
                int surface = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE).get(lx, lz);
                if (surface <= minY) continue;

                // Mars has no oceans: drain exposed overworld water/lava columns.
                int y = Math.min(surface - 1, maxY);
                boolean clearedFluid = false;
                while (y > minY && (world.getBlockState(new BlockPos(x, y, z)).isOf(Blocks.WATER)
                        || world.getBlockState(new BlockPos(x, y, z)).isOf(Blocks.LAVA))) {
                    world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 2);
                    clearedFluid = true;
                    y--;
                }

                // The heightmap included the fluid surface. Once the fluid is
                // removed, continue terraforming from the real terrain surface
                // so Mars does not get an artificial air gap over old oceans.
                surface = clearedFluid ? Math.min(y + 1, maxY) : Math.min(surface, maxY);
                // Replace the upper geological layers with the supplied red Mars stone.
                int depth = 0;
                for (int yy = surface - 1; yy >= minY && depth < 10; yy--, depth++) {
                    BlockPos pos = new BlockPos(x, yy, z);
                    BlockState state = world.getBlockState(pos);
                    if (isReplaceableTerrain(state)) {
                        if (depth == 0 || depth == 1) {
                            world.setBlockState(pos, GallifreyModBlocks.MARS_STONE.getDefaultState(), 2);
                        } else if (depth == 2 && world.random.nextInt(6) == 0) {
                            world.setBlockState(pos, GallifreyModBlocks.MARS_COBBLESTONE.getDefaultState(), 2);
                        } else {
                            world.setBlockState(pos, GallifreyModBlocks.MARS_STONE.getDefaultState(), 2);
                        }
                    } else if (state.isOf(Blocks.IRON_ORE)) {
                        world.setBlockState(pos, GallifreyModBlocks.MARS_IRON_ORE.getDefaultState(), 2);
                    }
                }
            }
        }
    }

    private static boolean isReplaceableTerrain(BlockState state) {
        return state.isOf(Blocks.STONE) || state.isOf(Blocks.DEEPSLATE)
                || state.isOf(Blocks.DIRT) || state.isOf(Blocks.GRASS_BLOCK)
                || state.isOf(Blocks.SAND) || state.isOf(Blocks.RED_SAND)
                || state.isOf(Blocks.GRAVEL) || state.isOf(Blocks.TUFF)
                || state.isOf(Blocks.CLAY);
    }
}
