package life.superapp.test.web;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import life.core.web.BasePage;
import life.utils.BasePageFactory;

public class IokaHomePage extends BasePage<IokaHomePage> {

    private Locator loginButton;
    private Locator connectButton;
    private Locator mainHeroTitle;

    @Override
    public IokaHomePage checkThatPageLoaded() {
        mainHeroTitle.waitFor();
        return this;
    }

    @Override
    public void initComponents() {
        loginButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Войти"));
        connectButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Подключиться"));
        mainHeroTitle = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Решение для всех"));

    }

    public IokaHomePage openLogin() {
        loginButton.click();
        return BasePageFactory.createInstance(page, IokaLoginPage.class);
    }

    public IokaHomePage openConnectForm() {
        connectButton.click();
        return BasePageFactory.createInstance(page, IokaConnectFormPage.class);
    }
}
