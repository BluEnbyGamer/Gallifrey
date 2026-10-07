package com.timelordmod.gallifrey.fluid;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class GallifreyFluids {
    public static final FlowableFluid STILL_RADIATION = Registry.register(
            Registries.FLUID,
            new Identifier(GallifreyMod.MOD_ID, "radiation"),
            new Still()
    );

    public static final FlowableFluid FLOWING_RADIATION = Registry.register(
            Registries.FLUID,
            new Identifier(GallifreyMod.MOD_ID, "flowing_radiation"),
            new Flowing()
    );

    public static final FlowableFluid STILL_CLASSIC_LAVA = Registry.register(
            Registries.FLUID,
            new Identifier(GallifreyMod.MOD_ID, "classic_lava"),
            new ClassicLavaStill()
    );

    public static final FlowableFluid FLOWING_CLASSIC_LAVA = Registry.register(
            Registries.FLUID,
            new Identifier(GallifreyMod.MOD_ID, "flowing_classic_lava"),
            new ClassicLavaFlowing()
    );

    public static final FluidBlock CLASSIC_LAVA = Registry.register(
            Registries.BLOCK,
            new Identifier(GallifreyMod.MOD_ID, "classic_nether_lava"),
            new FluidBlock(STILL_CLASSIC_LAVA, FabricBlockSettings.copyOf(net.minecraft.block.Blocks.LAVA)
                    .strength(100.0F)
                    .noCollision()
                    .dropsNothing()
                    .replaceable())
    );

    public static final FluidBlock RADIATION = Registry.register(
            Registries.BLOCK,
            new Identifier(GallifreyMod.MOD_ID, "radiation"),
            new FluidBlock(STILL_RADIATION, FabricBlockSettings.copyOf(net.minecraft.block.Blocks.WATER)
                    .mapColor(net.minecraft.block.MapColor.DARK_GREEN)
                    .strength(100.0F)
                    .noCollision()
                    .dropsNothing()
                    .replaceable()
                    .sounds(BlockSoundGroup.SLIME)) {
                @Override
                public void onEntityCollision(BlockState state, net.minecraft.world.World world, BlockPos pos, Entity entity) {
                    super.onEntityCollision(state, world, pos, entity);
                    // Radiation remains swimmable, but it must still burn anything
                    // that enters it. Apply the fire after vanilla water collision
                    // handling so the water tag cannot immediately extinguish it.
                    entity.setOnFireFor(2);
                }
            }
    );

    public static void registerModFluids() {
        GallifreyMod.LOGGER.info("Registering ModFluids for " + GallifreyMod.MOD_ID);
    }

    public abstract static class RadiationFluid extends FlowableFluid {
        @Override
        public Fluid getStill() {
            return STILL_RADIATION;
        }

        @Override
        public Fluid getFlowing() {
            return FLOWING_RADIATION;
        }



        @Override
        protected void beforeBreakingBlock(net.minecraft.world.WorldAccess world, BlockPos pos, BlockState state) {
            BlockEntityAccessor.dropStacks(state, world, pos);
        }





        @Override
        protected BlockState toBlockState(FluidState state) {
            return GallifreyFluids.RADIATION.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        @Override
        public boolean matchesType(Fluid fluid) {
            return fluid == STILL_RADIATION || fluid == FLOWING_RADIATION;
        }

        @Override
        protected int getLevelDecreasePerBlock(WorldView world) {
            return 1;
        }

        @Override
        protected int getFlowSpeed(WorldView world) {
            return 1;
        }

        @Override
        public boolean isInfinite(net.minecraft.world.World world) {
            return false;
        }

        @Override
        protected float getBlastResistance() {
            return 100.0F;
        }

    }

    public static class Flowing extends RadiationFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(Properties.LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(Properties.LEVEL_1_8);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends RadiationFluid {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

    public abstract static class ClassicLavaFluid extends LavaFluid {
        @Override
        public Fluid getStill() {
            return STILL_CLASSIC_LAVA;
        }

        @Override
        public Fluid getFlowing() {
            return FLOWING_CLASSIC_LAVA;
        }



        @Override
        protected void beforeBreakingBlock(net.minecraft.world.WorldAccess world, BlockPos pos, BlockState state) {
            Block.dropStacks(state, world, pos, state.hasBlockEntity() ? world.getBlockEntity(pos) : null);
        }


        @Override
        public BlockState toBlockState(FluidState state) {
            return CLASSIC_LAVA.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        @Override
        public boolean matchesType(Fluid fluid) {
            return fluid == STILL_CLASSIC_LAVA || fluid == FLOWING_CLASSIC_LAVA;
        }

    }

    public static class ClassicLavaFlowing extends ClassicLavaFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(Properties.LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(Properties.LEVEL_1_8);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class ClassicLavaStill extends ClassicLavaFluid {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

    private static final class BlockEntityAccessor {
        private static void dropStacks(BlockState state, net.minecraft.world.WorldAccess world, BlockPos pos) {
            net.minecraft.block.Block.dropStacks(state, world, pos, state.hasBlockEntity() ? world.getBlockEntity(pos) : null);
        }
    }
}
