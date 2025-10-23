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
                .introduce()
                .setUsername(testConfig().username())
                .setPassword(testConfig().password())
                .submit(new HomePage())
                .checkThatPageLoaded();
    }

}
