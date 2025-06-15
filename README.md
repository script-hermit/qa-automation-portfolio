# qa-automation-portfolio

A showcase of my QA automation skills across web, mobile, and API testing. This repo highlights real-world test strategies using Playwright, Appium (Java + TestNG), Postman, and GitHub Actions for CI/CD integration.

---

## 📁 Project Structure

```
qa-automation-portfolio/
├── web-tests/             # Web UI automation with Playwright
├── mobile-tests/          # Mobile automation using Appium (Java + TestNG)
├── api-tests/             # API testing with Postman collections
├── github-actions/        # CI/CD workflows and pipelines
└── README.md              # Project overview and usage instructions
```

---

## 🧪 Features

- **Web Testing**: Automated UI tests for modern web apps (e.g., login flows, form validation)
- **Mobile Testing**: Sample tests on Android/iOS using Appium and Java with TestNG
- **API Testing**: REST API tests via Postman with data-driven assertions and reporting
- **CI/CD Integration**: GitHub Actions workflow to run tests on pull requests or schedules
- **Artifacts & Reporting**: Screenshots, logs, and HTML test reports exported from CI

---

## 🚀 Getting Started

### ✅ Clone the Repo

```bash
git clone https://github.com/yourusername/qa-automation-portfolio.git
cd qa-automation-portfolio
```

### 🔧 Install Dependencies

Each subfolder includes its own setup. Start with web tests for example:

```bash
cd web-tests
npm install
npx playwright install
```

For mobile tests, ensure Java, Maven, and Appium are installed. Then:

```bash
cd mobile-tests
mvn test
```

### ▶️ Run API Tests with Newman

```bash
cd api-tests
newman run collection.json -e environment.json
```

---

## ⚙️ GitHub Actions

CI is configured to:
- Install dependencies
- Run web/API/mobile tests
- Upload test reports as artifacts
- Show pass/fail badge in this README

To view results:
1. Click on the **Actions** tab above
2. Open the latest workflow run
3. Download artifacts for logs/screenshots

---

## 📸 Example Artifacts

Coming soon:
- ✅ Screenshot examples from Playwright
- ✅ API test logs and response validation
- ✅ HTML test reports
- ✅ Video recording (optional)

---

## 📚 Technologies Used

| Area           | Tools/Frameworks                     |
|----------------|--------------------------------------|
| Web Automation | Playwright, Node.js, HTML Reports    |
| Mobile Testing | Appium, Java, TestNG, Maven          |
| API Testing    | Postman, Newman, JSON Schemas        |
| CI/CD          | GitHub Actions, YAML Pipelines       |

---

## 🧠 Author

**Dane Wilbur**  
QA Engineer | Automation Enthusiast | CI/CD Advocate  
[LinkedIn](https://www.linkedin.com/in/dane-wilbur) | [GitHub](https://github.com/yourusername)

---

![Playwright CI](https://github.com/yourusername/qa-automation-portfolio/actions/workflows/playwright.yml/badge.svg)