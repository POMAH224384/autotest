package life.utils;

import com.microsoft.playwright.Page;
import life.service.web.BasePage;



public final class Pages {
    private Pages() {}

    public static <T extends BasePage> T open(String url, Class<T> pageClass) {
        Page page = UiSession.context().newPage();
        UiSession.setPage(page);
        page.navigate(url);
        return BasePageFactory.createInstance(page, pageClass);
    }

    public static <T extends BasePage> T on(Class<T> pageClass) {
        var page = UiSession.page();
        if (page != null) {
            page = UiSession.context().newPage();
            UiSession.setPage(page);
        }

        return BasePageFactory.createInstance(page, pageClass);
    }

    public static Page open(String url) {
        var ctx = UiSession.context();
        var page = ctx.newPage();
        UiSession.setPage(page);
        page.navigate(url);
        return page;
    }
}
