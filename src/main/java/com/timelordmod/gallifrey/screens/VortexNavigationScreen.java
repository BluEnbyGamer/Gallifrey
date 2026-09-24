package com.timelordmod.gallifrey.screens;

import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VortexNavigationScreen extends VortexManipulatorSubScreen {
    private TextFieldWidget dimension, x, y, z, targetPlayer;
    private boolean surfaceMode;

    public VortexNavigationScreen() { super("Vortex Manipulator - Navigation"); }

    @Override protected void build() {
        dimension = field(left + 24, top + 92, 492, "Dimension");
        dimension.setText("overworld");
        targetPlayer = field(left + 24, top + 137, 492, "Player target (optional)");
        x = field(left + 24, top + 184, 150, "X");
        y = field(left + 195, top + 184, 150, "Y");
        z = field(left + 366, top + 184, 150, "Z");

        addDrawableChild(btn(left + 24, top + 224, 150, 24, "SURFACE: OFF",
                b -> { surfaceMode = !surfaceMode; b.setMessage(Text.literal(surfaceMode ? "SURFACE: ON" : "SURFACE: OFF")); }));
        addDrawableChild(btn(left + 195, top + 224, 150, 24, "ENGAGE VORTEX", b -> teleport()));
        addDrawableChild(btn(left + 366, top + 224, 150, 24, "SELF-DESTRUCT",
                b -> client.setScreen(new VortexSelfDestructScreen())));
        buildBackButton();
    }

    private void teleport() {
        String playerName = targetPlayer.getText().trim();
        PacketByteBuf p = PacketByteBufs.create();
        p.writeString("TELEPORT");
        p.writeBoolean(!playerName.isEmpty());
        if (!playerName.isEmpty()) {
            p.writeString(playerName, 64);
        } else {
            Identifier id = parseDimension(dimension.getText());
            Double tx = parse(x.getText()), ty = parse(y.getText()), tz = parse(z.getText());
            if (id == null || tx == null || ty == null || tz == null) {
                client.player.sendMessage(Text.literal("INVALID DESTINATION"), true);
                return;
            }
            p.writeIdentifier(id);
            p.writeDouble(tx); p.writeDouble(ty); p.writeDouble(tz);
            p.writeBoolean(surfaceMode);
        }
        ClientPlayNetworking.send(ModPackets.VM_PACKET, p);
        client.setScreen(null);
    }

    private Double parse(String s) {
        try { return s == null || s.trim().isEmpty() ? null : Double.parseDouble(s.trim()); }
        catch (Exception e) { return null; }
    }

    private Identifier parseDimension(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        String n = input.trim().toLowerCase();
        return switch (n) {
            case "overworld" -> new Identifier("minecraft", "overworld");
            case "nether" -> new Identifier("minecraft", "the_nether");
            case "end" -> new Identifier("minecraft", "the_end");
            case "gallifrey" -> new Identifier("gallifrey", "gallifrey");
            case "skaro" -> new Identifier("gallifrey", "skaro");
            case "mars" -> new Identifier("gallifrey", "mars");
            case "mondas" -> new Identifier("gallifrey", "mondas");
            default -> Identifier.tryParse(n);
        };
    }

    @Override public void render(DrawContext c, int mx, int my, float delta) {
        buildHeader(c, "TEMPORAL NAVIGATION");
        c.drawText(textRenderer, "Dimension", left + 24, top + 82, DIM, false);
        c.drawText(textRenderer, "Player target overrides coordinates when filled.", left + 24, top + 127, DIM, false);
        super.render(c, mx, my, delta);
    }
}
