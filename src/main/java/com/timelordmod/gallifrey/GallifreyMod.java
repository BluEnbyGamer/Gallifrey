package com.timelordmod.gallifrey;

import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.item.GallifreyCreativeTab;
import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.fabricmc.api.ModInitializer;
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

	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}