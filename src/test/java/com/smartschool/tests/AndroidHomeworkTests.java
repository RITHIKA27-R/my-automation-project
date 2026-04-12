package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.HomeworkPage;

public class AndroidHomeworkTests extends AndroidBase {

    HomeworkPage homeworkPage;

    @BeforeClass
    public void setUpPage() {
        homeworkPage = new HomeworkPage(driver);
    }

    @Test(priority = 1, description = "TC01 - Homework page navigation")
    public void tc01_homeworkNavigation() {
        homeworkPage.clickHomeworkCard();
        Assert.assertTrue(
            homeworkPage.isHomeworkPageDisplayed(),
            "Homework page should be displayed"
        );
        System.out.println("TC01 PASSED - Homework page displayed");
    }

    @Test(priority = 2, description = "TC02 - Homework data displayed dynamically")
    public void tc02_homeworkDataDisplayed() {
        boolean hasData    = homeworkPage.isHomeworkDataDisplayed();
        boolean noHomework = homeworkPage.isNoHomeworkDisplayed();
        int count          = homeworkPage.getHomeworkCount();
        System.out.println("Homework count: " + count);

        if (noHomework) {
            System.out.println("TC02 INFO - No homework by Admin. Valid state.");
            return;
        }
        Assert.assertTrue(hasData, "Homework data should display");
        System.out.println("TC02 PASSED - Count: " + count);
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}