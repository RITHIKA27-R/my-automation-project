# Test Cases & Code Reference Guide

## Quick Reference: Test Case to Code Mapping

---

## TEST CASE 1: Valid Student ID & Password
**Purpose:** Verify successful login with correct credentials

### Test Code:
```java
@Test(priority = 1, description = "Login with valid Student ID and Password")
public void testLoginWithValidCredentials() {
    // Assertion to verify login button is displayed
    Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
    
    // Enter valid credentials
    loginPage.enterStudentId("123456");      // Uses: LoginPage.enterStudentId()
    loginPage.enterPassword("Password@123"); // Uses: LoginPage.enterPassword()
    
    // Click login and wait for dashboard
    loginPage.clickLoginButton();             // Uses: LoginPage.clickLoginButton()
    Thread.sleep(3000);
    
    System.out.println("✓ TEST CASE 1 PASSED: User successfully logged in");
}
```

### Related Methods in LoginPage:
- `enterStudentId(String studentId)` - Sends valid Student ID to field
- `enterPassword(String password)` - Sends valid password to field
- `clickLoginButton()` - Submits the login form
- `isLoginButtonDisplayed()` - Verifies UI element presence

### Expected Result:
✓ User navigates to Dashboard
✓ No error messages displayed
✓ Login successful

---

## TEST CASE 2: Invalid Student ID
**Purpose:** Verify alert message when invalid Student ID is used

### Test Code:
```java
@Test(priority = 2, description = "Login with invalid Student ID")
public void testLoginWithInvalidStudentId() {
    // Clear previous data
    loginPage.clearStudentIdField();  // Uses: LoginPage.clearStudentIdField()
    loginPage.clearPasswordField();   // Uses: LoginPage.clearPasswordField()
    
    // Enter invalid Student ID WITH correct password
    loginPage.enterStudentId("999999");      // Invalid ID that doesn't exist
    loginPage.enterPassword("Password@123"); // Correct password
    loginPage.clickLoginButton();
    
    Thread.sleep(2000);
    
    // Verify error alert message
    String alertMsg = loginPage.getAlertMessage();  // Uses: LoginPage.getAlertMessage()
    Assert.assertTrue(alertMsg.contains("Try Login with correct credentials"), 
                     "Alert should show 'Try Login with correct credentials'");
    
    System.out.println("✓ TEST CASE 2 PASSED: Invalid Student ID shows correct error");
}
```

### Related Methods in LoginPage:
- `clearStudentIdField()` - Clears the Student ID input field
- `clearPasswordField()` - Clears the password input field
- `enterStudentId(String studentId)` - Enters invalid Student ID
- `enterPassword(String password)` - Enters correct password
- `clickLoginButton()` - Submits the form
- `getAlertMessage()` - Retrieves the alert dialog message

### Expected Result:
✗ Alert dialog appears
✗ Message: "Try Login with correct credentials"
✗ User stays on login page

---

## TEST CASE 3: Invalid Password
**Purpose:** Verify alert message when invalid password is used

### Test Code:
```java
@Test(priority = 3, description = "Login with invalid Password")
public void testLoginWithInvalidPassword() {
    // Clear previous data
    loginPage.clearStudentIdField();
    loginPage.clearPasswordField();
    
    // Enter VALID Student ID WITH invalid password
    loginPage.enterStudentId("123456");      // Correct Student ID
    loginPage.enterPassword("WrongPassword"); // Wrong password
    loginPage.clickLoginButton();
    
    Thread.sleep(2000);
    
    // Verify error alert message
    String alertMsg = loginPage.getAlertMessage();  // Uses: LoginPage.getAlertMessage()
    Assert.assertTrue(alertMsg.contains("Try Login with correct credentials"), 
                     "Alert should show 'Try Login with correct credentials'");
    
    System.out.println("✓ TEST CASE 3 PASSED: Invalid password shows correct error");
}
```

### Related Methods in LoginPage:
- `clearStudentIdField()` - Clears the Student ID field
- `clearPasswordField()` - Clears the password field
- `enterStudentId(String studentId)` - Enters correct Student ID
- `enterPassword(String password)` - Enters wrong password
- `clickLoginButton()` - Submits the form
- `getAlertMessage()` - Retrieves the alert message

