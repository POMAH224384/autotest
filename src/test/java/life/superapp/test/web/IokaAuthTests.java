package life.superapp.test.web;

import life.core.jupiter.annotation.WebTest;
import life.utils.Pages;
import org.junit.jupiter.api.*;


@WebTest
public class IokaAuthTests {


    @Test
    void loginShouldShowErrorForInvalidCredentials() {
        Pages.open("https://ioka.kz/ru/products/for-all", IokaHomePage.class)
                .checkThatPageLoaded()
                .openLogin(IokaLoginPage.class)
                .checkThatPageLoaded()
                .loginWith("wrong@example.com", "wrongPassword123")
                .assertLoginErrorVisible();
    }
}
