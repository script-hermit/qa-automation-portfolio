package com.wikidemo.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.wikidemo.utils.DeviceHelper;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;

public class BaseTest {

    protected AppiumDriver driver;
    protected DeviceHelper deviceHelper;
    private static final String APP_PACKAGE = "org.wikipedia";
    private String apkPath;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    @BeforeClass
    public void setUpDriver() throws Exception {
        // Load properties from mobile-tests/config.properties if present
        Properties props = new Properties();
        Path configPath = Paths.get("mobile-tests", "config.properties");
        if (Files.exists(configPath)) {
            try (FileInputStream fis = new FileInputStream(configPath.toFile())) {
                props.load(fis);
                LOGGER.info("Loaded properties from mobile-tests/config.properties");
            }
        } else {
            LOGGER.warning("mobile-tests/config.properties not found, using defaults and system properties.");
        }

        // Allow override via system properties
        String deviceName = System.getProperty("deviceName", props.getProperty("deviceName", "emulator-5554"));
        apkPath = System.getProperty(
            "apkPath",
            props.getProperty(
                "apkPath",
                Paths.get("src", "test", "resources", "apks", "org.wikipedia_50530.apk")
                    .toAbsolutePath()
                    .toString()
            )
        );

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(deviceName)
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setApp(apkPath)
                .autoGrantPermissions()
                .setAppPackage(APP_PACKAGE)
                .setAppActivity("org.wikipedia.main.MainActivity")
                .setAppWaitActivity("*")
                .amend("fullReset", true)
                .amend("noReset", false)
                .amend("androidInstallTimeout", 180000);

        URL url = URI.create("http://127.0.0.1:4723").toURL();
        driver = new AndroidDriver(url, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        deviceHelper = new DeviceHelper(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            try {
                // Ensure deviceHelper is initialized before use
                if (deviceHelper == null) {
                    deviceHelper = new DeviceHelper(driver);
                }
                if (deviceHelper.isAppInstalled(APP_PACKAGE)) {
                    LOGGER.info("[INFO] Uninstalling app: " + APP_PACKAGE);
                    deviceHelper.uninstallApp(APP_PACKAGE);
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "[WARN] App uninstall failed: " + e.getMessage(), e);
            } finally {
                try {
                    if (deviceHelper == null) {
                        deviceHelper = new DeviceHelper(driver);
                    }
                    deviceHelper.resetApp();  // ✅ ensures a clean state
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "[WARN] App reset failed: " + e.getMessage(), e);
                }
                driver.quit();
            }
        }
    }
}
