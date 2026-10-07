package com.timelordmod.gallifrey;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.entity.ModBoats;
import com.timelordmod.gallifrey.entity.GallifreyEntities;
import com.timelordmod.gallifrey.item.GallifreyCreativeTab;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.networking.packets.VMPacket;
import com.timelordmod.gallifrey.world.biome.ModBiomes;
import com.timelordmod.gallifrey.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.gen.GenerationStep;
import com.timelordmod.gallifrey.world.MarsWorldHandler;
import com.timelordmod.gallifrey.world.portal.GallifreyPortalAreaHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import com.timelordmod.gallifrey.tardis.TardisCommands;
import com.timelordmod.gallifrey.tardis.TardisDimensionManager;
import com.timelordmod.gallifrey.tardis.TardisMonitorNetworking;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;
import com.timelordmod.gallifrey.networking.packets.SonicCasingPacket;
import com.timelordmod.gallifrey.networking.packets.SonicShadesPacket;

import com.timelordmod.gallifrey.world.feature.PrehistoricVinesFeature;
public class GallifreyMod implements ModInitializer {
	public static final String MOD_ID = "gallifrey";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier VM_PACKET_ID = new Identifier(MOD_ID, "vm_packet");
	public static final Identifier GALLIFREY_FRAME_TESTER =
			new Identifier(GallifreyMod.MOD_ID, "gallifrey_frame");


