package life.service.http;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import life.service.http.filters.AllureFilter;

import static life.utils.config.ProdConfig.prodConfig;

public class RequestSpec {

    public static RequestSpecification json() {
        return new RequestSpecBuilder()
                .setBaseUri(prodConfig().apiFfinUrl())
                .setAccept("application/json")
                .setContentType("application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new AllureFilter())
                .log(LogDetail.URI)
                .log(LogDetail.BODY)
                .build();
    }
}
