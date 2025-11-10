package life.core.jupiter.extension;

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

    private final ThreadLocal<Boolean> needVideoThreadLocal = ThreadLocal.withInitial(() -> false);

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {

        BrowserContext browserContext = UiSession.context();
        if (browserContext == null) return;

        Page page = UiSession.page();
        boolean failed = extensionContext.getExecutionException().isPresent();

        Path trace = null;
        Path videoPath = null;

        try {
            // Скриншот только если тест падает
            if (failed && page != null) {
                attachPng(page.screenshot(new Page.ScreenshotOptions().setFullPage(true)));
            }

//            trace = Paths.get("build", "trace-" + sanitize(extensionContext.getDisplayName()) + ".zip");
//            Files.createDirectories(trace.getParent());
//            browserContext.tracing().stop(new Tracing.StopOptions().setPath(trace));
//            attachFile("Trace", Files.readAllBytes(trace), "application/zip", "zip");

            if (page != null) {
                try {
                    videoPath = page.video().path();
                } catch (Exception ignored) { }
            }

            browserContext.close();
        } finally {
            browserContext.close();

            // Приложить видео если тест упал (или был флаг из handlerа)
            boolean shouldAttachVideo = prodConfig().video() && (failed || needVideoThreadLocal.get());
            if (shouldAttachVideo && videoPath != null && Files.exists(videoPath)) {
                attachVideo(Files.readAllBytes(videoPath));
            }

            UiSession.clear();
            needVideoThreadLocal.remove();
        }

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
            options.setRecordVideoSize(1280, 720);
        }
        BrowserContext browserContext = browser.newContext(options);

        browserContext.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        UiSession.set(browser, browserContext);

        needVideoThreadLocal.set(false);
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
