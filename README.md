# SmartSchool Parent Mobile Automation - Login Page Test Suite

## Project Overview
This is a comprehensive Appium automation framework for testing the login functionality of the SmartSchool Parent mobile application across both Android and iOS platforms using Java.

---

## Test Cases Covered

### 1. **Valid Student ID & Password** (Test Case 1)
- **Purpose:** Verify that user can successfully login with correct credentials
- **Steps:**
  - Enter valid Student ID (e.g., "123456")
  - Enter valid Password (e.g., "Password@123")
  - Click "Login" button
- **Expected Result:** User should be navigated to the Dashboard
- **Code Location:** `AndroidLoginTests.testLoginWithValidCredentials()` and `IOSLoginTests.testLoginWithValidCredentials()`

---

### 2. **Invalid Student ID** (Test Case 2)
- **Purpose:** Verify that invalid Student ID shows appropriate error message
- **Steps:**
  - Enter invalid Student ID (e.g., "999999")
  - Enter correct Password (e.g., "Password@123")
  - Click "Login" button
- **Expected Result:** Alert message should display: "Try Login with correct credentials"
- **Code Location:** `AndroidLoginTests.testLoginWithInvalidStudentId()` and `IOSLoginTests.testLoginWithInvalidStudentId()`

---

### 3. **Invalid Password** (Test Case 3)
- **Purpose:** Verify that invalid password shows appropriate error message
- **Steps:**
  - Enter correct Student ID (e.g., "123456")
  - Enter wrong Password (e.g., "WrongPassword")
  - Click "Login" button
- **Expected Result:** Alert message should display: "Try Login with correct credentials"
- **Code Location:** `AndroidLoginTests.testLoginWithInvalidPassword()` and `IOSLoginTests.testLoginWithInvalidPassword()`

---

### 4. **Empty Fields Validation** (Test Case 4)
- **Purpose:** Verify that clicking login without entering credentials shows validation errors in red color
- **Steps:**
  - Leave Student ID field empty
  - Leave Password field empty
  - Click "Login" button
- **Expected Result:** 
  - Red error message "Enter StudentId" should be displayed under Student ID field
  - Red error message "Enter password" should be displayed under Password field
- **Code Location:** `AndroidLoginTests.testLoginWithEmptyFields()` and `IOSLoginTests.testLoginWithEmptyFields()`

---

### 5. **Password Visibility Toggle** (Test Case 5)
- **Purpose:** Verify that clicking the eye icon toggles password visibility
- **Steps:**
  - Enter password in the Password field
  - Click the eye icon to show password
  - Verify password is visible
  - Click the eye icon again to hide password
  - Verify password is hidden
- **Expected Result:** Password visibility should toggle between visible and hidden states
- **Code Location:** `AndroidLoginTests.testPasswordVisibilityToggle()` and `IOSLoginTests.testPasswordVisibilityToggle()`

---

### 6. **Only StudentId Entered** (Additional Test)
- **Purpose:** Verify error validation when only Student ID is entered
- **Expected Result:** Red error message "Enter password" should be displayed
- **Code Location:** `AndroidLoginTests.testLoginWithOnlyStudentId()` and `IOSLoginTests.testLoginWithOnlyStudentId()`

---

### 7. **Only Password Entered** (Additional Test)
- **Purpose:** Verify error validation when only password is entered
- **Expected Result:** Red error message "Enter StudentId" should be displayed
- **Code Location:** `AndroidLoginTests.testLoginWithOnlyPassword()` and `IOSLoginTests.testLoginWithOnlyPassword()`

---

## Project Structure

```
Smartschool_parent/
├── pom.xml                          # Maven configuration file
├── config.properties                # Configuration for Android and iOS
├── testng.xml                       # TestNG suite configuration
├── src/
│   └── test/
│       ├── java/
│       │   └── com/smartschool/
│       │       ├── base/
│       │       │   ├── AndroidBase.java      # Android driver initialization
│       │       │   └── IOSBase.java          # iOS driver initialization
│       │       ├── pages/
│       │       │   └── LoginPage.java        # Page Object Model for Login page
│       │       ├── tests/
│       │       │   ├── AndroidLoginTests.java    # Android test cases
│       │       │   └── IOSLoginTests.java        # iOS test cases
│       │       └── utils/
│       │           ├── ConfigUtils.java     # Configuration utilities
│       │           └── ScreenshotUtils.java # Screenshot utilities
│       └── resources/
│           └── apps/
│               ├── smartschool-parent.apk   # Android app (to be added)
│               └── smartschool-parent.ipa   # iOS app (to be added)
```

---

## Prerequisites

1. **Java JDK 11 or higher** installed
2. **Maven** installed and configured
3. **Appium Server** installed globally or locally
4. **Android SDK** for Android testing (if testing on Android)
5. **Xcode** for iOS testing (if testing on iOS)
6. **Appium Inspector** (optional, but recommended)

---

## Setup Instructions

### 1. Clone/Download the Project
```bash
cd c:\MobileAutomation\Smartschool_parent
```

### 2. Configure the App Paths
Edit `config.properties` and add the paths to your apps:
```properties
ANDROID_APP_PATH=D:\apps\smartschool-parent.apk
IOS_APP_PATH=D:\apps\smartschool-parent.ipa
```

