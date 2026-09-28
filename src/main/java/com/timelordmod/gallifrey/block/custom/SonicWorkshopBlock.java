package com.timelordmod.gallifrey.block.custom;

import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import com.timelordmod.gallifrey.networking.ModPackets;

public class SonicWorkshopBlock extends Block implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    /** The Geo model is a full 16x16 footprint but only 9 pixels tall. */
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);

    public SonicWorkshopBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // GeoBlockRenderer rotates the model from this property, so the model's
        // front points back toward the player after placement.
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, net.minecraft.util.BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, net.minecraft.util.BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SonicWorkshopBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SonicWorkshopBlockEntity workshop) {
                ItemStack sonic = workshop.removeSonic();
                if (!sonic.isEmpty()) {
                    Block.dropStack(world, pos, sonic);
                }
            }
            world.removeBlockEntity(pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        // The workshop has no per-tick logic, so don't tick it at all.
        return null;
    }

    /**
     * World-space socket region for the supplied model when FACING=NORTH.
     * The model's top-level panel bone is rotated 90 degrees around Y, so the
     * visible gold holder ends up around x=.7-.9, z=.3-.7 in block space.
     * GeoBlockRenderer then applies the block FACING rotation automatically.
     */
    public static boolean isSonicPort(BlockState state, BlockHitResult hit) {
        /*
         * The Geo model's sonic_holder bone is around the upper/front portion
         * of the panel.  Do the hit test in block-local coordinates and make
         * the interaction area deliberately generous so the player can use
         * the visible gold socket without pixel-perfect aiming.
         *
         * The old implementation tested the wrong side of the model after the
         * block-facing rotation, which is why the Sonic could not be inserted.
         */
        Direction facing = state.get(FACING);
        if (hit.getSide() != facing) {
            return false;
        }

        Vec3d p = hit.getPos();
        double x = p.x - Math.floor(p.x);
        double y = p.y - Math.floor(p.y);
        double z = p.z - Math.floor(p.z);

        // Tangential coordinate across the workshop face.  The socket is on
        // the upper-right portion of the player's view of the panel.
        double across;
        switch (facing) {
            case EAST, WEST -> across = z;
            default -> across = x;
        }

        // Upper half of the visible gold holder.  Keep this separate from the
        // lower panel so clicking the panel still opens the GUI.
        return across >= 0.45D && across <= 0.95D
                && y >= 0.28D && y <= 0.72D;
    }

    @Override
    public ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit) {

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof SonicWorkshopBlockEntity workshop)) {
            return ActionResult.PASS;
        }

        if (isSonicPort(state, hit)) {
            if (world.isClient) {
                // Claim the socket on the client so a held Sonic's normal
                // right-click action cannot run. The actual insert/remove is
                // performed by the server when the interaction packet arrives.
                return ActionResult.SUCCESS;
            }

            ItemStack held = player.getStackInHand(hand);

            if (workshop.getSonic().isEmpty()) {
                if (!(held.getItem() instanceof SonicScrewdriver)) {
                    return ActionResult.SUCCESS;
                }

                ItemStack inserted = held.copy();
                inserted.setCount(1);
                workshop.setSonic(inserted);

                if (!player.getAbilities().creativeMode) {
                    held.decrement(1);
                }

                return ActionResult.SUCCESS;
            }

            // An empty hand removes the installed Sonic from the socket.
            if (held.isEmpty()) {
                ItemStack removed = workshop.removeSonic();
                if (!removed.isEmpty()) {
                    if (!player.giveItemStack(removed)) {
                        player.dropItem(removed, false);
                    }
                }
                return ActionResult.SUCCESS;
            }

            // The socket has claimed this click even when it cannot perform
            // an operation with the currently held item.
            return ActionResult.SUCCESS;
        }

        // Only the installed Sonic may open the workshop.  Ask the server to
        // open the client screen so this is deterministic and cannot depend on
        // client-side BlockEntity synchronization timing.
        if (!world.isClient && workshop.hasSonic() && player instanceof ServerPlayerEntity serverPlayer) {
            net.minecraft.network.PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            ServerPlayNetworking.send(
                    serverPlayer,
                    ModPackets.OPEN_SONIC_WORKSHOP,
                    buf
            );
        }

        // Claim the click.  A held Sonic therefore cannot use its own
        // right-click action against the workshop.
        return ActionResult.SUCCESS;
    }
}
