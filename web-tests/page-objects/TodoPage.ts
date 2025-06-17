// page-objects/TodoPage.ts
import { Page } from '@playwright/test';

export class TodoPage {
  constructor(private page: Page) {}

  async goto() {
    await this.page.goto('https://demo.playwright.dev/todomvc');
  }

  async getTitle() {
    return this.page.title();
  }
}