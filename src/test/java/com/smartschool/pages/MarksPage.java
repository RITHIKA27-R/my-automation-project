package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MarksPage {

    AppiumDriver driver;
    WebDriverWait wait;

    // ✅ Confirmed from Appium Inspector
    private static final By MARKS_PAGE_TITLE = By.xpath(
        "//android.view.View[@content-desc='Marks Scored']");

    private static final By NO_RECORDS = By.xpath(
        "//android.view.View[contains(@content-desc,'No Records Found')]");

    // ✅ Dropdown button = android.widget.Button inside Select Exam parent
    // In closed state: shows currently selected exam name (e.g. "Term1", "Half Yearly")
    // In open state: all exam options appear as android.widget.Button list
    private static final By DROPDOWN_BUTTON = By.xpath(
        "//android.view.View[@content-desc='Select Exam']/../android.widget.Button");

    public MarksPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void clickMarksCard() {
        sleep(2000);
        try {
            By marksCard = By.xpath("//android.view.View[contains(@content-desc,'Marks')]");
            wait.until(ExpectedConditions.elementToBeClickable(marksCard)).click();
        } catch (Exception e) {
            tapAt(810, 1200);
        }
        sleep(2000);
    }

    public boolean isMarksPageDisplayed() {
        sleep(2000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(MARKS_PAGE_TITLE));
            System.out.println("✅ Marks Scored page displayed");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Marks page not found: " + e.getMessage());
            return false;
        }
    }

    // ─── Dropdown select — confirmed flow from Inspector ─────────────────────
    // Closed: one Button showing current exam (e.g. "Term1")
    // Click it → list opens: multiple Buttons (1st Mid Term, Term1, Half Yearly, Annual...)
    // Click target → list closes, page shows data or "No Records Found"
    public void selectExamFromDropdown(String examName) {
        sleep(1000);
        System.out.println("📋 Opening exam dropdown...");

        // ── Open dropdown ────────────────────────────────────────────────────
        // The button in closed state has content-desc = currently selected exam
        // We just find the Button child of Select Exam's parent
        boolean opened = false;

        // Primary: Button sibling to "Select Exam" label
        String[] dropdownXpaths = {
            "//android.view.View[@content-desc='Select Exam']/../android.widget.Button",
            "//android.view.View[@content-desc='Select Exam']/following-sibling::android.widget.Button",
            "//android.widget.Button[contains(@content-desc,'Term') or contains(@content-desc,'Mid') " +
            "or contains(@content-desc,'Annual') or contains(@content-desc,'Half') " +
            "or contains(@content-desc,'Exam') or contains(@content-desc,'exam') " +
            "or contains(@content-desc,'part') or contains(@content-desc,'Aug')]"
        };

        for (String xpath : dropdownXpaths) {
            try {
                WebElement btn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                System.out.println("   Found dropdown button: " + btn.getAttribute("content-desc"));
                btn.click();
                opened = true;
                break;
            } catch (Exception ignored) {}
        }

        if (!opened) throw new RuntimeException("Cannot open dropdown");
        sleep(1500);
        System.out.println("✅ Dropdown open — looking for: " + examName);

        // ── Find & click the target exam option ──────────────────────────────
        // ✅ Confirmed: options are android.widget.Button with exact content-desc
        By targetLocator = By.xpath(
            "//android.widget.Button[@content-desc='" + examName + "']");

        WebElement option = null;
        for (int i = 0; i < 5; i++) {
            List<WebElement> els = driver.findElements(targetLocator);
            if (!els.isEmpty()) {
                option = els.get(0);
                System.out.println("   ✅ Found '" + examName + "' on attempt " + (i + 1));
                break;
            }
            System.out.println("   Scrolling to find '" + examName + "'... attempt " + (i + 1));
            scrollDropdown();
            sleep(600);
        }

        if (option == null) throw new RuntimeException(
            "Option '" + examName + "' not found in dropdown");

        option.click();
        System.out.println("✅ Selected: " + examName);
        sleep(2500); // wait for page to reload with new exam data
    }

    public boolean isNoRecordsDisplayed() {
        sleep(2000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(NO_RECORDS));
            System.out.println("✅ No Records Found is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("❌ No Records Found NOT shown: " + e.getMessage());
            return false;
        }
    }

    private void scrollDropdown() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO,
            PointerInput.Origin.viewport(), 550, 650));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500),
            PointerInput.Origin.viewport(), 550, 380));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    private void tapAt(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO,
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerMove(Duration.ofMillis(80),
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }
}