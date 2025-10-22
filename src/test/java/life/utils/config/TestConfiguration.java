package life.utils.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:testConfig.properties"})
public interface TestConfiguration extends Config {

    @Key("dev.cabinet.url")
    String devCabinetUrl();

    String username();

    String password();
}