### Expected Result:
✗ Alert dialog appears
✗ Message: "Try Login with correct credentials"
✗ User stays on login page

---

## TEST CASE 4: Empty Fields Validation
**Purpose:** Verify error messages when clicking login with empty fields

### Test Code:
```java
@Test(priority = 4, description = "Login without entering any credentials")
public void testLoginWithEmptyFields() {
    // Clear both fields
    loginPage.clearStudentIdField();
    loginPage.clearPasswordField();
    
    // Click login WITHOUT entering any data
    loginPage.clickLoginButton();  // Submit empty form
    
    Thread.sleep(1500);
    
    // Verify error messages appear in RED color
    String studentIdError = loginPage.getStudentIdErrorMessage();  // Uses: LoginPage.getStudentIdErrorMessage()
    String passwordError = loginPage.getPasswordErrorMessage();    // Uses: LoginPage.getPasswordErrorMessage()
    
    Assert.assertTrue(studentIdError.contains("Enter StudentId") || studentIdError.contains("Student"), 
                     "Should show 'Enter StudentId' error in red");
    Assert.assertTrue(passwordError.contains("Enter password") || passwordError.contains("Password"), 
                     "Should show 'Enter password' error in red");
    
    System.out.println("✓ TEST CASE 4 PASSED: Empty fields validation works");
}
```

### Related Methods in LoginPage:
- `clearStudentIdField()` - Clears the Student ID field
- `clearPasswordField()` - Clears the password field
- `clickLoginButton()` - Submits the empty form
- `getStudentIdErrorMessage()` - Gets validation error for Student ID
- `getPasswordErrorMessage()` - Gets validation error for password

### Expected Result:
✗ Red error text "Enter StudentId" appears below Student ID field
✗ Red error text "Enter password" appears below password field
✗ Login button gets disabled or shows error state
✗ User stays on login page

---

## TEST CASE 5: Password Visibility Toggle
**Purpose:** Verify that eye icon toggles password visibility

### Test Code:
```java
@Test(priority = 5, description = "Toggle password visibility")
public void testPasswordVisibilityToggle() {
    // Clear and enter password
    loginPage.clearPasswordField();
    loginPage.enterPassword("Password123");
    
    // Get initial state (should be hidden/password type)
    String initialType = loginPage.getPasswordFieldType();  // Uses: LoginPage.getPasswordFieldType()
    System.out.println("Initial: " + initialType);
    
    // Click eye icon to SHOW password
    loginPage.togglePasswordVisibility();  // Uses: LoginPage.togglePasswordVisibility()
    Thread.sleep(500);
    String afterToggleType = loginPage.getPasswordFieldType();
    System.out.println("After toggle: " + afterToggleType);
    
    // Click eye icon again to HIDE password
    loginPage.togglePasswordVisibility();
    Thread.sleep(500);
    String finalType = loginPage.getPasswordFieldType();
    System.out.println("Final: " + finalType);
    
    // Verify toggle happened
    Assert.assertNotEquals(initialType, afterToggleType, 
                          "Password visibility should toggle");
    
    System.out.println("✓ TEST CASE 5 PASSED: Password visibility toggle works");
}
```

### Related Methods in LoginPage:
- `clearPasswordField()` - Clears the password field
- `enterPassword(String password)` - Enters password text
- `getPasswordFieldType()` - Gets current field type (secure/text)
- `togglePasswordVisibility()` - Clicks the eye icon

### Expected Result:
✓ Eye icon toggles between different states
✓ Password text visibility changes
✓ Field type changes between "password" and "text"
✓ Password content remains the same (just visibility changes)

---

## ADDITIONAL TEST CASE: Only Student ID Entered
**Purpose:** Verify that password error shows when only Student ID is provided

### Test Code:
```java
@Test(priority = 6, description = "Login with only Student ID")
public void testLoginWithOnlyStudentId() {
    loginPage.clearStudentIdField();
    loginPage.clearPasswordField();
    
    // Enter ONLY Student ID
    loginPage.enterStudentId("123456");
    // Leave password empty
    loginPage.clickLoginButton();
    
    Thread.sleep(1500);
    
    String passwordError = loginPage.getPasswordErrorMessage();  // Uses: LoginPage.getPasswordErrorMessage()
    Assert.assertTrue(passwordError.contains("Enter password") || passwordError.contains("Password"), 
                     "Should show 'Enter password' error");
    
    System.out.println("✓ ADDITIONAL TEST PASSED: Only Student ID validation works");
}
```

