# Finding & Updating Element Locators with Appium Inspector

## Overview
Since your app's actual element IDs will differ from the generic ones provided, you need to find the EXACT locators using Appium Inspector.

---

## Getting Started with Appium Inspector

### 1. Start Appium
```bash
# Open Command Prompt
# Run Appium server
appium
```

### 2. Open Appium Inspector
- Download from: http://appium.io/
- Or use: `npm install -g appium-inspector`
- After installation, open Appium Inspector

### 3. Create Desired Capabilities JSON

#### For Android:
```json
{
  "platformName": "Android",
  "deviceName": "emulator-5554",
  "app": "C:\\path\\to\\smartschool-parent.apk",
  "automationName": "UiAutomator2",
  "appPackage": "com.smartschool.parent",
  "appActivity": ".LoginActivity",
  "noReset": false
}
```

#### For iOS:
```json
{
  "platformName": "iOS",
  "deviceName": "iPhone 14",
  "app": "C:\\path\\to\\smartschool-parent.ipa",
  "automationName": "XCUITest",
  "bundleId": "com.smartschool.parent"
}
```

### 4. Start Session
- Paste JSON in Appium Inspector "Desired Capabilities" field
- Click "Start Session"
- Wait for device to load the app

---

## Finding Locators in Inspector

### Step 1: Navigate to Login Screen
- Wait until Inspector shows the app loaded
- Inspector UI will display the current screen

### Step 2: Find Student ID Field Element

1. Click on the Student ID input field in the Inspector view (middle panel)
2. The element details will appear in the RIGHT panel showing:
   - **`resource-id`** (Android) - Use this as Priority 1
   - **`name`** or **`label`** (iOS) - Use this as Priority 1
   - **`xpath`** - Use this as Priority 2
   - **`className`** - Use this as Priority 3

### Example: Finding Student ID Field

**What you see in Inspector Right Panel:**
```
Element: EditText
Attributes:
  resource-id: com.smartschool.parent:id/studentIdInput
  text: (empty)
  class: android.widget.EditText
  xpath: //android.widget.EditText[@resource-id='com.smartschool.parent:id/studentIdInput']
```

**Update LoginPage.java:**
```java
// OLD (Generic)
@FindBy(xpath = "//android.widget.EditText[@resource-id='studentId_input']")
private WebElement studentIdField;

// NEW (Your App Specific)
@FindBy(xpath = "//android.widget.EditText[@resource-id='com.smartschool.parent:id/studentIdInput']")
private WebElement studentIdField;
```

---

## Finding All Required Elements

### 1. Student ID Input Field
**Location:** Login Screen → Student ID text box

**What to look for:**
- Android: `android.widget.EditText` or `android.widget.TextInputEditText`
- iOS: `XCUIElementTypeTextField` or `XCUIElementTypeSecureTextField`

**Example Android XPath:**
```
//android.widget.EditText[@resource-id='com.smartschool.parent:id/studentId']
```

**Example iOS XPath:**
```
//XCUIElementTypeTextField[@name='Student ID']
```

### 2. Password Input Field
**Location:** Login Screen → Password text box

**Example Android XPath:**
```
//android.widget.EditText[@resource-id='com.smartschool.parent:id/password']
```

**Example iOS XPath:**
```
//XCUIElementTypeSecureTextField[@name='Password']
```

### 3. Login Button
**Location:** Login Screen → Login/Submit button

**Example Android XPath:**
```
//android.widget.Button[@text='Login']
//android.widget.Button[@resource-id='com.smartschool.parent:id/loginBtn']
```

**Example iOS XPath:**
```
//XCUIElementTypeButton[@name='Login']
```

### 4. Eye Icon (Password Visibility Toggle)
**Location:** Inside Password field → Eye icon

**Example Android XPath:**
```
//android.widget.ImageButton[@resource-id='com.smartschool.parent:id/passwordToggle']
```

**Example iOS XPath:**
```
//XCUIElementTypeButton[@name='Show Password']
```

### 5. Student ID Error Text
**Location:** Below Student ID field → Red error message

**Example Android XPath:**
```
//android.widget.TextView[@resource-id='com.smartschool.parent:id/studentIdError']
```

### 6. Password Error Text
**Location:** Below Password field → Red error message

**Example Android XPath:**
```
//android.widget.TextView[@resource-id='com.smartschool.parent:id/passwordError']
```

### 7. Alert Dialog Message
**Location:** Alert dialog that appears on invalid login

**Example Android XPath:**
```
//android.app.AlertDialog//android.widget.TextView[@resource-id='android:id/message']
```

---

## Common Locator Patterns

### Android:
```
//android.widget.EditText[@resource-id='app_id/studentId']
//android.widget.Button[@text='Login']
//android.widget.TextView[@text='Enter StudentId']
```

### iOS:
```
//XCUIElementTypeTextField[@name='studentId']
//XCUIElementTypeButton[@name='Login']
//XCUIElementTypeStaticText[@name='error message']
```

---

## After Finding All Locators

Update LoginPage.java with your actual IDs and run tests!
