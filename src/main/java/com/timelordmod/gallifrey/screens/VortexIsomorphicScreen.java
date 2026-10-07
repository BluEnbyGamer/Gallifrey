package com.timelordmod.gallifrey.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget;

public class VortexIsomorphicScreen extends VortexManipulatorSubScreen {
    @Override protected int accent() { return 0xFFB58CFF; }
    @Override protected int accentDim() { return 0xFF6848A8; }
    @Override protected int accentDark() { return 0xFF241443; }
    @Override protected int panel() { return 0xF0090710; }
    @Override protected int panelLight() { return 0xFF171021; }
    @Override protected int panelDark() { return 0xFF040306; }
    @Override protected int dimColor() { return 0xFF9D90B5; }
    private TextFieldWidget accessPlayer;

    public VortexIsomorphicScreen() { super("Vortex Manipulator - Isomorphic Controls"); }

    @Override protected void build() {
        accessPlayer = field(left + 24, top + 92, 350, "Online player name");
        ButtonWidget add = btn(left + 386, top + 92, 130, 24, "ADD PLAYER", b -> addPlayer());
        add.active = owner;
        addDrawableChild(add);

        for (int i = 0; i < USERS.size() && i < 6; i++) {
            AccessUser user = USERS.get(i);
            int yy = top + 132 + i * 30;
            addDrawableChild(btn(left + 24, yy, 390, 24, user.name(), b -> accessPlayer.setText(user.name())));
            ButtonWidget rem = btn(left + 424, yy, 92, 24, "REMOVE", b -> removePlayer(user.name()));
            rem.active = owner;
            addDrawableChild(rem);
        }
        addDrawableChild(btn(left + 24, top + 328, 492, 22,
                owner ? "OWNER: YOU" : "OWNER: ISOMORPHIC LOCKED", b -> {}));
        buildBackButton();
    }

    private void addPlayer() {
        String name = accessPlayer.getText().trim();
        if (!name.isEmpty()) sendString("ADD_PLAYER", name);
    }

    private void removePlayer(String name) {
        sendString("REMOVE_PLAYER", name);
    }

    @Override public void render(DrawContext c, int mx, int my, float delta) {
        buildHeader(c, "ISOMORPHIC CONTROLS");
        c.drawText(textRenderer, "Only the VM owner can change authorized users.", left + 24, top + 116, dimColor(), false);
        super.render(c, mx, my, delta);
    }
}
