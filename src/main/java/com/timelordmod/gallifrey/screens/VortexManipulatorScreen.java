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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VortexManipulatorScreen extends Screen {
    private static final int W = 500, H = 250;
    private static final int PANEL = 0xF0091118, PANEL_LIGHT = 0xFF101D25, PANEL_DARK = 0xFF080D12;
    private static final int CYAN = 0xFF26E6FF, CYAN_DIM = 0xFF08758C, CYAN_DARK = 0xFF063D4A;
    private static final int TEXT = 0xFFE7FBFF, DIM = 0xFF75AAB5, GREEN = 0xFF38FF88, RED = 0xFFFF4F6B;

    private int left, top;
    private int tab = 0;
    private TextFieldWidget dimension, x, y, z, targetPlayer, locationName, accessPlayer;
    private boolean surfaceMode;
    private boolean owner;
    private int locationPage;
    private static final List<Location> LOCATIONS = new ArrayList<>();
    private static final List<AccessUser> USERS = new ArrayList<>();
    private final List<ButtonWidget> dynamicButtons = new ArrayList<>();

    public VortexManipulatorScreen() { super(Text.literal("Vortex Manipulator")); }

    @Override protected void init() {
        left = (width - W) / 2; top = (height - H) / 2;
        clearAndBuild();
        requestState();
    }

    private void clearAndBuild() {
        clearChildren(); dynamicButtons.clear();
        if (tab == 0) buildNavigation(); else if (tab == 1) buildLocations(); else buildAccess();
        buildTabs();
    }

    private void buildTabs() {
        addDrawableChild(btn(left + 10, top + 32, 150, 22, "NAVIGATION", b -> switchTab(0)));
        addDrawableChild(btn(left + 168, top + 32, 150, 22, "SAVED LOCATIONS", b -> switchTab(1)));
        addDrawableChild(btn(left + 326, top + 32, 164, 22, "ISOMORPHIC CONTROLS", b -> switchTab(2)));
    }

    private void buildNavigation() {
        dimension = field(left + 16, top + 68, 468, "Dimension"); dimension.setText("overworld");
        targetPlayer = field(left + 16, top + 104, 468, "Player target (optional)");
        x = field(left + 16, top + 140, 146, "X"); y = field(left + 177, top + 140, 146, "Y"); z = field(left + 338, top + 140, 146, "Z");
        addDrawableChild(btn(left + 16, top + 176, 146, 24, "SURFACE: OFF", b -> { surfaceMode = !surfaceMode; b.setMessage(Text.literal(surfaceMode ? "SURFACE: ON" : "SURFACE: OFF")); }));
        addDrawableChild(btn(left + 177, top + 176, 146, 24, "ENGAGE VORTEX", b -> teleport()));
        addDrawableChild(btn(left + 338, top + 176, 146, 24, "SELF-DESTRUCT", b -> openSelfDestructConfirmation()));
    }

    private void buildLocations() {
        locationName = field(left + 16, top + 68, 280, "Location name");
        addDrawableChild(btn(left + 306, top + 68, 88, 24, "SAVE HERE", b -> saveLocation()));
        addDrawableChild(btn(left + 402, top + 68, 82, 24, "DELETE", b -> deleteLocation()));
        int start = locationPage * 4;
        for (int i = 0; i < 4; i++) {
            int idx = start + i; if (idx >= LOCATIONS.size()) break;
            Location loc = LOCATIONS.get(idx);
            int yy = top + 104 + i * 27;
            ButtonWidget go = btn(left + 16, yy, 368, 24, loc.name() + "  §7" + shortDimension(loc.dimension()), b -> goLocation(loc.name()));
            addDrawableChild(go); dynamicButtons.add(go);
            ButtonWidget del = btn(left + 392, yy, 92, 24, "DELETE", b -> { locationName.setText(loc.name()); deleteLocation(); });
            addDrawableChild(del); dynamicButtons.add(del);
        }
        addDrawableChild(btn(left + 16, top + 220, 100, 22, "◀ PAGE", b -> { if (locationPage > 0) { locationPage--; clearAndBuild(); } }));
        addDrawableChild(btn(left + 384, top + 220, 100, 22, "PAGE ▶", b -> { if ((locationPage + 1) * 4 < LOCATIONS.size()) { locationPage++; clearAndBuild(); } }));
    }

    private void buildAccess() {
        accessPlayer = field(left + 16, top + 68, 340, "Online player name");
        ButtonWidget add = btn(left + 366, top + 68, 118, 24, "ADD PLAYER", b -> addPlayer()); add.active = owner; addDrawableChild(add);
        for (int i = 0; i < Math.min(4, USERS.size()); i++) {
            AccessUser user = USERS.get(i); int yy = top + 104 + i * 27;
            addDrawableChild(btn(left + 16, yy, 368, 24, user.name(), b -> accessPlayer.setText(user.name())));
            ButtonWidget rem = btn(left + 392, yy, 92, 24, "REMOVE", b -> removePlayer(user.name())); rem.active = owner; addDrawableChild(rem);
        }
        String ownerText = owner ? "OWNER: YOU" : "OWNER: ISOMORPHIC LOCKED";
        addDrawableChild(btn(left + 16, top + 220, 468, 22, ownerText, b -> {}));
    }

    private TextFieldWidget field(int x, int y, int w, String placeholder) {
        TextFieldWidget f = new TextFieldWidget(textRenderer, x, y, w, 24, Text.literal(placeholder));
        f.setMaxLength(128); f.setPlaceholder(Text.literal(placeholder)); f.setEditableColor(TEXT); f.setUneditableColor(DIM); addDrawableChild(f); return f;
    }

    private ButtonWidget btn(int x, int y, int w, int h, String text, ButtonWidget.PressAction action) {
        return new ThemedButtonWidget(x, y, w, h, Text.literal(text), action);
    }

    private void switchTab(int newTab) { tab = newTab; clearAndBuild(); }

    private void requestState() {
        PacketByteBuf p = PacketByteBufs.create(); p.writeString("REQUEST_STATE"); ClientPlayNetworking.send(ModPackets.VM_PACKET, p);
    }

    public static void applyServerState(PacketByteBuf buf) {
        // The active client screen may be absent during disconnect; simply consume state safely.
        MinecraftState.apply(buf);
    }

    private static final class MinecraftState {
        static void apply(PacketByteBuf buf) {
            boolean newOwner = buf.readBoolean();
            buf.readVarInt(); // legacy self-destruct state field; always zero now
            LOCATIONS.clear(); USERS.clear();
            int lc = buf.readVarInt();
            for (int i = 0; i < lc; i++) LOCATIONS.add(new Location(buf.readString(32), buf.readString(128), buf.readDouble(), buf.readDouble(), buf.readDouble()));
            int uc = buf.readVarInt();
            for (int i = 0; i < uc; i++) USERS.add(new AccessUser(buf.readUuid(), buf.readString(64)));
            if (net.minecraft.client.MinecraftClient.getInstance().currentScreen instanceof VortexManipulatorScreen screen) {
                screen.owner = newOwner; screen.clearAndBuild();
            }
        }
    }

    private void teleport() {
        if (client == null || client.player == null) return;
        String playerName = targetPlayer.getText().trim(); boolean playerMode = !playerName.isEmpty();
        PacketByteBuf p = PacketByteBufs.create(); p.writeString("TELEPORT"); p.writeBoolean(playerMode);
        if (playerMode) p.writeString(playerName, 64); else {
            Identifier id = parseDimension(dimension.getText()); Double tx = parse(x.getText()), ty = parse(y.getText()), tz = parse(z.getText());
            if (id == null || tx == null || ty == null || tz == null) { client.player.sendMessage(Text.literal("INVALID DESTINATION"), true); return; }
            p.writeIdentifier(id); p.writeDouble(tx); p.writeDouble(ty); p.writeDouble(tz); p.writeBoolean(surfaceMode);
        }
        ClientPlayNetworking.send(ModPackets.VM_PACKET, p); client.setScreen(null);
    }

    private void saveLocation() { String name = locationName.getText().trim(); if (name.isEmpty()) return; sendString("SAVE", name); }
    private void deleteLocation() { String name = locationName.getText().trim(); if (name.isEmpty()) return; sendString("DELETE", name); }
    private void goLocation(String name) { sendString("GO", name); client.setScreen(null); }
    private void addPlayer() { String name = accessPlayer.getText().trim(); if (!name.isEmpty()) sendString("ADD_PLAYER", name); }
    private void removePlayer(String name) { sendString("REMOVE_PLAYER", name); }
    private void openSelfDestructConfirmation() {
        client.setScreen(new VortexSelfDestructConfirmScreen(this));
    }
    private void sendString(String action, String value) { PacketByteBuf p = PacketByteBufs.create(); p.writeString(action); p.writeString(value, 128); ClientPlayNetworking.send(ModPackets.VM_PACKET, p); }

    private Double parse(String s) { try { return s == null || s.trim().isEmpty() ? null : Double.parseDouble(s.trim()); } catch (Exception e) { return null; } }
    private Identifier parseDimension(String input) {
        if (input == null || input.trim().isEmpty()) return null; String n = input.trim().toLowerCase();
        return switch (n) { case "overworld" -> new Identifier("minecraft", "overworld"); case "nether" -> new Identifier("minecraft", "the_nether"); case "end" -> new Identifier("minecraft", "the_end"); case "gallifrey" -> new Identifier("gallifrey", "gallifrey"); case "skaro" -> new Identifier("gallifrey", "skaro"); case "mars" -> new Identifier("gallifrey", "mars"); case "mondas" -> new Identifier("gallifrey", "mondas"); default -> Identifier.tryParse(n); };
    }
    private String shortDimension(String d) { int i = d.indexOf(':'); return i >= 0 ? d.substring(i + 1) : d; }

    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawPanel(context); super.render(context, mouseX, mouseY, delta); drawLabels(context);
    }
    private void drawPanel(DrawContext c) {
        c.fill(0, 0, width, height, 0x99000000); c.fill(left - 3, top - 3, left + W + 3, top + H + 3, 0x4016D9FF); c.fill(left, top, left + W, top + H, PANEL); c.drawBorder(left, top, W, H, CYAN); c.drawBorder(left + 4, top + 4, W - 8, H - 8, CYAN_DARK);
        c.fill(left + 8, top + 8, left + W - 8, top + 28, PANEL_LIGHT); c.fill(left + 8, top + 27, left + W - 8, top + 28, CYAN_DIM);
        c.drawText(textRenderer, "VORTEX MANIPULATOR", left + 16, top + 12, CYAN, false); c.drawText(textRenderer, owner ? "ONLINE" : "LOCKED", left + W - 62, top + 12, owner ? GREEN : RED, false);
        c.fill(left + 16, top + 61, left + W - 16, top + 62, CYAN_DARK);
    }
    private void drawLabels(DrawContext c) {
        String title = tab == 0 ? "TEMPORAL NAVIGATION" : tab == 1 ? "VM MEMORY — SAVED LOCATIONS" : "ISOMORPHIC AUTHORIZATION";
        c.drawText(textRenderer, title, left + 16, top + 44, CYAN, false);
        if (tab == 0) {
            c.drawText(textRenderer, "Dimension", left + 16, top + 58, DIM, false); c.drawText(textRenderer, "Target player overrides coordinates when filled", left + 16, top + 94, DIM, false);
            c.drawText(textRenderer, "VORTEX READY", left + 16, top + 206, GREEN, false);
        } else if (tab == 1) c.drawText(textRenderer, LOCATIONS.isEmpty() ? "No locations stored." : "Select a location to travel; use DELETE to erase it.", left + 16, top + 94, DIM, false);
        else c.drawText(textRenderer, "Only the owner can change this VM's authorized users.", left + 16, top + 94, DIM, false);
    }

    private class ThemedButtonWidget extends ButtonWidget {
        ThemedButtonWidget(int x, int y, int w, int h, Text message, PressAction action) { super(x, y, w, h, message, action, DEFAULT_NARRATION_SUPPLIER); }
        @Override public void renderButton(DrawContext c, int mx, int my, float delta) { int bg = isHovered() ? 0xFF123744 : PANEL_DARK; c.fill(getX(), getY(), getX()+getWidth(), getY()+getHeight(), bg); c.drawBorder(getX(), getY(), getWidth(), getHeight(), active ? CYAN_DIM : 0xFF30434A); c.drawCenteredTextWithShadow(textRenderer, getMessage(), getX()+getWidth()/2, getY()+getHeight()/2-4, active ? TEXT : DIM); }
    }

    private record Location(String name, String dimension, double x, double y, double z) {}
    private record AccessUser(UUID uuid, String name) {}
}
