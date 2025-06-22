// page-objects/MainPage.ts
import { Page, Locator } from '@playwright/test';

export class MainPage {
  readonly page: Page;
  readonly searchInput: Locator;
  readonly searchLanguageSelect: Locator;
  readonly searchButton: Locator;
  readonly centralLogo: Locator;
  readonly languageLinks: Locator;
  readonly pageTitle: string = 'Wikipedia';

  constructor(page: Page) {
    this.page = page;
    // The main search bar (center of portal)
    this.searchInput = page.locator('input#searchInput');
    // The language dropdown next to the search bar
    this.searchLanguageSelect = page.locator('select#searchLanguage');
    // The search button (magnifying glass)
    this.searchButton = page.locator('button.pure-button.pure-button-primary-progressive');
    // The central Wikipedia globe logo
    this.centralLogo = page.locator('.central-featured-logo');
    // List of all prominent language links ("English", "Español", etc.)
    this.languageLinks = page.locator('.central-featured-lang a');
  }

  async goto() {
    await this.page.goto('https://www.wikipedia.org/');
  }

  async search(term: string, languageCode: string = 'en') {
    await this.searchInput.fill(term);
    await this.searchLanguageSelect.selectOption(languageCode);
    await this.searchButton.click();
  }

  // Get a language link by visible text, e.g., "English"
  languageLink(lang: string): Locator {
    return this.page.locator('.central-featured-lang a', { hasText: lang });
  }
}
