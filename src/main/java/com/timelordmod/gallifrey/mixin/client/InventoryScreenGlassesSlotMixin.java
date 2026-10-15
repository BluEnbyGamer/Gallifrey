package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Draws a vanilla-style slot background behind Gallifrey's additional glasses slot. */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenGlassesSlotMixin {
    @Shadow protected int x;
    @Shadow protected int y;

    private static final Identifier GALLIFREY$INVENTORY_TEXTURE =
            new Identifier("minecraft", "textures/gui/container/inventory.png");

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void gallifrey$drawGlassesSlot(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        // PlayerScreenHandlerMixin adds the custom slot at (77, 8).
        context.drawTexture(GALLIFREY$INVENTORY_TEXTURE, this.x + 76, this.y + 7,
                7.0F, 83.0F, 18, 18, 256, 256);
    }
}
