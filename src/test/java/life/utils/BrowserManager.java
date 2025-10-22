package life.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import static life.utils.config.ProdConfig.prodConfig;

public class BrowserManager {
    public static Browser getBrowser(final Playwright playwright) {
        return BrowserFactory.valueOf(prodConfig().browser().toUpperCase()).createInstance(playwright);
    }
}
