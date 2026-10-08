package com.timelordmod.gallifrey.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Wearable sonic sunglasses. The actual sonic logic is inherited from the
 * Gallifrey Sonic Screwdriver so the shades use the same power, modes, casing
 * data and SonicHandler behaviour without pulling in AWT/AI code.
 *
 * While worn, the client-side "Use Sonic Shades" keybind invokes the same
 * SonicScrewdriver activation logic against the player's look target.
 */
public class SonicShadesItem extends SonicScrewdriver implements Equipment {

    public SonicShadesItem(Settings settings) {
        super(settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack held = user.getStackInHand(hand);
        ItemStack head = user.getEquippedStack(EquipmentSlot.HEAD);

        if (head.isEmpty()) {
            if (!world.isClient) {
                user.equipStack(EquipmentSlot.HEAD, held.copyWithCount(1));
                held.decrement(1);
            }
            return TypedActionResult.success(held, world.isClient);
        }

        return TypedActionResult.pass(held);
    }
}
