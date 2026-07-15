package com.timelordmod.gallifrey.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.intellij.lang.annotations.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDate;
import java.time.Month;

@Mixin(value = LogoDrawerMixin.class, priority = 200000)
public class LogoDrawerMixin {

    private static final Identifier GALL_STANDARD = new Identifier("gallifrey", "textures/title/gall_standard");
    private static final Identifier GALL_XMAS = new Identifier("gallifrey", "textures/title/gall_xmas");
    private static final Identifier GALL_DWDAY = new Identifier("gallifery", "textures/title/gall_dwday");
    private static final Identifier GALL_MODDAY = new Identifier("gallifery", "textures/title/gall_modday");
    private static final Identifier GALL_PRIDE = new Identifier("gallifery", "textures/title/gall_pride");
    private static final Identifier GALL_BLUESDAY = new Identifier("gallifery", "textures/title/gall_bluesday");

    @Inject(method = "draw*", at = @At("HEAD"), cancellable = false)
    private void gallifrey$drawCustomLogo(DrawContext context, int screenWidth, float alpha, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        Identifier logo;
        if (isChristmas()) {
            logo = GALL_XMAS;
        } else if (isbluesday()) {
            logo = GALL_BLUESDAY;
        } else if (isdwday()) {
            logo = GALL_DWDAY;
        } else if (ismodday()) {
            logo = GALL_MODDAY;
        } else if (ispride()) {
            logo = GALL_PRIDE;
        } else {
            logo = GALL_STANDARD;
        }

        int logoWidth = 1024;
        int logoHeight = 172;

        int centerX = (screenWidth - logoWidth) / 2;
        int logoY = 12; // Adjust vertical position

        RenderSystem.enableBlend();
        context.drawTexture(logo, centerX, logoY, 0.0f, 0.0f, logoWidth, logoHeight, logoWidth, logoHeight);

        ci.cancel(); // prevent other logo logic
    }

    @Unique
    private boolean isChristmas() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.DECEMBER;
    }

    @Unique
    private boolean isbluesday() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.OCTOBER && date.getDayOfMonth() == 15;
    }

    @Unique
    private boolean isdwday() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.NOVEMBER && date.getDayOfMonth() == 23;
    }

    @Unique
    private boolean ismodday() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.AUGUST && date.getDayOfMonth() == 15;
    }

    @Unique
    private boolean ispride() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.JUNE;
    }
}