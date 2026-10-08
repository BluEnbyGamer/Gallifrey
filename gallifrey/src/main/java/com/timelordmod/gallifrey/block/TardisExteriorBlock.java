package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity;
import com.timelordmod.gallifrey.tardis.TardisDimensionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.BlockView;
import net.minecraft.sound.SoundCategory;
import com.timelordmod.gallifrey.GallifreySounds;

public class TardisExteriorBlock extends Block implements BlockEntityProvider {
    private static final VoxelShape TARDIS_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 32, 16);
    public static final IntProperty ROTATION = IntProperty.of("rotation", 0, 7);
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public TardisExteriorBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(ROTATION, 0).with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, OPEN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        float yaw = ctx.getPlayerYaw();
        int rotation = MathHelper.floor((double)((yaw + 180.0F) * 8.0F / 360.0F) + 0.5D) & 7;
        return getDefaultState().with(ROTATION, rotation);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        // A TARDIS must never be placed inside another TARDIS pocket dimension,
        // and its full two-block footprint must be clear.
        return !TardisDimensionManager.isInteriorWorld(world)
                && super.canPlaceAt(state, world, pos)
                && world.getBlockState(pos.up()).isAir();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TARDIS_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TARDIS_SHAPE;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack stack) {
        super.onPlaced(world, pos, state, placer, stack);
        if (!world.isClient && placer instanceof ServerPlayerEntity player
                && world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis) {
            tardis.ensureInitialized(player);
            if (tardis.getTardisId() != null) {
                player.sendMessage(Text.literal("New TARDIS created: " + tardis.getTardisId()), true);
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TardisExteriorBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                               PlayerEntity player, Hand hand, net.minecraft.util.hit.BlockHitResult hit) {
        if (world.isClient || !(player instanceof ServerPlayerEntity serverPlayer)) {
            return ActionResult.SUCCESS;
        }

        if (!(world.getBlockEntity(pos) instanceof TardisExteriorBlockEntity tardis)) {
            return ActionResult.PASS;
        }

        tardis.ensureInitialized(serverPlayer);

        if (serverPlayer.isSneaking()) {
            if (!tardis.canPilot(serverPlayer.getUuid())) {
                serverPlayer.sendMessage(Text.literal("Only the TARDIS owner can operate its locks."), true);
                return ActionResult.SUCCESS;
            }
            tardis.setLocked(!tardis.isLocked());
            world.playSound(null, pos, tardis.isLocked()
                    ? GallifreySounds.POLICE_BOX_DOOR_CLOSE
                    : GallifreySounds.POLICE_BOX_DOOR_OPEN,
                    SoundCategory.BLOCKS, 0.8F, 1.0F);
            serverPlayer.sendMessage(Text.literal(tardis.isLocked()
                    ? "TARDIS locked. Isomorphic security active."
                    : "TARDIS unlocked."), true);
            return ActionResult.SUCCESS;
        }

        if (!tardis.canAccess(serverPlayer.getUuid())) {
            serverPlayer.sendMessage(Text.literal("The TARDIS is locked."), true);
            return ActionResult.SUCCESS;
        }

        if (!state.get(OPEN)) {
            world.setBlockState(pos, state.with(OPEN, true), 3);
            world.playSound(null, pos, GallifreySounds.POLICE_BOX_DOOR_OPEN, SoundCategory.BLOCKS, 0.65F, 1.0F);
            serverPlayer.sendMessage(Text.literal("TARDIS doors opened. Click again to enter."), true);
            return ActionResult.SUCCESS;
        }

        boolean entered = TardisDimensionManager.enter(serverPlayer, tardis);
        if (entered) {
            world.playSound(null, pos, GallifreySounds.POLICE_BOX_DOOR_OPEN, SoundCategory.BLOCKS, 0.65F, 1.0F);
        }
        return ActionResult.SUCCESS;
    }
}
