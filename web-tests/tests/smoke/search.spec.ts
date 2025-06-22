import { test, expect } from '@playwright/test';
import { MainPage } from '../../page-objects/MainPage';
import { ArticlePage } from '../../page-objects/ArticlePage';

test.describe('Wikipedia.org Search', () => {
  test('redirects to correct article for exact match', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await mainPage.search('Selenium', 'en'); // 'en' = English

    // Now we should be on en.wikipedia.org with the Selenium article loaded
    const articlePage = new ArticlePage(page);
    await expect(page).toHaveURL(/en\.wikipedia\.org\/wiki\/Selenium/);
    await expect(articlePage.heading).toHaveText(/Selenium/i);
    await expect(articlePage.firstParagraph).toBeVisible();
  });

  test('redirects to search results for ambiguous term', async ({ page }) => {
    const mainPage = new MainPage(page);
    await mainPage.goto();
    await mainPage.search('Selen', 'en'); // Less exact

    // Now on en.wikipedia.org, either a disambiguation page or search results
    await expect(page).toHaveURL(/en\.wikipedia\.org/);

    // Check that the page contains search results or a disambiguation heading
    // Try to find a heading or result list
    const heading = page.locator('#firstHeading');
    await expect(heading).toContainText(/Selen/i);

    // Optionally, check the presence of a search results list
    const searchResults = page.locator('#bodyContent');
    await expect(searchResults.first()).toBeVisible();
  });
});
