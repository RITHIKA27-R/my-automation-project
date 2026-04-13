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

    // ─── Locators ─────────────────────────────────────────────────────────────

    private static final By CLASS_TEST_PAGE_TITLE = By.xpath(
        "//android.view.View[contains(@content-desc,'Class Test - Weekly Report')]");

    private static final By WEEKLY_REPORT_BUTTON = By.xpath(
        "//android.view.View[contains(@content-desc,'Weekly Report')]");

    // ✅ All date range rows (each accordion item)
    private static final By ALL_DATE_ROWS = By.xpath(
        "//android.view.View[contains(@content-desc,'to') and contains(@content-desc,'2025') or contains(@content-desc,'2026')]");

    // ✅ First date range row specifically
    private static final By FIRST_DATE_ROW = By.xpath(
        "(//android.view.View[contains(@content-desc,'to')])[1]");

    // ✅ Expanded detail table — Date, Subject, Scored, Max header
    private static final By EXPANDED_TABLE_HEADER = By.xpath(
        "//android.view.View[contains(@content-desc,'Date') and contains(@content-desc,'Subject')]");

    // ✅ Any visible marks row inside expanded dropdown
    private static final By MARKS_ROW = By.xpath(
        "//android.view.View[contains(@content-desc,'Tamil') or contains(@content-desc,'Maths') or contains(@content-desc,'Science')]");

    // ─── Constructor ─────────────────────────────────────────────────────────

    public ClassTestPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ─── Tap Helper ──────────────────────────────────────────────────────────

    private void tapAtCoordinate(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO,
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerMove(Duration.ofMillis(100),
            PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    // ─── Navigation ──────────────────────────────────────────────────────────

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

    // ─── Accordion: Expand ───────────────────────────────────────────────────

    /**
     * ✅ First date row-ஐ click பண்ணு → dropdown expand ஆகும்
     */
    public void expandFirstWeeklyReport() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        WebElement firstRow = wait.until(ExpectedConditions
            .elementToBeClickable(FIRST_DATE_ROW));
        firstRow.click();
        System.out.println("Clicked first date row → dropdown should expand");
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    /**
     * ✅ Dynamic: எந்த date range வேணும்னாலும் click பண்ணலாம்
     * Example: expandWeeklyReportByDate("12-04-2026")
     */
    public void expandWeeklyReportByDate(String dateText) {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        String xpath = String.format(
            "//android.view.View[contains(@content-desc,'%s')]", dateText);
        WebElement row = wait.until(ExpectedConditions
            .elementToBeClickable(By.xpath(xpath)));
        row.click();
        System.out.println("Expanded report for date: " + dateText);
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    // ─── Accordion: Collapse ─────────────────────────────────────────────────

    /**
     * ✅ Same row-ஐ மீண்டும் click → collapse ஆகும்
     */
    public void collapseFirstWeeklyReport() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        WebElement firstRow = wait.until(ExpectedConditions
            .elementToBeClickable(FIRST_DATE_ROW));
        firstRow.click();
        System.out.println("Clicked again → dropdown should collapse");
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    public void collapseWeeklyReportByDate(String dateText) {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        String xpath = String.format(
            "//android.view.View[contains(@content-desc,'%s')]", dateText);
        WebElement row = wait.until(ExpectedConditions
            .elementToBeClickable(By.xpath(xpath)));
        row.click();
        System.out.println("Collapsed report for date: " + dateText);
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public boolean isClassTestPageDisplayed() {
        try {
            try { Thread.sleep(2000); } catch (Exception ignored) {}
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(CLASS_TEST_PAGE_TITLE));
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isWeeklyReportDisplayed() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(WEEKLY_REPORT_BUTTON));
            return true;
        } catch (Exception e) { return false; }
    }

    /**
     * ✅ Dropdown expand ஆச்சான்னு check — table header தெரியணும்
     */
    public boolean isDropdownExpanded() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(EXPANDED_TABLE_HEADER));
            System.out.println("Dropdown IS expanded - table header visible");
            return true;
        } catch (Exception e) {
            System.out.println("Dropdown NOT expanded");
            return false;
        }
    }

    /**
     * ✅ Dropdown collapse ஆச்சான்னு check — marks row disappear ஆகணும்
     */
    public boolean isDropdownCollapsed() {
        try {
            wait.until(ExpectedConditions
                .invisibilityOfElementLocated(MARKS_ROW));
            System.out.println("Dropdown IS collapsed - marks not visible");
            return true;
        } catch (Exception e) {
            System.out.println("Dropdown still visible");
            return false;
        }
    }

    /**
     * ✅ Expanded ஆன marks text எடு
     * Example: getMarksText("Tamil") → "50"
     */
    public String getMarksText(String subjectName) {
        String xpath = String.format(
            "//android.view.View[contains(@content-desc,'%s')]", subjectName);
        try {
            WebElement el = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(xpath)));
            String fullText = el.getAttribute("content-desc");
            System.out.println("Marks content-desc: " + fullText);
            return fullText;
        } catch (Exception e) {
            return "NOT_FOUND";
        }
    }
}