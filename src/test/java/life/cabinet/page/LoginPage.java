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

    private final Locator usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("ИИН ИИН"));
    private final Locator passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль Пароль"));
    private final Locator introducedButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ознакомлен"));
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"));
    private final Locator homeHeaderImg = page.locator("header")
            .filter(new Locator.FilterOptions().setHasText("Заявить о страховом случае")).getByRole(AriaRole.IMG).first();
    private final Locator modalWindow = page.locator(".confirmation-dialog__body");
    private final Locator errorContainer = page.locator(".v-card");
    private final Locator errorText = page.getByRole(AriaRole.DIALOG);

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
