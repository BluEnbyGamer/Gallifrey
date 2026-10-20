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
    private static final String KEY_VORTEX_MENU = "vortexMenuBackground";

    private static boolean loaded = false;
    /** true = time vortex behind the title screen, false = the normal panorama. */
    private static boolean vortexMenuBackground = true;

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
            vortexMenuBackground = Boolean.parseBoolean(
                    properties.getProperty(KEY_VORTEX_MENU, Boolean.toString(vortexMenuBackground)));
        } catch (IOException | RuntimeException ignored) {
            // A broken settings file must never stop the game from starting; keep the default.
        }
    }

    private static void save() {
        Properties properties = new Properties();
        properties.setProperty(KEY_VORTEX_MENU, Boolean.toString(vortexMenuBackground));
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

    public static boolean isVortexMenuBackground() {
        if (!loaded) {
            load();
        }
        return vortexMenuBackground;
    }

    public static void setVortexMenuBackground(boolean value) {
        if (!loaded) {
            load();
        }
        vortexMenuBackground = value;
        save();
    }
}
