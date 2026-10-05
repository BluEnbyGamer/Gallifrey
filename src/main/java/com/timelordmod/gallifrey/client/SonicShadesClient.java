package com.timelordmod.gallifrey.client;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.networking.ModPackets;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.lwjgl.glfw.GLFW;


/**
 * Client-only controls and HUD for Sonic Shades.
 *
 * V activates the worn shades; holding Shift while pressing V changes the
 * sonic mode, matching the screwdriver's sneak + right-click mode switch.
 * The complete supplied Gallifreyan symbol spins in the top-left while the shades
 * are worn.
 */
public final class SonicShadesClient {
    private static final Identifier SYMBOL_TEXTURE =
            GallifreyMod.id("textures/gui/sonic_shades_symbol.png");

    private static KeyBinding useShades;

    private SonicShadesClient() {}

    public static void register() {
        useShades = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.gallifrey.sonic_shades",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.gallifrey"
        ));

        HudRenderCallback.EVENT.register(SonicShadesClient::renderHud);

        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (useShades.wasPressed()) {
                if (client.player == null || client.world == null) {
                    continue;
                }

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(client.player.isSneaking());
                ClientPlayNetworking.send(ModPackets.SONIC_SHADES_USE, buf);
            }
        });
    }

    private static void renderHud(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.player.isSpectator()) {
            return;
        }

        if (!client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(GallifreyModItems.SONIC_SHADES)) {
            return;
        }

        // Draw the complete 64x64 supplied PNG in the top-left.  The centre
        // is kept far enough from the corner that rotation never clips the
        // transparent corners or any part of the symbol.
        int size = 64;
        int centreX = 52;
        int centreY = 52;

        float angle = (client.player.age + tickDelta) * 1.5F;
        context.getMatrices().push();
        context.getMatrices().translate(centreX, centreY, 0.0F);
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle));
        context.drawTexture(
                SYMBOL_TEXTURE,
                -size / 2,
                -size / 2,
                0,
                0,
                size,
                size,
                64,
                64
        );
        context.getMatrices().pop();
    }
}
