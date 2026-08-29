package com.timelordmod.gallifrey.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.timelordmod.gallifrey.worldgen.GallifreyWorldgen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

// Poplar-style foliage, used for the Tardis tree: a tapered diamond (rhombus) cluster of leaves
// at each tree node, narrower than it is tall (width capped at 2/3 of height) so it reads as
// pointed rather than round, its wide face running along either X or Z (picked per-tree) with
// 3 blocks of thickness on the other axis, plus a chance to knock out edge blocks so the outline
// isn't perfectly crisp.
//
// Requires FoliagePlacerTypeInvoker to be registered first (see mixin above).
public class TardisTreeFoliagePlacer extends FoliagePlacer {

    public static final Codec<TardisTreeFoliagePlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillFoliagePlacerFields(instance)
                    .and(IntProvider.createValidatingCodec(5, 16)
                            .fieldOf("height")
                            .forGetter(placer -> placer.height))
                    .and(Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("side_hole_chance")
                            .forGetter(placer -> placer.sideHoleChance))
                    .apply(instance, TardisTreeFoliagePlacer::new));

    private final IntProvider height;
    private final float sideHoleChance;

    public TardisTreeFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height, float sideHoleChance) {
        super(radius, offset);
        this.height = height;
        this.sideHoleChance = sideHoleChance;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return GallifreyWorldgen.TARDIS_TREE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random,
                            TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight,
                            int radius, int offset) {

        BlockPos center = treeNode.getCenter();
        int totalHeight = this.height.get(random);
        int halfHeight = Math.max(1, totalHeight / 2);
        int maxHalfWidth = Math.max(1, halfHeight * 2 / 3);
        boolean wideAlongX = random.nextBoolean();

        for (int dy = 0; dy < totalHeight; dy++) {
            int distanceFromMiddle = Math.abs(dy - halfHeight);
            double taper = 1.0 - Math.min(1.0, (double) distanceFromMiddle / halfHeight);
            int idealHalfWidth = (int) Math.round(maxHalfWidth * taper);

            // Jitter each row's width by -1/0/+1 instead of following the ideal
            // taper exactly - a perfectly smooth mathematical curve is exactly
            // what reads as "too clean." Leave the very tip alone so it still
            // comes to a clean point rather than randomly growing a stub.
            int rowHalfWidth = idealHalfWidth <= 0 ? 0 : Math.max(0, idealHalfWidth + random.nextInt(3) - 1);

            int y = center.getY() - halfHeight + dy;

            for (int d = -rowHalfWidth; d <= rowHalfWidth; d++) {
                int distanceFromEdge = rowHalfWidth - Math.abs(d);
                // Two rings get a hole chance now, not just the outermost block,
                // so raggedness shows up across the outline instead of one pixel deep
                float skipChance = distanceFromEdge == 0 ? this.sideHoleChance
                        : distanceFromEdge == 1 ? this.sideHoleChance * 0.2F
                        : 0.0F;
                if (skipChance > 0 && random.nextFloat() < skipChance) {
                    continue;
                }

                for (int thin = -1; thin <= 1; thin++) {
                    // Center slice always fills in - no gaps straight through the
                    // middle - but the two outer slices are independently thinned,
                    // so depth varies instead of every cluster being a uniform slab
                    if (thin != 0 && random.nextFloat() < 0.15F) {
                        continue;
                    }

                    BlockPos pos = wideAlongX
                            ? new BlockPos(center.getX() + d, y, center.getZ() + thin)
                            : new BlockPos(center.getX() + thin, y, center.getZ() + d);

                    this.placeFoliageBlock(world, placer, random, config, pos);
                }
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height.get(random);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
