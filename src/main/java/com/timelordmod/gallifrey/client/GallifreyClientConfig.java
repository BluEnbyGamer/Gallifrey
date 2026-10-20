package com.timelordmod.gallifrey.client;

import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Tiny client-side settings file: config/gallifrey-client.properties.
 * Only holds things the player chooses on their own machine.
 */
public final class GallifreyClientConfig {
    private static final String FILE_NAME = "gallifrey-client.properties";
    private static final String KEY_MENU_BACKGROUND = "menuBackground";
    /** Older versions saved only true/false (vortex or panorama); still read so the choice carries over. */
    private static final String KEY_OLD_VORTEX_MENU = "vortexMenuBackground";

    private static boolean loaded = false;
    private static MenuBackground menuBackground = MenuBackground.VORTEX;

    private GallifreyClientConfig() {}

    private static Path file() {
        return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve(FILE_NAME);
    }

    private static void load() {
        loaded = true;
        Path path = file();
        if (!Files.exists(path)) {
            return;
        }
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(path)) {
            properties.load(in);
            String saved = properties.getProperty(KEY_MENU_BACKGROUND);
            if (saved != null) {
                menuBackground = MenuBackground.byName(saved, menuBackground);
            } else if (properties.containsKey(KEY_OLD_VORTEX_MENU)) {
                menuBackground = Boolean.parseBoolean(properties.getProperty(KEY_OLD_VORTEX_MENU))
                        ? MenuBackground.VORTEX : MenuBackground.PANORAMA;
            }
        } catch (IOException | RuntimeException ignored) {
            // A broken settings file must never stop the game from starting; keep the default.
        }
    }

    private static void save() {
        Properties properties = new Properties();
        properties.setProperty(KEY_MENU_BACKGROUND, menuBackground.name());
        try {
            Path path = file();
            Files.createDirectories(path.getParent());
            try (OutputStream out = Files.newOutputStream(path)) {
                properties.store(out, "Gallifrey Mod client settings");
            }
        } catch (IOException | RuntimeException ignored) {
            // Not being able to save only means the choice is forgotten next launch.
        }
    }

    public static MenuBackground getMenuBackground() {
        if (!loaded) {
            load();
        }
        return menuBackground;
    }

    public static void setMenuBackground(MenuBackground value) {
        MenuBackground previous = getMenuBackground();
        menuBackground = value;
        save();
        // Free the animation we just switched away from.
        if (previous != value && previous.frameFolder() != null) {
            VideoVortexRenderer.unload();
        }
    }

    /** Moves on to the next background (title screen button and Mod Menu settings). */
    public static MenuBackground cycleMenuBackground() {
        setMenuBackground(getMenuBackground().next());
        return menuBackground;
    }
}
