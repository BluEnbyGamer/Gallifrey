package com.timelordmod.gallifrey.item.custom;

import net.minecraft.item.ItemStack;

/** Access to Gallifrey's fez slot (added to every player by SonicShadesSlotMixin). */
public interface FezSlotAccess {
    ItemStack gallifrey$getFez();
    void gallifrey$setFez(ItemStack stack);
}
