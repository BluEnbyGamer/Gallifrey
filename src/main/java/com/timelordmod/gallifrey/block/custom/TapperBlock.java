package com.timelordmod.gallifrey.block.custom;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;


/**
 *                     ==== Block Type info ====
 *  This block is a Stardew Valley inspired tree tapper
 * it hangs on the side of a log and slowly fills with product
 * the product depends on the tree type (EG. Treeborg Tree = Treeborg paste)
 */


public class TapperBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final int MAX_LEVEL = 3;
    public static final IntProperty LEVEL = IntProperty.of("level", 0, MAX_LEVEL);

    private static final int FILL_CHANCE = 6;

    private static final int MAX_TRUNK_HEIGHT = 16;
    private static int LEAF_RADIUS = 2;

    private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(5, 2, 0, 11, 10, 9);
    private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(5, 2, 0, 11, 10, 9);
    private static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 2, 5, 9, 10, 11);
    private static final VoxelShape SHAPE_WEST = Block.createCuboidShape(7, 2, 5, 16, 10, 11);

    public TapperBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(LEVEL, 0));
    }

    // ------------------------------------------------------------------
    // Tree lookup table. Add new tappable trees here (e.g. Maple -> syrup).
    // ------------------------------------------------------------------

    @Nullable
    private static Block leavesFor(BlockState log) {
        if (log.isOf(GallifreyModBlocks.TREEBORG_LOG)) return GallifreyModBlocks.TREEBORG_LEAVES;
        return null;
    }

    @Nullable
    private static Item productFor(BlockState log) {
        if (log.isOf(GallifreyModBlocks.TREEBORG_LOG)) return GallifreyModItems.TREEBORG_PASTE;
        return null;
    }

    // ------------------------------------------------------------------
    // Placement: only on the side of a tappable log
    // ------------------------------------------------------------------

    private static BlockPos attachedPos(BlockState state, BlockPos pos) {
        return pos.offset(state.get(FACING).getOpposite());
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return leavesFor(world.getBlockState(attachedPos(state, pos))) != null;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = getDefaultState();
        for (Direction dir : ctx.getPlacementDirections()) {
            if (dir.getAxis().isHorizontal()) {
                BlockState candidate = state.with(FACING, dir.getOpposite());
                if (candidate.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return candidate;
                }
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // ------------------------------------------------------------------
    // Filling
    // ------------------------------------------------------------------

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LEVEL) < MAX_LEVEL;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int level = state.get(LEVEL);
        if (level >= MAX_LEVEL) return;
        if (random.nextInt(FILL_CHANCE) != 0) return;      // cheap roll first
        if (!isOnLivingTree(world, state, pos)) return;     // only then the tree scan

        world.setBlockState(pos, state.with(LEVEL, level + 1), Block.NOTIFY_LISTENERS);
    }
    /**
     * A "real" tree = the tapper's log is part of a trunk whose top is surrounded
     * by natural (non-persistent) leaves of the matching type. Player-placed leaves
     * are persistent, so a pillar of logs with leaves stuck on top doesn't count,
     * and a tree whose leaves have all decayed stops producing.
     */
    @SuppressWarnings("deprecation") // isChunkLoaded is deprecated in Yarn but is the vanilla way to avoid loading chunks
    private static boolean isOnLivingTree(World world, BlockState state, BlockPos pos) {
        BlockPos logPos = attachedPos(state, pos);
        BlockState log = world.getBlockState(logPos);
        Block leaves = leavesFor(log);
        if (leaves == null) return false;

        Block logBlock = log.getBlock();
        BlockPos.Mutable m = logPos.mutableCopy();

        int topY = logPos.getY();
        int maxY = Math.min(logPos.getY() + MAX_TRUNK_HEIGHT, world.getTopY() - 1);
        for (int y = logPos.getY() + 1; y <= maxY; y++) {
            m.setY(y);
            if (!world.getBlockState(m).isOf(logBlock)) break;
            topY = y;
        }

        for (int dy = -1; dy <= 2; dy++) {
            int y = topY + dy;
            if (y < world.getBottomY() || y >= world.getTopY()) continue;
            for (int dx = -LEAF_RADIUS; dx <= LEAF_RADIUS; dx++) {
                for (int dz = -LEAF_RADIUS; dz <= LEAF_RADIUS; dz++) {
                    m.set(logPos.getX() + dx, y, logPos.getZ() + dz);
                    if (!world.isChunkLoaded(m)) continue; // never force-load a neighbour chunk
                    BlockState s = world.getBlockState(m);
                    if (s.isOf(leaves) && s.contains(LeavesBlock.PERSISTENT) && !s.get(LeavesBlock.PERSISTENT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Collecting by hand
    // ------------------------------------------------------------------

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        int level = state.get(LEVEL);

        if (level >= MAX_LEVEL) {
            if (!world.isClient) {
                Item product = productFor(world.getBlockState(attachedPos(state, pos)));
                if (product != null) {
                    player.getInventory().offerOrDrop(new ItemStack(product));
                }
                world.setBlockState(pos, state.with(LEVEL, 0), Block.NOTIFY_ALL);
                world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResult.success(world.isClient);
        }

        if (player.getStackInHand(hand).isEmpty()) {
            if (!world.isClient) {
                if (isOnLivingTree(world, state, pos)) {
                    player.sendMessage(Text.translatable("block.gallifrey.tapper.filling", level, MAX_LEVEL), true);
                } else {
                    player.sendMessage(Text.translatable("block.gallifrey.tapper.not_a_tree"), true);
                }
            }
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    // ------------------------------------------------------------------
    // Boilerplate
    // ------------------------------------------------------------------

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_SOUTH;
        };
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LEVEL);
    }
}