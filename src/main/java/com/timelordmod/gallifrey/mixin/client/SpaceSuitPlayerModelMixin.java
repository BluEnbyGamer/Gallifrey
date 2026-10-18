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

        // Timelord robes use the same covered-skin behaviour as the spacesuit.
        // Their GeckoLib model supplies the visible robe geometry, so don't render
        // the player's skin/outer clothing layers underneath it.
        boolean robeChest = chest.isOf(
                GallifreyModItems.TIMELORD_ROBE_CHESTPLATE);
        boolean robeLegs = legs.isOf(
                GallifreyModItems.TIMELORD_ROBE_LEGGINGS);
        boolean hideChestLayers = suitChest || robeChest;
        boolean hideLegLayers = suitLegs || robeLegs;

        // Keep the head visible.
        model.head.visible = true;
        model.hat.visible = true;

        // Hide the skin overlays and body beneath the chestplate.
        model.jacket.visible = !hideChestLayers;
        model.leftSleeve.visible = !hideChestLayers;
        model.rightSleeve.visible = !hideChestLayers;
        model.body.visible = !hideChestLayers;
        model.leftArm.visible = !hideChestLayers;
        model.rightArm.visible = !hideChestLayers;

        // Hide the skin overlays and body beneath the leggings.
        model.leftPants.visible = !hideLegLayers;
        model.rightPants.visible = !hideLegLayers;
        model.leftLeg.visible = !hideLegLayers;
        model.rightLeg.visible = !hideLegLayers;
    }
}