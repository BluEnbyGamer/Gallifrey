package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.GallifreySounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VortexManipulator extends Item {

    private static final String IN_FLIGHT_KEY = "VMInFlight";

    public VortexManipulator(Settings settings) {
        super(settings
                .rarity(Rarity.RARE)
                .maxCount(1)
                .maxDamage(100)
                .fireproof()
        );
    }

    // =========================================================
    // USE
    // =========================================================

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity user,
            Hand hand
    ) {

        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient && user instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
            VortexManipulatorData.ensureOwner(stack, serverPlayer);
            if (!VortexManipulatorData.isAuthorized(stack, serverPlayer.getUuid())) {
                serverPlayer.sendMessage(Text.literal("ISOMORPHIC LOCK: this Vortex Manipulator is keyed to another Time Lord."), true);
                return TypedActionResult.fail(stack);
            }
        }

        // -----------------------------------------------------
        // TAKE-OFF SOUND
        // -----------------------------------------------------

        if (!world.isClient) {

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    GallifreySounds.VM_TAKE_OFF,
                    net.minecraft.sound.SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            // Mark the player as being in-flight.
            stack.getOrCreateNbt().putBoolean(
                    IN_FLIGHT_KEY,
                    true
            );
        }

        return TypedActionResult.success(
                stack,
                world.isClient()
        );
    }

    // =========================================================
    // LANDING DETECTION
    // =========================================================

    @Override
    public void inventoryTick(
            ItemStack stack,
            World world,
            Entity entity,
            int slot,
            boolean selected
    ) {

        super.inventoryTick(
                stack,
                world,
                entity,
                slot,
                selected
        );

        // Only run on the server.
        if (world.isClient) {
            return;
        }

        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        boolean inFlight =
                stack
                        .getOrCreateNbt()
                        .getBoolean(IN_FLIGHT_KEY);

        if (!inFlight) {
            return;
        }

        // -----------------------------------------------------
        // PLAYER HAS LANDED
        // -----------------------------------------------------

        if (player.isOnGround()) {

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    GallifreySounds.VM_LAND,
                    net.minecraft.sound.SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            stack.getOrCreateNbt().putBoolean(
                    IN_FLIGHT_KEY,
                    false
            );
        }
    }

    // =========================================================
    // TOOLTIP
    // =========================================================

    @Override
    public void appendTooltip(
            ItemStack stack,
            @Nullable World world,
            List<Text> tooltip,
            net.minecraft.client.item.TooltipContext context
    ) {

        tooltip.add(
                Text.literal(
                        "A dangerous tool used for easy space travel."
                )
        );

        tooltip.add(
                Text.literal(
                        "WARNING: Time Lords are not responsible for any side effects caused!"
                ).formatted(Formatting.RED)
        );

        tooltip.add(
                Text.literal(
                        "Blame UNIT!"
                ).formatted(Formatting.DARK_RED)
        );

        super.appendTooltip(
                stack,
                world,
                tooltip,
                context
        );
    }
}
