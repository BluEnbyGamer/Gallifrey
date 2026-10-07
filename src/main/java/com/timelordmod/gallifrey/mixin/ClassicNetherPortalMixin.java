package com.timelordmod.gallifrey.mixin;

import com.timelordmod.gallifrey.world.dimension.ModDimensions;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;
import java.util.Optional;

/**
 * Makes ordinary vanilla Nether portals link the Classic dimension and the
 * Classic Nether instead of the vanilla Nether. The normal obsidian portal
 * block is still used, so flint and steel works exactly as usual.
 */
@Mixin(Entity.class)
public abstract class ClassicNetherPortalMixin {

    @Shadow protected int netherPortalTime;
    @Shadow protected BlockPos lastNetherPortalPosition;
    @Shadow protected boolean inNetherPortal;

    @Shadow public abstract World getWorld();
    @Shadow public abstract boolean hasVehicle();
    @Shadow public abstract void resetPortalCooldown();
    @Shadow public abstract int getMaxNetherPortalTime();
    @Shadow public abstract double getX();
    @Shadow public abstract double getY();
    @Shadow public abstract double getZ();
    @Shadow public abstract float getYaw();
    @Shadow public abstract float getPitch();

    @Inject(method = "tickPortal", at = @At("HEAD"), cancellable = true)
    private void gallifrey$classicNetherPortal(CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayerEntity player)) return;
        if (!(this.getWorld() instanceof ServerWorld source)) return;
        if (!isClassicPortalDimension(source.getRegistryKey())) return;

        // Only replace the vanilla portal countdown when the player is actually
        // inside a portal. If not, vanilla handles the normal timer decay.
        if (!this.inNetherPortal) return;

        ServerWorld target = source.getServer().getWorld(
                source.getRegistryKey().equals(ModDimensions.CLASSIC_LEVEL_KEY)
                        ? ModDimensions.CLASSIC_NETHER_LEVEL_KEY
                        : ModDimensions.CLASSIC_LEVEL_KEY
        );
        if (target == null || player.hasVehicle()) {
            this.inNetherPortal = false;
            ci.cancel();
            return;
        }

        this.netherPortalTime++;
        int maxTime = this.getMaxNetherPortalTime();
        if (this.netherPortalTime < maxTime) {
            this.inNetherPortal = false;
            ci.cancel();
            return;
        }

        this.netherPortalTime = maxTime;
        this.resetPortalCooldown();
        this.gallifrey$teleportThroughClassicPortal(player, source, target);
        this.inNetherPortal = false;
        ci.cancel();
    }

    private static boolean isClassicPortalDimension(net.minecraft.registry.RegistryKey<World> key) {
        return key.equals(ModDimensions.CLASSIC_LEVEL_KEY)
                || key.equals(ModDimensions.CLASSIC_NETHER_LEVEL_KEY);
    }

    private void gallifrey$teleportThroughClassicPortal(
            ServerPlayerEntity player, ServerWorld source, ServerWorld target) {
        BlockPos sourcePos = this.lastNetherPortalPosition != null
                ? this.lastNetherPortalPosition
                : BlockPos.ofFloored(this.getX(), this.getY(), this.getZ());

        Direction.Axis axis = source.getBlockState(sourcePos)
                .getOrEmpty(NetherPortalBlock.AXIS)
                .orElse(Direction.Axis.X);

        // Keep the Classic pair at a 1:1 coordinate scale, like the requested
        // classic-era Nether. Find an existing portal first, otherwise create
        // the normal vanilla 2x3 obsidian portal at the matching coordinates.
        BlockPos targetPos = BlockPos.ofFloored(this.getX(), this.getY(), this.getZ());
        Optional<BlockLocating.Rectangle> portal = target.getPortalForcer()
                .getPortalRect(targetPos, false, target.getWorldBorder());
        if (portal.isEmpty()) {
            portal = target.getPortalForcer().createPortal(targetPos, axis);
        }

        double x = targetPos.getX() + 0.5D;
        double y = targetPos.getY() + 0.5D;
        double z = targetPos.getZ() + 0.5D;

        if (portal.isPresent()) {
            BlockLocating.Rectangle rect = portal.get();
            x = rect.lowerLeft.getX() + 0.5D;
            y = rect.lowerLeft.getY() + 0.5D;
            z = rect.lowerLeft.getZ() + 0.5D;
        }

        player.teleport(target, x, y, z, EnumSet.noneOf(PositionFlag.class), this.getYaw(), this.getPitch());
    }
}
