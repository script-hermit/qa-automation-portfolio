package com.wikidemo.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.wikidemo.utils.DeviceHelper;

import java.util.List;

public class WikipediaMainPage {
    private final AppiumDriver driver;

    // Locators
    private final By skipButton = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
    private final By searchContainer = By.id("org.wikipedia:id/search_container");
    private final By searchInput = By.id("org.wikipedia:id/search_src_text");
    private final By searchResultTitle = By.id("org.wikipedia:id/page_list_item_title");

    // Additional locators for regression suite
    private final By moreOptions = By.xpath("//android.widget.ImageView[@content-desc='More options']");
    private final By settingsMenu = By.xpath("//android.widget.TextView[@text='Settings']");
    private final By languageSettings = By.xpath("//android.widget.TextView[@text='Wikipedia languages']");
    private final By addLanguageButton = By.id("org.wikipedia:id/addLanguageButton");
    private final By readingListMenu = By.xpath("//android.widget.TextView[@text='My lists']");
    private final By saveButton = By.xpath("//android.widget.ImageView[@content-desc='Save']");
    private final By articleTitle = By.id("org.wikipedia:id/view_page_title_text");
    private final By articleImage = By.xpath("//android.widget.ImageView");
    private final By articleContent = By.id("org.wikipedia:id/view_page_content_text");
    private final By appThemeMenu = By.xpath("//android.widget.TextView[@text='App theme']");
    private final By darkThemeOption = By.xpath("//android.widget.CheckedTextView[@text='Dark']");
    private final By lightThemeOption = By.xpath("//android.widget.CheckedTextView[@text='Light']");
    private final By mainContainer = By.id("org.wikipedia:id/main_container");

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

    public List<WebElement> getSearchResults(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementVisible(searchResultTitle, 10);
        return driver.findElements(searchResultTitle);
    }

    // --- Regression Suite Methods ---

    public void openSettings(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementClickable(moreOptions, 5).click();
        deviceHelper.waitForElementClickable(settingsMenu, 5).click();
    }

    public void openLanguageSettings(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementClickable(languageSettings, 5).click();
    }

    public void addLanguage(DeviceHelper deviceHelper, String language) {
        deviceHelper.waitForElementClickable(addLanguageButton, 5).click();
        By languageOption = By.xpath("//android.widget.TextView[@text='" + language + "']");
        deviceHelper.waitForElementClickable(languageOption, 5).click();
    }

    public boolean isLanguagePresent(DeviceHelper deviceHelper, String language) {
        By languageOption = By.xpath("//android.widget.TextView[@text='" + language + "']");
        try {
            return deviceHelper.waitForElementVisible(languageOption, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getArticleTitle(DeviceHelper deviceHelper) {
        return deviceHelper.waitForElementVisible(articleTitle, 10);
    }

    public WebElement getArticleImage(DeviceHelper deviceHelper) {
        return deviceHelper.waitForElementVisible(articleImage, 10);
    }

    public void saveArticleToReadingList(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementClickable(saveButton, 5).click();
    }

    public void openReadingList(DeviceHelper deviceHelper) {
        deviceHelper.waitForElementClickable(moreOptions, 5).click();
        deviceHelper.waitForElementClickable(readingListMenu, 5).click();
    }

    public boolean isArticleInReadingList(DeviceHelper deviceHelper, String articleTitleText) {
        By articleInList = By.xpath("//android.widget.TextView[contains(@text,'" + articleTitleText + "')]");
        try {
            return deviceHelper.waitForElementVisible(articleInList, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openArticleFromReadingList(DeviceHelper deviceHelper, String articleTitleText) {
        By articleInList = By.xpath("//android.widget.TextView[contains(@text='" + articleTitleText + "')]");
        deviceHelper.waitForElementClickable(articleInList, 5).click();
    }

    public WebElement getArticleContent(DeviceHelper deviceHelper) {
        return deviceHelper.waitForElementVisible(articleContent, 10);
    }

    public void setAppTheme(DeviceHelper deviceHelper, String theme) {
        deviceHelper.waitForElementClickable(appThemeMenu, 5).click();
        By themeOption = theme.equalsIgnoreCase("Dark") ? darkThemeOption : lightThemeOption;
        deviceHelper.waitForElementClickable(themeOption, 5).click();
    }

    public boolean isDarkModeEnabled(DeviceHelper deviceHelper) {
        // This is a placeholder: you may want to check a color attribute or a dark mode indicator
        try {
            WebElement container = deviceHelper.waitForElementVisible(mainContainer, 5);
            String bgColor = container.getCssValue("background-color");
            return bgColor != null && (bgColor.contains("0, 0, 0") || bgColor.contains("#000"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isThemeSelected(DeviceHelper deviceHelper, String theme) {
        By themeOption = By.xpath("//android.widget.CheckedTextView[@text='" + theme + "' and @checked='true']");
        try {
            return deviceHelper.waitForElementVisible(themeOption, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void goBackToMain(DeviceHelper deviceHelper) {
        driver.navigate().back();
    }
}
