package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.AttendancePage;

public class AndroidAttendanceTests extends AndroidBase {

    AttendancePage attendancePage;

    @BeforeClass
    public void setUpPage() {
        attendancePage = new AttendancePage(driver);
    }

    @Test(priority = 1, description = "TC01 - Attendance page navigation")
    public void tc01_attendanceNavigation() {
        attendancePage.clickAttendanceCard();
        Assert.assertTrue(
            attendancePage.isAttendancePageDisplayed(),
            "Attendance page should be displayed"
        );
        System.out.println("TC01 PASSED - Attendance page displayed");
    }

    @AfterClass
    public void goBackToHome() {
        goBack(); // ✅ Home screen-க்கு திரும்பு
    }
}