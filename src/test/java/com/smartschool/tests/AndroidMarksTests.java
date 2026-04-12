package com.smartschool.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import com.smartschool.base.AndroidBase;
import com.smartschool.pages.MarksPage;
import com.smartschool.utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class AndroidMarksTests extends AndroidBase {

    MarksPage marksPage;

    @BeforeClass
    public void setUpPage() {
        marksPage = new MarksPage(driver);
        marksPage.clickMarksCard();
        System.out.println("Marks card clicked");
    }

    @Test(priority = 1, description = "All Marks test cases from MarksData sheet")
    public void marksTests() {
        List<Map<String, String>> rows = ExcelUtils.getTestData("MarksData");
        for (Map<String, String> row : rows) {
            String tc       = row.get("TestCase");
            String examName = row.getOrDefault("ExamName", "");
            String expected = row.get("ExpectedResult").toLowerCase().trim();

            System.out.println("Running: " + tc + " | Exam: " + examName);

            boolean result;
            if (expected.contains("page") || expected.contains("display")) {
                result = marksPage.isMarksPageDisplayed();
            } else if (expected.contains("no record")) {
                marksPage.selectExamFromDropdown(examName);
                result = marksPage.isNoRecordsDisplayed();
            } else {
                result = false;
            }

            Assert.assertTrue(result, tc + " FAILED | Expected: " + expected);
            System.out.println("PASSED: " + tc);
        }
    }

    @AfterClass
    public void goBackToHome() {
        goBack();
    }
}