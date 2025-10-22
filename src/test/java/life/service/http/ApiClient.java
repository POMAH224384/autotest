package life.service.http;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public ValidatableResponse get(String uriPath) {
        return given().spec(RequestSpec.json())
                .when().get(uriPath)
                .then();
    }

    public ValidatableResponse post(String uriPath, Object body) {
        return given().spec(RequestSpec.json())
                .body(body)
                .when().post(uriPath)
                .then();
    }

    public ValidatableResponse put(String uriPath, Object body) {
        return given().spec(RequestSpec.json())
                .body(body)
                .when().put(uriPath)
                .then();
    }

    public ValidatableResponse delete(String uriPath) {
        return given().spec(RequestSpec.json())
                .when().delete(uriPath)
                .then();
    }
}
