package life.superapp.api.service.impl;

import life.core.http.RetrofitClient;
import life.superapp.api.AuthApi;
import life.superapp.api.model.AuthUserRequest;
import life.superapp.api.model.AuthUserResponse;
import life.superapp.api.model.OttGatewayRequest;
import life.superapp.api.model.OttGatewayResponse;
import life.superapp.api.service.AuthClient;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;
import java.util.Objects;

import static life.utils.config.TestConfig.testConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParametersAreNonnullByDefault
public class AuthApiClient implements AuthClient {

    private final AuthApi api;

    public AuthApiClient() {
        this.api = RetrofitClient.create(testConfig().apiSuperAppUrl()).create(AuthApi.class);
    }

    @NotNull
    @Override
    public String getOtt(String iin, String fullName) throws Exception {

        OttGatewayRequest request = new OttGatewayRequest(
                fullName,
                iin,
                "+77772145973",
                "1970-05-11",
                "F",
                "drr@mail.ru",
                List.of(new OttGatewayRequest.Document(
                                "054041413",
                                "04",
                                "МВД РК",
                                "2023-08-09"
                        )
                )
        );

        Response<OttGatewayResponse> response = api.gateway(request).execute();
        assertEquals(200, response.code(), "Unexpected status code for /auth/gateway");

        String token = Objects.requireNonNull(response.body(), "Body is null")
                .oneTimeToken();

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("one_time_token is null");
        }

        return token;
    }

    @NotNull
    @Override
    public String getAccessToken(String oneTimeToken) throws Exception {
        AuthUserRequest request = new AuthUserRequest(oneTimeToken);

        Response<AuthUserResponse> response = api.auth(request).execute();
        assertEquals(200, response.code(), "Unexpected status code for /auth/user");

        String token = Objects.requireNonNull(response.body(), "Body is null")
                .data().accessToken();

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Access token is null");
        }

        return token;
    }
}
