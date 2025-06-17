package com.wikidemo.tests;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wikidemo.base.BaseTest;
import com.wikidemo.pages.WikipediaMainPage;

import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;

public class WikipediaSearchTest extends BaseTest {

    private WikipediaMainPage mainPage;

    @BeforeMethod
    public void prepareApp(Method method) {
        System.out.println("[BeforeMethod] Resetting app for test: " + method.getName());
        deviceHelper.resetApp();
        mainPage = new WikipediaMainPage(driver);
        mainPage.skipOnboardingIfPresent(deviceHelper);
    }

    @Test(dataProvider = "searchTerms", description = "Verify Wikipedia search returns results for various terms")
    @Description("Searches for each provided term in the Wikipedia app and verifies that results are displayed.")
    public void shouldDisplayResultsForSearchTerm(String term) {
        mainPage.enterSearchTerm(deviceHelper, term);
        verifyResults(term);
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
            {"Appium"},
            {"TestNG"},
            {"Android"},
            {"Selenium"}
        };
    }

    @Step("Verify at least one search result appears for term: {term}")
    public void verifyResults(String term) {
        List<WebElement> results = mainPage.getSearchResults(deviceHelper);
        Assert.assertTrue(
            results.size() > 0,
            "Expected at least one search result to appear for term: '" + term + "', but none were found."
        );

        // Press back button after results are verified
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
            System.out.println("[INFO] Back button pressed to return to previous screen.");

            // Wait for the search field to reappear, indicating return to main screen
            mainPage.skipOnboardingIfPresent(deviceHelper); // Ensures main screen is ready
        } else {
            System.out.println("[WARN] Driver is not an instance of AndroidDriver.");
        }
    }
}
