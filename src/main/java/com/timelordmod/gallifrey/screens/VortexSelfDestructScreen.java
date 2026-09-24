package com.timelordmod.gallifrey.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;

public class VortexSelfDestructScreen extends VortexManipulatorSubScreen {
    public VortexSelfDestructScreen() { super("Vortex Manipulator - Self Destruct"); }

    @Override protected void build() {
        if (selfDestructTicks > 0) {
            int seconds = (selfDestructTicks + 19) / 20;
            addDrawableChild(btn(left + 24, top + 115, 492, 42,
                    "SELF-DESTRUCT ARMED — " + seconds + "s",
                    b -> selfDestructCommand("CANCEL_SELF_DESTRUCT")));
            addDrawableChild(btn(left + 24, top + 175, 492, 32, "CANCEL SELF-DESTRUCT",
                    b -> selfDestructCommand("CANCEL_SELF_DESTRUCT")));
        } else {
            ButtonWidget arm = btn(left + 24, top + 125, 492, 42, "ARM SELF-DESTRUCT (10 SECONDS)",
                    b -> selfDestructCommand("SELF_DESTRUCT"));
            arm.active = owner;
            addDrawableChild(arm);
        }
        addDrawableChild(btn(left + 24, top + 240, 492, 30, "BACK TO MAIN",
                b -> client.setScreen(new VortexManipulatorScreen())));
        buildBackButton();
    }

    @Override public void render(DrawContext c, int mx, int my, float delta) {
        buildHeader(c, "SELF-DESTRUCT");
        c.drawText(textRenderer, "Owner authorization is required. The VM will be destroyed after 10 seconds.",
                left + 24, top + 82, RED, false);
        super.render(c, mx, my, delta);
    }
}
