# Quick Start Guide - Login Page Automation

## 📋 Situation
- You have Appium running in command prompt
- You're connected to Appium Inspector
- You want to run automated login tests for SmartSchool Parent app
- You need Java-based scripts for both Android and iOS

---

## 🚀 Step-by-Step Setup

### Step 1: Identify Your App Elements
Open Appium Inspector and identify these elements:

1. **Student ID Field**
   - Locate the text input field for Student ID
   - Note its Resource ID (Android) or Accessibility Label (iOS)
   - Example Android: `resource-id="studentId_input"`
   - Example iOS: `name="studentId"`

2. **Password Field**
   - Locate the password input field
   - Example: `resource-id="password_input"`

3. **Login Button**
   - Find the login/submit button
   - Example: `text="Login"`

4. **Eye Icon** (for password visibility)
   - Find the eye icon button
   - Example: `resource-id="eye_icon"`

5. **Error Messages**
   - Identify error text locations
   - Example: `resource-id="studentId_error"`

6. **Alert Dialog**
   - Check alert message element
   - Example: Android Alert: `//android.app.AlertDialog`

### Step 2: Update Locators in LoginPage.java

Replace the XPath locators in `src/test/java/com/smartschool/pages/LoginPage.java`:

```java
// BEFORE (Generic)
@FindBy(xpath = "//android.widget.EditText[@resource-id='studentId_input'] | .....")
private WebElement studentIdField;

// AFTER (Your App Specific)
@FindBy(xpath = "//android.widget.EditText[@resource-id='your_actual_id']")
private WebElement studentIdField;
```

### Step 3: Update Configuration

Edit `config.properties`:

```properties
# Android Configuration
ANDROID_APP_PATH=C:\path\to\smartschool-parent.apk
ANDROID_DEVICE_NAME=emulator-5554
ANDROID_APP_PACKAGE=com.smartschool.parent
ANDROID_APP_ACTIVITY=.LoginActivity

# iOS Configuration
IOS_APP_PATH=C:\path\to\smartschool-parent.ipa
IOS_DEVICE_NAME=iPhone 14
IOS_BUNDLE_ID=com.smartschool.parent

# Server
APPIUM_SERVER_URL=http://localhost:4723/wd/hub
```

### Step 4: Update Test Credentials

Edit your test class and update valid credentials:

```java
// In AndroidLoginTests.java
loginPage.enterStudentId("YOUR_VALID_STUDENT_ID");
loginPage.enterPassword("YOUR_VALID_PASSWORD");
```

---

## 📝 5 Main Test Cases

### What Each Test Does:

| # | Test | What Code Does | When to Use |
|---|------|---|---|
| 1 | Valid Credentials | Enters correct ID+password, clicks login | Verify successful login |
| 2 | Invalid ID | Enters wrong ID+correct password, checks alert | Verify security |
| 3 | Invalid Password | Enters correct ID+wrong password, checks alert | Verify security |
| 4 | Empty Fields | Clicks login with no data, checks error messages | Verify validation |
| 5 | Password Toggle | Clicks eye icon, verifies password visibility changes | Verify UI feature |

---

## 🎯 Running Tests

### Prerequisites:
```bash
# Open Command Prompt - Start Appium Server
appium

# In Appium Inspector:
# 1. Enter capability JSON
# 2. Click "Start Session"
# 3. Keep it running (tests will connect to it)
```

### Run ALL Tests (Android + iOS):
```bash
cd c:\MobileAutomation\Smartschool_parent
mvn clean test
```

### Run ONLY Android Tests:
```bash
mvn clean test -Dtest=AndroidLoginTests
```

### Run ONLY iOS Tests:
```bash
mvn clean test -Dtest=IOSLoginTests
```

### Run SPECIFIC Test Case:
```bash
# Run only "Valid Credentials" test
mvn clean test -Dtest=AndroidLoginTests#testLoginWithValidCredentials

# Run only "Empty Fields" test
mvn clean test -Dtest=AndroidLoginTests#testLoginWithEmptyFields
```

---

## 🔍 Understanding Test Output

When tests run, you'll see console output like:

```
=== Android Login Tests Setup Completed ===

Entered Student ID: 123456
Entered Password
Clicked Login Button
✓ TEST CASE 1 PASSED: User successfully logged in with valid credentials

Cleared Student ID Field
Cleared Password Field
Entered Student ID: 999999
Entered Password
Clicked Login Button
Alert Message: Try Login with correct credentials
✓ TEST CASE 2 PASSED: Invalid Student ID shows correct error message

=== Android Login Tests Teardown Completed ===
```

