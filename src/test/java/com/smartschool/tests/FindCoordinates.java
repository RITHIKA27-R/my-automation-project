package com.smartschool.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;
import com.smartschool.pages.LoginPage;

import java.net.URL;
import java.time.Duration;

public class FindCoordinates {

    AndroidDriver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.vijayantech.smartschoolparent");
        options.setAppActivity("com.vijayantech.MainActivity");
        options.setAppWaitActivity("com.vijayantech.MainActivity");
        options.setNoReset(false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        loginPage.login("MYST00636", "10102008");
        Thread.sleep(3000);
    }

    @Test
    public void findMenuCoordinates() throws Exception {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("\n========== YOUR SCREEN RESOLUTION ==========");
        System.out.println("Width: " + width + ", Height: " + height);
        System.out.println("=============================================\n");
        
        System.out.println("📌 DEFAULT COORDINATES FOR YOUR DEVICE:");
        System.out.println("Menu icon (top-left): X=50, Y=100");
        System.out.println("Profile Information (in menu): X=" + (width/2) + ", Y=" + (height/3));
        System.out.println("Profile photo center: X=" + (width/2) + ", Y=" + (height/6 + 50));
        System.out.println("\n⚠️ If these don't work, use Appium Inspector to find exact coordinates.");
        System.out.println("⏳ Waiting 30 seconds for manual inspection...");
        
        Thread.sleep(30000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}