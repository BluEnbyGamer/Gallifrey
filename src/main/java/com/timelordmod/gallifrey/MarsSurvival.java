
package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.item.GallifreyModItems;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public final class MarsSurvival {

    // Change this if your Mars dimension uses a different ID.
    public static final RegistryKey<World> MARS =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    new Identifier("gallifrey", "mars")
            );

    private MarsSurvival() {
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!world.getRegistryKey().equals(MARS)) {
                return;
            }

            for (ServerPlayerEntity player : world.getPlayers()) {
                boolean hasHelmet =
                        player.getEquippedStack(EquipmentSlot.HEAD)
                                .isOf(GallifreyModItems.SPACE_SUIT_HELMET);

                boolean hasChestplate =
                        player.getEquippedStack(EquipmentSlot.CHEST)
                                .isOf(GallifreyModItems.SPACE_SUIT_CHESTPLATE);

                boolean hasLeggings =
                        player.getEquippedStack(EquipmentSlot.LEGS)
                                .isOf(GallifreyModItems.SPACE_SUIT_LEGGINGS);

                boolean hasBoots =
                        player.getEquippedStack(EquipmentSlot.FEET)
                                .isOf(GallifreyModItems.SPACE_SUIT_BOOTS);

                boolean fullSuit =
                        hasHelmet && hasChestplate
                                && hasLeggings && hasBoots;

                if (!fullSuit && !player.isCreative()
                        && !player.isSpectator()) {
                    DamageSource source = player.getDamageSources().dryOut();

                    player.damage(source, 1.0F);
                }
            }
        });
    }
}

