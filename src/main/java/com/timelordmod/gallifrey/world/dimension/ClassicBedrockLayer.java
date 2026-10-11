package com.timelordmod.gallifrey.world.dimension;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

/** Keeps a solid Classic Bedrock floor at Y=1 in every Classic-dimension chunk. */
public final class ClassicBedrockLayer {
    private ClassicBedrockLayer() {}

    public static void register() {
        // CHUNK_GENERATE covers new terrain; CHUNK_LOAD also repairs worlds/chunks made
        // before this feature was installed. Both callbacks are server-side.
        ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
            if (world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)) enforce(chunk);
        });
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (world.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)) enforce(chunk);
        });
    }

    private static void enforce(WorldChunk chunk) {
        int minX = chunk.getPos().getStartX();
        int minZ = chunk.getPos().getStartZ();
        boolean changed = false;
        for (int x = minX; x < minX + 16; x++) {
            for (int z = minZ; z < minZ + 16; z++) {
                BlockPos pos = new BlockPos(x, 1, z);
                if (!chunk.getBlockState(pos).isOf(GallifreyModBlocks.CLASSIC_BEDROCK)) {
                    chunk.setBlockState(pos, GallifreyModBlocks.CLASSIC_BEDROCK.getDefaultState(), false);
                    changed = true;
                }
                // The generator's old bottom rule made vanilla bedrock at Y=0. Keep the
                // visual/functional floor consistently Classic Bedrock across both levels.
                BlockPos bottom = new BlockPos(x, 0, z);
                if (chunk.getBlockState(bottom).isOf(Blocks.BEDROCK)) {
                    chunk.setBlockState(bottom, GallifreyModBlocks.CLASSIC_BEDROCK.getDefaultState(), false);
                    changed = true;
                }
            }
        }
        if (changed) chunk.setShouldSave(true);
    }
}
