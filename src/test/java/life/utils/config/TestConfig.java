package life.utils.config;

import org.aeonbits.owner.ConfigCache;

public class TestConfig {
    public static TestConfiguration testConfig() {
        return ConfigCache.getOrCreate(TestConfiguration.class);
    }
}
