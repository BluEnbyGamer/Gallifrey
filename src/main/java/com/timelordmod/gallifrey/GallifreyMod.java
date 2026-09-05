package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyCreativeTab;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import com.timelordmod.gallifrey.networking.packets.VMPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.timelordmod.gallifrey.block.GallifreyModBlockEntities;

public class GallifreyMod implements ModInitializer {
	public static final String MOD_ID = "gallifrey";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier VM_PACKET_ID = new Identifier(MOD_ID, "vm_packet");

	@Override
	public void onInitialize() {


		LOGGER.info("[Gallifrey] Initialising core systems...!");

		GallifreyModItems.register();
		GallifreyModBlocks.register();
		GallifreyModBlockEntities.register();
		GallifreyCreativeTab.register();



		ServerPlayNetworking.registerGlobalReceiver(
				new Identifier("gallifrey", "vm_packet"),
				VMPacket::receive
		);

		// Strippable blocks registry

		//Tardis wood type
		StrippableBlockRegistry.register(GallifreyModBlocks.TARDIS_LOG, GallifreyModBlocks.STRIP_TARDIS_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.TARDIS_WOOD, GallifreyModBlocks.STRIP_TARDIS_WOOD);

		//Ulanda wood type
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_LOG, GallifreyModBlocks.STRIP_ULANDA_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_WOOD, GallifreyModBlocks.STRIP_ULANDA_WOOD);

		//Treeborg wood set


		//Ash wood set


		//Maple wood set


		//Willow wood set


		//Moon-pine wood set





		// Custom Dimension Stuff
		CustomPortalBuilder.beginPortal()
						.frameBlock(GallifreyModBlocks.REINFORCED_STEEL_BLOCK)
						.lightWithItem(GallifreyModItems.WHITE_POINT_STAR)
						.destDimID(new Identifier(GallifreyMod.MOD_ID, "gallifrey"))
						.tintColor(230, 142, 48)
						.registerPortal();


		LOGGER.info("[Gallifrey] Core systems ready.");
	}

	public static Identifier id(String path) { return new Identifier(MOD_ID, path);}
}