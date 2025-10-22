package life.ffin.page;

import io.qameta.allure.Step;
import life.service.web.BasePage;

public class MainPage extends BasePage<MainPage> {

    @Step("Проверить хедер на главной странице")
    public MainPage checkMainHeader() {
        page.locator(".header").isVisible();
        return this;
    }

    @Override
    public MainPage checkThatPageLoaded() {
        page.locator(".header").isVisible();
        return this;
    }
}
