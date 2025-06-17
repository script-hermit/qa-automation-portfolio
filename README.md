# qa-automation-portfolio

[![Playwright Tests](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/playwright.yml/badge.svg)](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/playwright.yml)
[![Mobile Tests](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/mobile-test.yml/badge.svg)](https://github.com/script-hermit/qa-automation-portfolio/actions/workflows/mobile-test.yml)

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

- **Web Testing**: Automated UI tests for modern web apps (e.g., login flows, form validation) using Playwright.
- **Mobile Testing**: Android emulator-based tests using Appium 2.x, Java, and TestNG. CI runs on GitHub Actions with a real emulator and Allure HTML reporting.
- **API Testing**: REST API tests via Rest-Assured (Java + TestNG) with data-driven assertions and Allure reporting.  
  <sup>_(Optional: You can also add Postman collections and Newman if desired.)_</sup>
- **CI/CD Integration**: GitHub Actions workflows for all test suites, including mobile tests on an Android emulator.
- **Artifacts & Reporting**: Screenshots, logs, and HTML test reports (including Allure) exported from CI as downloadable artifacts.

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

## ⚙️ GitHub Actions

CI is configured to:
- Install dependencies for each test suite
- For mobile: set up an Android emulator, install Appium and drivers, and run tests on the emulator
- Generate and upload Allure HTML reports as workflow artifacts
- Show pass/fail badges for each workflow in this README

To view results:
1. Click on the **Actions** tab above
2. Open the latest workflow run (e.g., "Mobile Tests with Allure")
3. Download the `allure-report` artifact for mobile or API test results

---

## 📸 Example Artifacts

- ✅ Playwright screenshots and HTML reports
- ✅ API test logs and response validation (Rest-Assured)
- ✅ Allure HTML reports for mobile and API tests (downloadable from CI)
- ⏳ Video recording (optional, coming soon)

---

## 📚 Technologies Used

| Area           | Tools/Frameworks                     |
|----------------|--------------------------------------|
| Web Automation | Playwright, Node.js, HTML Reports    |
| Mobile Testing | Appium 2.x, Java, TestNG, Maven, Allure, Android Emulator |
| API Testing    | Rest-Assured, TestNG, Allure <br>_(Optional: Postman, Newman)_ |
| CI/CD          | GitHub Actions, YAML Pipelines       |

---

## 🧠 Author

**Dane Wilbur**  
QA Engineer | Automation Enthusiast | CI/CD Advocate  
[LinkedIn](https://www.linkedin.com/in/danevader) | [GitHub](https://github.com/script-hermit)

---
