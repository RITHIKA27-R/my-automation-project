package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AttendancePage {

    AppiumDriver driver;
    WebDriverWait wait;

    // ─── Locators ────────────────────────────────────────────────────────────

    private static final By ATTENDANCE_CARD = By.xpath(
        "//android.view.View[starts-with(@content-desc, 'Attendance')]");

    private static final By ATTENDANCE_PAGE_TITLE = By.xpath(
        "//android.view.View[starts-with(@content-desc, 'Attendance')]");

    // ─── Constructor ─────────────────────────────────────────────────────────

    public AttendancePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    public void clickAttendanceCard() {
        try { Thread.sleep(2000); } catch (Exception ignored) {}
        wait.until(ExpectedConditions.elementToBeClickable(ATTENDANCE_CARD)).click();
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public boolean isAttendancePageDisplayed() {
        try {
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            wait.until(ExpectedConditions.visibilityOfElementLocated(ATTENDANCE_PAGE_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}