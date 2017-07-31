import { browser, by, element } from 'protractor';

export class Embrapa.Share.WebPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }
}
