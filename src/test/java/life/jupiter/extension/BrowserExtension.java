package life.jupiter.extension;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import life.utils.BrowserManager;
import life.utils.UiSession;
import org.junit.jupiter.api.extension.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static life.utils.config.ProdConfig.prodConfig;

public class BrowserExtension implements BeforeEachCallback,
        AfterEachCallback,
        AfterAllCallback,
        BeforeAllCallback,
        TestExecutionExceptionHandler,
        LifecycleMethodExecutionExceptionHandler {

    private Playwright playwright;
    private Browser browser;

    private final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Boolean> needVideoThreadLocal = ThreadLocal.withInitial(() -> false);

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        browser.close();
        playwright.close();

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        BrowserContext browserContext = contextThreadLocal.get();
        var page = UiSession.page();
        boolean failed = extensionContext.getExecutionException().isPresent();

        if (page != null) {
            attachPng(page.screenshot(new Page.ScreenshotOptions().setFullPage(true)));
        }

        Path trace = Paths.get("build", "trace-" + sanitize(extensionContext.getDisplayName()) + ".zip");
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(trace));
        attachFile("Trace", Files.readAllBytes(trace), "application/zip", "zip");

        if (prodConfig().video() && (failed || needVideoThreadLocal.get() && page != null && page.video() != null)) {
            attachVideo(Files.readAllBytes(page.video().path()));
        }

        browserContext.close();
        UiSession.clear();
        contextThreadLocal.remove();
        needVideoThreadLocal.remove();

    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Browser.NewContextOptions options = new Browser.NewContextOptions();
        if (prodConfig().video()) {
            Path dir = Paths.get(prodConfig().baseTestVideoPath());
            try {
                Files.createDirectories(dir);
            } catch (Exception ignored) { }
            options.setRecordVideoDir(dir);
        }
        BrowserContext browserContext = browser.newContext(options);

        browserContext.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        contextThreadLocal.set(browserContext);
        needVideoThreadLocal.set(false);

        UiSession.set(browser, browserContext);

    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        var page = UiSession.page();
        if (page != null) {
            attachPng(page.screenshot());
        }
        needVideoThreadLocal.set(true);
        throw throwable;
    }

    @Attachment(value = "{name}", type = "{mime}")
    private byte[] attachFile(String name, byte[] bytes, String mime, String ext) {
        return bytes;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachPng(byte[] bytes) {
        return bytes;
    }

    @Attachment(value = "Test Video", type = "video/webm")
    private byte[] attachVideo(byte[] bytes) {
        return bytes;
    }

    private String sanitize(String s) {
        return s.replaceAll("[^a-zA-Z0-9-_]", "_");
    }


}
