package com.smartschool.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Utility class for configuration management
 */
public class ConfigUtils {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            System.out.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) throws IOException {
        properties.setProperty(key, value);
        properties.store(new FileOutputStream("config.properties"), null);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    public static String getScreenshotPath() {
        return getProperty("SCREENSHOT_PATH") + getTimestamp() + ".png";
    }
}
