package com.timelordmod.gallifrey.tardis;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public final class TardisCommands {
    private TardisCommands() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                register(dispatcher));
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tardis")
                .then(CommandManager.literal("exit")
                        .executes(ctx -> exit(ctx.getSource())))
                .then(CommandManager.literal("travel")
                        .then(CommandManager.argument("dimension", StringArgumentType.word())
                                .then(CommandManager.argument("x", DoubleArgumentType.doubleArg())
                                        .then(CommandManager.argument("y", DoubleArgumentType.doubleArg())
                                                .then(CommandManager.argument("z", DoubleArgumentType.doubleArg())
                                                        .executes(ctx -> travel(
                                                                ctx.getSource(),
                                                                StringArgumentType.getString(ctx, "dimension"),
                                                                DoubleArgumentType.getDouble(ctx, "x"),
                                                                DoubleArgumentType.getDouble(ctx, "y"),
                                                                DoubleArgumentType.getDouble(ctx, "z"))))))))
                .then(CommandManager.literal("lock")
                        .executes(ctx -> setLock(ctx.getSource(), true)))
                .then(CommandManager.literal("unlock")
                        .executes(ctx -> setLock(ctx.getSource(), false)))
                .then(CommandManager.literal("refuel")
                        .executes(ctx -> refuel(ctx.getSource())))
                .then(CommandManager.literal("info")
                        .executes(ctx -> info(ctx.getSource()))));
    }

    private static int exit(ServerCommandSource source) {
        try {
            return TardisDimensionManager.exit(source.getPlayerOrThrow()) ? 1 : 0;
        } catch (Exception e) {
            source.sendError(Text.literal("Only players can use TARDIS controls."));
            return 0;
        }
    }

    private static int travel(ServerCommandSource source, String dimension, double x, double y, double z) {
        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            Identifier id = new Identifier(dimension);
            ServerWorld world = player.getServer().getWorld(
                    net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, id));
            if (world == null) {
                source.sendError(Text.literal("Unknown dimension: " + id));
                return 0;
            }
            int minY = world.getBottomY();
            int maxY = world.getTopY() - 2;
            if (y < minY || y > maxY) {
                source.sendError(Text.literal("Y must be between " + minY + " and " + maxY + "."));
                return 0;
            }
            return TardisDimensionManager.travel(player, world,
                    BlockPos.ofFloored(x, y, z), player.getYaw()) ? 1 : 0;
        } catch (Exception e) {
            source.sendError(Text.literal("Invalid TARDIS destination."));
            return 0;
        }
    }

    private static int setLock(ServerCommandSource source, boolean locked) {
        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            TardisRegistryState state = TardisRegistryState.get(player.getServer());
            java.util.UUID id = state.getActiveTardis(player.getUuid());
            if (id == null || state.get(id) == null) {
                source.sendError(Text.literal("You are not linked to a TARDIS."));
                return 0;
            }
            TardisRegistryState.Record record = state.get(id);
            if (record.owner() == null || !record.owner().equals(player.getUuid())) {
                source.sendError(Text.literal("Only the TARDIS owner can change its lock."));
                return 0;
            }
            ServerWorld world = player.getServer().getWorld(
                    net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, new Identifier(record.world())));
            BlockPos pos = BlockPos.fromLong(record.pos());
            if (world == null || !(world.getBlockEntity(pos) instanceof com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity tardis)) {
                source.sendError(Text.literal("TARDIS exterior is unavailable."));
                return 0;
            }
            tardis.setLocked(locked);
            source.sendFeedback(() -> Text.literal(locked ? "TARDIS locked." : "TARDIS unlocked."), true);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int refuel(ServerCommandSource source) {
        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            TardisRegistryState state = TardisRegistryState.get(player.getServer());
            java.util.UUID id = state.getActiveTardis(player.getUuid());
            if (id == null || state.get(id) == null) {
                source.sendError(Text.literal("Enter your TARDIS before refuelling it."));
                return 0;
            }
            TardisRegistryState.Record record = state.get(id);
            if (record.owner() == null || !record.owner().equals(player.getUuid())) {
                source.sendError(Text.literal("Only the owner can refuel the TARDIS."));
                return 0;
            }
            ServerWorld world = player.getServer().getWorld(
                    net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, new Identifier(record.world())));
            BlockPos pos = BlockPos.fromLong(record.pos());
            if (world == null || !(world.getBlockEntity(pos) instanceof com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity tardis)) {
                source.sendError(Text.literal("TARDIS exterior is unavailable."));
                return 0;
            }

            int consumed = 0;
            int added = 0;
            for (int slot = 0; slot < player.getInventory().size() && tardis.getFuel() < com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity.MAX_FUEL; slot++) {
                net.minecraft.item.ItemStack stack = player.getInventory().getStack(slot);
                if (!stack.isOf(GallifreyModItems.ATRIUM_FUEL)) continue;
                stack.decrement(1);
                int before = tardis.getFuel();
                added += tardis.addFuel(100);
                consumed++;
            }
            if (consumed == 0) {
                source.sendError(Text.literal("You need Atrium Fuel."));
                return 0;
            }
            int finalAdded = added;
            source.sendFeedback(() -> Text.literal("Refuelled TARDIS: +" + finalAdded + " artron energy (" +
                    tardis.getFuel() + "/" + com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity.MAX_FUEL + ")."), true);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int info(ServerCommandSource source) {
        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            TardisRegistryState state = TardisRegistryState.get(player.getServer());
            java.util.UUID id = state.getActiveTardis(player.getUuid());
            if (id == null || state.get(id) == null) {
                source.sendFeedback(() -> Text.literal("No active TARDIS link."), false);
                return 1;
            }
            TardisRegistryState.Record record = state.get(id);
            ServerWorld world = player.getServer().getWorld(
                    net.minecraft.registry.RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, new Identifier(record.world())));
            BlockPos pos = BlockPos.fromLong(record.pos());
            int fuel = -1;
            boolean locked = record.locked();
            if (world != null && world.getBlockEntity(pos) instanceof com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity tardis) {
                fuel = tardis.getFuel();
                locked = tardis.isLocked();
            }
            int shownFuel = fuel;
            source.sendFeedback(() -> Text.literal("TARDIS " + id + " | " + record.world() +
                    " @ " + pos.toShortString() + " | fuel " + shownFuel + "/" +
                    com.timelordmod.gallifrey.block.entity.TardisExteriorBlockEntity.MAX_FUEL +
                    " | " + (locked ? "LOCKED" : "UNLOCKED")), false);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
