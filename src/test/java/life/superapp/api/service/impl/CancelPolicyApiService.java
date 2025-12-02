package life.superapp.api.service.impl;

import life.core.http.RetrofitClient;
import life.superapp.api.CancelPolicyApi;
import life.superapp.api.service.CancelPolicyService;
import life.utils.config.EnvConfig;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;



public class CancelPolicyApiService implements CancelPolicyService {

    private final CancelPolicyApi api;
    private final String cancelPolicyUrl = EnvConfig.cfg().cancelPolicyUrl();

    public CancelPolicyApiService() {
        this.api = RetrofitClient.create(cancelPolicyUrl).create(CancelPolicyApi.class);
    }


    @Override
    public void cancelPolicy(String policyNumber, String reason) throws IOException {
        Response<Void> response = api.cancelPolicy(policyNumber, reason).execute();
    }
}
