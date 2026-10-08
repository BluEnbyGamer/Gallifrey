package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.client.GallifreyClientConfig;
import com.timelordmod.gallifrey.client.VortexBackgroundRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Title screen background choice: the time vortex or the normal panorama.
 *
 * - Swaps the one call that draws the panorama for the vortex when it is switched on.
 * - Adds a small button in the bottom-left corner to flip between the two.
 *   The choice is saved in config/gallifrey-client.properties.
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenVortexMixin extends Screen {

    protected TitleScreenVortexMixin(Text title) {
        super(title);
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V")
    )
    private void gallifrey$drawMenuBackground(RotatingCubeMapRenderer panorama, float delta, float alpha,
                                              DrawContext context, int mouseX, int mouseY, float tickDelta) {
        if (GallifreyClientConfig.isVortexMenuBackground()) {
            VortexBackgroundRenderer.render(context, this.width, this.height, alpha);
        } else {
            panorama.render(delta, alpha);
        }
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void gallifrey$addBackgroundButton(CallbackInfo ci) {
        int buttonWidth = 98;
        this.addDrawableChild(ButtonWidget.builder(gallifrey$label(), button -> {
                    GallifreyClientConfig.setVortexMenuBackground(!GallifreyClientConfig.isVortexMenuBackground());
                    button.setMessage(gallifrey$label());
                })
                // Bottom-left corner, just above the "Minecraft 1.20.1" version text.
                .dimensions(4, this.height - 34, buttonWidth, 20)
                .tooltip(Tooltip.of(Text.translatable("gui.gallifrey.menu_background.tooltip")))
                .build());
    }

    private static Text gallifrey$label() {
        return Text.translatable(GallifreyClientConfig.isVortexMenuBackground()
                ? "gui.gallifrey.menu_background.vortex"
                : "gui.gallifrey.menu_background.panorama");
    }
}
