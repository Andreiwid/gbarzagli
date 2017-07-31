import { Embrapa.Share.WebPage } from './app.po';

describe('embrapa.share.web App', () => {
  let page: Embrapa.Share.WebPage;

  beforeEach(() => {
    page = new Embrapa.Share.WebPage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to app!!'))
      .then(done, done.fail);
  });
});
