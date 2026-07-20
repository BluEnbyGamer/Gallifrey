package com.timelordmod.gallifrey.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDate;
import java.time.Month;

@Mixin(value = LogoDrawer.class, priority = 10001)
public class LogoDrawerMixin {

    private static final Identifier GALL_STANDARD =
            new Identifier("gallifrey", "textures/gui/title/gall_standard.png");

    private static final Identifier GALL_XMAS =
            new Identifier("gallifrey", "textures/gui/title/gall_xmas.png");

    private static final Identifier GALL_DWDAY =
            new Identifier("gallifrey", "textures/gui/title/gall_dwday.png");

    private static final Identifier GALL_MODDAY =
            new Identifier("gallifrey", "textures/gui/title/gall_modday.png");

    private static final Identifier GALL_PRIDE =
            new Identifier("gallifrey", "textures/gui/title/gall_pride.png");

    private static final Identifier GALL_BLUESDAY =
            new Identifier("gallifrey", "textures/gui/title/gall_bluesday.png");


    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void gallifrey$replaceLogo(DrawContext context, int screenWidth, float alpha, CallbackInfo ci) {

        Identifier logo = getLogo();

        int logoWidth = 380;
        int logoHeight = 40;

        int x = (screenWidth - logoWidth) / 2;
        int y = 10;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        context.drawTexture(
                logo,
                x,
                y,
                0,
                0,
                logoWidth,
                logoHeight,
                logoWidth,
                logoHeight
        );

        ci.cancel();
    }


    @Unique
    private Identifier getLogo() {
        LocalDate date = LocalDate.now();

        if (date.getMonth() == Month.DECEMBER) {
            return GALL_XMAS;
        }

        if (date.getMonth() == Month.OCTOBER && date.getDayOfMonth() == 15) {
            return GALL_BLUESDAY;
        }

        if (date.getMonth() == Month.NOVEMBER && date.getDayOfMonth() == 23) {
            return GALL_DWDAY;
        }

        if (date.getMonth() == Month.AUGUST && date.getDayOfMonth() == 15) {
            return GALL_MODDAY;
        }

        if (date.getMonth() == Month.JUNE) {
            return GALL_PRIDE;
        }

        return GALL_STANDARD;
    }
}