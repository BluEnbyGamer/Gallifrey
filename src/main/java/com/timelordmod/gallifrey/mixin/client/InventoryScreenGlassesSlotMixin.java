package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Draws vanilla-style slot squares for the Sonic Shades and fez slots (the slots draw their own empty icons). */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenGlassesSlotMixin {
    private static final Identifier GALLIFREY$INVENTORY_TEXTURE =
            new Identifier("minecraft", "textures/gui/container/inventory.png");

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void gallifrey$drawGlassesSlot(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        // x/y are declared by HandledScreen, the superclass of InventoryScreen.
        // Read them through the existing accessor mixin rather than shadowing
        // inherited fields on InventoryScreen (which crashes mixin application).
        HandledScreenAccessor screen = (HandledScreenAccessor) this;
        int slotX = screen.gallifrey$getX() + 76;
        int slotY = screen.gallifrey$getY() + 7;
        context.drawTexture(GALLIFREY$INVENTORY_TEXTURE, slotX, slotY,
                7.0F, 83.0F, 18, 18, 256, 256);
        // The fez slot, just below.
        context.drawTexture(GALLIFREY$INVENTORY_TEXTURE, slotX, slotY + 18,
                7.0F, 83.0F, 18, 18, 256, 256);
    }
}
