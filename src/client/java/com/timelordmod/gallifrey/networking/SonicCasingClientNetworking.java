package com.timelordmod.gallifrey.networking;

import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.sonic.SonicCasing;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class SonicCasingClientNetworking {

    public static final Identifier CHANGE_CASING =
            new Identifier(
                    GallifreyMod.MOD_ID,
                    "change_sonic_casing"
            );

    public static void sendCasingChange(
            Hand hand,
            SonicCasing casing
    ) {

        PacketByteBuf buf =
                PacketByteBufs.create();

        buf.writeString(
                casing.getId()
        );

        buf.writeInt(
                hand == Hand.MAIN_HAND
                        ? 0
                        : 1
        );

        ClientPlayNetworking.send(
                CHANGE_CASING,
                buf
        );
    }
}
