package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NotificationPage {

    AppiumDriver driver;
    WebDriverWait wait;

    // ─── Locators ────────────────────────────────────────────────────────────

    // ✅ FIXED — content-desc starts with 'Notification'
    private static final By NOTIFICATION_CARD = By.xpath(
        "//android.view.View[starts-with(@content-desc, 'Notification')]");

    // ✅ FIXED — Notifications page title
    private static final By NOTIFICATIONS_PAGE_TITLE = By.xpath(
        "//android.view.View[starts-with(@content-desc, 'Notifications')]");

    // ─── Constructor ─────────────────────────────────────────────────────────

    public NotificationPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    public void clickNotificationCard() {
        try { Thread.sleep(2000); } catch (Exception ignored) {}
        wait.until(ExpectedConditions.elementToBeClickable(NOTIFICATION_CARD)).click();
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public boolean isNotificationsPageDisplayed() {
        try {
            // Page load ஆக கொஞ்சம் wait
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            wait.until(ExpectedConditions.visibilityOfElementLocated(NOTIFICATIONS_PAGE_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}