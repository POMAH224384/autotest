package life.ffin.test.web;

import life.core.jupiter.annotation.WebTest;
import org.junit.jupiter.api.Tag;

import static life.utils.config.TestConfig.testConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebTest()
@Tag("Ffin-UI")
public class HealthCheckTest {

    private final String ffinUrl = testConfig().ffinUrl();




}
