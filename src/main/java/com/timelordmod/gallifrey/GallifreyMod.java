package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyCreativeTab;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GallifreyMod implements ModInitializer {
	public static final String MOD_ID = "gallifrey";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[Gallifrey] Initialising core systems...!");

		GallifreyModItems.register();
		GallifreyModBlocks.register();
		GallifreyCreativeTab.register();





		// Strippable blocks registry
		//Ulanda wood type
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_LOG, GallifreyModBlocks.STRIP_ULANDA_LOG);
		StrippableBlockRegistry.register(GallifreyModBlocks.ULANDA_WOOD, GallifreyModBlocks.STRIP_ULANDA_WOOD);

		//Treeborg wood set


		//Ash wood set


		//Maple wood set


		//Willow wood set


		//Moon-pine wood set





		// Custom Dimension Stuff



		LOGGER.info("[Gallifrey] Core systems ready.");
	}

	public static Identifier id(String path) { return new Identifier(MOD_ID, path);}
}