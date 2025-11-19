package life.superapp.page;

import com.microsoft.playwright.Response;
import io.qameta.allure.Step;
import life.core.web.BasePage;
import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RegistrationPage extends BasePage<RegistrationPage> {

    private Locator header;
    private Locator inputOPV;
    private Locator insurancePremiumAmount;
    private Locator dividendAmount;
    private Locator annuityAmount;
    private Locator annuityItem;
    private Locator dividendItem;



    @Override
    public void initComponents() {
        this.header = page.locator(".appHeader__title");
        this.inputOPV = page.locator("#input-v-2");
        this.annuityItem = page.locator(".calcTotal__item")
                .filter(new Locator.FilterOptions().setHasText("Аннуитетная выплата"));
        this.dividendItem = page.locator(".calcTotal__item")
                .filter(new Locator.FilterOptions().setHasText("Страховой дивиденд"));
        this.insurancePremiumAmount =
                page.locator(".calcTotal__item:has(.calcTotal__title:has-text(\"Страховая премия\")) .calcTotal__amount\n ");
        this.dividendAmount =
                dividendItem.locator(".calcTotal__amount");
        this.annuityAmount =
                annuityItem.locator(".calcTotal__amount");

    }

    @Override
    public RegistrationPage checkThatPageLoaded() {
        assertThat(header).isVisible();
        return this;
    }

    @Step("Ввести сумму ОПВ")
    public RegistrationPage inputAmountOfOPV(String amount) {
        inputOPV.fill(amount);
        return this;
    }

    @Step("Проверить, что страховая премия отображается и не пустая")
    public RegistrationPage assertInsurancePremiumVisible() {
        insurancePremiumAmount.scrollIntoViewIfNeeded();

        assertThat(insurancePremiumAmount).isVisible();

        String text = insurancePremiumAmount.innerText().trim();
        assertFalse(text.isBlank(), "Сумма страховой премии не должна быть пустой");
        return this;
    }

    @Step("Проверить, что сумма страхового дивиденда отображается и не пустая")
    public RegistrationPage assertDividendAmountVisible() {
        dividendAmount.scrollIntoViewIfNeeded();

        assertThat(dividendAmount).isVisible();

        String text = dividendAmount.innerText().trim();
        assertFalse(text.isBlank(), "Сумма страхового дивиденда не должна быть пустой");
        return this;
    }

    public RegistrationPage assertAnnuityAmountVisible() {
        annuityAmount.scrollIntoViewIfNeeded();

        assertThat(annuityAmount).isVisible();

        String text = annuityAmount.innerText().trim();
        assertFalse(text.isBlank(), "Аннуитетная выплата не должна быть пустой");
        return this;
    }


}