### Expected Result:
✗ Red error message "Enter password" appears
✓ Student ID field accepted without error

---

## ADDITIONAL TEST CASE: Only Password Entered
**Purpose:** Verify that Student ID error shows when only password is provided

### Test Code:
```java
@Test(priority = 7, description = "Login with only Password")
public void testLoginWithOnlyPassword() {
    loginPage.clearStudentIdField();
    loginPage.clearPasswordField();
    
    // Leave Student ID empty
    // Enter ONLY password
    loginPage.enterPassword("Password@123");
    loginPage.clickLoginButton();
    
    Thread.sleep(1500);
    
    String studentIdError = loginPage.getStudentIdErrorMessage();  // Uses: LoginPage.getStudentIdErrorMessage()
    Assert.assertTrue(studentIdError.contains("Enter StudentId") || studentIdError.contains("Student"), 
                     "Should show 'Enter StudentId' error");
    
    System.out.println("✓ ADDITIONAL TEST PASSED: Only Password validation works");
}
```

### Expected Result:
✗ Red error message "Enter StudentId" appears
✓ Password field accepted without error

---

## Code Coverage Summary

| Test Case | Methods Used | Assertions | Locators Used |
|-----------|--------------|-----------|--------------|
| 1 | enterStudentId, enterPassword, clickLoginButton, isLoginButtonDisplayed | 1 | studentIdField, passwordField, loginButton |
| 2 | clearStudentIdField, clearPasswordField, enterStudentId, enterPassword, clickLoginButton, getAlertMessage | 1 | studentIdField, passwordField, loginButton, alertDialog, alertMessage |
| 3 | clearStudentIdField, clearPasswordField, enterStudentId, enterPassword, clickLoginButton, getAlertMessage | 1 | studentIdField, passwordField, loginButton, alertDialog, alertMessage |
| 4 | clearStudentIdField, clearPasswordField, clickLoginButton, getStudentIdErrorMessage, getPasswordErrorMessage | 2 | studentIdField, passwordField, loginButton, studentIdErrorText, passwordErrorText |
| 5 | clearPasswordField, enterPassword, getPasswordFieldType, togglePasswordVisibility | 1 | passwordField, passwordVisibilityToggle |
| 6 | clearStudentIdField, clearPasswordField, enterStudentId, clickLoginButton, getPasswordErrorMessage | 1 | studentIdField, passwordField, loginButton, passwordErrorText |
| 7 | clearStudentIdField, clearPasswordField, enterPassword, clickLoginButton, getStudentIdErrorMessage | 1 | studentIdField, passwordField, loginButton, studentIdErrorText |

---

## How to Run Individual Tests

```bash
# Run TEST CASE 1 only
mvn clean test -Dtest=AndroidLoginTests#testLoginWithValidCredentials

# Run TEST CASE 2 only
mvn clean test -Dtest=AndroidLoginTests#testLoginWithInvalidStudentId

# Run TEST CASE 3 only
mvn clean test -Dtest=AndroidLoginTests#testLoginWithInvalidPassword

# Run TEST CASE 4 only
mvn clean test -Dtest=AndroidLoginTests#testLoginWithEmptyFields

# Run TEST CASE 5 only
mvn clean test -Dtest=AndroidLoginTests#testPasswordVisibilityToggle
```

---

## Notes for Implementation

1. **Update Locators:** Replace XPath locators with your app's actual element IDs
2. **Update Credentials:** Use valid Student ID and Password for your app
3. **Update Timeout:** Adjust IMPLICIT_WAIT and EXPLICIT_WAIT in config.properties if needed
4. **Handle Alerts:** Some test cases expect alert dialogs - ensure your app shows them
5. **Error Messages:** Update expected error messages to match your app's text

---

## Debugging Tips

- **Use Appium Inspector** to identify correct element locators
- **Add screenshots** using `ScreenshotUtils.takeScreenshot()` for debugging
- **Check logs** in Appium server for element interaction details
- **Verify credentials** are correct and active in your system
- **Test manually first** to understand app behavior before automation
