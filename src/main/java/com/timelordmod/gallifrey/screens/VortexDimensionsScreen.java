package com.timelordmod.gallifrey.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

/** Compact Vortex Manipulator destination reference for built-in Minecraft/Gallifrey dimensions. */
public class VortexDimensionsScreen extends Screen {
    private static final int W = 540, H = 360;
    private static final int PANEL = 0xF0060B13, PANEL_LIGHT = 0xFF111C28, PANEL_DARK = 0xFF050A10;
    private static final int CYAN = 0xFF6FE8FF, CYAN_DIM = 0xFF1C7896, CYAN_DARK = 0xFF0B3B4D;
    private static final int TEXT = 0xFFEAF8FF, DIM = 0xFF7896A6;

    private static final List<Destination> DESTINATIONS = List.of(
            new Destination("Overworld", "minecraft:overworld"),
            new Destination("Nether", "minecraft:the_nether"),
            new Destination("The End", "minecraft:the_end"),
            new Destination("Gallifrey", "gallifrey:gallifrey"),
            new Destination("Skaro", "gallifrey:skaro"),
            new Destination("Mars", "gallifrey:mars"),
            new Destination("Mondas", "gallifrey:mondas"),
            new Destination("Prehistoric", "gallifrey:prehistoric"),
            new Destination("Classic", "gallifrey:classic"),
            new Destination("Pete's World", "gallifrey:petes_world"),
            new Destination("Lost Reality", "gallifrey:lost_reality")
    );

    private final VortexManipulatorScreen parent;
    private int left, top;

    public VortexDimensionsScreen(VortexManipulatorScreen parent) {
        super(Text.literal("Vortex Manipulator - Dimensions"));
        this.parent = parent;
    }

    @Override protected void init() {
        left = (width - W) / 2;
        top = (height - H) / 2;

        int rowY = top + 70;
        for (int i = 0; i < DESTINATIONS.size(); i++) {
            Destination d = DESTINATIONS.get(i);
            int col = i % 2;
            int row = i / 2;
            int x = left + 18 + col * 252;
            int y = rowY + row * 35;
            addDrawableChild(button(x, y, 240, 28, d.name + "  •  " + d.id, b -> select(d.id)));
        }

        addDrawableChild(button(left + 18, top + H - 34, 110, 22, "◀ BACK", b -> client.setScreen(parent)));
    }

    private void select(String id) {
        client.setScreen(parent);
        parent.setDimensionValue(id);
    }

    private ButtonWidget button(int x, int y, int w, int h, String label, ButtonWidget.PressAction action) {
        return new ThemedButtonWidget(x, y, w, h, Text.literal(label), action);
    }

    @Override public void render(DrawContext c, int mouseX, int mouseY, float delta) {
        c.fill(0, 0, width, height, 0x99000000);
        c.fill(left - 3, top - 3, left + W + 3, top + H + 3, 0x402B9DFF);
        c.fill(left, top, left + W, top + H, PANEL);
        c.drawBorder(left, top, W, H, CYAN);
        c.drawBorder(left + 4, top + 4, W - 8, H - 8, CYAN_DARK);
        c.fill(left + 10, top + 10, left + W - 10, top + 34, PANEL_LIGHT);
        c.fill(left + 10, top + 33, left + W - 10, top + 34, CYAN_DIM);
        c.drawText(textRenderer, "VORTEX MANIPULATOR", left + 20, top + 17, CYAN, false);
        super.render(c, mouseX, mouseY, delta);
        c.drawText(textRenderer, "Other mods: enter their dimension ID as namespace:modid (example: othermod:dimension_name).",
                left + 18, top + H - 58, DIM, false);
        c.drawText(textRenderer, "The namespace is normally the mod ID; use the exact ID supplied by that mod.",
                left + 18, top + H - 47, DIM, false);
    }

    private class ThemedButtonWidget extends ButtonWidget {
        ThemedButtonWidget(int x, int y, int w, int h, Text message, PressAction action) {
            super(x, y, w, h, message, action, DEFAULT_NARRATION_SUPPLIER);
        }

        @Override public void renderButton(DrawContext c, int mx, int my, float delta) {
            int bg = isHovered() ? 0xFF123044 : PANEL_DARK;
            c.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), bg);
            c.drawBorder(getX(), getY(), getWidth(), getHeight(), CYAN_DIM);
            c.drawCenteredTextWithShadow(textRenderer, getMessage(), getX() + getWidth() / 2,
                    getY() + getHeight() / 2 - 4, TEXT);
        }
    }

    private record Destination(String name, String id) {}
}
