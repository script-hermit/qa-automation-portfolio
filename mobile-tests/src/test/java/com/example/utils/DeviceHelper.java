package com.example.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DeviceHelper {

    private final AppiumDriver driver;

    public DeviceHelper(AppiumDriver driver) {
        this.driver = driver;
    }

    public void resetApp() {
        if (isAndroid()) {
            driver.reset(); // Use reset() instead of resetApp()
        }
    }

    public void uninstallApp(String appPackage) {
        if (isAndroid()) {
            ((AndroidDriver) driver).removeApp(appPackage);
        }
    }

    public void installApp(String apkPath) {
        if (isAndroid()) {
            ((AndroidDriver) driver).installApp(apkPath);
        }
    }

    public boolean isAppInstalled(String appPackage) {
        if (isAndroid()) {
            return ((AndroidDriver) driver).isAppInstalled(appPackage);
        }
        return false;
    }

    private boolean isAndroid() {
        return driver instanceof AndroidDriver;
    }
}
