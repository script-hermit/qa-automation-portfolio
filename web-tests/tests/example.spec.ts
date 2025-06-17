import { test, expect } from '@playwright/test';
import { TodoPage } from '../page-objects/TodoPage';

test('homepage has expected title', async ({ page }) => {
  const todoPage = new TodoPage(page);
  await todoPage.goto();
  await expect(page).toHaveTitle(/TodoMVC/);
});
