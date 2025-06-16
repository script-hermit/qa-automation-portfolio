package com.example.utils;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class DeviceHelper {
    private AppiumDriver driver;

    public DeviceHelper(AppiumDriver driver) {
        this.driver = driver;
    }

    public void resetApp() {
        if (isAndroid()) {
            ((AndroidDriver) driver).runAppInBackground(Duration.ofSeconds(0));
            try {
                ((AndroidDriver) driver).activateApp("org.wikipedia");
            } catch (Exception e) {
                System.err.println("Unable to resolve the launchable activity of 'org.wikipedia'. Original error: " + e.getMessage());
            }
        }
    }

    private boolean isAndroid() {
        return driver instanceof io.appium.java_client.android.AndroidDriver;
    }

    public void uninstallApp(String appPackage) {
        if (driver instanceof io.appium.java_client.android.AndroidDriver) {
            ((io.appium.java_client.android.AndroidDriver) driver).removeApp(appPackage);
        } else if (driver instanceof io.appium.java_client.ios.IOSDriver) {
            ((io.appium.java_client.ios.IOSDriver) driver).removeApp(appPackage);
        } else {
            throw new UnsupportedOperationException("removeApp is not supported for this driver type");
        }
    }

    public void installApp(String appPath) {
        if (driver instanceof io.appium.java_client.android.AndroidDriver) {
            ((io.appium.java_client.android.AndroidDriver) driver).installApp(appPath);
        } else if (driver instanceof io.appium.java_client.ios.IOSDriver) {
            ((io.appium.java_client.ios.IOSDriver) driver).installApp(appPath);
        } else {
            throw new UnsupportedOperationException("installApp is not supported for this driver type");
        }
    }

    public boolean isAppInstalled(String appPackage) {
        if (driver instanceof io.appium.java_client.android.AndroidDriver) {
            return ((io.appium.java_client.android.AndroidDriver) driver).isAppInstalled(appPackage);
        } else if (driver instanceof io.appium.java_client.ios.IOSDriver) {
            return ((io.appium.java_client.ios.IOSDriver) driver).isAppInstalled(appPackage);
        } else {
            throw new UnsupportedOperationException("isAppInstalled is not supported for this driver type");
        }
    }
}
