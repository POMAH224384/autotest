package life.cabinet.test.web;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import life.cabinet.page.HomePage;
import life.cabinet.page.LoginPage;
import life.core.jupiter.annotation.WebTest;
import life.core.web.Pages;
import life.core.web.UiSession;
import life.utils.config.EnvConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Cabinet-UI")
@WebTest(width = 375, height = 812, isMobile = true)
public class LoginTest {


    private final String authUrl = EnvConfig.cfg().cabinetUrl();
    private final String iin = "";
    private final String password = "";

    @Test
    void homePageShouldBeDisplayedAfterSuccessLogin() {
        Pages.open(authUrl, LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .setUsername(EnvConfig.cfg().username())
                .setPassword(EnvConfig.cfg().password())
                .submit(HomePage.class)
                .checkThatPageLoaded();

    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        Pages.open(authUrl, LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .fillUsernameAndPassword(EnvConfig.cfg().username(), "1234567")
                .submit(LoginPage.class)
                .checkError("Неверный пароль");
    }


    @Test
    void checkchetotest() {
        BrowserContext context = UiSession.context();
        if(context == null) {
            throw new IllegalStateException("BrowserContext is null. Ensure @WebTest is added");
        }

        context.route("**/api/v2/contracts/2986594391", route -> {
            route.fulfill(
                    new Route.FulfillOptions()
                            .setStatus(200)
                            .setContentType("application/json")
                            .setBody("""
                                    {
                                                 "status": true,
                                                 "data": {
                                                     "px_id": 2986594391,
                                                     "policy_number": "M602553",
                                                     "insured": "\\u041f\\u0430\\u0432\\u043b\\u044b\\u0448 \\u0422\\u0430\\u0442\\u044c\\u044f\\u043d\\u0430 \\u041f\\u0435\\u0442\\u0440\\u043e\\u0432\\u043d\\u0430",
                                                     "product": "Freedom Money",
                                                     "start_date": "2024-08-08",
                                                     "end_date": "2029-08-07",
                                                     "progress_percent": 27,
                                                     "conclusion_date": "2024-08-08",
                                                     "plan_date": "2026-01-19",
                                                     "insurance_sum": "12072.00",
                                                     "insurance_premium": "210.00",
                                                     "insurance_sum_kzt": "5741757.00",
                                                     "insurance_premium_kzt": "108134.00",
                                                     "contract_url": null,
                                                     "notification_url": null,
                                                     "talon_url": null,
                                                     "currency": "KZT",
                                                     "status": "\\u0414\\u0435\\u0439\\u0441\\u0442\\u0432\\u0443\\u0435\\u0442",
                                                     "mfo": null,
                                                     "cancel": false,
                                                     "pay": true,
                                                     "prolongation": false,
                                                     "insurance_case": true,
                                                     "subscription_availability": true,
                                                     "termination_cancellation": false,
                                                     "refund_application": true,
                                                     "client_appeal": true,
                                                     "cancel_refuse": {
                                                         "refuse": false,
                                                         "requisite": null,
                                                         "notification": null
                                                     },
                                                     "cancel_early": {
                                                         "early": false,
                                                         "requisite": null,
                                                         "notification": null
                                                     },
                                                     "can_top_up_withdraw": false,
                                                     "check_end_of_month": false,
                                                     "notification_url_directory": false,
                                                     "re_registration": false,
                                                     "auto_payment": false,
                                                     "journal": [
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2024-08-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2024-09-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2024-10-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2024-11-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2024-12-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-01-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-02-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-03-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-04-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-05-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-06-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-07-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-08-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-09-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-10-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-11-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041e\\u0431\\u0440\\u0430\\u0431\\u043e\\u0442\\u0430\\u043d\\u043e",
                                                             "date": "2025-12-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-01-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-02-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-03-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-04-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-05-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-06-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-07-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-08-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-09-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-10-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-11-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2026-12-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-01-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-02-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-03-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-04-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-05-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-06-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-07-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-08-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-09-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-10-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-11-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2027-12-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-01-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-02-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-03-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-04-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-05-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-06-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-07-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-08-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-09-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-10-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-11-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2028-12-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-01-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-02-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-03-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-04-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-05-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-06-08"
                                                         },
                                                         {
                                                             "sum": 210.25,
                                                             "currency": "USD",
                                                             "status": "\\u041f\\u043b\\u0430\\u043d\\u043e\\u0432\\u0430\\u044f",
                                                             "date": "2029-07-08"
                                                         }
                                                     ],
                                                     "dividends": null,
                                                     "countries": null,
                                                     "product_type": "CUMULATIVE"
                                                 }
                                             }
                            """)

            );
        });

        Pages.open(authUrl, LoginPage.class)
                .checkThatPageLoaded()
                .acknowledge()
                .setUsername(EnvConfig.cfg().username())
                .setPassword(EnvConfig.cfg().password())
                .submit(HomePage.class);

        Page page = UiSession.page(); // или из homePage, если ты там хранишь page
        page.pause();
    }




}
