package com.timelordmod.gallifrey.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.text.Text;

/** A usable dice tablet: right-click to roll a six-sided die. */
public class DiceTabletItem extends Item {
    public DiceTabletItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            int roll = world.random.nextInt(6) + 1;
            user.sendMessage(Text.literal("Dice Tablet: rolled " + roll + " (1-6)"), true);
        }
        return TypedActionResult.success(stack, world.isClient);
    }
}
