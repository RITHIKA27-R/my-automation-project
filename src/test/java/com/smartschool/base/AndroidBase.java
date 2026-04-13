package com.smartschool.base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.smartschool.pages.LoginPage;

import java.time.Duration;

public class AndroidBase {

    public static AndroidDriver driver;
    public static LoginPage loginPage;

    // ✅ @BeforeSuite — driver LoginTests-ல already create ஆச்சு
    // home page-ல இருக்கான்னு மட்டும் verify பண்றோம்
    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        // driver LoginTests @BeforeClass-ல already set ஆகிவிடும்
        // testng.xml-ல Login Tests முதல்ல run ஆகுது
        // அதனால driver இங்க ready-ஆ இருக்கும்

        // Home page load ஆக wait
        Thread.sleep(3000);

        if (driver != null) {
            By homeIndicator = By.xpath(
                "//android.widget.Button[@content-desc='Open navigation menu']");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(homeIndicator));
                System.out.println("Home page ready — feature tests starting");
            } catch (Exception e) {
                System.out.println("Warning: Home page not detected — " + e.getMessage());
            }
        }
    }

    public void goBack() {
        try {
            driver.navigate().back();
            Thread.sleep(1500);
        } catch (Exception ignored) {}
        System.out.println("Navigated back");
    }

    public void goToHome() {
        for (int i = 0; i < 3; i++) {
            try {
                driver.navigate().back();
                Thread.sleep(1000);
            } catch (Exception ignored) {}
        }
        System.out.println("Navigated to Home");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed — Suite finished");
        }
    }
}