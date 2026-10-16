package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeScreenHandlerMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void gallifrey$debugPlayerInventory(CallbackInfo ci) {
        CreativeInventoryScreen screen =
                (CreativeInventoryScreen) (Object) this;

        ScreenHandler handler = screen.getScreenHandler();

        System.out.println(
                "========== GALLIFREY PLAYER INVENTORY =========="
        );

        for (Slot slot : handler.slots) {
            System.out.println(
                    "[Gallifrey] slot=(" + slot.x + ", " + slot.y
                            + "), inventory="
                            + slot.inventory.getClass().getName()
                            + ", index=" + slot.getIndex()
                            + ", item=" + slot.getStack()
                            + ", class=" + slot.getClass().getName()
            );
        }

        System.out.println(
                "========== END GALLIFREY PLAYER INVENTORY =========="
        );
    }
}