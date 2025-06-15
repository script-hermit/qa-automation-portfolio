import { test, expect } from '@playwright/test';

test('homepage has expected title', async ({ page }) => {
  await page.goto('https://demo.playwright.dev/todomvc');
  await expect(page).toHaveTitle(/TodoMVC/);
});
