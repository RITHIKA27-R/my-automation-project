package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public class ClassTestPage {

    AppiumDriver driver;
    WebDriverWait wait;

    private static final By CLASS_TEST_PAGE_TITLE = By.xpath(
        "//android.view.View[contains(@content-desc,'Class Test - Weekly Report')]");

    private static final By WEEKLY_REPORT_BUTTON = By.xpath(
        "//android.view.View[contains(@content-desc,'Weekly Report')]" );

    public ClassTestPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private void tapAtCoordinate(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO,
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(
            PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerMove(Duration.ofMillis(100),
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerUp(
            PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    public void clickClassTestCard() {
        try { Thread.sleep(2000); } catch (Exception ignored) {}
        try {
            WebElement classTestCard = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(
                    "//android.view.View[starts-with(@content-desc,'Class Test')]")));
            classTestCard.click();
        } catch (Exception e) {
            tapAtCoordinate(270, 1400);
        }
        try { Thread.sleep(2000); } catch (Exception ignored) {}
    }

    public boolean isClassTestPageDisplayed() {
        try {
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(CLASS_TEST_PAGE_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWeeklyReportDisplayed() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(WEEKLY_REPORT_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}