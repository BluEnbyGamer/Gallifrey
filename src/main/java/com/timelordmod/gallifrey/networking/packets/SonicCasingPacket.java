package com.timelordmod.gallifrey.networking.packets;

import com.timelordmod.gallifrey.block.entity.SonicWorkshopBlockEntity;
import com.timelordmod.gallifrey.sonic.SonicCasing;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class SonicCasingPacket {
    private SonicCasingPacket() {}

    public static void receive(
            net.minecraft.server.MinecraftServer server,
            PlayerEntity player,
            ServerPlayNetworkHandler handler,
            PacketByteBuf buf,
            net.fabricmc.fabric.api.networking.v1.PacketSender responseSender) {

        BlockPos pos = buf.readBlockPos();
        String casingId = buf.readString(64);

        server.execute(() -> {
            if (!(player.getWorld() instanceof ServerWorld world)) {
                return;
            }

            if (player.squaredDistanceTo(
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D) > 64.0D) {
                return;
            }

            if (!(world.getBlockEntity(pos) instanceof SonicWorkshopBlockEntity workshop)) {
                return;
            }

            SonicCasing casing = SonicCasing.fromId(casingId);
            workshop.setCasing(casing);
        });
    }
}
