package com.timelordmod.gallifrey.world.dimension;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

/** Enforces the Classic dimension's y=1 bedrock floor for newly generated and loaded chunks. */
public final class ClassicBedrockLayer {
    private ClassicBedrockLayer() {}
    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (!world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)) return;
            enforce(chunk);
        });
        ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
            if (world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)) enforce(chunk);
        });
    }
    private static void enforce(WorldChunk chunk) {
        int minX = chunk.getPos().getStartX();
        int minZ = chunk.getPos().getStartZ();
        for (int x = minX; x < minX + 16; x++) for (int z = minZ; z < minZ + 16; z++) {
            BlockPos pos = new BlockPos(x, 1, z);
            if (!chunk.getBlockState(pos).isOf(GallifreyModBlocks.CLASSIC_BEDROCK)) {
                chunk.setBlockState(pos, GallifreyModBlocks.CLASSIC_BEDROCK.getDefaultState(), false);
            }
        }
    }
}
