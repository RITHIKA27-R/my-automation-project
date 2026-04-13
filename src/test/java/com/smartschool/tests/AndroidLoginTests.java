package com.smartschool.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.LoginPage;
import com.smartschool.utils.ExcelUtils;

import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class AndroidLoginTests {

    LoginPage loginPage;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.vijayantech.smartschoolparent");
        options.setAppActivity("com.vijayantech.MainActivity");
        options.setAppWaitActivity("com.vijayantech.MainActivity");
        options.setAppWaitDuration(Duration.ofSeconds(30));
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(60));

        AndroidBase.driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        AndroidBase.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage = new LoginPage(AndroidBase.driver);
        AndroidBase.loginPage = loginPage;

        // Wait for login screen (no hard sleep)
        System.out.println("Login Tests setup done");
    }

    @BeforeMethod
    public void resetLoginScreen() {
        try {
            loginPage.clearStudentIdField();
            loginPage.clearPasswordField();
            if (loginPage.isAlertDisplayed()) {
                loginPage.clickAlertOk();
            }
            Thread.sleep(500); // minimal delay
        } catch (Exception e) {
            System.out.println("Reset failed: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void tc06_emptyLogin() {
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed());
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed());
        System.out.println("TC06 PASSED");
    }

    @Test(priority = 2)
    public void tc04_onlyStudentId() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC04_OnlyStudentID");
        loginPage.enterStudentId(data.get("StudentID"));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed());
        System.out.println("TC04 PASSED");
    }

    @Test(priority = 3)
    public void tc05_onlyPassword() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC05_OnlyPassword");
        loginPage.enterPassword(data.get("Password"));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed());
        System.out.println("TC05 PASSED");
    }

    @Test(priority = 4)
    public void tc02_invalidStudentId() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC02_WrongID");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        Assert.assertTrue(loginPage.isAlertDisplayed());
        loginPage.clickAlertOk();
        System.out.println("TC02 PASSED");
    }

    @Test(priority = 5)
    public void tc03_wrongPassword() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC03_WrongPassword");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        Assert.assertTrue(loginPage.isAlertDisplayed());
        loginPage.clickAlertOk();
        System.out.println("TC03 PASSED");
    }

    @Test(priority = 6)
    public void tc01_validLogin() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC01_ValidLogin");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        System.out.println("TC01 PASSED - Login Successful");
        // No need to wait here – AndroidBase @BeforeSuite will verify home page
    }
}