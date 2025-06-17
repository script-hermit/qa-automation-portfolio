package com.wikidemo.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class TestSuiteSetup {

    public static AppiumDriverLocalService service;

    @BeforeSuite
    public void startAppiumServer() {
        String appiumJsPath = System.getenv("APPIUM_MAIN_JS");
        String os = System.getProperty("os.name").toLowerCase();

        if (appiumJsPath != null && !appiumJsPath.isEmpty()) {
            System.out.println("[Suite] Starting Appium server using APPIUM_MAIN_JS: " + appiumJsPath);
            service = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumJsPath))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
            service.start();
        } else {
            System.out.println("[Suite][INFO] APPIUM_MAIN_JS environment variable not set. Please start Appium manually.");
            System.out.println("[Suite][INFO] Example: appium");
        }

        System.out.println("[Suite] Clearing Allure results...");
        try {
            Path allureResults = Paths.get("allure-results");
            if (Files.exists(allureResults)) {
                Files.walk(allureResults)
                    .map(Path::toFile)
                    .sorted((a, b) -> -a.compareTo(b)) // delete files before dirs
                    .forEach(File::delete);
            }
        } catch (IOException e) {
            System.out.println("[WARN] Could not clear allure-results: " + e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServer() {
        System.out.println("[Suite] Stopping Appium Server...");
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
}
