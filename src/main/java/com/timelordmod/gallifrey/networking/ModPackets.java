package com.timelordmod.gallifrey.networking;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.networking.packets.VMPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier VM_PACKET = new Identifier(GallifreyMod.MOD_ID, "vm_packet");
    public static final Identifier VM_STATE = new Identifier(GallifreyMod.MOD_ID, "vm_state");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(VM_STATE, (client, handler, buf, responseSender) -> {
            PacketByteBuf copy = new PacketByteBuf(buf.copy());
            client.execute(() -> com.timelordmod.gallifrey.screens.VortexManipulatorScreen.applyServerState(copy));
        });
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VM_PACKET, VMPacket::receive);
    }
}
