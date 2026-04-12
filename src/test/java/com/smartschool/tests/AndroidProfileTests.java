package com.smartschool.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.pages.LoginPage;
import com.smartschool.pages.ProfilePage;
import com.smartschool.utils.ExcelUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class AndroidProfileTests {

    AndroidDriver driver;
    LoginPage loginPage;
    ProfilePage profilePage;

    @BeforeClass
    public void setUp() throws MalformedURLException, InterruptedException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.vijayantech.smartschoolparent");
        options.setAppActivity("com.vijayantech.MainActivity");
        options.setAppWaitActivity("com.vijayantech.MainActivity");
        options.setAppWaitDuration(Duration.ofSeconds(30));
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage   = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // ✅ Excel-ல இருந்து credentials படிக்குது
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC01_ValidLogin");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        System.out.println("Login done: " + data.get("StudentID"));

        Thread.sleep(5000);
        profilePage.openMenu();
        profilePage.clickProfileInformation();
        System.out.println("Profile page opened");
    }

    @Test(priority = 1, description = "TC015 - Profile page displayed with student details")
    public void tc015_viewProfileInformation() {
        Assert.assertTrue(profilePage.isProfilePageDisplayed(),
            "Profile page should be displayed");
        Assert.assertTrue(profilePage.areProfileDetailsDisplayed(),
            "Profile details should be displayed");
        System.out.println("TC015 PASSED");
    }

    @Test(priority = 2, description = "TC016 - Edit icon opens gallery")
    public void tc016_editIconOpensGallery() {
        Assert.assertTrue(profilePage.isEditIconDisplayed(),
            "Edit icon should be visible");
        profilePage.clickEditIcon();
        boolean galleryOpen = profilePage.isGalleryPickerDisplayed();
        Assert.assertTrue(galleryOpen, "Gallery should open");
        profilePage.dismissGalleryPickerIfPresent();
        System.out.println("TC016 PASSED");
    }

    @Test(priority = 3, description = "TC017 - Delete icon shows confirmation dialog")
    public void tc017_deleteIconShowsConfirmation() {
        Assert.assertTrue(profilePage.isDeleteIconDisplayed(),
            "Delete icon should be visible");
        profilePage.clickDeleteIcon();
        Assert.assertTrue(profilePage.isConfirmationDialogDisplayed(),
            "Remove Photo dialog should appear");
        profilePage.clickCancelOnDialog();
        System.out.println("TC017 PASSED");
    }

    @Test(priority = 4, description = "TC018 - Cancel keeps photo")
    public void tc018_deletePhotoCancel() {
        profilePage.clickDeleteIcon();
        Assert.assertTrue(profilePage.isConfirmationDialogDisplayed(),
            "Dialog should appear");
        profilePage.clickCancelOnDialog();
        Assert.assertTrue(profilePage.isDeleteIconDisplayed(),
            "Delete icon should still be visible after Cancel");
        System.out.println("TC018 PASSED");
    }

    @Test(priority = 5, description = "TC019 - OK deletes photo")
    public void tc019_deletePhotoOk() {
        profilePage.clickDeleteIcon();
        Assert.assertTrue(profilePage.isConfirmationDialogDisplayed(),
            "Dialog should appear");
        profilePage.clickOkOnDialog();
        Assert.assertFalse(profilePage.isDeleteIconDisplayed(),
            "Delete icon should NOT be visible after deletion");
        System.out.println("TC019 PASSED");
    }

    @Test(priority = 6, description = "TC020 - User stays on Profile page after delete")
    public void tc020_staysOnProfilePage() {
        Assert.assertTrue(profilePage.isProfilePageDisplayed(),
            "User should remain on Profile page");
        Assert.assertTrue(profilePage.areProfileDetailsDisplayed(),
            "Profile details should still be visible");
        System.out.println("TC020 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed");
        }
    }
}