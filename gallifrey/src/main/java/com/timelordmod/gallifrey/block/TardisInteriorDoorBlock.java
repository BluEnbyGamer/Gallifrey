package com.timelordmod.gallifrey.block;

import com.timelordmod.gallifrey.block.entity.TardisInteriorDoorBlockEntity;
import com.timelordmod.gallifrey.GallifreySounds;
import net.minecraft.sound.SoundCategory;
import com.timelordmod.gallifrey.tardis.TardisDimensionManager;
import com.timelordmod.gallifrey.tardis.TardisRegistryState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TardisInteriorDoorBlock extends Block implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public TardisInteriorDoorBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Like a normal Minecraft door, the front faces the player placing it.
        // FACING is the outward/front face; use the direction toward the placer.
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, net.minecraft.item.ItemStack stack) {
        super.onPlaced(world, pos, state, placer, stack);
        if (world.isClient || !(placer instanceof ServerPlayerEntity player) || !(world.getBlockEntity(pos) instanceof TardisInteriorDoorBlockEntity door)) return;
        TardisRegistryState registry = TardisRegistryState.get(player.getServer());
        java.util.UUID id = registry.getActiveTardis(player.getUuid());
        TardisRegistryState.Record record = id == null ? null : registry.get(id);
        if (record == null || record.owner() == null || !record.owner().equals(player.getUuid()) || !TardisDimensionManager.isInteriorWorld(world)) return;
        door.setTardisId(id);
        door.setExteriorStyle(record.id() != null ? exteriorStyleFor(player) : "policebox");
        door.setPowered(findPower(player));
    }

    private String exteriorStyleFor(ServerPlayerEntity player) {
        TardisRegistryState.Record record = TardisRegistryState.get(player.getServer()).get(TardisRegistryState.get(player.getServer()).getActiveTardis(player.getUuid()));
        if (record == null) return "policebox";
        net.minecraft.server.world.ServerWorld world = player.getServer().getWorld(net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, new net.minecraft.util.Identifier(record.world())));
        net.minecraft.util.math.BlockPos pos = record == null ? net.minecraft.util.math.BlockPos.ORIGIN : net.minecraft.util.math.BlockPos.fromLong(record.pos());
        if (world != null && world.getBlockEntity(pos) instanceof com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity tardis) return tardis.getExteriorStyle();
        return "policebox";
    }

    private boolean findPower(ServerPlayerEntity player) {
        TardisRegistryState.Record record = TardisRegistryState.get(player.getServer()).get(TardisRegistryState.get(player.getServer()).getActiveTardis(player.getUuid()));
        if (record == null) return false;
        net.minecraft.server.world.ServerWorld world = player.getServer().getWorld(net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, new net.minecraft.util.Identifier(record.world())));
        net.minecraft.util.math.BlockPos pos = net.minecraft.util.math.BlockPos.fromLong(record.pos());
        return world != null && world.getBlockEntity(pos) instanceof com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity tardis && tardis.isPowered();
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TardisInteriorDoorBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;
        if (!(world.getBlockEntity(pos) instanceof TardisInteriorDoorBlockEntity door)) return ActionResult.PASS;

        TardisRegistryState registry = TardisRegistryState.get(serverPlayer.getServer());
        java.util.UUID active = registry.getActiveTardis(serverPlayer.getUuid());
        if (active == null || door.getTardisId() == null || !active.equals(door.getTardisId())) {
            return ActionResult.FAIL;
        }
        world.playSound(null, pos, GallifreySounds.POLICE_BOX_DOOR_OPEN, SoundCategory.BLOCKS, 0.65F, 1.0F);
        return TardisDimensionManager.exit(serverPlayer)
                ? ActionResult.CONSUME : ActionResult.FAIL;
    }
}
