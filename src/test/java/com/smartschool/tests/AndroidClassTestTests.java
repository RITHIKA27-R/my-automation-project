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
        Assert.assertTrue(classTestPage.isClassTestPageDisplayed(),
            "Class Test page should be displayed");
        Assert.assertTrue(classTestPage.isWeeklyReportDisplayed(),
            "Weekly Report should be displayed");
        System.out.println("TC01 PASSED - Class Test page displayed");
    }

    @Test(priority = 2, description = "TC02 - Expand first weekly report dropdown")
    public void tc02_expandFirstDropdown() {
        // ✅ First row click → expand
        classTestPage.expandFirstWeeklyReport();
        Assert.assertTrue(classTestPage.isDropdownExpanded(),
            "Dropdown should be expanded and show marks table");
        System.out.println("TC02 PASSED - Dropdown expanded, marks visible");
    }

    @Test(priority = 3, description = "TC03 - Verify marks inside dropdown")
    public void tc03_verifyMarksVisible() {
        // ✅ Expanded dropdown-ல marks verify
        String marksInfo = classTestPage.getMarksText("Tamil");
        Assert.assertNotEquals(marksInfo, "NOT_FOUND",
            "Tamil marks should be visible in expanded dropdown");
        System.out.println("TC03 PASSED - Marks content: " + marksInfo);
    }

    @Test(priority = 4, description = "TC04 - Collapse dropdown by clicking again")
    public void tc04_collapseDropdown() {
        // ✅ Same row மீண்டும் click → collapse
        classTestPage.collapseFirstWeeklyReport();
        Assert.assertTrue(classTestPage.isDropdownCollapsed(),
            "Dropdown should be collapsed after second click");
        System.out.println("TC04 PASSED - Dropdown collapsed");
    }

    @Test(priority = 5, description = "TC05 - Dynamic date expand/collapse")
    public void tc05_dynamicDateExpandCollapse() {
        // ✅ Specific date-ஐ target பண்ணி expand பண்ணு
        classTestPage.expandWeeklyReportByDate("12-04-2026");
        Assert.assertTrue(classTestPage.isDropdownExpanded(),
            "12-04-2026 report should expand");
        System.out.println("TC05 - Expanded 12-04-2026 report");

        // ✅ மீண்டும் collapse
        classTestPage.collapseWeeklyReportByDate("12-04-2026");
        System.out.println("TC05 PASSED - Collapse also done");
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}