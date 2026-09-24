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

import static org.spongepowered.asm.mixin.MixinEnvironment.Feature.isActive;

public class VortexManipulatorScreen extends Screen {
    private static final int W = 540, H = 360;
    private static final int PANEL = 0xF0091118, PANEL_LIGHT = 0xFF101D25, PANEL_DARK = 0xFF080D12;
    private static final int CYAN = 0xFF26E6FF, CYAN_DIM = 0xFF08758C, CYAN_DARK = 0xFF063D4A;
    private static final int TEXT = 0xFFE7FBFF, DIM = 0xFF75AAB5, GREEN = 0xFF38FF88, RED = 0xFFFF4F6B;

    private int left, top;
    private int tab = 0;
    private TextFieldWidget dimension, x, y, z, targetPlayer, locationName, accessPlayer;
    private boolean surfaceMode;
    private boolean owner;
    private int selfDestructTicks;
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
        addDrawableChild(btn(left + 18, top + 42, 155, 22, "NAVIGATION", b -> switchTab(0)));
        addDrawableChild(btn(left + 185, top + 42, 155, 22, "SAVED LOCATIONS", b -> switchTab(1)));
        addDrawableChild(btn(left + 352, top + 42, 170, 22, "ISOMORPHIC CONTROLS", b -> switchTab(2)));
    }

    private void buildNavigation() {
        dimension = field(left + 24, top + 92, 492, "Dimension"); dimension.setText("overworld");
        targetPlayer = field(left + 24, top + 137, 492, "Player target (optional)");
        x = field(left + 24, top + 184, 150, "X"); y = field(left + 195, top + 184, 150, "Y"); z = field(left + 366, top + 184, 150, "Z");
        addDrawableChild(btn(left + 24, top + 224, 150, 24, "SURFACE: OFF", b -> { surfaceMode = !surfaceMode; b.setMessage(Text.literal(surfaceMode ? "SURFACE: ON" : "SURFACE: OFF")); }));
        addDrawableChild(btn(left + 195, top + 224, 150, 24, "ENGAGE VORTEX", b -> teleport()));
        String sd = selfDestructTicks > 0 ? "CANCEL SELF-DESTRUCT" : "SELF-DESTRUCT";
        addDrawableChild(btn(left + 366, top + 224, 150, 24, sd, b -> selfDestruct()));
        if (selfDestructTicks > 0) addDrawableChild(btn(left + 24, top + 270, 492, 28, "SELF-DESTRUCT ARMED — " + ((selfDestructTicks + 19) / 20) + "s", b -> cancelSelfDestruct()));
    }

    private void buildLocations() {
        locationName = field(left + 24, top + 92, 300, "Location name");
        addDrawableChild(btn(left + 334, top + 92, 85, 24, "SAVE HERE", b -> saveLocation()));
        addDrawableChild(btn(left + 429, top + 92, 87, 24, "DELETE", b -> deleteLocation()));
        int start = locationPage * 6;
        for (int i = 0; i < 6; i++) {
            int idx = start + i; if (idx >= LOCATIONS.size()) break;
            Location loc = LOCATIONS.get(idx);
            int yy = top + 132 + i * 32;
            ButtonWidget go = btn(left + 24, yy, 390, 25, loc.name + "  §7" + shortDimension(loc.dimension), b -> goLocation(loc.name));
            addDrawableChild(go); dynamicButtons.add(go);
            ButtonWidget del = btn(left + 424, yy, 92, 25, "DELETE", b -> { locationName.setText(loc.name); deleteLocation(); });
            addDrawableChild(del); dynamicButtons.add(del);
        }
        addDrawableChild(btn(left + 24, top + 328, 110, 22, "◀ PAGE", b -> { if (locationPage > 0) { locationPage--; clearAndBuild(); } }));
        addDrawableChild(btn(left + 406, top + 328, 110, 22, "PAGE ▶", b -> { if ((locationPage + 1) * 6 < LOCATIONS.size()) { locationPage++; clearAndBuild(); } }));
    }

    private void buildAccess() {
        accessPlayer = field(left + 24, top + 92, 350, "Online player name");
        ButtonWidget add = btn(left + 386, top + 92, 130, 24, "ADD PLAYER", b -> addPlayer()); add.active = owner; addDrawableChild(add);
        for (int i = 0; i < USERS.size(); i++) {
            AccessUser user = USERS.get(i); int yy = top + 132 + i * 30;
            addDrawableChild(btn(left + 24, yy, 390, 24, user.name, b -> accessPlayer.setText(user.name)));
            ButtonWidget rem = btn(left + 424, yy, 92, 24, "REMOVE", b -> removePlayer(user.name)); rem.active = owner; addDrawableChild(rem);
        }
        String ownerText = owner ? "OWNER: YOU" : "OWNER: ISOMORPHIC LOCKED";
        addDrawableChild(btn(left + 24, top + 328, 492, 22, ownerText, b -> {}));
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
            int sd = buf.readVarInt();
            LOCATIONS.clear(); USERS.clear();
            int lc = buf.readVarInt();
            for (int i = 0; i < lc; i++) LOCATIONS.add(new Location(buf.readString(32), buf.readString(128), buf.readDouble(), buf.readDouble(), buf.readDouble()));
            int uc = buf.readVarInt();
            for (int i = 0; i < uc; i++) USERS.add(new AccessUser(buf.readUuid(), buf.readString(64)));
            if (net.minecraft.client.MinecraftClient.getInstance().currentScreen instanceof VortexManipulatorScreen screen) {
                screen.owner = newOwner; screen.selfDestructTicks = sd; screen.clearAndBuild();
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
    private void selfDestruct() { PacketByteBuf p = PacketByteBufs.create(); p.writeString("SELF_DESTRUCT"); ClientPlayNetworking.send(ModPackets.VM_PACKET, p); }
    private void cancelSelfDestruct() { PacketByteBuf p = PacketByteBufs.create(); p.writeString("CANCEL_SELF_DESTRUCT"); ClientPlayNetworking.send(ModPackets.VM_PACKET, p); }
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
        c.fill(left + 10, top + 10, left + W - 10, top + 34, PANEL_LIGHT); c.fill(left + 10, top + 33, left + W - 10, top + 34, CYAN_DIM);
        c.drawText(textRenderer, "VORTEX MANIPULATOR", left + 20, top + 17, CYAN, false); c.drawText(textRenderer, owner ? "ISOMORPHIC CONTROLS ONLINE" : "ISOMORPHIC LOCK", left + 345, top + 17, owner ? GREEN : RED, false);
        c.fill(left + 20, top + 73, left + W - 20, top + 74, CYAN_DARK);
    }
    private void drawLabels(DrawContext c) {
        String title = tab == 0 ? "TEMPORAL NAVIGATION" : tab == 1 ? "VM MEMORY — SAVED LOCATIONS" : "ISOMORPHIC AUTHORIZATION";
        c.drawText(textRenderer, title, left + 24, top + 62, CYAN, false);
        if (tab == 0) {
            c.drawText(textRenderer, "Dimension", left + 24, top + 82, DIM, false); c.drawText(textRenderer, "Target player overrides coordinates when filled", left + 24, top + 127, DIM, false);
            c.drawText(textRenderer, selfDestructTicks > 0 ? "DESTRUCT SEQUENCE ACTIVE" : "VORTEX READY", left + 24, top + 316, selfDestructTicks > 0 ? RED : GREEN, false);
        } else if (tab == 1) c.drawText(textRenderer, LOCATIONS.isEmpty() ? "No locations stored." : "Select a location to travel; use DELETE to erase it.", left + 24, top + 116, DIM, false);
        else c.drawText(textRenderer, "Only the owner can change this VM's authorized users.", left + 24, top + 116, DIM, false);
    }

    private class ThemedButtonWidget extends ButtonWidget {
        ThemedButtonWidget(int x, int y, int w, int h, Text message, PressAction action) { super(x, y, w, h, message, action, DEFAULT_NARRATION_SUPPLIER); }
        @Override public void renderButton(DrawContext c, int mx, int my, float delta) { int bg = isHovered() ? 0xFF123744 : PANEL_DARK; c.fill(getX(), getY(), getX()+getWidth(), getY()+getHeight(), bg); c.drawBorder(getX(), getY(), getWidth(), getHeight(), isActive() ? CYAN_DIM : 0xFF30434A); c.drawCenteredTextWithShadow(textRenderer, getMessage(), getX()+getWidth()/2, getY()+getHeight()/2-4, isActive() ? TEXT : DIM); }
    }

    private record Location(String name, String dimension, double x, double y, double z) {}
    private record AccessUser(UUID uuid, String name) {}
}
