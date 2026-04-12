package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.TransportPage;

public class AndroidTransportTests extends AndroidBase {

    TransportPage transportPage;

    @BeforeClass
    public void setUpPage() {
        transportPage = new TransportPage(driver);
    }

    @Test(priority = 1, description = "TC01 - Transport page navigation")
    public void tc01_transportNavigation() {
        transportPage.clickTransportCard();
        Assert.assertTrue(
            transportPage.isTransportPageDisplayed(),
            "Transport page should be displayed"
        );
        String busDetails = transportPage.getBusNumber();
        System.out.println("Bus Details: " + busDetails);
        Assert.assertTrue(
            transportPage.isBusDetailsDisplayed(),
            "Bus details should be displayed"
        );
        System.out.println("TC01 PASSED - Bus: " + busDetails);
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}