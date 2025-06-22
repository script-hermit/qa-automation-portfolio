import { Page, Locator } from '@playwright/test';

export class ArticlePage {
  readonly page: Page;
  readonly heading: Locator;
  readonly firstParagraph: Locator;
  readonly firstInternalLink: Locator;

  constructor(page: Page) {
    this.page = page;
    this.heading = page.locator('#firstHeading');
    this.firstParagraph = page.locator('#mw-content-text > div.mw-content-ltr.mw-parser-output > p:nth-child(8)').first();
    this.firstInternalLink = page.locator('#mw-content-text > div.mw-content-ltr.mw-parser-output > p:nth-child(8) > a:nth-child(2)').first();
  }
}


