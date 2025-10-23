package life.cabinet.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import life.service.web.BasePage;
import lombok.NonNull;

import javax.annotation.Nonnull;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage<LoginPage> {



    private Locator usernameInput;
    private Locator passwordInput;
    private Locator introducedButton;
    private Locator submitButton;
    private Locator homeHeaderImg;
    private Locator modalWindow;
    private Locator errorContainer;
    private Locator errorText;

    @Override
    public void initComponents() {
        usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("ИИН ИИН"));
        passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль Пароль"));
        introducedButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ознакомлен"));
        submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"));
        homeHeaderImg = page.locator("header")
                .filter(new Locator.FilterOptions().setHasText("Заявить о страховом случае")).getByRole(AriaRole.IMG).first();
        modalWindow = page.locator(".confirmation-dialog__body");
        errorContainer = page.locator(".v-card");
        errorText = page.getByRole(AriaRole.DIALOG);
    }

    @Step("Ввести ИИН: {0}")
    @Nonnull
    public LoginPage setUsername(String username) {
        usernameInput.fill(username);
        return this;
    }

    @Step("Ввести пароль: {0}")
    @Nonnull
    public LoginPage setPassword(String password) {
        passwordInput.fill(password);
        return this;
    }

    @Step("Кликнуть на кнопку 'Ознакомлен'")
    @NonNull
    public LoginPage introduce() {
        introducedButton.click();
        return this;
    }

    @Step("Кликнуть на кнопку 'Войти'")
    @Nonnull
    public <T extends BasePage<?>> T submit(T expectedPage) {
        submitButton.click();
        expectedPage.setAndConfigurePage(page);
        expectedPage.initComponents();
        return expectedPage;
    }

    @Step("Проверить что страница загрузилась")
    @Override
    @Nonnull
    public LoginPage checkThatPageLoaded() {
        modalWindow.isVisible();
        return this;
    }

    @Step("Проверить что вышла ошибка: {0}")
    @Nonnull
    public LoginPage checkError(String expectedText) {
        assertThat(errorContainer).isVisible();
        assertThat(errorText).containsText(expectedText);

        return this;
    }
}
