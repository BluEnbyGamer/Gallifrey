package com.timelordmod.gallifrey.screens;

import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;
import com.timelordmod.gallifrey.networking.SonicCasingClientNetworking;
import com.timelordmod.gallifrey.sonic.SonicCasing;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class SonicWorkshopScreen extends Screen {

    private static final int GUI_WIDTH = 420;
    private static final int GUI_HEIGHT = 270;

    private static final int PANEL = 0xF0091118;
    private static final int PANEL_LIGHT = 0xFF101D25;
    private static final int PANEL_DARK = 0xFF080D12;

    private static final int CYAN = 0xFF26E6FF;
    private static final int CYAN_DIM = 0xFF08758C;
    private static final int CYAN_DARK = 0xFF063D4A;

    private static final int TEXT = 0xFFE7FBFF;
    private static final int TEXT_DIM = 0xFF75AAB5;

    private static final int GREEN = 0xFF38FF88;

    private int left;
    private int top;

    private SonicCasing selectedCasing;
    private final Hand hand;

    public SonicWorkshopScreen(Hand hand) {
        super(Text.literal("Sonic Workshop"));

        this.hand = hand;
        this.selectedCasing = SonicCasing.THIRD_DOCTOR;

        if (client != null && client.player != null) {

            ItemStack stack =
                    client.player.getStackInHand(hand);

            if (stack.getItem() instanceof SonicScrewdriver) {
                selectedCasing =
                        SonicScrewdriver.getCasing(stack);
            }
        }
    }

    @Override
    protected void init() {

        left =
                (width - GUI_WIDTH) / 2;

        top =
                (height - GUI_HEIGHT) / 2;

        createCasingButtons();
    }

    private void createCasingButtons() {

        SonicCasing[] casings =
                SonicCasing.values();

        int columns = 5;

        int buttonWidth = 72;
        int buttonHeight = 28;

        int spacingX = 8;
        int spacingY = 8;

        int startX =
                left + 25;

        int startY =
                top + 108;

        for (int i = 0; i < casings.length; i++) {

            SonicCasing casing =
                    casings[i];

            int column =
                    i % columns;

            int row =
                    i / columns;

            int buttonX =
                    startX
                            + column
                            * (buttonWidth + spacingX);

            int buttonY =
                    startY
                            + row
                            * (buttonHeight + spacingY);

            addDrawableChild(
                    new CasingButton(
                            buttonX,
                            buttonY,
                            buttonWidth,
                            buttonHeight,
                            casing
                    )
            );
        }
    }

    private class CasingButton extends ButtonWidget {

        private final SonicCasing casing;

        public CasingButton(
                int x,
                int y,
                int width,
                int height,
                SonicCasing casing
        ) {

            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(casing.getDisplayName()),

                    button ->
                            onCasingSelected(casing),

                    DEFAULT_NARRATION_SUPPLIER
            );

            this.casing = casing;
        }

        @Override
        public void renderButton(
                DrawContext context,
                int mouseX,
                int mouseY,
                float delta
        ) {

            boolean hovered =
                    mouseX >= getX()
                            && mouseX < getX() + getWidth()
                            && mouseY >= getY()
                            && mouseY < getY() + getHeight();

            boolean selected =
                    selectedCasing == casing;

            int fill;
            int border;
            int textColor;

            if (selected) {

                fill = CYAN_DARK;
                border = CYAN;
                textColor = TEXT;

            } else if (hovered) {

                fill = PANEL_LIGHT;
                border = CYAN;
                textColor = TEXT;

            } else {

                fill = PANEL_DARK;
                border = CYAN_DIM;
                textColor = CYAN;
            }

            context.fill(
                    getX(),
                    getY(),
                    getX() + getWidth(),
                    getY() + getHeight(),
                    fill
            );

            context.drawBorder(
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight(),
                    border
            );

            context.drawCenteredTextWithShadow(
                    textRenderer,
                    getMessage(),
                    getX() + getWidth() / 2,
                    getY() + 10,
                    textColor
            );
        }
    }

    private void onCasingSelected(
            SonicCasing casing
    ) {

        if (client == null ||
                client.player == null) {

            return;
        }

        selectedCasing = casing;

        SonicCasingClientNetworking.sendCasingChange(
                hand,
                casing
        );

        client.player.sendMessage(
                Text.literal(
                        "§bCASING: §f"
                                + casing.getDisplayName()
                ),
                true
        );
    }

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

    private void drawBackground(
            DrawContext context
    ) {

        context.fill(
                0,
                0,
                width,
                height,
                0x99000000
        );

        context.fill(
                left - 3,
                top - 3,
                left + GUI_WIDTH + 3,
                top + GUI_HEIGHT + 3,
                0x4016D9FF
        );

        context.fill(
                left,
                top,
                left + GUI_WIDTH,
                top + GUI_HEIGHT,
                PANEL
        );

        context.drawBorder(
                left,
                top,
                GUI_WIDTH,
                GUI_HEIGHT,
                CYAN
        );

        context.drawBorder(
                left + 4,
                top + 4,
                GUI_WIDTH - 8,
                GUI_HEIGHT - 8,
                CYAN_DARK
        );

        context.fill(
                left + 10,
                top + 10,
                left + GUI_WIDTH - 10,
                top + 47,
                PANEL_LIGHT
        );

        context.fill(
                left + 10,
                top + 46,
                left + GUI_WIDTH - 10,
                top + 47,
                CYAN_DIM
        );

        drawSectionBox(
                context,
                left + 18,
                top + 57,
                GUI_WIDTH - 36,
                43
        );

        drawSectionBox(
                context,
                left + 18,
                top + 102,
                GUI_WIDTH - 36,
                151
        );

        context.fill(
                left + 18,
                top + 255,
                left + GUI_WIDTH - 18,
                top + 256,
                CYAN_DARK
        );
    }

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

        context.fill(
                x,
                y,
                x + width,
                y + 1,
                CYAN_DARK
        );

        context.fill(
                x,
                y + height - 1,
                x + width,
                y + height,
                0xFF102A33
        );
    }

    private void drawLabels(
            DrawContext context
    ) {

        context.drawText(
                textRenderer,
                "SONIC WORKSHOP",
                left + 20,
                top + 17,
                CYAN,
                false
        );

        context.drawText(
                textRenderer,
                "SONIC CASING CONFIGURATION SYSTEM",
                left + 20,
                top + 31,
                TEXT_DIM,
                false
        );

        context.drawText(
                textRenderer,
                "SONIC SCREWDRIVER",
                left + 27,
                top + 63,
                CYAN,
                false
        );

        context.drawText(
                textRenderer,
                "INSERT SONIC",
                left + 27,
                top + 78,
                TEXT_DIM,
                false
        );

        context.drawText(
                textRenderer,
                "SELECT CASING",
                left + 27,
                top + 108,
                CYAN,
                false
        );

        context.drawText(
                textRenderer,
                "CURRENT:",
                left + 27,
                top + 239,
                TEXT_DIM,
                false
        );

        context.drawText(
                textRenderer,
                selectedCasing.getDisplayName(),
                left + 85,
                top + 239,
                GREEN,
                false
        );

        context.drawText(
                textRenderer,
                "WORKSHOP ONLINE",
                left + 27,
                top + 260,
                GREEN,
                false
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}