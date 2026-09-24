package com.timelordmod.gallifrey.screens;

import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

/** Confirmation screen shown before the Vortex Manipulator self-destructs. */
public class VortexSelfDestructConfirmScreen extends Screen {
    private static final int W = 500, H = 210;
    private static final int PANEL = 0xF0091118, PANEL_LIGHT = 0xFF101D25, PANEL_DARK = 0xFF080D12;
    private static final int CYAN = 0xFF26E6FF, CYAN_DIM = 0xFF08758C, CYAN_DARK = 0xFF063D4A;
    private static final int TEXT = 0xFFE7FBFF, DIM = 0xFF75AAB5, RED = 0xFFFF4F6B;

    private final Screen parent;
    private int left, top;

    public VortexSelfDestructConfirmScreen(Screen parent) {
        super(Text.literal("Vortex Manipulator Self-Destruct"));
        this.parent = parent;
    }

    @Override protected void init() {
        left = (width - W) / 2;
        top = (height - H) / 2;
        addDrawableChild(new ThemedButton(left + 16, top + 150, 226, 30, Text.literal("YES — SELF-DESTRUCT"), b -> confirm()));
        addDrawableChild(new ThemedButton(left + 258, top + 150, 226, 30, Text.literal("NO — GO BACK"), b -> client.setScreen(parent)));
    }

    private void confirm() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("SELF_DESTRUCT");
        ClientPlayNetworking.send(ModPackets.VM_PACKET, buf);
        client.setScreen(null);
    }

    @Override public void close() {
        client.setScreen(parent);
    }

    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x99000000);
        context.fill(left - 3, top - 3, left + W + 3, top + H + 3, 0x4016D9FF);
        context.fill(left, top, left + W, top + H, PANEL);
        context.drawBorder(left, top, W, H, CYAN);
        context.drawBorder(left + 4, top + 4, W - 8, H - 8, CYAN_DARK);
        context.fill(left + 8, top + 8, left + W - 8, top + 28, PANEL_LIGHT);
        context.fill(left + 8, top + 27, left + W - 8, top + 28, CYAN_DIM);
        context.drawText(textRenderer, "VORTEX MANIPULATOR", left + 16, top + 12, CYAN, false);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("SELF-DESTRUCT"), left + W / 2, top + 42, RED);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Arm the Vortex Manipulator self-destruct sequence?"), left + W / 2, top + 68, TEXT);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("YES starts a 10-second countdown."), left + W / 2, top + 91, RED);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("You can cancel it before detonation."), left + W / 2, top + 106, RED);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("NO returns you to the control console."), left + W / 2, top + 123, DIM);
        super.render(context, mouseX, mouseY, delta);
    }

    private String fit(String value, int maxWidth) {
        if (textRenderer.getWidth(value) <= maxWidth) return value;
        String ellipsis = "…";
        int end = value.length();
        while (end > 0 && textRenderer.getWidth(value.substring(0, end) + ellipsis) > maxWidth) end--;
        return end <= 0 ? ellipsis : value.substring(0, end) + ellipsis;
    }

    private class ThemedButton extends ButtonWidget {
        ThemedButton(int x, int y, int w, int h, Text message, PressAction action) {
            super(x, y, w, h, message, action, DEFAULT_NARRATION_SUPPLIER);
        }
        @Override public void renderButton(DrawContext c, int mx, int my, float delta) {
            int bg = isHovered() ? 0xFF123744 : PANEL_DARK;
            c.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), bg);
            c.drawBorder(getX(), getY(), getWidth(), getHeight(), active ? CYAN_DIM : 0xFF30434A);
            Text visible = Text.literal(fit(getMessage().getString(), Math.max(12, getWidth() - 12)));
            c.drawCenteredTextWithShadow(textRenderer, visible, getX() + getWidth() / 2, getY() + getHeight() / 2 - 4, active ? TEXT : DIM);
        }
    }
}
