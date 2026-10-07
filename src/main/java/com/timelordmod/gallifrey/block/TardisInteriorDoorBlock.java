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
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TardisInteriorDoorBlock extends Block implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public TardisInteriorDoorBlock(Settings settings) {
        super(settings);
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
