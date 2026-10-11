package com.timelordmod.gallifrey.mixin.client;

import com.timelordmod.gallifrey.item.GallifreyModItems;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class SpaceSuitPlayerModelMixin {

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void gallifrey$hideSuitCoveredParts(
            AbstractClientPlayerEntity player,
            CallbackInfo ci
    ) {
        PlayerEntityModel<AbstractClientPlayerEntity> model =
                ((LivingEntityRendererAccessor<
                        AbstractClientPlayerEntity,
                        PlayerEntityModel<AbstractClientPlayerEntity>
                        >) (Object) this).gallifrey$getModel();

        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);

        boolean suitChest = chest.isOf(
                GallifreyModItems.SPACE_SUIT_CHESTPLATE);
        boolean suitLegs = legs.isOf(
                GallifreyModItems.SPACE_SUIT_LEGGINGS);

        // Keep the head visible.
        model.head.visible = true;
        model.hat.visible = true;

        // Hide the skin overlays and body beneath the chestplate.
        model.jacket.visible = !suitChest;
        model.leftSleeve.visible = !suitChest;
        model.rightSleeve.visible = !suitChest;
        model.body.visible = !suitChest;
        model.leftArm.visible = !suitChest;
        model.rightArm.visible = !suitChest;

        // Hide the skin overlays and body beneath the leggings.
        model.leftPants.visible = !suitLegs;
        model.rightPants.visible = !suitLegs;
        model.leftLeg.visible = !suitLegs;
        model.rightLeg.visible = !suitLegs;
    }
}