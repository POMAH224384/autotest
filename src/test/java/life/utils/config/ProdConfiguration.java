package life.utils.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:prodConfig.properties"})
public interface ProdConfiguration extends Config {

    @Key("ffin.url")
    String ffinUrl();

    @Key("api.ffin.url")
    String apiFfinUrl();

    @Key("base.test.video.path")
    String baseTestVideoPath();

    String browser();

    boolean headless();

    @Key("slow.motion")
    int slowMotion();

    int timeout();

    boolean video();
}
