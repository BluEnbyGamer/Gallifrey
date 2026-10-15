package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.item.custom.SonicShadesSlotAccess;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class SonicShadesSlotMixin implements SonicShadesSlotAccess {
    @Unique private static final TrackedData<ItemStack> GALLIFREY_SONIC_SHADES =
            DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void gallifrey$registerGlassesSlot(CallbackInfo ci) {
        ((Entity)(Object)this).getDataTracker().startTracking(GALLIFREY_SONIC_SHADES, ItemStack.EMPTY);
    }

    @Override
    public ItemStack gallifrey$getSonicShades() {
        return ((Entity)(Object)this).getDataTracker().get(GALLIFREY_SONIC_SHADES);
    }

    @Override
    public void gallifrey$setSonicShades(ItemStack stack) {
        ((Entity)(Object)this).getDataTracker().set(GALLIFREY_SONIC_SHADES, stack == null ? ItemStack.EMPTY : stack.copy());
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void gallifrey$saveGlassesSlot(NbtCompound nbt, CallbackInfo ci) {
        ItemStack stack = gallifrey$getSonicShades();
        if (!stack.isEmpty()) nbt.put("GallifreySonicShades", stack.writeNbt(new NbtCompound()));
        else nbt.remove("GallifreySonicShades");
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void gallifrey$loadGlassesSlot(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("GallifreySonicShades", NbtElement.COMPOUND_TYPE)) {
            gallifrey$setSonicShades(ItemStack.fromNbt(nbt.getCompound("GallifreySonicShades")));
        } else {
            gallifrey$setSonicShades(ItemStack.EMPTY);
        }
    }
}
