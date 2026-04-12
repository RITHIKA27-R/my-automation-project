package com.smartschool.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for taking screenshots during test execution
 */
public class ScreenshotUtils {
    
    /**
     * Take screenshot of the current screen
     */
    public static String takeScreenshot(AppiumDriver driver, String testName) {
        try {
            String screenshotPath = ConfigUtils.getProperty("SCREENSHOT_PATH");
            Files.createDirectories(Paths.get(screenshotPath));
            
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destination = screenshotPath + testName + "_" + ConfigUtils.getTimestamp() + ".png";
            Files.copy(source.toPath(), Paths.get(destination));
            
            System.out.println("Screenshot saved: " + destination);
            return destination;
            
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
}
