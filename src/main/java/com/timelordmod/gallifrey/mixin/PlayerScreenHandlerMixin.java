package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.item.custom.SonicShadesSlotAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {
    @Shadow public abstract Slot addSlot(Slot slot);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gallifrey$addSonicShadesSlot(PlayerInventory playerInventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        Inventory glasses = new Inventory() {
            @Override public int size() { return 1; }
            @Override public boolean isEmpty() { return ((SonicShadesSlotAccess) owner).gallifrey$getSonicShades().isEmpty(); }
            @Override public ItemStack getStack(int slot) { return slot == 0 ? ((SonicShadesSlotAccess) owner).gallifrey$getSonicShades() : ItemStack.EMPTY; }
            @Override public ItemStack removeStack(int slot, int amount) {
                if (slot != 0 || amount <= 0) return ItemStack.EMPTY;
                ItemStack current = ((SonicShadesSlotAccess) owner).gallifrey$getSonicShades();
                if (current.isEmpty()) return ItemStack.EMPTY;
                ItemStack removed = current.split(amount);
                ((SonicShadesSlotAccess) owner).gallifrey$setSonicShades(current.isEmpty() ? ItemStack.EMPTY : current);
                return removed;
            }
            @Override public ItemStack removeStack(int slot) {
                if (slot != 0) return ItemStack.EMPTY;
                ItemStack current = ((SonicShadesSlotAccess) owner).gallifrey$getSonicShades();
                ((SonicShadesSlotAccess) owner).gallifrey$setSonicShades(ItemStack.EMPTY);
                return current;
            }
            @Override public void setStack(int slot, ItemStack stack) { if (slot == 0) ((SonicShadesSlotAccess) owner).gallifrey$setSonicShades(stack); }
            @Override public void markDirty() { }
            @Override public boolean canPlayerUse(PlayerEntity player) { return player == owner; }
            @Override public void clear() { ((SonicShadesSlotAccess) owner).gallifrey$setSonicShades(ItemStack.EMPTY); }
        };
        this.addSlot(new Slot(glasses, 0, 77, 8) {
            @Override public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof com.timelordmod.gallifrey.item.custom.SonicShadesItem;
            }
            @Override public int getMaxItemCount() { return 1; }
        });
    }
}
