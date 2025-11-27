package life.utils.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:testConfig.properties"})
public interface TestConfiguration extends Config {

    @Key("cabinet.url")
    String cabinetUrl();

    @Key("api.ffin.url")
    String apiFfinUrl();

    @Key("api.superApp.url")
    String apiSuperAppUrl();

    @Key("superApp.registration.url")
    String superAppRegUrl();

    @Key("ffin.url")
    String ffinUrl();

    String username();

    String password();
}
