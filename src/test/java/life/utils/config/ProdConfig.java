package life.utils.config;

import org.aeonbits.owner.ConfigCache;

public class ProdConfig {
    public static ProdConfiguration prodConfig() {
        return ConfigCache.getOrCreate(ProdConfiguration.class);
    }
}
