package com.example.base;

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
        // Only start Appium locally if the property is set
        if (Boolean.getBoolean("startLocalAppium")) {
            System.out.println("[Suite] Starting Appium Server...");
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File("C:\\Users\\danev\\node_modules\\appium\\build\\lib\\main.js"))
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .build();
            service.start();
        } else {
            System.out.println("[Suite] Skipping Appium server startup (handled by workflow or externally).");
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
