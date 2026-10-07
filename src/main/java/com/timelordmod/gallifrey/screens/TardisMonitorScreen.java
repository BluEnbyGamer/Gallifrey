package com.timelordmod.gallifrey.screens;

import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/** Full TARDIS console: navigation, artron reserves, interior and exterior controls. */
public class TardisMonitorScreen extends Screen {
    private static final int W = 560;
    private static final int H = 360;
    private static final int PANEL = 0xF0081117;
    private static final int PANEL_LIGHT = 0xFF102832;
    private static final int PANEL_DARK = 0xFF071015;
    private static final int BLUE = 0xFF53D6F2;
    private static final int BLUE_DIM = 0xFF1D7188;
    private static final int GOLD = 0xFFE2B85C;
    private static final int TEXT = 0xFFEAF6F7;
    private static final int DIM = 0xFF86A9B2;
    private static final int GREEN = 0xFF68E09A;
    private static final int RED = 0xFFFF6675;

    private int left, top;
    private int fuel, maxFuel;
    private int exteriorVariant;
    private String exteriorVariantName = "Default";
    private String interior = "tardis_platform";
    private List<String> interiors = new ArrayList<>();
    private boolean flightPending;
    private boolean locked;
    private int interiorIndex;

    private TextFieldWidget dimension, x, y, z;
    private ButtonWidget flightButton, refuelButton, interiorApplyButton, variantButton;

    public TardisMonitorScreen() {
        super(Text.literal("TARDIS Console"));
    }

    @Override
    protected void init() {
        left = (width - W) / 2;
        top = (height - H) / 2;
        clearChildren();

        dimension = field(left + 22, top + 112, 240, "minecraft:overworld");
        addDrawableChild(button(left + 270, top + 112, 124, 24, "DIM LIST", b -> client.setScreen(new TardisDimensionsScreen(this))));
        x = field(left + 22, top + 160, 72, "X");
        y = field(left + 102, top + 160, 72, "Y");
        z = field(left + 182, top + 160, 72, "Z");

        flightButton = button(left + 264, top + 160, 130, 24, "FLIGHT", b -> sendFlight());
        refuelButton = button(left + 402, top + 160, 136, 24, "REFUEL", b -> sendAction("REFUEL"));

        addDrawableChild(button(left + 22, top + 218, 72, 24, "◀", b -> cycleInterior(-1)));
        interiorApplyButton = button(left + 102, top + 218, 300, 24, "APPLY INTERIOR", b -> applyInterior());
        addDrawableChild(button(left + 410, top + 218, 72, 24, "▶", b -> cycleInterior(1)));

        variantButton = button(left + 22, top + 260, 216, 24, "EXTERIOR", b -> cycleVariant());
        addDrawableChild(variantButton);
        addDrawableChild(button(left + 246, top + 260, 292, 24, "CLOSE", b -> close()));
        addDrawableChild(button(left + 22, top + 292, 516, 24, "MUSIC: PLAY DR WHO VALE", b -> sendAction("PLAY_DRWHO_VALE")));

        syncInteriorIndex();
        requestState();
    }

    private TextFieldWidget field(int x, int y, int w, String placeholder) {
        TextFieldWidget f = new TextFieldWidget(textRenderer, x, y, w, 24, Text.literal(placeholder));
        f.setMaxLength(128);
        f.setPlaceholder(Text.literal(placeholder));
        f.setEditableColor(TEXT);
        f.setUneditableColor(DIM);
        addDrawableChild(f);
        return f;
    }

    private ButtonWidget button(int x, int y, int w, int h, String label, ButtonWidget.PressAction action) {
        return ButtonWidget.builder(Text.literal(label), action).dimensions(x, y, w, h).build();
    }

    public void setDimensionValue(String id) {
        if (dimension != null) {
            dimension.setText(id);
            dimension.setSelectionStart(id.length());
            dimension.setSelectionEnd(id.length());
        }
    }

    private void requestState() {
        sendAction("REQUEST_STATE");
    }

    private void sendFlight() {
        try {
            String dim = dimension.getText().trim();
            double tx = Double.parseDouble(x.getText().trim());
            double ty = Double.parseDouble(y.getText().trim());
            double tz = Double.parseDouble(z.getText().trim());
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString("FLIGHT");
            buf.writeString(dim, 128);
            buf.writeDouble(tx);
            buf.writeDouble(ty);
            buf.writeDouble(tz);
            ClientPlayNetworking.send(ModPackets.TARDIS_MONITOR_ACTION, buf);
        } catch (NumberFormatException ignored) {
            // The status line below gives the player feedback without closing the monitor.
        }
    }

