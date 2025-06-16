package com.example.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;
    private AppiumDriverLocalService service;

    

    @BeforeClass
    public void startAppiumServerAndDriver() throws Exception {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\danev\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();

        String apkPath = Paths.get("src", "test", "resources", "apks", "ApiDemos-debug.apk")
                              .toAbsolutePath()
                              .toString();

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setApp(apkPath)
                .autoGrantPermissions();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        if (service != null) service.stop();
    }
}
