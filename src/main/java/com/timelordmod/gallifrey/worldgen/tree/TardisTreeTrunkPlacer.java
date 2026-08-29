package com.timelordmod.gallifrey.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.timelordmod.gallifrey.worldgen.GallifreyWorldgen;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

// Poplar-style trunk, used for the Tardis tree: a straight base, a handful of branches jutting out
// near the top, and the main leader continuing a bit further above that.
//
// Requires TrunkPlacerTypeInvoker to be registered first (see mixin above).
public class TardisTreeTrunkPlacer extends TrunkPlacer {

    public static final Codec<TardisTreeTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillTrunkPlacerFields(instance)
                    .and(IntProvider.createValidatingCodec(0, 8)
                            .fieldOf("trunk_height_above_branches")
                            .forGetter(placer -> placer.trunkHeightAboveBranches))
                    .and(IntProvider.createValidatingCodec(1, 4)
                            .fieldOf("branch_amount")
                            .forGetter(placer -> placer.branchAmount))
                    .apply(instance, TardisTreeTrunkPlacer::new));

    private final IntProvider trunkHeightAboveBranches;
    private final IntProvider branchAmount;

    public TardisTreeTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight,
                                 IntProvider trunkHeightAboveBranches, IntProvider branchAmount) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.trunkHeightAboveBranches = trunkHeightAboveBranches;
        this.branchAmount = branchAmount;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return GallifreyWorldgen.TARDIS_TREE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int height, BlockPos startPos, TreeFeatureConfig config) {

        setToDirt(world, replacer, random, startPos.down(), config);

        // Straight trunk up to the fork point
        for (int i = 0; i < height; i++) {
            this.getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        BlockPos forkPoint = startPos.up(height);
        List<FoliagePlacer.TreeNode> nodes = new ArrayList<>();

        // Branches: pick 1-4 of the 4 horizontal directions, shuffled, each
        // extending 2-3 blocks outward and rising 1-3 blocks at a randomized
        // height (not the same for every branch), so the branch tips land at
        // varied heights instead of forming one perfectly symmetric cross
        List<Direction> directions = new ArrayList<>(List.of(
                Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
        for (int i = directions.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Direction temp = directions.get(i);
            directions.set(i, directions.get(j));
            directions.set(j, temp);
        }

        int branches = this.branchAmount.get(random);
        for (int i = 0; i < branches && i < directions.size(); i++) {
            Direction dir = directions.get(i);
            int branchLength = 2 + random.nextInt(2);
            int branchRise = 1 + random.nextInt(3);

            BlockPos branchPos = forkPoint;
            for (int step = 0; step < branchLength; step++) {
                branchPos = branchPos.offset(dir);
                this.getAndSetState(world, replacer, random, branchPos, config);
            }

            for (int riseStep = 1; riseStep <= branchRise; riseStep++) {
                this.getAndSetState(world, replacer, random, branchPos.up(riseStep), config);
            }

            BlockPos branchEnd = branchPos.up(branchRise);
            nodes.add(new FoliagePlacer.TreeNode(branchEnd, 0, false));
        }

        // The leader keeps going straight up a bit further above the fork
        int aboveBranches = this.trunkHeightAboveBranches.get(random);
        for (int i = 0; i < aboveBranches; i++) {
            this.getAndSetState(world, replacer, random, forkPoint.up(i), config);
        }
        nodes.add(new FoliagePlacer.TreeNode(forkPoint.up(aboveBranches), 0, false));

        return nodes;
    }
}