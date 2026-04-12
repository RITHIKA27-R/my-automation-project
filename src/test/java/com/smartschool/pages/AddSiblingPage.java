package com.smartschool.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddSiblingPage {

    AndroidDriver driver;
    WebDriverWait wait;

    // ✅ Confirmed locators
    private static final By MENU_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='Open navigation menu']");

    private static final By ADD_SIBLING_MENU_ITEM = By.xpath(
        "//android.view.View[@content-desc='Add Sibling']");

    // ✅ UiAutomator — XPath hierarchy தேவையில்ல
    private static final By SIBLING_ID_FIELD =
        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

    private static final By PASSWORD_FIELD =
        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

    private static final By RELATIONSHIP_DROPDOWN = By.xpath(
        "//android.widget.Button[@content-desc='Select Relationship']");

    private static final By ADD_SIBLING_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='Add Sibling']");

    private static final By VALIDATION_MESSAGE = By.xpath(
        "//android.view.View[@content-desc='Please fill all fields']");

    private static final By ERROR_MESSAGE = By.xpath(
        "//android.view.View[contains(@content-desc,'Try adding with correct details')]");

    private static final By SUCCESS_MESSAGE = By.xpath(
        "//android.view.View[contains(@content-desc,'Sibling added successfully')]");

    private static final By OK_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='OK']");

    public AddSiblingPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void waitFor(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    public void openMenu() {
        waitFor(2000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(MENU_BUTTON)).click();
            System.out.println("Menu opened");
        } catch (Exception e) {
            System.out.println("Menu open failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    // ✅ elementToBeClickable use பண்றோம் — menu item visible + clickable ஆனதும் click
    public void clickAddSiblingFromMenu() {
        try {
            WebElement item = wait.until(
                ExpectedConditions.elementToBeClickable(ADD_SIBLING_MENU_ITEM));
            item.click();
            System.out.println("Add Sibling menu item clicked");
        } catch (Exception e) {
            System.out.println("Add Sibling click failed: " + e.getMessage());
        }
        waitFor(5000);
    }

    public void clearAllFields() {
        try { driver.findElement(SIBLING_ID_FIELD).clear(); } catch (Exception ignored) {}
        try { driver.findElement(PASSWORD_FIELD).clear(); } catch (Exception ignored) {}
        waitFor(500);
    }

    public void enterSiblingId(String siblingId) {
        try {
            WebElement f = wait.until(ExpectedConditions.visibilityOfElementLocated(SIBLING_ID_FIELD));
            f.clear();
            f.sendKeys(siblingId);
            System.out.println("Sibling ID: " + siblingId);
        } catch (Exception e) {
            System.out.println("Sibling ID failed: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            WebElement f = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
            f.clear();
            f.sendKeys(password);
            System.out.println("Password entered");
        } catch (Exception e) {
            System.out.println("Password failed: " + e.getMessage());
        }
    }

    public void selectRelationship(String relationship) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(RELATIONSHIP_DROPDOWN)).click();
            waitFor(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='" + relationship + "']"))).click();
            System.out.println("Relationship: " + relationship);
        } catch (Exception e) {
            System.out.println("Relationship failed: " + e.getMessage());
        }
        waitFor(500);
    }

    public void clickAddSiblingButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ADD_SIBLING_BUTTON)).click();
            System.out.println("Add Sibling button clicked");
        } catch (Exception e) {
            System.out.println("Add Sibling button failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    public void clickOkButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
            System.out.println("OK clicked");
        } catch (Exception e) {
            System.out.println("OK failed: " + e.getMessage());
        }
        waitFor(1000);
    }

    public boolean isAddSiblingPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SIBLING_ID_FIELD));
            System.out.println("Add Sibling page displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Add Sibling page not found");
            return false;
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(VALIDATION_MESSAGE));
            System.out.println("Validation: Please fill all fields");
            return true;
        } catch (Exception e) {
            System.out.println("Validation not found");
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            System.out.println("Error message shown");
            return true;
        } catch (Exception e) {
            System.out.println("Error message not found");
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
            System.out.println("Success message shown");
            return true;
        } catch (Exception e) {
            System.out.println("Success not found");
            return false;
        }
    }
}