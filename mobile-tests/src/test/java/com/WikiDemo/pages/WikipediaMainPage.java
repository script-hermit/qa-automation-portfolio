package com.wikidemo.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.wikidemo.utils.DeviceHelper;

public class WikipediaMainPage {
    private final AppiumDriver driver;

    // Locators
    private final By skipButton = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
    private final By searchContainer = By.id("org.wikipedia:id/search_container");
    private final By searchInput = By.id("org.wikipedia:id/search_src_text");
    private final By searchResultTitle = By.id("org.wikipedia:id/page_list_item_title");

    public WikipediaMainPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void skipOnboardingIfPresent(DeviceHelper deviceHelper) {
        try {
            WebElement skip = deviceHelper.waitForElementVisible(skipButton, 10);
            skip.click();
        } catch (Exception e) {
            // Onboarding not present
        }
        deviceHelper.waitForElementVisible(searchContainer, 10);
    }

    public void enterSearchTerm(DeviceHelper deviceHelper, String term) {
        deviceHelper.waitForElementClickable(searchContainer, 5).click();
        deviceHelper.waitForElementVisible(searchInput, 5).sendKeys(term);
    }

    public java.util.List<WebElement> getSearchResults(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementVisible(searchResultTitle, 10);
        return driver.findElements(searchResultTitle);
    }
}
