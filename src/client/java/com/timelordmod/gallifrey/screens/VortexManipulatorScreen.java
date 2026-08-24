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

    private static final int GUI_WIDTH = 300;
    private static final int GUI_HEIGHT = 220;

    private int left;
    private int top;

    private TextFieldWidget dimension;
    private TextFieldWidget x;
    private TextFieldWidget y;
    private TextFieldWidget z;

    private ButtonWidget surface;
    private ButtonWidget teleport;
    private boolean surfaceMode = false;


    public VortexManipulatorScreen() {
        super(Text.literal("Vortex Manipulator"));
    }

    @Override
    protected void init() {
        left = (width - GUI_WIDTH) / 2;
        top = (height - GUI_HEIGHT) / 2;

        // ---------------------------------------------------------
        // DIMENSION
        // ---------------------------------------------------------

        dimension = new TextFieldWidget(
                textRenderer,
                left + 45,
                top + 55,
                210,
                20,
                Text.literal("Dimension")
        );

        dimension.setText("overworld");
        dimension.setMaxLength(100);
        dimension.setDrawsBackground(true);
        dimension.setEditableColor(0xFFFFFF);
        dimension.setUneditableColor(0x777777);

        addDrawableChild(dimension);

        // ---------------------------------------------------------
        // COORDINATES
        // ---------------------------------------------------------

        x = createField(left + 45, top + 105, "X");
        y = createField(left + 125, top + 105, "Y");
        z = createField(left + 205, top + 105, "Z");

        // ---------------------------------------------------------
        // SURFACE MODE
        // ---------------------------------------------------------

        surface = ButtonWidget.builder(
                Text.literal("SURFACE: OFF"),
                button -> {
                    surfaceMode = !surfaceMode;
                    button.setMessage(Text.literal(surfaceMode ? "SURFACE: ON" : "SURFACE: OFF"));
                }
        ).dimensions(
                left + 45,
                top + 145,
                100,
                20
        ).build();

        addDrawableChild(surface);

        // ---------------------------------------------------------
        // TELEPORT BUTTON
        // ---------------------------------------------------------

        teleport = ButtonWidget.builder(
                Text.literal("ENGAGE VORTEX"),
                button -> teleport()
        ).dimensions(
                left + 45,
                top + 175,
                210,
                25
        ).build();

        addDrawableChild(teleport);
    }

    private TextFieldWidget createField(
            int x,
            int y,
            String name
    ) {
        TextFieldWidget field = new TextFieldWidget(
                textRenderer,
                x,
                y,
                65,
                20,
                Text.literal(name)
        );

        field.setPlaceholder(Text.literal(name));
        field.setMaxLength(30);
        field.setEditableColor(0xFFFFFF);

        addDrawableChild(field);

        return field;
    }

    // -------------------------------------------------------------
    // TELEPORT
    // -------------------------------------------------------------

    private void teleport() {

        if (client == null || client.player == null) {
            return;
        }

        // ---------------------------------------------------------
        // DIMENSION
        // ---------------------------------------------------------

        Identifier dimensionId = parseDimension(dimension.getText());

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

        Double targetX = parseCoordinate(x.getText());
        Double targetY = parseCoordinate(y.getText());
        Double targetZ = parseCoordinate(z.getText());

        if (targetX == null || targetY == null || targetZ == null) {

            client.player.sendMessage(
                    Text.literal("INVALID COORDINATES"),
                    true
            );

            return;
        }

        // ---------------------------------------------------------
        // CREATE PACKET
        // ---------------------------------------------------------

        PacketByteBuf payload = PacketByteBufs.create();

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
        // SEND TO SERVER
        // ---------------------------------------------------------

        ClientPlayNetworking.send(
                ModPackets.VM_PACKET,
                payload
        );

        // Close screen
        client.setScreen(null);
    }

    // -------------------------------------------------------------
    // COORDINATE PARSER
    // -------------------------------------------------------------

    private Double parseCoordinate(String text) {

        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        try {
            return Double.parseDouble(text.trim());

        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    // -------------------------------------------------------------
    // DIMENSION PARSER
    // -------------------------------------------------------------

    private Identifier parseDimension(String input) {

        if (input == null) {
            return null;
        }

        String normalized = input
                .trim()
                .toLowerCase();

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

    // -------------------------------------------------------------
    // DRAWING
    // -------------------------------------------------------------

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {

        drawBackground(context);

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );

        drawLabels(context);
    }

    // -------------------------------------------------------------
    // BACKGROUND
    // -------------------------------------------------------------

    private void drawBackground(
            DrawContext context
    ) {

        // Outer panel
        context.fill(
                left,
                top,
                left + GUI_WIDTH,
                top + GUI_HEIGHT,
                0xFF080D12
        );

        // Cyan outer border
        drawBorder(
                context,
                left,
                top,
                GUI_WIDTH,
                GUI_HEIGHT,
                0xFF16D9FF
        );

        // Dark cyan inner border
        drawBorder(
                context,
                left + 5,
                top + 5,
                GUI_WIDTH - 10,
                GUI_HEIGHT - 10,
                0xFF07566B
        );

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        context.fill(
                left + 10,
                top + 10,
                left + GUI_WIDTH - 10,
                top + 40,
                0xFF101C24
        );

        context.drawText(
                textRenderer,
                "VORTEX MANIPULATOR",
                left + 20,
                top + 20,
                0xFF26E6FF,
                false
        );

        // Vortex symbol
        int centerX = left + GUI_WIDTH - 35;
        int centerY = top + 25;

        context.drawText(
                textRenderer,
                "◉",
                centerX - 5,
                centerY - 7,
                0xFF26E6FF,
                false
        );

        // ---------------------------------------------------------
        // SEPARATORS
        // ---------------------------------------------------------

        context.fill(
                left + 20,
                top + 45,
                left + GUI_WIDTH - 20,
                top + 46,
                0xFF07566B
        );

        context.fill(
                left + 20,
                top + 135,
                left + GUI_WIDTH - 20,
                top + 136,
                0xFF07566B
        );
    }

    // -------------------------------------------------------------
    // LABELS
    // -------------------------------------------------------------

    private void drawLabels(
            DrawContext context
    ) {

        context.drawText(
                textRenderer,
                "DESTINATION",
                left + 45,
                top + 44,
                0xFF7DEFFF,
                false
        );

        context.drawText(
                textRenderer,
                "COORDINATES",
                left + 45,
                top + 94,
                0xFF7DEFFF,
                false
        );

        context.drawText(
                textRenderer,
                "X",
                left + 45,
                top + 128,
                0xFF26E6FF,
                false
        );

        context.drawText(
                textRenderer,
                "Y",
                left + 125,
                top + 128,
                0xFF26E6FF,
                false
        );

        context.drawText(
                textRenderer,
                "Z",
                left + 205,
                top + 128,
                0xFF26E6FF,
                false
        );

        // Status
        context.drawText(
                textRenderer,
                "VORTEX ONLINE",
                left + 165,
                top + 150,
                0xFF38FF88,
                false
        );
    }

    // -------------------------------------------------------------
    // BORDER
    // -------------------------------------------------------------

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

    // -------------------------------------------------------------
    // GAME DOES NOT PAUSE
    // -------------------------------------------------------------

    @Override
    public boolean shouldPause() {
        return false;
    }
}

