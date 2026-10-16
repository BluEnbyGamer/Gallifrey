
package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryGlassesSlotMixin {

    private static final Identifier GALLIFREY$INVENTORY_TEXTURE =
            new Identifier("minecraft", "textures/gui/container/inventory.png");

    private static final Identifier GALLIFREY$GLASSES_ICON =
            new Identifier("gallifrey", "textures/gui/empty_glasses_slot.png");

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void gallifrey$drawCreativeGlassesSlot(
            DrawContext context,
            float delta,
            int mouseX,
            int mouseY,
            CallbackInfo ci
    ) {
        CreativeInventoryScreen screen =
                (CreativeInventoryScreen) (Object) this;

        if (!screen.isInventoryTabSelected()) {
            return;
        }

        HandledScreenAccessor accessor = (HandledScreenAccessor) this;

        int guiX = accessor.gallifrey$getX();
        int guiY = accessor.gallifrey$getY();

        int slotX = guiX + 59;
        int slotY = guiY + 62;

        context.drawTexture(
                GALLIFREY$INVENTORY_TEXTURE,
                slotX, slotY,
                7, 83,
                18, 18,
                256, 256
        );

        context.drawTexture(
                GALLIFREY$GLASSES_ICON,
                slotX + 1, slotY + 1,
                0, 0,
                16, 16,
                16, 16
        );
    }
}

