package com.timelordmod.gallifrey.networking;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.networking.packets.VMPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModPackets {
    public static final Identifier VM_PACKET = new Identifier(GallifreyMod.MOD_ID, "vm_packet");
    public static final Identifier VM_STATE = new Identifier(GallifreyMod.MOD_ID, "vm_state");
    public static final Identifier OPEN_SONIC_WORKSHOP = new Identifier(GallifreyMod.MOD_ID, "open_sonic_workshop");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_SONIC_WORKSHOP, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> client.setScreen(new com.timelordmod.gallifrey.screens.SonicWorkshopScreen(pos)));
        });
        ClientPlayNetworking.registerGlobalReceiver(VM_STATE, (client, handler, buf, responseSender) -> {
            PacketByteBuf copy = new PacketByteBuf(buf.copy());
            client.execute(() -> com.timelordmod.gallifrey.screens.VortexManipulatorScreen.applyServerState(copy));
        });
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VM_PACKET, VMPacket::receive);
    }
}
