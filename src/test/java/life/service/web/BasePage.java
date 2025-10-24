package life.service.web;

import com.microsoft.playwright.Page;

import static life.utils.config.ProdConfig.prodConfig;

public abstract class BasePage<T extends BasePage<?>> {

    protected Page page;

    public abstract T checkThatPageLoaded();

    public void setAndConfigurePage(final Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Playwright page instance must not be null");
        }
        this.page = page;
        page.setDefaultNavigationTimeout(prodConfig().navigationTimeout());
        page.setDefaultTimeout(prodConfig().timeout());
    }

    public abstract void initComponents();

    protected Page page() {
        return page;
    }
}