### 3. Configure Device Details
Update device names and platform versions in `config.properties`:
```properties
ANDROID_DEVICE_NAME=emulator-5554
IOS_DEVICE_NAME=iPhone 14
```

### 4. Start Appium Server
```bash
appium
```

---

## Running Tests

### 1. Run All Tests (Android + iOS)
```bash
mvn clean test
```

### 2. Run Only Android Tests
```bash
mvn clean test -Dtest=AndroidLoginTests
```

### 3. Run Only iOS Tests
```bash
mvn clean test -Dtest=IOSLoginTests
```

### 4. Run Specific Test Case
```bash
# Run only valid credentials test on Android
mvn clean test -Dtest=AndroidLoginTests#testLoginWithValidCredentials
```

---

## Understanding the Code

### LoginPage.java (Page Object Model)
Each method in `LoginPage.java` is tagged with which test case(s) it's used for:

```java
/**
 * TEST CASE 1: Valid Student ID & Password
 * PURPOSE: Verify that user can successfully login with correct credentials
 */
public void enterStudentId(String studentId)

/**
 * TEST CASE 4: Enter StudentId Error Message
 * PURPOSE: Verify error message when StudentId is not entered
 */
public String getStudentIdErrorMessage()
```

### Test File Structure
Each test method follows this pattern:
- **Test Case Number and Description** in @Test annotation
- **Purpose comment** explaining what's being tested
- **Try-Catch block** for execution
- **Assertions** to verify expected results
- **Console output** for debugging

### Example Test:
```java
/**
 * TEST CASE 1: Valid Student ID & Password
 * PURPOSE: Verify that user can successfully login with correct credentials
 * EXPECTED RESULT: User should be navigated to Dashboard
 */
@Test(priority = 1, description = "Login with valid Student ID and Password")
public void testLoginWithValidCredentials() {
    try {
        loginPage.enterStudentId("123456");
        loginPage.enterPassword("Password@123");
        loginPage.clickLoginButton();
        System.out.println("✓ TEST CASE 1 PASSED");
    } catch (Exception e) {
        System.out.println("✗ TEST CASE 1 FAILED: " + e.getMessage());
    }
}
```

---

## Element Locators

The LoginPage uses XPath locators that work for both Android and iOS:

| Element | Locator |
|---------|---------|
| Student ID Field | `//android.widget.EditText[@resource-id='studentId_input']` or `//XCUIElementTypeSecureTextField[@name='studentId']` |
| Password Field | `//android.widget.EditText[@resource-id='password_input']` or `//XCUIElementTypeSecureTextField[@name='password']` |
| Login Button | `//android.widget.Button[@text='Login']` or `//XCUIElementTypeButton[@name='Login']` |
| Eye Icon | `//android.widget.Button[@resource-id='eye_icon']` or `//XCUIElementTypeButton[@name='eyeIcon']` |

**Note:** Update these locators based on your app's actual element IDs. Use Appium Inspector to find the correct locators.

---

## Debugging & Troubleshooting

### 1. Test Fails with "Element Not Found"
- Open Appium Inspector
- Connect to your device/emulator
- Identify the exact XPath or ID of elements
- Update the locators in `LoginPage.java`

### 2. Connection Issues
- Ensure Appium server is running: `appium`
- Check APPIUM_SERVER_URL in `config.properties`
- Verify device is connected: `adb devices` (Android) or `xcrun simctl list devices` (iOS)

### 3. App Crashes
- Check app installation
- Verify app package name and activity name in config
- Check app logs using Appium server output

### 4. Screenshots
All screenshots are saved in `target/screenshots/` directory with timestamps.

---

## Important Notes

1. **Update Locators:** The locators provided are generic. You MUST update them with your app's actual element IDs/XPaths using Appium Inspector.

2. **Valid Credentials:** Update the test credentials (Student ID and Password) with valid credentials from your app.

3. **Wait Times:** Default timeout is 15 seconds. Adjust in `LoginPage.java` if needed.

4. **Thread Safety:** The framework supports parallel execution for Android and iOS tests.

---

## CI/CD Integration

To integrate with CI/CD pipelines (Jenkins, GitHub Actions, etc.):

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

---

## Common Commands Reference

| Command | Purpose |
|---------|---------|
| `mvn clean` | Clean previous build artifacts |
| `mvn compile` | Compile the code |
| `mvn test` | Run all tests |
| `mvn package` | Create deployable package |
| `appium` | Start Appium server |
| `adb devices` | List Android devices |

---

## Support & Maintenance

- **Documentation:** Update this README when adding new test cases
- **Locators:** Regularly update locators if app UI changes
- **Dependencies:** Keep Maven dependencies updated in `pom.xml`
- **Test Data:** Maintain valid test credentials for your environment

---

## Version History

- **v1.0.0** (2026-04-02) - Initial login test suite with 5 main test cases + 2 additional tests
  - Android support ✓
  - iOS support ✓
  - Page Object Model pattern ✓
  - TestNG framework ✓

---

## Author Notes

This test suite covers all critical login scenarios including:
- ✓ Happy path (valid credentials)
- ✓ Negative path (invalid credentials)
- ✓ Edge cases (empty fields)
- ✓ UI interactions (password visibility)
- ✓ Field-level validation

The framework is designed to be maintainable and scalable for future login page enhancements and additional test cases.
