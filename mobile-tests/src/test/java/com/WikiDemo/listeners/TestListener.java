package com.wikidemo.listeners;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;

import java.io.FileInputStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TestListener implements ITestListener {

    private static final String LOG_FILE = "target/test-log.txt";
    private static final String SCREENSHOT_DIR = "target/screenshots";

    private void logToFile(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + " - " + message + "\n");
        } catch (IOException e) {
            System.err.println("Could not write to test log: " + e.getMessage());
        }
    }

    private void captureScreenshot(ITestResult result) {
    Object testClass = result.getInstance();
    try {
        java.lang.reflect.Field driverField = testClass.getClass().getDeclaredField("driver");
        driverField.setAccessible(true);
        AppiumDriver driver = (AppiumDriver) driverField.get(testClass);

        if (driver != null) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filename = SCREENSHOT_DIR + "/" + result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + ".png";
            File targetFile = new File(filename);
            FileUtils.copyFile(screenshot, targetFile);
            logToFile("[SCREENSHOT] Saved to: " + filename);

            // 👉 Attach to Allure
            Allure.addAttachment("Screenshot", new FileInputStream(targetFile));
        }
        } catch (Exception e) {
            logToFile("[ERROR] Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String name = result.getMethod().getMethodName();
        logToFile("[FAIL] Test: " + name + " - " + result.getThrowable());
        captureScreenshot(result);
        // Attach log file to Allure on failure
        try (FileInputStream logStream = new FileInputStream(LOG_FILE)) {
            Allure.addAttachment("Test Log", logStream);
        } catch (IOException e) {
            System.err.println("Could not attach log to Allure: " + e.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        logToFile("[START] Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logToFile("[PASS] Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logToFile("[SKIP] Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        new File(SCREENSHOT_DIR).mkdirs();  // create screenshot folder
        logToFile("=== TEST SUITE STARTED ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        logToFile("=== TEST SUITE FINISHED ===");
        // Attach log file to Allure at suite end
        try (FileInputStream logStream = new FileInputStream(LOG_FILE)) {
            Allure.addAttachment("Full Test Log", logStream);
        } catch (IOException e) {
            System.err.println("Could not attach log to Allure: " + e.getMessage());
        }
    }
}
