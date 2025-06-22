import { test, expect } from '@playwright/test';
import { MainPage } from '../../page-objects/MainPage';
import { ArticlePage } from '../../page-objects/ArticlePage';

test('navigates from portal to article and follows an internal link', async ({ page }) => {
  const mainPage = new MainPage(page);
  await mainPage.goto();
  // Search for an article in English
  await mainPage.search('Selenium', 'en');

  // Now on en.wikipedia.org article page
  const articlePage = new ArticlePage(page);
  await expect(articlePage.heading).toHaveText(/Selenium/i);

  // Find and click the first internal link in the article's main content
  const firstInternalLink = articlePage.firstInternalLink;
  const linkedArticleTitle = await firstInternalLink.textContent();

  await firstInternalLink.click();

  // After navigation, verify heading matches linked article
  await expect(articlePage.heading).toHaveText(linkedArticleTitle?.trim() || '', { ignoreCase: true });
  await expect(page).not.toHaveURL(/Selenium/); // Should now be a different URL
});
