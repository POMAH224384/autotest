package life.service.web;

import com.microsoft.playwright.Page;

import static life.utils.config.ProdConfig.prodConfig;

public abstract class BasePage<T extends BasePage<?>> {

    protected Page page;

    public abstract T checkThatPageLoaded();

    public void setAndConfigurePage(final Page page) {
        this.page = page;
        page.setDefaultNavigationTimeout(prodConfig().timeout());
    }

    public void initComponents() {}
}
