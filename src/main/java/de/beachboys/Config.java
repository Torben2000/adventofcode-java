package de.beachboys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    private static final String CONFIG_FILE = "config.properties";
    private static final String EXAMPLE_CONFIG_FILE = "config.properties.example";

    private static final String AOC_SESSION_KEY = "aoc.browserSession";
    private static final String EC_SESSION_KEY = "ec.browserSession";
    private static final String EC_SEED_KEY = "ec.seed";

    private static final Properties props = loadProperties();

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = loadConfigStream()) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config. " +
                    "Please ensure " + CONFIG_FILE + " exists in the project root with your credentials.", e);
        }
        return props;
    }

    private static InputStream loadConfigStream() throws IOException {
        Path configPath = Path.of(CONFIG_FILE);
        if (Files.exists(configPath)) {
            return new FileInputStream(configPath.toFile());
        }
        Path examplePath = Path.of(EXAMPLE_CONFIG_FILE);
        if (Files.exists(examplePath)) {
            System.err.println("WARNING: " + CONFIG_FILE + " not found. " +
                    "Please create it from " + EXAMPLE_CONFIG_FILE + " and add your credentials.");
            return new FileInputStream(examplePath.toFile());
        }
        throw new IOException("Neither " + CONFIG_FILE + " nor " + EXAMPLE_CONFIG_FILE + " found in project root");
    }

    public static String getAocSession() {
        return getRequired(AOC_SESSION_KEY);
    }

    public static String getEcSession() {
        return getRequired(EC_SESSION_KEY);
    }

    public static Integer getEcSeed() {
        String seed = props.getProperty(EC_SEED_KEY);
        if (seed == null || seed.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(seed.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String getRequired(String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config property: " + key + ". " +
                    "Please update " + CONFIG_FILE + " with your credentials.");
        }
        return value.trim();
    }
}
