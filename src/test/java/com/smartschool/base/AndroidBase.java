package com.smartschool.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.smartschool.pages.LoginPage;
import com.smartschool.utils.ExcelUtils;

import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class AndroidBase {

    public static AndroidDriver driver;
    public static LoginPage loginPage;
    private static boolean driverInitialized = false;

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        // Driver already created by LoginTests? If not, create it.
        if (driver == null) {
            System.out.println("Driver not found – initializing for standalone execution");
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
            driverInitialized = true;

            // Perform login using valid credentials from Excel
            loginPage = new LoginPage(driver);
            Map<String, String> data = ExcelUtils.getRowByTestCase("LoginData", "TC01_ValidLogin");
            loginPage.login(data.get("StudentID"), data.get("Password"));
            System.out.println("Login Successful for standalone suite");
            Thread.sleep(3000); // wait for home page
        } else {
            System.out.println("Driver already exists – reusing for feature tests");
        }

        // Ensure we are on home page before feature tests start
        By homeIndicator = By.xpath("//android.widget.Button[@content-desc='Open navigation menu']");
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.visibilityOfElementLocated(homeIndicator));
        System.out.println("Home page ready — proceeding with tests");
    }

    public void goBack() {
        try {
            driver.navigate().back();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        System.out.println("Navigated back");
    }

    public void goToHome() {
        for (int i = 0; i < 3; i++) {
            try {
                driver.navigate().back();
                Thread.sleep(800);
            } catch (Exception ignored) {}
        }
        System.out.println("Navigated to Home");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null && driverInitialized) {
            driver.quit();
            System.out.println("Driver closed — Suite finished");
        } else if (driver != null) {
            // Driver created by LoginTests – do not quit here because LoginTests may need it?
            // Actually after full suite, we should quit. Let's quit only if we created it.
            // For full suite, LoginTests doesn't quit, so we quit here.
            driver.quit();
            System.out.println("Driver closed (created by LoginTests)");
        }
    }
}