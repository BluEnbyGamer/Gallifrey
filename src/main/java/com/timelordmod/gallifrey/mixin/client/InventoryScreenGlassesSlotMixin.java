package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Renders a vanilla slot plus a glasses silhouette when the custom slot is empty. */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenGlassesSlotMixin {
    @Shadow protected int x;
    @Shadow protected int y;

    private static final Identifier GALLIFREY$INVENTORY_TEXTURE =
            new Identifier("minecraft", "textures/gui/container/inventory.png");
    private static final Identifier GALLIFREY$GLASSES_ICON =
            new Identifier("gallifrey", "textures/gui/empty_glasses_slot.png");

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void gallifrey$drawGlassesSlot(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        // Must match PlayerScreenHandlerMixin's slot at (77, 8).
        int slotX = this.x + 76;
        int slotY = this.y + 7;
        context.drawTexture(GALLIFREY$INVENTORY_TEXTURE, slotX, slotY,
                7.0F, 83.0F, 18, 18, 256, 256);
        context.drawTexture(GALLIFREY$GLASSES_ICON, slotX + 1, slotY + 1,
                0, 0, 16, 16, 16, 16);
    }
}
