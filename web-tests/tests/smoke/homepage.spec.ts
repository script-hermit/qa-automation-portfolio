import { test, expect } from '@playwright/test';
import { MainPage } from '../../page-objects/MainPage';

test.describe('Wikipedia.org Homepage', () => {
  test('loads successfully', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await expect(page).toHaveTitle(/Wikipedia/);
    await expect(mainPage.centralLogo).toBeVisible();
  });

  test('displays main search bar', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await expect(mainPage.searchInput).toBeVisible();
    await expect(mainPage.searchInput).toBeEditable();
  });

  test('shows language selector dropdown', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await expect(mainPage.searchLanguageSelect).toBeVisible();
    // Optional: check for presence of popular language (e.g., "English" option exists)
    await expect(mainPage.searchLanguageSelect).toContainText('English');
  });

  test('displays all main language links', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await expect(mainPage.languageLinks).toHaveCount(10); // 10 main featured languages
    // Check that "English" is one of the prominent languages
    await expect(mainPage.languageLink('English')).toBeVisible();
  });
});
