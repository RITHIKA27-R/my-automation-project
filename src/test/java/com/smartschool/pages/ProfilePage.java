package com.smartschool.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {

    AppiumDriver driver;
    WebDriverWait wait;

    // ✅ Confirmed locators
    private static final By MENU_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='Open navigation menu']");

    private static final By PROFILE_OPTION = By.xpath(
        "//android.view.View[@content-desc='Profile Information']");

    private static final By PROFILE_TITLE = By.xpath(
        "//android.view.View[@content-desc='Profile']");

    // ✅ Edit = Button instance(1), Delete = Button instance(2)
    private static final By EDIT_BUTTON =
        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)");

    private static final By DELETE_BUTTON =
        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(2)");

    // ✅ Dialog locators confirmed
    private static final By REMOVE_PHOTO_TITLE = By.xpath(
        "//android.view.View[@content-desc='Remove Photo']");

    private static final By OK_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='OK']");

    private static final By CANCEL_BUTTON = By.xpath(
        "//android.widget.Button[@content-desc='Cancel']");

    private static final By GALLERY_PICKER = By.xpath(
        "//android.view.View[@content-desc='Photos']");

    private static final By DISMISS_BUTTON = By.xpath(
        "//android.view.View[@content-desc='Dismiss']");

    public ProfilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
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

    public void clickProfileInformation() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(PROFILE_OPTION)).click();
            System.out.println("Profile Information clicked");
        } catch (Exception e) {
            System.out.println("Profile option failed: " + e.getMessage());
        }
        waitFor(3000);
    }

    public void clickEditIcon() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(EDIT_BUTTON)).click();
            System.out.println("Edit button clicked");
        } catch (Exception e) {
            System.out.println("Edit button failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    public void clickDeleteIcon() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON)).click();
            System.out.println("Delete button clicked");
        } catch (Exception e) {
            System.out.println("Delete button failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    public void clickOkOnDialog() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
            System.out.println("OK clicked");
        } catch (Exception e) {
            System.out.println("OK failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    public void clickCancelOnDialog() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(CANCEL_BUTTON)).click();
            System.out.println("Cancel clicked");
        } catch (Exception e) {
            System.out.println("Cancel failed: " + e.getMessage());
        }
        waitFor(2000);
    }

    public void dismissGalleryPickerIfPresent() {
        try {
            waitFor(2000);
            wait.until(ExpectedConditions.elementToBeClickable(DISMISS_BUTTON)).click();
            System.out.println("Gallery dismissed");
        } catch (Exception e) {
            System.out.println("No gallery picker");
        }
        waitFor(1500);
    }

    public boolean isProfilePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_TITLE));
            System.out.println("Profile page displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Profile page not found");
            return false;
        }
    }

    public boolean isEditIconDisplayed() {
        try {
            return driver.findElement(EDIT_BUTTON).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteIconDisplayed() {
        try {
            return driver.findElement(DELETE_BUTTON).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGalleryPickerDisplayed() {
        try {
            waitFor(2000);
            return driver.findElement(GALLERY_PICKER).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConfirmationDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(REMOVE_PHOTO_TITLE));
            System.out.println("Remove Photo dialog displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Dialog not found");
            return false;
        }
    }

    public boolean areProfileDetailsDisplayed() {
        try {
            waitFor(2000);
            List<WebElement> elements = driver.findElements(
                By.xpath("//android.view.View[@content-desc]"));
            System.out.println("Profile elements count: " + elements.size());
            return elements.size() > 3;
        } catch (Exception e) {
            return false;
        }
    }
}