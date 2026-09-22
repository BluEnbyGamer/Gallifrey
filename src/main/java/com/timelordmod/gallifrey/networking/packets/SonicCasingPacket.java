package com.timelordmod.gallifrey.networking.packets;

import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;
import com.timelordmod.gallifrey.sonic.SonicCasing;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;

public class SonicCasingPacket {

    public static void receive(
            net.minecraft.server.MinecraftServer server,
            PlayerEntity player,
            net.minecraft.server.network.ServerPlayNetworkHandler handler,
            PacketByteBuf buf,
            net.fabricmc.fabric.api.networking.v1.PacketSender responseSender
    ) {

        String casingId =
                buf.readString(64);

        int handValue =
                buf.readInt();

        server.execute(() -> {

            // =====================================================
            // HAND
            // =====================================================

            Hand hand =
                    handValue == 0
                            ? Hand.MAIN_HAND
                            : Hand.OFF_HAND;

            // =====================================================
            // GET SONIC
            // =====================================================

            ItemStack stack =
                    player.getStackInHand(hand);

            // =====================================================
            // SECURITY CHECK
            // =====================================================

            if (!(stack.getItem()
                    instanceof SonicScrewdriver)) {

                return;
            }

            // =====================================================
            // GET CASING
            // =====================================================

            SonicCasing casing =
                    SonicCasing.fromId(casingId);

            // =====================================================
            // SAVE CASING
            // =====================================================

            SonicScrewdriver.setCasing(
                    stack,
                    casing
            );
        });
    }
}

