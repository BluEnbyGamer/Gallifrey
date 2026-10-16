package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.item.custom.AccessorySlotIndices;
import com.timelordmod.gallifrey.item.custom.AccessorySlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Adds the Sonic Shades slot and the fez slot to the player's inventory menu, in the column
 * between the player preview and the crafting grid (above the offhand slot).
 *
 * Shift-clicking Sonic Shades or a fez from the inventory puts it in its slot when that slot is
 * empty. The creative inventory places these slots itself (CreativeInventoryAccessorySlotsMixin)
 * and the server accepts creative-mode changes to them (CreativeAccessorySlotsMixin).
 */
@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler implements AccessorySlotIndices {

    @Unique private int gallifrey$shadesSlot = -1;
    @Unique private int gallifrey$fezSlot = -1;

    protected PlayerScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addAccessorySlots(PlayerInventory playerInventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        gallifrey$shadesSlot = this.addSlot(AccessorySlot.shades(owner, 77, 8)).id;
        gallifrey$fezSlot = this.addSlot(AccessorySlot.fez(owner, 77, 26)).id;
    }

    /** Shift-click from the inventory or hotbar: Sonic Shades and fezzes go to their own slot first. */
    @Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
    private void gallifrey$quickMoveToAccessorySlot(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < 9 || index > 45) return; // only from the main inventory, hotbar and offhand
        Slot from = this.slots.get(index);
        if (!from.hasStack()) return;
        ItemStack stack = from.getStack();
        for (int target : new int[]{gallifrey$shadesSlot, gallifrey$fezSlot}) {
            if (target < 0) continue;
            Slot slot = this.slots.get(target);
            if (slot.hasStack() || !slot.canInsert(stack)) continue;
            slot.setStack(stack.split(1));
            if (stack.isEmpty()) from.setStack(ItemStack.EMPTY);
            else from.markDirty();
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }
    }

    @Override
    public int gallifrey$shadesSlotIndex() {
        return gallifrey$shadesSlot;
    }

    @Override
    public int gallifrey$fezSlotIndex() {
        return gallifrey$fezSlot;
    }
}
