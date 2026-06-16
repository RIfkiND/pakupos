package com.aulkhami.pakupos.config;

import com.aulkhami.pakupos.app.utils.ConfigLoader;

public class AppConfig {

    private AppConfig() {}

    public static String get(String key, String defaultValue) {
        return ConfigLoader.getProperty(key, defaultValue);
    }

    public static String getAppName() {
        return get(Constants.APP_NAME_KEY, Constants.DEFAULT_APP_NAME);
    }

    public static String getAppVersion() {
        return get(Constants.APP_VERSION_KEY, Constants.DEFAULT_APP_VERSION);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }
}