	@Override
	public void onInitialize() {
        net.minecraft.registry.Registry.register(
                net.minecraft.registry.Registries.FEATURE,
                id("prehistoric_vines"),
                new PrehistoricVinesFeature(net.minecraft.world.gen.feature.DefaultFeatureConfig.CODEC)
        );



		LOGGER.info("[Gallifrey] Initialising core systems...!");

		TardisCommands.register();
		TardisMonitorNetworking.register();
		ServerTickEvents.END_SERVER_TICK.register(TardisDimensionManager::tickFlight);

        // The physical TARDIS is persistent world infrastructure. Prevent normal
        // block breaking (including Creative mode); /tardis delete <id> is its
        // intentional removal path.
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) ->
                !state.isOf(GallifreyModBlocks.TARDIS_EXTERIOR));

		GallifreySounds.register();

		FabricDefaultAttributeRegistry.register(
				GallifreyEntities.SKARO_CITY_DALEK,
				com.timelordmod.gallifrey.entity.custom.SkaroCityDalekEntity.createAttributes()
		);

		GallifreyModItems.register();
		GallifreyModBlocks.register();
		GallifreyModBlockEntities.register();

		// Atrium fuel burns for 3x the time of coal: 4800 ticks vs 1600.
		FuelRegistry.INSTANCE.add(GallifreyModItems.ATRIUM_FUEL, 4800);

		// Atrium generation is explicitly limited to vanilla Overworld biomes.
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.ATRIUM_ORE_UPPER_PLACED_KEY
		);
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.ATRIUM_ORE_MIDDLE_PLACED_KEY
		);
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.ATRIUM_ORE_SMALL_PLACED_KEY
		);

		// Nether ore generation: both rare ores use the Nether-specific configured targets.
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(
						BiomeKeys.NETHER_WASTES, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.CRIMSON_FOREST,
						BiomeKeys.WARPED_FOREST, BiomeKeys.BASALT_DELTAS),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.NETHER_SONIC_CRYSTAL_ORE_PLACED_KEY);
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(
						BiomeKeys.NETHER_WASTES, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.CRIMSON_FOREST,
						BiomeKeys.WARPED_FOREST, BiomeKeys.BASALT_DELTAS),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.NETHER_WHITE_POINT_ORE_PLACED_KEY);

		// Sonic Crystal also belongs in the vanilla Overworld.
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES,
				ModPlacedFeatures.SONIC_CRYSTAL_ORE_PLACED_KEY);

		GallifreyCreativeTab.register();
		ModBoats.registerBoats();
		BiomePlacement.replaceOverworld(BiomeKeys.FOREST, ModBiomes.TREEBORG_FOREST, 0.3d);



		ServerPlayNetworking.registerGlobalReceiver(
				new Identifier("gallifrey", "vm_packet"),
				VMPacket::receive
		);

		ServerPlayNetworking.registerGlobalReceiver(
				new Identifier(MOD_ID, "change_sonic_workshop_casing"),
				SonicCasingPacket::receive
		);

        ServerPlayNetworking.registerGlobalReceiver(
                new Identifier(MOD_ID, "sonic_shades_use"),
                SonicShadesPacket::receive
        );


		// Strippable blocks registry

		//Tardis wood type
		StrippableBlockRegistry.register(GallifreyModBlocks.TARDIS_LOG, GallifreyModBlocks.STRIP_TARDIS_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.TARDIS_WOOD, GallifreyModBlocks.STRIP_TARDIS_WOOD);

		//Ulanda wood type
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_LOG, GallifreyModBlocks.STRIP_ULANDA_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_WOOD, GallifreyModBlocks.STRIP_ULANDA_WOOD);

		//Treeborg wood set
		StrippableBlockRegistry.register(GallifreyModBlocks.TREEBORG_LOG, GallifreyModBlocks.STRIP_TREEBORG_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.TREEBORG_WOOD, GallifreyModBlocks.STRIP_TREEBORG_WOOD);

		//Ash wood set
		StrippableBlockRegistry.register(GallifreyModBlocks.ASH_LOG, GallifreyModBlocks.STRIP_ASH_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.ASH_WOOD, GallifreyModBlocks.STRIP_ASH_WOOD);

		//Maple wood set
		StrippableBlockRegistry.register(GallifreyModBlocks.MAPLE_LOG, GallifreyModBlocks.STRIP_MAPLE_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.MAPLE_WOOD, GallifreyModBlocks.STRIP_MAPLE_WOOD);

		//Moonpine wood set
		StrippableBlockRegistry.register(GallifreyModBlocks.MOONPINE_LOG, GallifreyModBlocks.STRIP_MOONPINE_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.MOONPINE_WOOD, GallifreyModBlocks.STRIP_MOONPINE_WOOD);

		//Skaro wasted wood set


		//Moon-pine wood set


		//GRASSBLOCK STUFF
		com.timelordmod.gallifrey.block.custom.GrassInteractions.register();


		// Custom Dimension Stuff
		CustomPortalApiRegistry.registerPortalFrameTester(GALLIFREY_FRAME_TESTER, GallifreyPortalAreaHelper::new);

		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.REINFORCED_STEEL_BLOCK)
						.customFrameTester(GALLIFREY_FRAME_TESTER)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "gallifrey"))
						.tintColor(230, 142, 48)
						.registerPortal();

		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.MARS_STONE_BRICKS)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "mars"))
						.tintColor(150, 55, 35)
						.registerPortal();

		// AWT planet ports. These use blocks/items already present in Gallifrey.
		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.DALEKANIUM_BLOCK)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "skaro"))
						.tintColor(150, 110, 45)
						.registerPortal();

		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.STEEL_BLOCK)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "mondas"))
						.tintColor(130, 150, 170)
						.registerPortal();

		// Pete's World: parallel Overworld with a different seed.
		// Crying obsidian frame, lit with a White Point Star like the others.
		CustomPortalBuilder.beginPortal()
						.frameBlock(net.minecraft.block.Blocks.CRYING_OBSIDIAN)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "petes_world"))
						.tintColor(90, 140, 230)
						.registerPortal();

		// Lost Reality: patchwork of the other dimensions. Lost Dirt frame (creative-only for now).
		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.LOST_DIRT)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "lost_reality"))
						.tintColor(40, 160, 150)
						.registerPortal();



		MarsWorldHandler.register();


		LOGGER.info("[Gallifrey] Core systems ready.");
	}

	public static Identifier id(String path) { return new Identifier(MOD_ID, path);}
}
