package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.util.GallifreyDimensionTravelAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class GallifreyDimensionTravelMixin {

    @Unique
    private static final Identifier GALLIFREY_BLOCKED_DIMENSION =
            new Identifier("gallifrey", "lost_reality");

    @Unique
    private boolean gallifrey$shouldBlock(ServerWorld destination) {
        ServerPlayerEntity player =
                (ServerPlayerEntity) (Object) this;

        boolean enteringLostReality =
                destination.getRegistryKey().getValue().equals(
                        GALLIFREY_BLOCKED_DIMENSION
                )
                        && !player.getWorld().getRegistryKey()
                        .equals(destination.getRegistryKey());

        return enteringLostReality
                && !GallifreyDimensionTravelAccess.isAuthorized(player);
    }

    @Inject(
            method = "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void gallifrey$blockWorldTransfer(
            ServerWorld destination,
            CallbackInfoReturnable<Entity> cir
    ) {
        if (gallifrey$shouldBlock(destination)) {
            cir.setReturnValue((Entity) (Object) this);
        }
    }

    @Inject(
            method = "teleport(Lnet/minecraft/server/world/ServerWorld;DDDFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void gallifrey$blockTeleport(
            ServerWorld destination,
            double x,
            double y,
            double z,
            float yaw,
            float pitch,
            CallbackInfo ci
    ) {
        if (gallifrey$shouldBlock(destination)) {
            ci.cancel();
        }
    }
}
