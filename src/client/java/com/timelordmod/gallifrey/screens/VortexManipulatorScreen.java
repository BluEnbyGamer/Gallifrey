package com.timelordmod.gallifrey.screens;

import com.timelordmod.gallifrey.networking.ModPackets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VortexManipulatorScreen extends Screen {

    // =============================================================
    // GUI CONSTANTS
    // =============================================================

    private static final int GUI_WIDTH = 320;
    private static final int GUI_HEIGHT = 230;

    private static final int PANEL = 0xF0091118;
    private static final int PANEL_LIGHT = 0xFF101D25;
    private static final int PANEL_DARK = 0xFF080D12;

    private static final int CYAN = 0xFF26E6FF;
    private static final int CYAN_DIM = 0xFF08758C;
    private static final int CYAN_DARK = 0xFF063D4A;

    private static final int TEXT = 0xFFE7FBFF;
    private static final int TEXT_DIM = 0xFF75AAB5;

    private static final int GREEN = 0xFF38FF88;
    private static final int RED = 0xFFFF4F6B;

    private int left;
    private int top;

    // =============================================================
    // WIDGETS
    // =============================================================

    private TextFieldWidget dimension;
    private TextFieldWidget x;
    private TextFieldWidget y;
    private TextFieldWidget z;

    private ButtonWidget surface;
    private ButtonWidget teleport;

    private boolean surfaceMode = false;

    // Used for subtle animation
    private float vortexTime = 0;

    // =============================================================
    // CONSTRUCTOR
    // =============================================================

    public VortexManipulatorScreen() {
        super(Text.literal("Vortex Manipulator"));
    }

    // =============================================================
    // INIT
    // =============================================================

    @Override
    protected void init() {

        left = (width - GUI_WIDTH) / 2;
        top = (height - GUI_HEIGHT) / 2;

        // ---------------------------------------------------------
        // DIMENSION
        // ---------------------------------------------------------

        dimension = new TextFieldWidget(
                textRenderer,
                left + 25,
                top + 65,
                270,
                22,
                Text.literal("Dimension")
        );

        dimension.setText("overworld");
        dimension.setMaxLength(100);

        dimension.setEditableColor(TEXT);
        dimension.setUneditableColor(TEXT_DIM);

        addDrawableChild(dimension);

        // ---------------------------------------------------------
        // COORDINATES
        // ---------------------------------------------------------

        x = createField(
                left + 25,
                top + 112,
                82,
                "X"
        );

        y = createField(
                left + 119,
                top + 112,
                82,
                "Y"
        );

        z = createField(
                left + 213,
                top + 112,
                82,
                "Z"
        );

        // ---------------------------------------------------------
        // SURFACE MODE
        // ---------------------------------------------------------

        surface = ButtonWidget.builder(
                Text.literal("SURFACE  •  OFF"),
                button -> {

                    surfaceMode = !surfaceMode;

                    button.setMessage(
                            Text.literal(
                                    surfaceMode
                                            ? "SURFACE  •  ON"
                                            : "SURFACE  •  OFF"
                            )
                    );
                }
        ).dimensions(
                left + 25,
                top + 153,
                130,
                22
        ).build();

        addDrawableChild(surface);

        // ---------------------------------------------------------
        // STATUS
        // ---------------------------------------------------------

        // ---------------------------------------------------------
        // TELEPORT
        // ---------------------------------------------------------

        teleport = ButtonWidget.builder(
                Text.literal("ENGAGE VORTEX"),
                button -> teleport()
        ).dimensions(
                left + 25,
                top + 187,
                270,
                27
        ).build();

        addDrawableChild(teleport);
    }

    // =============================================================
    // CREATE COORDINATE FIELD
    // =============================================================

    private TextFieldWidget createField(
            int x,
            int y,
            int width,
            String name
    ) {

        TextFieldWidget field = new TextFieldWidget(
                textRenderer,
                x,
                y,
                width,
                22,
                Text.literal(name)
        );

        field.setPlaceholder(
                Text.literal(name)
        );

        field.setMaxLength(30);

        field.setEditableColor(TEXT);
        field.setUneditableColor(TEXT_DIM);

        addDrawableChild(field);

        return field;
    }

    // =============================================================
    // TELEPORT
    // =============================================================

    private void teleport() {

        if (client == null || client.player == null) {
            return;
        }

        // ---------------------------------------------------------
        // DIMENSION
        // ---------------------------------------------------------

        Identifier dimensionId =
                parseDimension(dimension.getText());

        if (dimensionId == null) {

            client.player.sendMessage(
                    Text.literal("INVALID DIMENSION"),
                    true
            );

            return;
        }

        // ---------------------------------------------------------
        // COORDINATES
        // ---------------------------------------------------------

        Double targetX =
                parseCoordinate(x.getText());

        Double targetY =
                parseCoordinate(y.getText());

        Double targetZ =
                parseCoordinate(z.getText());

        if (
                targetX == null ||
                        targetY == null ||
                        targetZ == null
        ) {

            client.player.sendMessage(
                    Text.literal("INVALID COORDINATES"),
                    true
            );

            return;
        }

        // ---------------------------------------------------------
        // CREATE PACKET
        // ---------------------------------------------------------

        PacketByteBuf payload =
                PacketByteBufs.create();

        /*
         * Target player mode
         *
         * false = coordinates
         * true  = target player
         */

        payload.writeBoolean(false);

        // Dimension
        payload.writeIdentifier(dimensionId);

        // Coordinates
        payload.writeDouble(targetX);
        payload.writeDouble(targetY);
        payload.writeDouble(targetZ);

        // Surface mode
        payload.writeBoolean(surfaceMode);

        // ---------------------------------------------------------
        // SEND
        // ---------------------------------------------------------

        ClientPlayNetworking.send(
                ModPackets.VM_PACKET,
                payload
        );

        // Close screen
        client.setScreen(null);
    }

    // =============================================================
    // COORDINATE PARSER
    // =============================================================

    private Double parseCoordinate(String text) {

        if (
                text == null ||
                        text.trim().isEmpty()
        ) {
            return null;
        }

        try {

            return Double.parseDouble(
                    text.trim()
            );

        } catch (NumberFormatException ignored) {

            return null;
        }
    }

    // =============================================================
    // DIMENSION PARSER
    // =============================================================

    private Identifier parseDimension(String input) {

        if (input == null) {
            return null;
        }

        String normalized =
                input.trim().toLowerCase();

        if (normalized.isEmpty()) {
            return null;
        }

        return switch (normalized) {

            case "overworld" ->
                    new Identifier(
                            "minecraft",
                            "overworld"
                    );

            case "nether" ->
                    new Identifier(
                            "minecraft",
                            "the_nether"
                    );

            case "end" ->
                    new Identifier(
                            "minecraft",
                            "the_end"
                    );

            default ->
                    Identifier.tryParse(normalized);
        };
    }

    // =============================================================
    // RENDER
    // =============================================================

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {

        vortexTime += delta;

        drawBackground(context);

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );

        drawLabels(
                context,
                mouseX,
                mouseY
        );
    }

    // =============================================================
    // BACKGROUND
    // =============================================================

    private void drawBackground(
            DrawContext context
    ) {

        // ---------------------------------------------------------
        // DARKEN WORLD
        // ---------------------------------------------------------

        context.fill(
                0,
                0,
                width,
                height,
                0x99000000
        );

        // ---------------------------------------------------------
        // OUTER GLOW
        // ---------------------------------------------------------

        context.fill(
                left - 3,
                top - 3,
                left + GUI_WIDTH + 3,
                top + GUI_HEIGHT + 3,
                0x4016D9FF
        );

        // ---------------------------------------------------------
        // MAIN PANEL
        // ---------------------------------------------------------

        context.fill(
                left,
                top,
                left + GUI_WIDTH,
                top + GUI_HEIGHT,
                PANEL
        );

        // ---------------------------------------------------------
        // OUTER BORDER
        // ---------------------------------------------------------

        drawBorder(
                context,
                left,
                top,
                GUI_WIDTH,
                GUI_HEIGHT,
                CYAN
        );

        // ---------------------------------------------------------
        // INNER BORDER
        // ---------------------------------------------------------

        drawBorder(
                context,
                left + 4,
                top + 4,
                GUI_WIDTH - 8,
                GUI_HEIGHT - 8,
                CYAN_DARK
        );

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        context.fill(
                left + 10,
                top + 10,
                left + GUI_WIDTH - 10,
                top + 43,
                PANEL_LIGHT
        );

        // Header accent line

        context.fill(
                left + 10,
                top + 42,
                left + GUI_WIDTH - 10,
                top + 43,
                CYAN_DIM
        );

        // ---------------------------------------------------------
        // HEADER TEXT
        // ---------------------------------------------------------

        context.drawText(
                textRenderer,
                "VORTEX MANIPULATOR",
                left + 20,
                top + 18,
                CYAN,
                false
        );

        context.drawText(
                textRenderer,
                "TEMPORAL NAVIGATION SYSTEM",
                left + 20,
                top + 30,
                TEXT_DIM,
                false
        );

        // ---------------------------------------------------------
        // VORTEX ICON
        // ---------------------------------------------------------

        drawVortexIcon(
                context,
                left + GUI_WIDTH - 32,
                top + 26
        );

        // ---------------------------------------------------------
        // SECTION DIVIDER
        // ---------------------------------------------------------

        context.fill(
                left + 20,
                top + 51,
                left + GUI_WIDTH - 20,
                top + 52,
                CYAN_DARK
        );

        // ---------------------------------------------------------
        // DESTINATION PANEL
        // ---------------------------------------------------------

        drawSectionBox(
                context,
                left + 18,
                top + 55,
                GUI_WIDTH - 36,
                39
        );

        // ---------------------------------------------------------
        // COORDINATE PANEL
        // ---------------------------------------------------------

        drawSectionBox(
                context,
                left + 18,
                top + 100,
                GUI_WIDTH - 36,
                61
        );

        // ---------------------------------------------------------
        // LOWER DIVIDER
        // ---------------------------------------------------------

        context.fill(
                left + 20,
                top + 170,
                left + GUI_WIDTH - 20,
                top + 171,
                CYAN_DARK
        );

        // ---------------------------------------------------------
        // STATUS LIGHT
        // ---------------------------------------------------------

        int statusColor =
                surfaceMode
                        ? 0xFFFFC247
                        : GREEN;

        context.fill(
                left + 183,
                top + 155,
                left + 188,
                top + 160,
                statusColor
        );
    }

    // =============================================================
    // SECTION BOX
    // =============================================================

    private void drawSectionBox(
            DrawContext context,
            int x,
            int y,
            int width,
            int height
    ) {

        context.fill(
                x,
                y,
                x + width,
                y + height,
                PANEL_DARK
        );

        // top highlight
        context.fill(
                x,
                y,
                x + width,
                y + 1,
                CYAN_DARK
        );

        // bottom highlight
        context.fill(
                x,
                y + height - 1,
                x + width,
                y + height,
                0xFF102A33
        );
    }

    // =============================================================
    // VORTEX ICON
    // =============================================================

    private void drawVortexIcon(
            DrawContext context,
            int centerX,
            int centerY
    ) {

        int pulse =
                (int) (
                        Math.sin(
                                vortexTime * 0.08
                        ) * 2
                );

        // Outer ring

        context.drawBorder(
                centerX - 11 - pulse,
                centerY - 11 - pulse,
                22 + pulse * 2,
                22 + pulse * 2,
                CYAN_DARK
        );

        // Inner ring

        context.drawBorder(
                centerX - 7,
                centerY - 7,
                14,
                14,
                CYAN_DIM
        );

        // Core

        context.fill(
                centerX - 3,
                centerY - 3,
                centerX + 4,
                centerY + 4,
                CYAN
        );

        // Crosshair

        context.fill(
                centerX - 14,
                centerY,
                centerX - 9,
                centerY + 1,
                CYAN_DARK
        );

        context.fill(
                centerX + 9,
                centerY,
                centerX + 14,
                centerY + 1,
                CYAN_DARK
        );
    }

    // =============================================================
    // LABELS
    // =============================================================

    private void drawLabels(
            DrawContext context,
            int mouseX,
            int mouseY
    ) {

        // ---------------------------------------------------------
        // DESTINATION
        // ---------------------------------------------------------

        context.drawText(
                textRenderer,
                "DESTINATION",
                left + 26,
                top + 59,
                CYAN,
                false
        );

        // ---------------------------------------------------------
        // COORDINATES
        // ---------------------------------------------------------

        context.drawText(
                textRenderer,
                "COORDINATES",
                left + 26,
                top + 104,
                CYAN,
                false
        );

        // Coordinate labels

        drawCoordinateLabel(
                context,
                "X",
                left + 25,
                top + 145
        );

        drawCoordinateLabel(
                context,
                "Y",
                left + 119,
                top + 145
        );

        drawCoordinateLabel(
                context,
                "Z",
                left + 213,
                top + 145
        );

        // ---------------------------------------------------------
        // STATUS
        // ---------------------------------------------------------

        context.drawText(
                textRenderer,
                surfaceMode
                        ? "SURFACE LOCK"
                        : "VORTEX ONLINE",
                left + 194,
                top + 153,
                surfaceMode
                        ? 0xFFFFC247
                        : GREEN,
                false
        );
    }

    // =============================================================
    // COORDINATE LABEL
    // =============================================================

    private void drawCoordinateLabel(
            DrawContext context,
            String label,
            int x,
            int y
    ) {

        context.drawText(
                textRenderer,
                label,
                x,
                y,
                TEXT_DIM,
                false
        );
    }

    // =============================================================
    // BORDER
    // =============================================================

    private void drawBorder(
            DrawContext context,
            int x,
            int y,
            int width,
            int height,
            int color
    ) {

        // Top

        context.fill(
                x,
                y,
                x + width,
                y + 1,
                color
        );

        // Bottom

        context.fill(
                x,
                y + height - 1,
                x + width,
                y + height,
                color
        );

        // Left

        context.fill(
                x,
                y,
                x + 1,
                y + height,
                color
        );

        // Right

        context.fill(
                x + width - 1,
                y,
                x + width,
                y + height,
                color
        );
    }

    // =============================================================
    // GAME DOES NOT PAUSE
    // =============================================================

    @Override
    public boolean shouldPause() {
        return false;
    }
}
