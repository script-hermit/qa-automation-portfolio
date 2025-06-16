package com.WikiDemo.tests;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.WikiDemo.base.BaseTest;

import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;
// Add this to your imports if not already
import io.appium.java_client.android.AndroidDriver;

public class WikipediaSearchTest extends BaseTest {

    @BeforeMethod
    public void prepareApp(Method method) {
        System.out.println("[BeforeMethod] Resetting app for test: " + method.getName());
        deviceHelper.resetApp();  // ✅ ensures a clean state
        skipOnboardingIfPresent();  // ✅ skips tutorial screen
    }

    @Test(dataProvider = "searchTerms")
    @Description("Searches for terms in the Wikipedia app and verifies results")
    public void testSearchForTerm(String term) {
        performSearch(term);
        verifyResults();
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

    @Step("Skip onboarding screen if present")
    public void skipOnboardingIfPresent() {
        // Temporarily set implicit wait to 0
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement skipButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia:id/fragment_onboarding_skip_button"))
            );
            skipButton.click();
            System.out.println("[INFO] Onboarding skipped.");
        } catch (Exception e) {
            System.out.println("[INFO] Onboarding not present or already skipped.");
        } finally {
            // Restore implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
        // Wait for search container to be present (can use a normal wait here)
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia:id/search_container")));
    }

    @Step("Search for term: {term}")
    public void performSearch(String term) {
        driver.findElement(By.id("org.wikipedia:id/search_container")).click();
        driver.findElement(By.id("org.wikipedia:id/search_src_text")).sendKeys(term);
    }

   @Step("Verify at least one search result appears")
    public void verifyResults() {
        List<WebElement> results = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue(results.size() > 0, "Expected search results to appear.");

        // Press back button after results are verified
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
            System.out.println("[INFO] Back button pressed to return to previous screen.");
            
            // Optional: Wait for the search field to reappear, indicating return to main screen
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia:id/search_container")));
        } else {
            System.out.println("[WARN] Driver is not an instance of AndroidDriver.");
        }
}


}
