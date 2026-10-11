package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.GallifreyModItems;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class SpaceSuitPlayerModelMixin {

    @Shadow
    protected abstract void setModelPose(
            AbstractClientPlayerEntity player
    );

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void gallifrey$hideSkinLayers(
            AbstractClientPlayerEntity player,
            CallbackInfo ci
    ) {
        PlayerEntityModel<AbstractClientPlayerEntity> model =
                ((LivingEntityRendererAccessor<
                        AbstractClientPlayerEntity,
                        PlayerEntityModel<AbstractClientPlayerEntity>
                        >) (Object) this).gallifrey$getModel();

        ItemStack chest =
                player.getEquippedStack(EquipmentSlot.CHEST);

        boolean wearingSpaceSuit =
                chest.isOf(GallifreyModItems.SPACE_SUIT_CHESTPLATE);

        model.hat.visible = !wearingSpaceSuit;
        model.jacket.visible = !wearingSpaceSuit;
        model.leftSleeve.visible = !wearingSpaceSuit;
        model.rightSleeve.visible = !wearingSpaceSuit;
        model.leftPants.visible = !wearingSpaceSuit;
        model.rightPants.visible = !wearingSpaceSuit;
    }
}