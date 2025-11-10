package life.superapp.api;

import life.superapp.api.model.AuthUserRequest;
import life.superapp.api.model.AuthUserResponse;
import life.superapp.api.model.OttGatewayRequest;
import life.superapp.api.model.OttGatewayResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/gateway")
    @Headers({
            "Content-type: application/json",
            "X-Gateway-Secret: life_test"
    })
    Call<OttGatewayResponse> gateway(@Body OttGatewayRequest body);

    @POST("auth/user")
    @Headers("Content-type: application/json")
    Call<AuthUserResponse> auth(@Body AuthUserRequest body);
}