    private void sendAction(String action) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(action);
        ClientPlayNetworking.send(ModPackets.TARDIS_MONITOR_ACTION, buf);
    }

    private void applyInterior() {
        if (interiors.isEmpty()) return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("INTERIOR");
        buf.writeString(interiors.get(interiorIndex), 64);
        ClientPlayNetworking.send(ModPackets.TARDIS_MONITOR_ACTION, buf);
    }

    private void cycleInterior(int delta) {
        if (interiors.isEmpty()) return;
        interiorIndex = (interiorIndex + delta) % interiors.size();
        if (interiorIndex < 0) interiorIndex += interiors.size();
        interiorApplyButton.setMessage(Text.literal("INTERIOR: " + interiors.get(interiorIndex)));
    }

    private void syncInteriorIndex() {
        interiorIndex = Math.max(0, interiors.indexOf(interior));
        if (interiorApplyButton != null && !interiors.isEmpty()) {
            interiorApplyButton.setMessage(Text.literal("INTERIOR: " + interiors.get(interiorIndex)));
        }
    }

    private void cycleVariant() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("EXTERIOR_VARIANT");
        buf.writeVarInt((exteriorVariant + 1) % 4);
        ClientPlayNetworking.send(ModPackets.TARDIS_MONITOR_ACTION, buf);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        left = (width - W) / 2;
        top = (height - H) / 2;

        context.fill(left, top, left + W, top + H, PANEL);
        context.drawBorder(left, top, W, H, BLUE_DIM);
        context.fill(left + 12, top + 12, left + W - 12, top + 44, PANEL_DARK);
        context.drawText(textRenderer, Text.literal("TARDIS CONTROL CONSOLE"), left + 24, top + 23, BLUE, false);
        context.drawText(textRenderer, Text.literal(locked ? "ISOMORPHIC SECURITY: LOCKED" : "ISOMORPHIC SECURITY: OPEN"), left + 330, top + 23, locked ? GOLD : GREEN, false);

        context.drawText(textRenderer, Text.literal("ARTRON RESERVES"), left + 22, top + 60, DIM, false);
        context.drawText(textRenderer, Text.literal(fuel + " / " + maxFuel), left + 22, top + 76, TEXT, false);
        int barWidth = 240;
        int filled = maxFuel <= 0 ? 0 : (int)(barWidth * (fuel / (double) maxFuel));
        context.fill(left + 100, top + 76, left + 100 + barWidth, top + 88, PANEL_DARK);
        context.fill(left + 100, top + 76, left + 100 + filled, top + 88, GREEN);

        context.drawText(textRenderer, Text.literal("EXTERIOR VARIANT"), left + 370, top + 60, DIM, false);
        context.drawText(textRenderer, Text.literal(exteriorVariantName), left + 370, top + 76, GOLD, false);

        context.drawText(textRenderer, Text.literal("DESTINATION"), left + 22, top + 94, DIM, false);
        context.drawText(textRenderer, Text.literal("DIMENSION"), left + 22, top + 103, DIM, false);
        context.drawText(textRenderer, Text.literal("X / Y / Z"), left + 22, top + 145, DIM, false);

        context.drawText(textRenderer, Text.literal("CURRENT INTERIOR"), left + 22, top + 198, DIM, false);
        context.drawText(textRenderer, Text.literal(interior), left + 145, top + 198, TEXT, false);

        String status = flightPending ? "IN FLIGHT — DEMATERIALISING" : "READY FOR FLIGHT";
        context.drawText(textRenderer, Text.literal(status), left + 22, top + 326, flightPending ? GOLD : GREEN, false);
        context.drawText(textRenderer, Text.literal("Flight consumes " + com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity.FLIGHT_COST + " artron energy."), left + 22, top + 344, DIM, false);

        flightButton.active = !flightPending;
        refuelButton.active = !flightPending;
        interiorApplyButton.active = !flightPending && !interiors.isEmpty();
        variantButton.setMessage(Text.literal("EXTERIOR: " + exteriorVariantName));
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }

    public static void applyServerState(PacketByteBuf buf) {
        boolean valid = buf.readBoolean();
        MinecraftClient client = MinecraftClient.getInstance();
        if (!valid) return;
        int fuel = buf.readInt();
        int maxFuel = buf.readInt();
        int variant = buf.readVarInt();
        String variantName = buf.readString(32);
        String interior = buf.readString(64);
        int count = buf.readVarInt();
        List<String> interiors = new ArrayList<>();
        for (int i = 0; i < count; i++) interiors.add(buf.readString(64));
        String dim = buf.readString(128);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        boolean flight = buf.readBoolean();
        boolean locked = buf.readBoolean();

        client.execute(() -> {
            TardisMonitorScreen screen;
            if (client.currentScreen instanceof TardisMonitorScreen existing) {
                screen = existing;
            } else {
                screen = new TardisMonitorScreen();
                client.setScreen(screen);
            }
            screen.fuel = fuel;
            screen.maxFuel = maxFuel;
            screen.exteriorVariant = variant;
            screen.exteriorVariantName = variantName;
            screen.interior = interior;
            screen.interiors = interiors;
            screen.flightPending = flight;
            screen.locked = locked;
            if (!screen.dimension.isFocused()) screen.dimension.setText(dim);
            if (!screen.x.isFocused()) screen.x.setText(format(x));
            if (!screen.y.isFocused()) screen.y.setText(format(y));
            if (!screen.z.isFocused()) screen.z.setText(format(z));
            screen.syncInteriorIndex();
        });
    }

    private static String format(double d) {
        if (d == Math.rint(d)) return Long.toString((long)d);
        return String.format(java.util.Locale.ROOT, "%.2f", d);
    }
}
