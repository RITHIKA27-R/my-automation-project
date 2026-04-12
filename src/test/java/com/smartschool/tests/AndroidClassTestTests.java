package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.ClassTestPage;

public class AndroidClassTestTests extends AndroidBase {

    ClassTestPage classTestPage;

    @BeforeClass
    public void setUpPage() {
        classTestPage = new ClassTestPage(driver);
    }

    @Test(priority = 1, description = "TC01 - Class Test page navigation")
    public void tc01_classTestNavigation() {
        classTestPage.clickClassTestCard();
        Assert.assertTrue(
            classTestPage.isClassTestPageDisplayed(),
            "Class Test page should be displayed"
        );
        Assert.assertTrue(
            classTestPage.isWeeklyReportDisplayed(),
            "Weekly Report should be displayed"
        );
        System.out.println("TC01 PASSED - Class Test page displayed");
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}