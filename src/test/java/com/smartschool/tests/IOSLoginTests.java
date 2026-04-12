package com.smartschool.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.pages.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class IOSLoginTests{

    AndroidDriver driver;
    LoginPage loginPage;

    private static final String VALID_ID       = "MYST00590";
    private static final String VALID_PASSWORD = "12012009";
    private static final String WRONG_ID       = "wrongID123";
    private static final String WRONG_PASSWORD = "wrongPass123";

    // ─── Setup ───────────────────────────────────────────────────────────────

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.vijayantech.smartschoolparent");
        options.setAppActivity("com.vijayantech.MainActivity");
        options.setAppWaitActivity("com.vijayantech.MainActivity");
        options.setAppWaitDuration(Duration.ofSeconds(30));
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(60));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage = new LoginPage(driver); // AndroidDriver → AppiumDriver auto cast
    }

    // ─── TC01: Valid Student ID & Password ───────────────────────────────────

    @Test(priority = 1, description = "Valid Student ID & Password - Login should succeed")
    public void tc01_validLogin() {
        loginPage.login(VALID_ID, VALID_PASSWORD);
        System.out.println("TC01 PASSED - Login Successful, Navigated to Dashboard");
    }

    // ─── TC02: Wrong Student ID + Correct Password ───────────────────────────

    @Test(priority = 2, description = "Invalid Student ID with correct password - Alert should appear")
    public void tc02_invalidStudentId_validPassword() {
        loginPage.login(WRONG_ID, VALID_PASSWORD);

        boolean alertShown = loginPage.isAlertDisplayed();
        System.out.println("TC02 - Alert Displayed: " + alertShown);

        Assert.assertTrue(alertShown, "Alert popup should be displayed");
        loginPage.clickAlertOk();
        System.out.println("TC02 PASSED - Alert shown for invalid Student ID");
    }

    // ─── TC03: Correct Student ID + Wrong Password ───────────────────────────

    @Test(priority = 3, description = "Valid Student ID with wrong password - Alert should appear")
    public void tc03_validStudentId_invalidPassword() {
        loginPage.login(VALID_ID, WRONG_PASSWORD);

        boolean alertShown = loginPage.isAlertDisplayed();
        System.out.println("TC03 - Alert Displayed: " + alertShown);

        Assert.assertTrue(alertShown, "Alert popup should be displayed");
        loginPage.clickAlertOk();
        System.out.println("TC03 PASSED - Alert shown for wrong password");
    }

    // ─── TC04: Only Student ID entered ───────────────────────────────────────

    @Test(priority = 4, description = "Only Student ID entered - Password error should show")
    public void tc04_onlyStudentId() {
        loginPage.enterStudentId(VALID_ID);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(),
                "Password error message should be displayed");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Enter Password",
                "Error should say 'Enter Password'");

        System.out.println("TC04 PASSED - 'Enter Password' error shown correctly");
    }

    // ─── TC05: Only Password entered ─────────────────────────────────────────

    @Test(priority = 5, description = "Only Password entered - StudentId error should show")
    public void tc05_onlyPassword() {
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed(),
                "Student ID error message should be displayed");
        Assert.assertEquals(loginPage.getStudentIdErrorText(), "Enter StudentId",
                "Error should say 'Enter StudentId'");

        System.out.println("TC05 PASSED - 'Enter StudentId' error shown correctly");
    }

    // ─── TC06: Nothing entered - Click Login ─────────────────────────────────

    @Test(priority = 6, description = "No fields filled - Both errors should show")
    public void tc06_emptyLogin() {
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed(),
                "Student ID error should be displayed");
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(),
                "Password error should be displayed");
        Assert.assertEquals(loginPage.getStudentIdErrorText(), "Enter StudentId",
                "Error should say 'Enter StudentId'");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Enter Password",
                "Error should say 'Enter Password'");

        System.out.println("TC06 PASSED - Both error messages shown correctly");
    }

    // ─── Teardown ─────────────────────────────────────────────────────────────

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}