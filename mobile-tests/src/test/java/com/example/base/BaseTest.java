package com.example.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;

    @BeforeClass
    public void setUpDriver() throws Exception {
        String apkPath = Paths.get("src", "test", "resources", "apks", "org.wikipedia_50530.apk")
                              .toAbsolutePath()
                              .toString();

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setApp(apkPath)
                .setAppWaitActivity("org.wikipedia.onboarding.InitialOnboardingActivity")
                .autoGrantPermissions();


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
