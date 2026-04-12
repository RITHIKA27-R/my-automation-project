package com.smartschool.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.pages.LoginPage;
import com.smartschool.pages.AddSiblingPage;
import com.smartschool.utils.ExcelUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class AndroidAddSiblingTests {

    AndroidDriver driver;
    LoginPage loginPage;
    AddSiblingPage addSiblingPage;

    private String validSiblingId;
    private String validSiblingPass;
    private String wrongSiblingId;
    private String wrongPass;
    private String relationship;

    @BeforeClass
    public void setUp() throws MalformedURLException, InterruptedException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.vijayantech.smartschoolparent");
        options.setAppActivity("com.vijayantech.MainActivity");
        options.setAppWaitActivity("com.vijayantech.MainActivity");
        options.setAppWaitDuration(Duration.ofSeconds(60));
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        loginPage      = new LoginPage(driver);
        addSiblingPage = new AddSiblingPage(driver);

        Map<String, String> loginData  = ExcelUtils.getRowByTestCase("LoginData", "TC01_ValidLogin");
        Map<String, String> validRow   = ExcelUtils.getRowByTestCase("AddSiblingData", "TC05_ValidSuccess");
        Map<String, String> wrongIdRow = ExcelUtils.getRowByTestCase("AddSiblingData", "TC07_WrongID");
        Map<String, String> wrongPwRow = ExcelUtils.getRowByTestCase("AddSiblingData", "TC06_WrongPassword");

        validSiblingId   = validRow.get("SiblingID");
        validSiblingPass = validRow.get("Password");
        relationship     = validRow.get("Relationship");
        wrongSiblingId   = wrongIdRow.get("SiblingID");
        wrongPass        = wrongPwRow.get("Password");

        Thread.sleep(5000);
        loginPage.login(loginData.get("StudentID"), loginData.get("Password"));
        System.out.println("Login done: " + loginData.get("StudentID"));
        Thread.sleep(5000);

        // ✅ Login ஆனதும் ஒரே ஒரு முறை மட்டும் Add Sibling page-க்கு navigate பண்ணு
        addSiblingPage.openMenu();
        addSiblingPage.clickAddSiblingFromMenu();
        System.out.println("Navigated to Add Sibling page — staying here for all tests");
    }

    // ✅ back press இல்லாம — page-லயே இருந்து fields clear பண்ணி continue
    private void resetAddSiblingForm() throws InterruptedException {
        addSiblingPage.clearAllFields();
        Thread.sleep(500);
    }

    @Test(priority = 1, description = "TC__021 - Navigate to Add Sibling screen")
    public void tc01_navigateToAddSibling() {
        Assert.assertTrue(addSiblingPage.isAddSiblingPageDisplayed(),
            "Add Sibling page should be displayed");
        System.out.println("TC01 PASSED");
    }

    @Test(priority = 2, description = "TC__022 - Empty fields validation")
    public void tc02_emptyFields() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isValidationMessageDisplayed(),
            "Please fill all fields should appear");
        System.out.println("TC02 PASSED");
    }

    @Test(priority = 3, description = "TC__023 - Only Sibling ID")
    public void tc03_onlySiblingId() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(validSiblingId);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isValidationMessageDisplayed(),
            "Please fill all fields should appear");
        System.out.println("TC03 PASSED");
    }

    @Test(priority = 4, description = "TC__024 - ID and Password only")
    public void tc04_idAndPasswordOnly() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(validSiblingId);
        addSiblingPage.enterPassword(validSiblingPass);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isValidationMessageDisplayed(),
            "Please fill all fields should appear");
        System.out.println("TC04 PASSED");
    }

    @Test(priority = 5, description = "TC__025 - All valid - Success")
    public void tc05_addSiblingSuccess() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(validSiblingId);
        addSiblingPage.enterPassword(validSiblingPass);
        addSiblingPage.selectRelationship(relationship);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isSuccessMessageDisplayed(),
            "Sibling added successfully should appear");
        addSiblingPage.clickOkButton();
        System.out.println("TC05 PASSED");
    }

    @Test(priority = 6, description = "TC__026 - Valid ID wrong password")
    public void tc06_validIdWrongPassword() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(validSiblingId);
        addSiblingPage.enterPassword(wrongPass);
        addSiblingPage.selectRelationship(relationship);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isErrorMessageDisplayed(),
            "Try adding with correct details should appear");
        addSiblingPage.clickOkButton();
        System.out.println("TC06 PASSED");
    }

    @Test(priority = 7, description = "TC__027 - Wrong ID valid password")
    public void tc07_wrongIdValidPassword() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(wrongSiblingId);
        addSiblingPage.enterPassword(validSiblingPass);
        addSiblingPage.selectRelationship(relationship);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isErrorMessageDisplayed(),
            "Try adding with correct details should appear");
        addSiblingPage.clickOkButton();
        System.out.println("TC07 PASSED");
    }

    @Test(priority = 8, description = "TC__028 - Already added sibling",
          dependsOnMethods = "tc05_addSiblingSuccess")
    public void tc08_alreadyAddedSibling() throws InterruptedException {
        resetAddSiblingForm();
        addSiblingPage.enterSiblingId(validSiblingId);
        addSiblingPage.enterPassword(validSiblingPass);
        addSiblingPage.selectRelationship(relationship);
        addSiblingPage.clickAddSiblingButton();
        Assert.assertTrue(addSiblingPage.isErrorMessageDisplayed(),
            "Try adding with correct details should appear");
        addSiblingPage.clickOkButton();
        System.out.println("TC08 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed");
        }
    }
}