package com.smartschool.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    AndroidDriver driver;
    WebDriverWait wait;

    // ✅ CORRECT XPATH — verified from UI dump (no View[3] index)
    private static final By STUDENT_ID_FIELD = By.xpath(
        "//android.widget.FrameLayout[@resource-id='android:id/content']//android.widget.EditText[1]");

    private static final By PASSWORD_FIELD = By.xpath(
        "//android.widget.FrameLayout[@resource-id='android:id/content']//android.widget.EditText[2]");

    private static final By LOGIN_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='Login']");

    private static final By ALERT_MESSAGE = By.xpath(
        "//android.view.View[@content-desc='Incorrect Credentials']");

    private static final By ALERT_OK_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='OK']");

    private static final By STUDENT_ID_ERROR = By.xpath(
        "//android.view.View[@content-desc='Enter StudentId']");

    private static final By PASSWORD_ERROR = By.xpath(
        "//android.view.View[@content-desc='Enter Password']");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterStudentId(String id) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(STUDENT_ID_FIELD));
        field.click();
        field.clear();
        field.sendKeys(id);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        field.click();
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    public void login(String id, String password) {
        enterStudentId(id);
        enterPassword(password);
        clickLogin();
    }

    public void clickAlertOk() {
        wait.until(ExpectedConditions.elementToBeClickable(ALERT_OK_BUTTON)).click();
    }

    public void clickAlertOkIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(ALERT_OK_BUTTON)).click();
        } catch (Exception ignored) {}
    }

    public boolean isAlertDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(ALERT_MESSAGE));
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isStudentIdErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(STUDENT_ID_ERROR));
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isPasswordErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR));
            return true;
        } catch (Exception e) { return false; }
    }

    public String getStudentIdErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(STUDENT_ID_ERROR))
                   .getAttribute("content-desc");
    }

    public String getPasswordErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR))
                   .getAttribute("content-desc");
    }

    public void clearStudentIdField() {
        try {
            WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(STUDENT_ID_FIELD));
            field.click();
            field.clear();
        } catch (Exception e) {
            System.out.println("Clear StudentId failed: " + e.getMessage());
        }
    }

    public void clearPasswordField() {
        try {
            WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
            field.click();
            field.clear();
        } catch (Exception e) {
            System.out.println("Clear Password failed: " + e.getMessage());
        }
    }
}