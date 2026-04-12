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

        // ✅ AndroidBase.driver-ல assign பண்றோம் — feature tests share பண்ணும்
        AndroidBase.driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        AndroidBase.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage = new LoginPage(AndroidBase.driver);
        AndroidBase.loginPage = loginPage;

        Thread.sleep(5000);
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
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Reset failed: " + e.getMessage());
        }
    }

    @Test(priority = 1, description = "TC06 - Empty login - Both errors")
    public void tc06_emptyLogin() {
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed(), "Student ID error should show");
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password error should show");
        Assert.assertEquals(loginPage.getStudentIdErrorText(), "Enter StudentId");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Enter Password");
        System.out.println("TC06 PASSED");
    }

    @Test(priority = 2, description = "TC04 - Only Student ID")
    public void tc04_onlyStudentId() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC04_OnlyStudentID");
        loginPage.enterStudentId(data.get("StudentID"));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password error should show");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Enter Password");
        System.out.println("TC04 PASSED");
    }

    @Test(priority = 3, description = "TC05 - Only Password")
    public void tc05_onlyPassword() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC05_OnlyPassword");
        loginPage.enterPassword(data.get("Password"));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isStudentIdErrorDisplayed(), "StudentId error should show");
        Assert.assertEquals(loginPage.getStudentIdErrorText(), "Enter StudentId");
        System.out.println("TC05 PASSED");
    }

    @Test(priority = 4, description = "TC02 - Invalid Student ID")
    public void tc02_invalidStudentId() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC02_WrongID");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        Assert.assertTrue(loginPage.isAlertDisplayed(), "Alert should appear");
        loginPage.clickAlertOk();
        System.out.println("TC02 PASSED");
    }

    @Test(priority = 5, description = "TC03 - Wrong Password")
    public void tc03_wrongPassword() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC03_WrongPassword");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        Assert.assertTrue(loginPage.isAlertDisplayed(), "Alert should appear");
        loginPage.clickAlertOk();
        System.out.println("TC03 PASSED");
    }

    @Test(priority = 6, description = "TC01 - Valid login - Dashboard")
    public void tc01_validLogin() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC01_ValidLogin");
        loginPage.login(data.get("StudentID"), data.get("Password"));
        System.out.println("TC01 PASSED - Login Successful — home page ready");
        try { Thread.sleep(4000); } catch (Exception ignored) {}
    }

    // ✅ driver.quit() இல்ல — feature tests-க்கு same driver தேவை
    // AndroidBase @AfterSuite-ல quit ஆகும்
}