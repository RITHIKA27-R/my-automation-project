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

public class TransportPage {

    AppiumDriver driver;
    WebDriverWait wait;

    private static final By TRANSPORT_PAGE_TITLE = By.xpath(
        "//android.view.View[contains(@content-desc,'Transport')]");

    private static final By BUS_DETAILS = By.xpath(
        "//android.view.View[contains(@content-desc,'Bus No')]");

    public TransportPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
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

    public void clickTransportCard() {
        try { Thread.sleep(2000); } catch (Exception ignored) {}
        try {
            WebElement transportCard = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(
                    "//android.view.View[starts-with(@content-desc,'Transport')]")));
            transportCard.click();
        } catch (Exception e) {
            tapAtCoordinate(810, 1400);
        }
        try { Thread.sleep(3000); } catch (Exception ignored) {}
    }

    public boolean isTransportPageDisplayed() {
        try {
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(TRANSPORT_PAGE_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBusDetailsDisplayed() {
        try {
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            WebElement busElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(BUS_DETAILS));
            String busDesc = busElement.getAttribute("content-desc");
            return busDesc != null && busDesc.contains("Bus No");
        } catch (Exception e) {
            return false;
        }
    }

    public String getBusNumber() {
        try {
            WebElement busElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(BUS_DETAILS));
            return busElement.getAttribute("content-desc");
        } catch (Exception e) {
            return "Not Found";
        }
    }
}