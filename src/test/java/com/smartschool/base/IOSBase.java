package com.smartschool.base;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class IOSBase {

    public IOSDriver driver;

    public void initializeDriver() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "iOS");
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("deviceName", "iPhone 14");
        caps.setCapability("platformVersion", "16.4");

        // 🔴 IMPORTANT - change this path
        caps.setCapability("app", "/path/to/your/app.ipa");

        driver = new IOSDriver(new URL("http://127.0.0.1:4723"), caps);

        System.out.println("iOS Driver initialized");
    }
}