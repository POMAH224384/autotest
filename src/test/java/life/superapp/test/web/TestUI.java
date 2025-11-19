package life.superapp.test.web;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import life.core.jupiter.annotation.WebTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@WebTest
public class TestUI {


    @Test
    @DisplayName("1.1 Авторизация: невалидные данные → ошибка")
    void loginShouldShowErrorWithInvalidCredentials() {
        // Кликаем "Войти" в шапке
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Войти")).click();

        // Ожидаем появления формы (подкорректируй селекторы под реальный DOM)
        Locator emailInput = page.locator("input[type='email'], input[name*='email']");
        Locator passwordInput = page.locator("input[type='password'], input[name*='password']");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Войти"));

        emailInput.fill("invalid@example.com");
        passwordInput.fill("wrongPassword123");
        submitButton.click();

        // Тут нужно посмотреть реальный текст/селектор ошибки в DOM
        Locator errorMessage = page.locator("text=Неверный логин")
                .first(); // пример — подстрой под фактический текст
        assertTrue(errorMessage.isVisible(), "Ожидается сообщение об ошибке при невалидной авторизации");
    }

    // 1. Авторизация — позитивный сценарий (опционально)
    @Test
    @DisplayName("1.2 Авторизация: валидные данные → успешный вход")
    void loginShouldSucceedWithValidCredentials() {
        String login = System.getenv("IOKA_LOGIN");
        String password = System.getenv("IOKA_PASSWORD");

        // Если нет тестовых учёток – скипаем тест
        assumeTrue(login != null && !login.isBlank() &&
                        password != null && !password.isBlank(),
                "Пропускаем тест: не заданы IOKA_LOGIN/IOKA_PASSWORD");

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Войти")).click();

        Locator emailInput = page.locator("input[type='email'], input[name*='email']");
        Locator passwordInput = page.locator("input[type='password'], input[name*='password']");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Войти"));

        emailInput.fill(login);
        passwordInput.fill(password);
        submitButton.click();

        // Пример: проверяем, что нас перекинуло в кабинет/изменился заголовок/появилось меню пользователя.
        page.waitForLoadState();
        assertFalse(page.url().contains("login"),
                "После успешного входа не должны оставаться на странице логина");
    }

    // 2. UI-проверка
    @Test
    @DisplayName("2. UI: ключевые элементы шапки и главного блока видимы и кликабельны")
    void mainPageHeaderAndMenuShouldBeVisibleAndClickable() {
        // Логотип (по alt или роли)
        Locator logo = page.locator("img[alt*='ioka'], img[alt*='Ioka']");
        assertTrue(logo.isVisible(), "Логотип ioka должен быть видим");

        // Основное меню
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Продукты")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Тарифы")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("О компании")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Блог")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Разработчикам")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Войти")).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Подключиться")).isVisible());

        // CTA-кнопка «Подключиться» (верхний блок)
        Locator connectCta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Подключиться"));
        assertTrue(connectCta.isEnabled(), "Кнопка/ссылка 'Подключиться' должна быть кликабельной");
        connectCta.click();

        // После клика может открыться форма/секция — проверяем, что переход/скролл произошёл (пример)
        assertTrue(page.url().contains("ioka.kz"), "После клика по 'Подключиться' всё ещё должны быть на домене ioka.kz");
    }

    // 3. Проверка формы — негативный сценарий
    @Test
    @DisplayName("3.1 Форма контакта: пустая отправка → валидация")
    void contactFormShouldValidateOnEmptySubmit() {
        // Скроллим к блоку формы (по тексту заголовка)
        page.getByText("Готовы подключить онлайн платежи к вашему бизнесу?", new Page.GetByTextOptions().setExact(true))
                .scrollIntoViewIfNeeded();

        // Находим кнопку «Отправить»
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Отправить"));
        assertTrue(submitButton.isVisible(), "Кнопка 'Отправить' должна быть видима");

        submitButton.click();

        // Проверяем валидацию: подсветка/текст ошибок (примеры селекторов)
        Locator errorMessages = page.locator(".error, .field-error, [data-error]");
        assertTrue(errorMessages.first().isVisible(),
                "Ожидается, что при пустой форме будут сообщения об ошибках валидации");
    }

    // 3. Проверка формы — позитивный сценарий
    @Test
    @DisplayName("3.2 Форма контакта: валидные данные → успешная отправка")
    void contactFormShouldShowSuccessOnValidData() {
        page.getByText("Готовы подключить онлайн платежи к вашему бизнесу?", new Page.GetByTextOptions().setExact(true))
                .scrollIntoViewIfNeeded();

        // Ниже селекторы нужно подогнать под реальную разметку формы
        Locator nameInput = page.locator("input[name*='name'], input[placeholder*='Имя']");
        Locator phoneInput = page.locator("input[type='tel'], input[name*='phone']");
        Locator emailInput = page.locator("input[type='email'], input[name*='email']");
        Locator commentInput = page.locator("textarea, textarea[name*='comment']");

        nameInput.fill("Тестовый бизнес");
        phoneInput.fill("+7 701 000 00 00");
        emailInput.fill("qa+ioka@test.local");
        if (commentInput.count() > 0) {
            commentInput.fill("Автотест: проверка формы связи ioka.kz");
        }

        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Отправить"));
        submitButton.click();

        // Тут надо посмотреть, как реально выглядит сообщение после успешной отправки.
        // Пример: текст "Спасибо" или "Мы свяжемся с вами"
        page.waitForTimeout(2000);
        Locator successMessage = page.locator("text=Спасибо").first();
        assertTrue(successMessage.isVisible(),
                "После успешной отправки формы ожидается сообщение об успехе (например, 'Спасибо')");
    }

    // 4. Проверка контента
    @Test
    @DisplayName("4. Контент: ключевые заголовки и описания должны отображаться")
    void mainPageShouldContainKeyHeadingsAndDescriptions() {
        assertTrue(
                page.getByText("Подключение онлайн оплаты", new Page.GetByTextOptions().setExact(true)).isVisible(),
                "Заголовок 'Подключение онлайн оплаты' должен быть видим"
        );

        assertTrue(
                page.getByText("Интернет-эквайринг для бизнеса", new Page.GetByTextOptions().setExact(true)).isVisible(),
                "Подзаголовок 'Интернет-эквайринг для бизнеса' должен быть видим"
        );

        assertTrue(
                page.getByText("Увеличивайте продажи с платежными решениями ioka").isVisible(),
                "Блок про 'Увеличивайте продажи с платежными решениями ioka' должен быть видим"
        );

        assertTrue(
                page.getByText("ioka — партнер по онлайн платежам в Казахстане").isVisible(),
                "Блок 'ioka — партнер по онлайн платежам в Казахстане' должен быть видим"
        );
    }
}
