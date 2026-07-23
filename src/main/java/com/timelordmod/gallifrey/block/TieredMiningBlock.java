package com.timelordmod.gallifrey.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;

public class TieredMiningBlock extends Block {

    private final List<String> normalSpeedTools;
    private final List<String> fastSpeedTools;
    private final List<String> fastestSpeedTools;

    public TieredMiningBlock(Settings settings,
                             List<String> normalSpeedTools,
                             List<String> fastSpeedTools,
                             List<String> fastestSpeedTools) {
        super(settings);
        this.normalSpeedTools = normalSpeedTools;
        this.fastSpeedTools = fastSpeedTools;
        this.fastestSpeedTools = fastestSpeedTools;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float baseDelta = super.calcBlockBreakingDelta(state, player, world, pos);

        ItemStack tool = player.getMainHandStack();
        String toolId = Registries.ITEM.getId(tool.getItem()).toString();

        // Tune these multipliers to taste -- higher number = faster mining.
        if (fastestSpeedTools.contains(toolId)) {
            return baseDelta * 10.0f;
        } else if (fastSpeedTools.contains(toolId)) {
            return baseDelta * 8.0f;
        } else if (normalSpeedTools.contains(toolId)) {
            return baseDelta * 5.0f;
        }
        return baseDelta;
    }
}
