package com.timelordmod.gallifrey.networking;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.networking.packets.VMPacket;
import com.timelordmod.gallifrey.networking.packets.SonicShadesPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

/**
 * Packet IDs and server-side receivers.
 *
 * This class is loaded on dedicated servers too (SonicWorkshopBlock uses
 * OPEN_SONIC_WORKSHOP), so it must never mention client-only classes such as
 * ClientPlayNetworking, MinecraftClient or screens. Client receivers live in
 * client/ModClientPackets.
 */
public class ModPackets {
    public static final Identifier VM_PACKET = new Identifier(GallifreyMod.MOD_ID, "vm_packet");
    public static final Identifier VM_STATE = new Identifier(GallifreyMod.MOD_ID, "vm_state");
    public static final Identifier OPEN_SONIC_WORKSHOP = new Identifier(GallifreyMod.MOD_ID, "open_sonic_workshop");
    public static final Identifier SONIC_SHADES_USE = new Identifier(GallifreyMod.MOD_ID, "sonic_shades_use");
    public static final Identifier TARDIS_MONITOR_ACTION = new Identifier(GallifreyMod.MOD_ID, "tardis_monitor_action");
    public static final Identifier TARDIS_MONITOR_STATE = new Identifier(GallifreyMod.MOD_ID, "tardis_monitor_state");
    public static final Identifier TARDIS_CLOSE_CONSOLE = new Identifier(GallifreyMod.MOD_ID, "tardis_close_console");
    public static final Identifier TARDIS_REGISTER_DIMENSION = new Identifier(GallifreyMod.MOD_ID, "tardis_register_dimension");
    public static final Identifier TARDIS_RWF_INPUT = new Identifier(GallifreyMod.MOD_ID, "tardis_rwf_input");
    public static final Identifier TARDIS_RWF_STATE = new Identifier(GallifreyMod.MOD_ID, "tardis_rwf_state");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VM_PACKET, VMPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SONIC_SHADES_USE, SonicShadesPacket::receive);
    }
}
