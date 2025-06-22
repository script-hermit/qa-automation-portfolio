import { test, expect } from '@playwright/test';
import { MainPage } from '../../page-objects/MainPage';

test.describe('Wikipedia portal responsive design', () => {
  // Set viewport to a typical mobile size (e.g., iPhone X)
  test.use({ viewport: { width: 375, height: 812 } });

  test('homepage loads and main elements are visible on mobile', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();

    await expect(mainPage.centralLogo).toBeVisible();
    await expect(mainPage.searchInput).toBeVisible();
    await expect(mainPage.searchLanguageSelect).toBeVisible();
    await expect(mainPage.languageLinks).toHaveCount(10);

    // Optionally check that the page is rendering in a mobile-friendly way
    // (e.g., hamburger menu present if Wikipedia ever adds one to this page)
  });

  test('can perform search from mobile viewport', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();

    await mainPage.search('Selenium', 'en');
    await expect(page).toHaveURL(/en\.wikipedia\.org\/wiki\/Selenium/);
  });
});
