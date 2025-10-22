package life.utils;

import com.microsoft.playwright.Page;
import groovy.util.logging.Slf4j;
import life.service.web.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class BasePageFactory {

    private static final Logger log = LoggerFactory.getLogger(BasePageFactory.class);

    public static <T extends BasePage> T createInstance(final Page page, final Class<T> clazz) {
        try {
            BasePage instance = clazz.getDeclaredConstructor().newInstance();

            instance.setAndConfigurePage(page);
            instance.initComponents();

            return clazz.cast(instance);
        } catch (Exception e) {
            log.error("BasePageFactory::createInstance", e);
        }
        throw new NullPointerException("Page class instantiation failed.");

    }

}