**✓ = PASS** (Test succeeded)
**✗ = FAIL** (Test failed - check error message)

---

## 🛠️ Troubleshooting

### Problem: "Element not found" Error
**Solution:**
- Use Appium Inspector to verify element exists
- Copy exact XPath from Inspector
- Update LoginPage.java with new locator

### Problem: "Connection refused" Error
**Solution:**
- Make sure Appium server is running: `appium`
- Verify URL in config.properties matches server
- Default: `http://localhost:4723/wd/hub`

### Problem: "App not installed" Error
**Solution:**
- Verify app APK/IPA path in config.properties
- Ensure file exists at that path
- Check app package name and activity name

### Problem: Test hangs or times out
**Solution:**
- Increase timeout in config.properties
- Check if app is responding
- Restart Appium server

---

## 📁 Important Files to Know

| File | Purpose | What to Update |
|------|---------|---|
| `config.properties` | App and device config | Device names, app paths, URLs |
| `LoginPage.java` | Element locators and methods | XPath/ID locators for your app |
| `AndroidLoginTests.java` | Android test cases | Test data (IDs, passwords) |
| `IOSLoginTests.java` | iOS test cases | Test data (IDs, passwords) |

---

## ⚡ Quick Commands Reference

```bash
# Navigate to project
cd c:\MobileAutomation\Smartschool_parent

# Download dependencies
mvn install

# Run all tests
mvn clean test

# Run with more details
mvn clean test -X

# Run specific test and show logs
mvn test -Dtest=AndroidLoginTests#testLoginWithValidCredentials -e

# Generate HTML report
mvn clean test site
```

---

## 📊 Test Case Details

### Test Case 1: Valid Login
```
Input: Student ID = "123456", Password = "Password@123"
Action: Click Login
Expected: Navigate to Dashboard
```

### Test Case 2: Invalid Student ID
```
Input: Student ID = "999999", Password = "Password@123"
Action: Click Login
Expected: Alert message "Try Login with correct credentials"
```

### Test Case 3: Invalid Password
```
Input: Student ID = "123456", Password = "WrongPassword"
Action: Click Login
Expected: Alert message "Try Login with correct credentials"
```

### Test Case 4: Empty Fields
```
Input: Student ID = "", Password = ""
Action: Click Login
Expected: Error "Enter StudentId" and "Enter password" in red
```

### Test Case 5: Password Visibility
```
Input: Enter password "Password123"
Action: Click eye icon
Expected: Password visibility toggles (shown/hidden/shown)
```

---

## 🎓 Code Examples

### How to Add New Test Case:

```java
@Test(priority = 8, description = "My new test case")
public void testMyNewScenario() {
    try {
        // Step 1: Setup
        loginPage.clearStudentIdField();
        
        // Step 2: Action
        loginPage.enterStudentId("123456");
        loginPage.clickLoginButton();
        
        // Step 3: Verify
        String result = loginPage.getAlertMessage();
        Assert.assertTrue(result.contains("expected text"), 
                         "Verify expected result");
        
        System.out.println("✓ NEW TEST CASE PASSED");
        
    } catch (Exception e) {
        System.out.println("✗ NEW TEST CASE FAILED: " + e.getMessage());
        throw new AssertionError("Test failed", e);
    }
}
```

---

## 📞 Summary

| Question | Answer |
|----------|--------|
| What are these scripts for? | Automated testing of login page |
| How many test cases? | 5 main + 2 additional = 7 total |
| Platforms? | Android and iOS |
| Language? | Java with Appium |
| How to run? | `mvn clean test` |
| Need Appium? | Yes, running on localhost:4723 |
| Valid IDs needed? | Yes, update in test files |

---

## ✅ Verification Checklist

Before running tests, verify:
- [ ] Appium server is running (`appium`)
- [ ] Device is connected to Appium
- [ ] App APK/IPA path is correct in config.properties
- [ ] Valid Student ID and Password are used in tests
- [ ] Element locators are updated from Appium Inspector
- [ ] Maven and Java JDK 11+ installed
- [ ] config.properties has correct device names

---

## 🎬 Next Steps

1. ✅ Identify all app elements using Appium Inspector
2. ✅ Update locators in LoginPage.java
3. ✅ Update credentials in test files
4. ✅ Update config.properties with paths
5. ✅ Run: `mvn clean test`
6. ✅ Check results and logs

Good luck! 🚀
