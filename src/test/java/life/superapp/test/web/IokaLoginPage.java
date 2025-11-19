package life.superapp.test.web;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import life.core.web.BasePage;

public class IokaLoginPage extends BasePage<IokaLoginPage> {

    private Locator emailInput;
    private Locator passwordInput;
    private Locator submitButton;
    private Locator errorMessage;

    @Override
    public void initComponents() {
        emailInput = page.locator("input[name*='email'], input[type='email']").first();
        passwordInput = page.locator("input[type='password']").first();
        submitButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Войти"));
        errorMessage = page.locator(".error, [data-error], .v-messages__message").first();
    }

    @Override
    public IokaLoginPage checkThatPageLoaded() {
        emailInput.waitFor();
        passwordInput.waitFor();
        return this;
    }

    public IokaLoginPage loginWith(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        submitButton.click();
        return this;
    }

    public IokaLoginPage assertLoginErrorVisible() {
        errorMessage.waitFor();
        if (!errorMessage.isVisible()) {
            throw new AssertionError("Ожидалось сообщение об ошибке логина");
        }
        return this;
    }
}

