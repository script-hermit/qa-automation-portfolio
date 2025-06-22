# qa-automation-portfolio

[![Playwright Tests](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/playwright.yml/badge.svg)](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/playwright.yml)
[![Mobile Tests](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/mobile-test.yml/badge.svg)](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/mobile-test.yml)
[![Wikipedia API Tests](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/api-tests.yml/badge.svg?branch=main)](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/api-tests.yml)

A showcase of my QA automation skills across web, mobile, and API testing. This repo highlights real-world test strategies using Playwright, Appium (Java + TestNG), Rest-Assured (Java), and GitHub Actions for CI/CD integration.

---

## 📁 Project Structure

```
qa-automation-portfolio/
├── web-tests/             # Web UI automation with Playwright
├── mobile-tests/          # Mobile automation using Appium (Java + TestNG)
├── api-tests/             # API testing with Rest-Assured (Java + TestNG)
├── .github/workflows/     # CI/CD workflows for GitHub Actions
└── README.md              # Project overview and usage instructions
```

---

## 🧪 Features

- **Web Testing**: Automated UI tests for modern web apps (e.g., login flows, form validation) using Playwright. Tests are organized in `web-tests/tests/smoke/` with page objects in `web-tests/page-objects/`.
- **Mobile Testing**: Android emulator-based tests using Appium 2.x, Java, and TestNG. CI runs on GitHub Actions with a real emulator and Allure HTML reporting, plus TestNG HTML reports.
- **API Testing**: REST API tests via Rest-Assured (Java + TestNG) with data-driven assertions and Allure reporting.  
  <sup>_(Optional: You can also add Postman collections and Newman if desired.)_</sup>
- **CI/CD Integration**: GitHub Actions workflows for all test suites, including mobile tests on an Android emulator.
- **Artifacts & Reporting**: Screenshots, logs, and HTML test reports (including Allure, TestNG HTML, and Playwright HTML) exported from CI as downloadable artifacts.

---

## 🚀 Getting Started

### ✅ Clone the Repo

```bash
git clone https://github.com/yourusername/qa-automation-portfolio.git
cd qa-automation-portfolio
```

### 🔧 Install Dependencies

Each subfolder includes its own setup.

#### Web Tests

```bash
cd web-tests
npm install
npx playwright install
```

#### Mobile Tests

- Ensure **Java 21+**, **Maven**, **Node.js**, and **Appium 2.x** are installed.
- Install the Appium UiAutomator2 driver:
  ```bash
  npm install -g appium
  appium driver install uiautomator2
  ```
- (Optional) Install Allure CLI for local HTML reports:
  ```bash
  npm install -g allure-commandline
  ```

```bash
cd mobile-tests
mvn test
```

#### API Tests

- Ensure **Java 21+** and **Maven** are installed.
- Run API tests using Maven (Rest-Assured + TestNG):

```bash
cd api-tests
mvn test
```

<sup>_(Optional: If you add Postman collections, you can run them with Newman as well.)_</sup>

---

## ⚙️ GitHub Actions & Reporting

CI is configured to:
- Install dependencies for each test suite
- For mobile: set up an Android emulator, install Appium and drivers, and run tests on the emulator
- **Cache Maven dependencies** for faster builds
- Generate and upload Allure HTML reports as workflow artifacts for mobile and API tests
- Generate and upload TestNG HTML reports for mobile and API tests
- Generate and upload Playwright HTML reports for web tests
- Show pass/fail badges for each workflow in this README

**To view results:**
1. Click on the **Actions** tab above
2. Open the latest workflow run (e.g., "Mobile Tests", "Playwright Tests", or "API Tests")
3. Download the relevant artifact:
   - `allure-report` (mobile or API)
   - `testng-html-report` (mobile or API)
   - `playwright-report` (web)

**Web Test Reports:**  
After running Playwright tests, view the HTML report locally at `web-tests/playwright-report/index.html` or download the `playwright-report` artifact from CI and open `index.html` in your browser.

---

## 📸 Example Artifacts

- ✅ Playwright screenshots and HTML reports (`web-tests/playwright-report/index.html`)
- ✅ API test logs and response validation (Rest-Assured)
- ✅ Allure HTML reports for mobile and API tests (downloadable from CI)
- ✅ TestNG HTML reports for mobile and API tests (downloadable from CI)
- ⏳ Video recording (optional, coming soon)

---

## ⚙️ Configuration and Parameterization

By default, the mobile tests use a specific Wikipedia APK (`src/test/resources/apks/org.wikipedia_50530.apk`) and a fixed device name (`emulator-5554`).  
For demonstration purposes, these values are set in [`mobile-tests/config.properties`](mobile-tests/config.properties).

**Production Recommendation:**  
In a real-world setup, you should inject these parameters (such as APK path and device name) via:
- **Environment variables** (e.g., `-DdeviceName=... -DapkPath=...`)
- **Maven profiles** or CI/CD pipeline variables
- **Config files** (excluded from version control for sensitive or environment-specific data)

This allows you to:
- Run tests on different devices (emulators or physical)
- Test different app versions without code changes
- Integrate seamlessly with CI/CD pipelines

**Example usage:**
```sh
mvn test -DdeviceName=emulator-1234 -DapkPath=/path/to/another.apk
```

---

## 📚 Technologies Used

| Area           | Tools/Frameworks                     |
|----------------|--------------------------------------|
| Web Automation | Playwright, Node.js, HTML Reports    |
| Mobile Testing | Appium 2.x, Java, TestNG, Maven, Allure, TestNG HTML, Android Emulator |
| API Testing    | Rest-Assured, TestNG, Allure <br>_(Optional: Postman, Newman)_ |
| CI/CD          | GitHub Actions, YAML Pipelines, Maven Caching       |

---

## ⚙️ Local Cross-Platform Appium Server

- On **Windows**, the Appium server is started automatically by the test suite (using the `APPIUM_MAIN_JS` environment variable for the path to Appium's main.js).
- On **Linux/macOS**, you must start the Appium server manually before running tests:
  ```sh
  appium
  ```
- To make the setup generic, set the `APPIUM_MAIN_JS` environment variable to the path of your Appium installation (e.g., `C:\Users\youruser\node_modules\appium\build\lib\main.js` on Windows, or `/usr/local/lib/node_modules/appium/build/lib/main.js` on macOS/Linux).

**Example:**
```sh
# Windows (PowerShell)
$env:APPIUM_MAIN_JS="C:\Users\youruser\node_modules\appium\build\lib\main.js"
# macOS/Linux (bash)
export APPIUM_MAIN_JS="/usr/local/lib/node_modules/appium/build/lib/main.js"
```

---

## 🧠 Author

**Dane Wilbur**  
QA Engineer | Automation Enthusiast | CI/CD Advocate  
[LinkedIn](https://www.linkedin.com/in/danevader) | [GitHub](https://github.com/script-hermit)

---
