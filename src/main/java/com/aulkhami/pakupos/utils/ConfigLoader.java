package com.aulkhami.pakupos.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();
    private static Dotenv dotenv;

    static {
        // Load properties file
        try (
            InputStream in = ConfigLoader.class.getResourceAsStream(
                "/config/application.properties"
            )
        ) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException ignored) {}

        // Initialize Dotenv (will look for .env in project root)
        try {
            dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        } catch (Exception e) {
            // Silently fail if .env is missing or invalid
        }
    }

    public static String getProperty(String key) {
        // 1. Check .env / System Env
        if (dotenv != null) {
            String envVal = dotenv.get(key.toUpperCase().replace(".", "_"));
            if (envVal != null) return envVal;
        }

        // 2. Check application.properties
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return val != null ? val : defaultValue;
    }
}
