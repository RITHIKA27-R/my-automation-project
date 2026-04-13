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
    List<Map<String, String>> allTestData;

    @BeforeClass
    public void setUpPage() {
        marksPage = new MarksPage(driver);
        allTestData = ExcelUtils.getAllRows("MarksData");
        marksPage.clickMarksCard();
        System.out.println("✅ Marks card clicked");
    }

    // ─── TC01: Default page — NO dropdown selection ───────────────────────────
    @Test(priority = 1, description = "TC01 - Default marks page navigation")
    public void tc01_defaultMarksNavigation() {
        System.out.println("\n[TC01] Checking default marks page");
        Assert.assertTrue(marksPage.isMarksPageDisplayed(),
            "Marks page should be displayed");
        Assert.assertTrue(marksPage.areMarksDisplayed(),
            "Default marks should be visible");
        System.out.println("[TC01] PASSED ✅");
    }

    // ─── TC02+: Dynamic loop — Excel rows ─────────────────────────────────────
    @Test(priority = 2, description = "TC02+ - Dynamic exam tests from Excel")
    public void tc02_dynamicExamTests() {

        for (Map<String, String> row : allTestData) {
            String testCase      = row.getOrDefault("TestCase", "").trim();
            String examName      = row.getOrDefault("ExamName", "").trim();
            String expectedResult = row.getOrDefault("ExpectedResult", "").trim();

            // ✅ TC01 row skip — dropdown தேவையில்ல
            if (testCase.equalsIgnoreCase("TC01_MarksNavigation")) {
                System.out.println("[SKIP] TC01 — no dropdown needed");
                continue;
            }

            // Empty row skip
            if (examName.isEmpty() || expectedResult.isEmpty()) continue;

            System.out.println("\n[" + testCase + "] Selecting: " + examName
                + " | Expected: " + expectedResult);

            // ✅ Dropdown select
            marksPage.selectExamFromDropdown(examName);

            // ✅ Assert based on ExpectedResult column
            if ("No Records Found".equalsIgnoreCase(expectedResult)) {
                Assert.assertTrue(
                    marksPage.isNoRecordsDisplayed(),
                    "[" + testCase + "] Expected 'No Records Found' for: " + examName
                );
                System.out.println("[" + testCase + "] PASSED — No Records Found ✅");

            } else if (expectedResult.toLowerCase().contains("marks")) {
                Assert.assertTrue(
                    marksPage.areMarksDisplayed(),
                    "[" + testCase + "] Expected marks table for: " + examName
                );
                System.out.println("[" + testCase + "] PASSED — Marks displayed ✅");

            } else {
                System.out.println("[" + testCase + "] SKIPPED — Unknown: " + expectedResult);
            }
        }
    }

    // ✅ FIX: method name "navigateBack" — goBack() infinite loop இல்ல
    @AfterClass
    public void navigateBack() {
        super.goBack();  // AndroidBase-ல உள்ள goBack() directly call
    }
}