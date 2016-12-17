package com.chatapp.mvp.login;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInInteractorImpl implements LogInInteractor {

    @Override
    public void login(LogInRequest logInRequest, final ApiCallback<ResponseModel<LogInModel>> loginCallback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<LogInModel>> call = service.signIn(logInRequest);
        call.enqueue(new Callback<ResponseModel<LogInModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<LogInModel>> call, Response<ResponseModel<LogInModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<LogInModel> responseModel = response.body();
                if (loginCallback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        AccountUtils.setLogInModel(responseModel.getResultSet());
                        loginCallback.onSuccess(responseModel);
                    } else {
                        loginCallback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<LogInModel>> call, Throwable t) {
                if (loginCallback != null) {
                    loginCallback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

}
