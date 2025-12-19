package life.superapp.test.web;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Route;
import io.qameta.allure.Description;
import life.core.jupiter.annotation.WebTest;
import life.core.web.UiSession;
import life.superapp.jupiter.annotation.AccessToken;
import life.superapp.jupiter.annotation.Auth;
import life.superapp.page.CalculationPage;
import life.core.web.Pages;
import life.superapp.page.PaymentSchedulePage;
import life.utils.config.EnvConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@WebTest(width = 375, height = 812, isMobile = true)
@Auth(iin = "", fullName = "")
@Tag("PA-UI")
@Disabled
public class CalculationWebTest {

    private final String regPaUrl = EnvConfig.cfg().superAppRegUrl();
    private final String errorTitle401 = "Авторизация истекла";
    private final String errorContent401 = "Сессия авторизации устарела. Пожалуйста, повторите вход из приложения банка.";


    @Test
    @DisplayName("Калькуляция только с ОПВ")
    @Description("Страховая премия, Аннуитетная выплата и Страховой дивиденд должны отображаться с введенной суммой ОПВ")
    void ResultsOfCalculationShouldBeVisibleWithOPVOnly(@AccessToken String token) {
        Pages.open(regPaUrl, CalculationPage.class)
                .checkThatPageLoaded()
                .inputAmountOfOPV("100000000")
                .assertInsurancePremiumVisible()
                .assertAnnuityAmountVisible()
                .assertDividendAmountVisible();
    }

    @Test
    @DisplayName("Калькуляция с ОПВ и ДПВ")
    @Description("Страховая премия, Аннуитетная выплата и Страховой дивиденд должны отображаться с введенной суммой ОПВ и ДПВ")
    void ResultsOfCalculationShouldBeVisibleOPVAndDPV(@AccessToken String token) {
        Pages.open(regPaUrl, CalculationPage.class)
                .checkThatPageLoaded()
                .inputAmountOfOPV("100000000")
                .inputAmountOfDPV("1000000")
                .assertAnnuityAmountVisible()
                .assertAnnuityAmountVisible()
                .assertInsurancePremiumVisible();
    }

    @Test
    @DisplayName("Калькуляция с ОПВ, ДПВ и Собственными средствами")
    @Description("Страховая премия, Аннуитетная выплата и Страховой дивиденд должны отображаться с введенной суммой ОПВ, ДПВ и Собственными средствами")
    void ResultsOfCalculationShouldBeVisibleOPVAndDPVAndOwnExpenses(@AccessToken String token) {
        Pages.open(regPaUrl, CalculationPage.class)
                .checkThatPageLoaded()
                .inputAmountOfOPV("100000000")
                .inputAmountOfDPV("100000")
                .inputAmountOfOwnExpenses("10000")
                .assertDividendAmountVisible()
                .assertAnnuityAmountVisible()
                .assertInsurancePremiumVisible();
    }

    @Test
    @DisplayName("Ошибка авторизации")
    @Description("Открывается модальное окно с ошибкой авторизации при ошибке 401 от /api/auth/me")
    void userShouldSeeUnauthorizedErrorWhenBackendReturns401(@AccessToken String token) {
        BrowserContext context = UiSession.context();

        if (context == null) {
            throw new IllegalStateException("BrowserContext is null. Ensure @WebTest is added");
        }

        context.route("/api/auth/me", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(401)
                    .setContentType("application/json")
                    .setBody("""
                            {
                              "status": "false",
                              "message": "Авторизация истекла🧚‍♀Сессия авторизации устарела. Пожалуйста, повторите вход из приложения банка.",
                              "data": null
                            }
                            """)
            );
        });

        Pages.open(regPaUrl, CalculationPage.class)
                .checkThatPageLoaded()
                .assertErrorTitleIsVisible(errorTitle401);

        Pages.on(CalculationPage.class)
                .assertErrorTextIsVisible(errorContent401);
    }

    @Test
    void paymentScheduleShouldOpenAndVisibleAfterInputOPV(@AccessToken String token) {
        Pages.open(regPaUrl, CalculationPage.class)
                .checkThatPageLoaded()
                .inputAmountOfOPV("100000000")
                .paymentScheduleIsVisible()
                .openPaymentSchedulePage();


        Pages.on(PaymentSchedulePage.class)
                .checkThatPageLoaded()
                .checkFirstPaymentMonth();
    }


}
