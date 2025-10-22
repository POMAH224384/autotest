package life.ffin.test.web;

import life.ffin.page.MainPage;
import life.jupiter.annotation.WebTest;
import life.utils.Pages;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static life.utils.config.ProdConfig.prodConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebTest
@Tag("webFfin")
public class HealthCheckTest {

    @Test
    void checkMainHeader() {
        Pages.open(prodConfig().ffinUrl(), MainPage.class)
                .checkMainHeader();

    }

}
