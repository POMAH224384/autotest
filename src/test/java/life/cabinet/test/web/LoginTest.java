package life.cabinet.test.web;

import life.cabinet.page.HomePage;
import life.cabinet.page.LoginPage;
import life.core.jupiter.annotation.WebTest;
import life.core.web.Pages;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static life.utils.config.TestConfig.testConfig;

@Tag("Cabinet-UI")
@WebTest(width = 375, height = 812, isMobile = true)
public class LoginTest {


    private final String authUrl = testConfig().cabinetUrl();

    @Test
    void homePageShouldBeDisplayedAfterSuccessLogin() {
        Pages.open(authUrl, LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .setUsername(testConfig().username())
                .setPassword(testConfig().password())
                .submit(HomePage.class)
                .checkThatPageLoaded();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        Pages.open(authUrl, LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .fillUsernameAndPassword(testConfig().username(), "1234567")
                .submit(LoginPage.class)
                .checkError("Неверный пароль");
    }





}
