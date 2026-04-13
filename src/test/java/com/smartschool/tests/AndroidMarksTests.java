package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.MarksPage;
import com.smartschool.utils.ExcelUtils;

import java.util.Map;

public class AndroidMarksTests extends AndroidBase {

    MarksPage marksPage;

    @BeforeClass
    public void setUpPage() {
        marksPage = new MarksPage(driver);
        marksPage.clickMarksCard();
        System.out.println("Marks card clicked");
    }

    // TC01: Default Term1 marks (no dropdown selection)
    @Test(priority = 1)
    public void tc01_defaultTerm1Marks() {
        System.out.println("\n[TC01] Checking default Term1 marks");
        boolean page = marksPage.isMarksPageDisplayed();
        Assert.assertTrue(page, "Marks page not displayed");
        boolean marks = marksPage.areMarksDisplayed();
        Assert.assertTrue(marks, "Term1 marks table not shown");
        System.out.println("[TC01] PASSED");
    }

    // TC02: Half Yearly -> No Records Found
    @Test(priority = 2)
    public void tc02_halfYearlyNoRecords() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("MarksData", "TC02_HalfYearly");
        String exam = data.get("ExamName");
        System.out.println("\n[TC02] Selecting " + exam);
        marksPage.selectExamFromDropdown(exam);
        boolean noRecords = marksPage.isNoRecordsDisplayed();
        Assert.assertTrue(noRecords, "Expected 'No Records Found' for " + exam);
        System.out.println("[TC02] PASSED");
    }

    // TC03: 3rd Mid Term -> Marks table
    @Test(priority = 3)
    public void tc03_thirdMidTermMarks() {
        Map<String, String> data = ExcelUtils.getRowByTestCase("MarksData", "TC03_3rd Mid Term");
        String exam = data.get("ExamName");
        System.out.println("\n[TC03] Selecting " + exam);
        marksPage.selectExamFromDropdown(exam);
        boolean marks = marksPage.areMarksDisplayed();
        Assert.assertTrue(marks, "Marks table not shown for " + exam);
        System.out.println("[TC03] PASSED");
    }

    @AfterClass
    public void goBack() {
        goBack();
    }
}