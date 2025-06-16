package com.WikiDemo.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.WikiDemo.utils.DeviceHelper;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

    protected AppiumDriver driver;
    protected DeviceHelper deviceHelper;
    private static final String APP_PACKAGE = "org.wikipedia";
    private String apkPath;

    @BeforeClass
    public void setUpDriver() throws Exception {
        apkPath = Paths.get("src", "test", "resources", "apks", "org.wikipedia_50530.apk")
                .toAbsolutePath()
                .toString();

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setApp(apkPath)
                .autoGrantPermissions()
                .setAppPackage(APP_PACKAGE)
                .setAppActivity("org.wikipedia.main.MainActivity")
                .setAppWaitActivity("*")
                .amend("fullReset", true)
                .amend("noReset", false)
                .amend("androidInstallTimeout", 180000); // <-- Add this line (3 minutes)

        URL url = URI.create("http://127.0.0.1:4723").toURL();
        driver = new AndroidDriver(url, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        deviceHelper = new DeviceHelper(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            try {
                if (deviceHelper.isAppInstalled(APP_PACKAGE)) {
                    System.out.println("[INFO] Uninstalling app: " + APP_PACKAGE);
                    deviceHelper.uninstallApp(APP_PACKAGE);
                }
            } catch (Exception e) {
                System.out.println("[WARN] App uninstall failed: " + e.getMessage());
            } finally {
                deviceHelper.resetApp();  // ✅ ensures a clean state
                driver.quit();
            }
        }
    }
}
