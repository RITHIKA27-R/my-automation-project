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

public class HomeworkPage {

    AppiumDriver driver;
    WebDriverWait wait;

    // ── Homework page title ───────────────────────────────────────────────────
    private static final By HOMEWORK_PAGE_TITLE = By.xpath(
        "//android.view.View[@content-desc='Homework']");

    // ── Homework card on Home screen ──────────────────────────────────────────
    private static final By HOMEWORK_CARD = By.xpath(
        "//android.view.View[starts-with(@content-desc,'Homework')]");

    // ── No homework message ───────────────────────────────────────────────────
    private static final By NO_HOMEWORK_MSG = By.xpath(
        "//android.view.View[contains(@content-desc,'No') or " +
        "contains(@content-desc,'no homework') or " +
        "contains(@content-desc,'No Homework')]");

    public HomeworkPage(AppiumDriver driver) {
        this.driver = driver;
        // Override wait timeout for homework page (30 seconds instead of default 20)
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    private void scrollDown() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);
        scroll.addAction(finger.createPointerMove(Duration.ZERO,
            PointerInput.Origin.viewport(), 540, 1400));
        scroll.addAction(finger.createPointerDown(
            PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(800),
            PointerInput.Origin.viewport(), 540, 600));
        scroll.addAction(finger.createPointerUp(
            PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(scroll));
        sleep(1000);
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

    // ── Click Homework Card ───────────────────────────────────────────────────
    public void clickHomeworkCard() {
        sleep(2000);

        // Method 1: XPath — starts-with Homework
        try {
            WebElement card = wait.until(
                ExpectedConditions.elementToBeClickable(HOMEWORK_CARD));
            card.click();
            System.out.println("Homework card clicked via XPath: " +
                card.getAttribute("content-desc"));
            return;
        } catch (Exception e) {
            System.out.println("XPath not found, trying after scroll...");
        }

        // Method 2: Scroll then XPath
        scrollDown();
        try {
            WebElement card = wait.until(
                ExpectedConditions.elementToBeClickable(HOMEWORK_CARD));
            card.click();
            System.out.println("Homework card clicked after scroll");
            return;
        } catch (Exception e) {
            System.out.println("Still not found, trying list scan...");
        }

        // Method 3: Scan all views for "Homework"
        try {
            List<WebElement> allViews = driver.findElements(
                By.xpath("//android.view.View[@content-desc]"));
            for (WebElement view : allViews) {
                String desc = view.getAttribute("content-desc");
                if (desc != null && desc.toLowerCase().contains("homework")) {
                    view.click();
                    System.out.println("Homework card clicked via scan: " + desc);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("List scan failed: " + e.getMessage());
        }

        // Method 4: Fallback coordinate tap
        tapAtCoordinate(270, 1200);
        System.out.println("Homework card tapped via coordinate fallback");
    }

    // ── Is Homework Page Displayed ────────────────────────────────────────────
    public boolean isHomeworkPageDisplayed() {
        try {
            sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(HOMEWORK_PAGE_TITLE));
            System.out.println("Homework page title found");
            return true;
        } catch (Exception e) {
            System.out.println("Homework page title not found: " + e.getMessage());
            return false;
        }
    }

    // ── Is Homework Data Displayed (dynamic) ──────────────────────────────────
    // Admin என்ன add பண்ணாலும் — elements இருந்தா true
    public boolean isHomeworkDataDisplayed() {
        sleep(2000);
        try {
            List<WebElement> items = driver.findElements(
                By.xpath("//android.view.View[@content-desc and string-length(@content-desc) > 3]"));

            int count = 0;
            for (WebElement item : items) {
                String desc = item.getAttribute("content-desc");
                if (desc != null && !desc.equals("Homework") && !desc.isEmpty()) {
                    count++;
                    System.out.println("Homework item found: " + desc);
                }
            }
            System.out.println("Total homework items: " + count);
            return count > 0;
        } catch (Exception e) {
            System.out.println("Error checking homework data: " + e.getMessage());
            return false;
        }
    }

    // ── Get Homework Count (dynamic) ──────────────────────────────────────────
    public int getHomeworkCount() {
        sleep(1000);
        try {
            List<WebElement> items = driver.findElements(
                By.xpath("//android.view.View[@content-desc and string-length(@content-desc) > 3]"));
            int count = 0;
            for (WebElement item : items) {
                String desc = item.getAttribute("content-desc");
                if (desc != null && !desc.equals("Homework") && !desc.isEmpty()) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    // ── Is No Homework ────────────────────────────────────────────────────────
    public boolean isNoHomeworkDisplayed() {
        try {
            return driver.findElement(NO_HOMEWORK_MSG).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}