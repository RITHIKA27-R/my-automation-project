package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.NotificationPage;

public class AndroidNotificationTests extends AndroidBase {

    NotificationPage notificationPage;

    @BeforeClass
    public void setUpPage() {
        notificationPage = new NotificationPage(driver);
    }

    @Test(priority = 1, description = "TC01 - Notification page navigation")
    public void tc01_notificationNavigation() {
        notificationPage.clickNotificationCard();
        Assert.assertTrue(
            notificationPage.isNotificationsPageDisplayed(),
            "Notifications page should be displayed"
        );
        System.out.println("TC01 PASSED - Notifications page displayed");
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}