package life.superapp.test.web;

import life.core.jupiter.annotation.WebTest;
import life.superapp.jupiter.annotation.AccessToken;
import life.superapp.jupiter.annotation.Auth;
import life.superapp.page.RegistrationPage;
import life.utils.Pages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static life.utils.config.TestConfig.testConfig;

@WebTest(width = 375, height = 812, isMobile = true)
@Auth(iin = "700511402493", fullName = "ЖАПБАРОВА ГУЛЬМИРА АБЫЛГАЗИНОВНА")
@Tag("PA-UI")
public class CalculationWebTest {

    private final String regPaUrl = testConfig().superAppRegUrl();


    @Test
    void calculationTest(@AccessToken String token) {
        Pages.open(regPaUrl, RegistrationPage.class)
                .checkThatPageLoaded()
                .inputAmountOfOPV("100000000")
                .assertInsurancePremiumVisible()
                .assertAnnuityAmountVisible()
                .assertDividendAmountVisible();
    }

}
