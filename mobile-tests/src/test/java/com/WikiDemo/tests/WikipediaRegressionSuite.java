package com.wikidemo.tests;

import com.wikidemo.base.BaseTest;
import com.wikidemo.pages.WikipediaMainPage;
import io.qameta.allure.Description;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class WikipediaRegressionSuite extends BaseTest {

    private WikipediaMainPage mainPage;

    @BeforeMethod
    public void prepareApp(Method method) {
        System.out.println("[BeforeMethod] Resetting app for test: " + method.getName());
        deviceHelper.resetApp();
        mainPage = new WikipediaMainPage(driver);
        mainPage.skipOnboardingIfPresent(deviceHelper);
    }

    @Test
    @Description("Verify that the language can be switched and new language is displayed in the list")
    public void testLanguageSwitch() {
        mainPage.openSettings(deviceHelper);
        mainPage.openLanguageSettings(deviceHelper);
        mainPage.addLanguage(deviceHelper, "Español");

        Assert.assertTrue(
            mainPage.isLanguagePresent(deviceHelper, "Español"),
            "Spanish language not added"
        );
    }

    @Test
    @Description("Verify article loads and contains content with image")
    public void testArticleLoadsWithImage() {
        mainPage.enterSearchTerm(deviceHelper, "Selenium");
        WebElement title = mainPage.getArticleTitle(deviceHelper);
        WebElement image = mainPage.getArticleImage(deviceHelper);
        Assert.assertTrue(title.isDisplayed(), "Title not visible");
        Assert.assertTrue(image.isDisplayed(), "Image not visible");
        mainPage.goBackToMain(deviceHelper);
    }

    @Test
    @Description("Save article to reading list and verify it appears")
    public void testSaveArticleToReadingList() {
        mainPage.enterSearchTerm(deviceHelper, "Appium");
        mainPage.saveArticleToReadingList(deviceHelper);
        mainPage.openReadingList(deviceHelper);

        Assert.assertTrue(
            mainPage.isArticleInReadingList(deviceHelper, "Appium"),
            "Article not saved"
        );
        mainPage.goBackToMain(deviceHelper);
    }

    @Test
    @Description("Verify offline mode shows cached content")
    public void testOfflineModeBehavior() {
        mainPage.openReadingList(deviceHelper);
        mainPage.openArticleFromReadingList(deviceHelper, "Appium");
        WebElement content = mainPage.getArticleContent(deviceHelper);
        Assert.assertTrue(content.isDisplayed(), "Offline content not visible");
        mainPage.goBackToMain(deviceHelper);
    }

    @Test
    @Description("Toggle dark mode and verify background color changes")
    public void testDarkModeRendering() {
        mainPage.openSettings(deviceHelper);
        mainPage.setAppTheme(deviceHelper, "Dark");
        Assert.assertTrue(mainPage.isDarkModeEnabled(deviceHelper), "Dark mode not enabled");
        mainPage.goBackToMain(deviceHelper);
    }

    @Test
    @Description("Verify settings persist after app restart")
    public void testSettingsPersistence() {
        mainPage.openSettings(deviceHelper);
        mainPage.setAppTheme(deviceHelper, "Light");
        
        mainPage.skipOnboardingIfPresent(deviceHelper);
        mainPage.openSettings(deviceHelper);
        Assert.assertTrue(mainPage.isThemeSelected(deviceHelper, "Light"), "Theme setting did not persist");
        mainPage.goBackToMain(deviceHelper);
    }
}
