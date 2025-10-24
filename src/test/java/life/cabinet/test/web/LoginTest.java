package life.cabinet.test.web;

import life.cabinet.page.HomePage;
import life.cabinet.page.LoginPage;
import life.jupiter.annotation.WebTest;
import life.utils.Pages;
import org.junit.jupiter.api.Test;

import static life.utils.config.TestConfig.testConfig;

@WebTest
public class LoginTest {


    @Test
    void homePageShouldBeDisplayedAfterSuccessLogin() {
        Pages.open(testConfig().devCabinetUrl(), LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .setUsername(testConfig().username())
                .setPassword(testConfig().password())
                .submit(HomePage.class)
                .checkThatPageLoaded();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        Pages.open(testConfig().devCabinetUrl(), LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .fillUsernameAndPassword(testConfig().username(), "1234567")
                .submit(LoginPage.class)
                .checkError("Неверный пароль");
    }

    @Test
    void badTest() {
        Pages.open(testConfig().devCabinetUrl(), LoginPage.class)
                .checkThatPageLoaded()
                .fillUsernameAndPassword(testConfig().username(), "1234567")
                .submit(LoginPage.class)
                .checkError("Неверный пароль");
    }
}
