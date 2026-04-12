package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ✅ BACK BUTTON — commonly at top-left of feature pages
    // Try multiple approaches: system back, top-left navigation, custom back button
    public void goBackToHome() {
        try {
            // Method 1: Try custom back button (common in Android apps)
            try {
                By backButton = By.xpath(
                    "//android.widget.ImageButton[@content-desc='Navigate up'] | " +
                    "//android.widget.ImageView[@content-desc='Back'] | " +
                    "//android.view.View[@content-desc='Back'] | " +
                    "//android.widget.Button[@content-desc='Back']");
                
                wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
                System.out.println("✅ Back button clicked (custom)");
                Thread.sleep(1500);
                return;
            } catch (Exception e1) {
                System.out.println("⚠️ Custom back button not found, trying system back...");
            }

            // Method 2: Try system back press (Android native back button)
            try {
                if (driver instanceof AndroidDriver) {
                    ((AndroidDriver) driver).pressKey(new io.appium.java_client.android.nativekey.KeyEvent(io.appium.java_client.android.nativekey.AndroidKey.BACK));
                    System.out.println("✅ Android BACK key pressed");
                    Thread.sleep(1500);
                    return;
                }
            } catch (Exception e2) {
                System.out.println("⚠️ Android BACK key failed, trying navigate.back...");
            }

            // Method 3: Fallback - try page back
            try {
                driver.navigate().back();
                System.out.println("✅ Navigate back executed");
                Thread.sleep(1500);
                return;
            } catch (Exception e3) {
                System.out.println("❌ All back methods failed");
                throw e3;
            }

        } catch (Exception e) {
            System.out.println("❌ Go back failed: " + e.getMessage());
            throw new RuntimeException("Failed to go back to home screen");
        }
    }

    // ✅ Helper method to wait before going back
    protected void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {}
    }
}
