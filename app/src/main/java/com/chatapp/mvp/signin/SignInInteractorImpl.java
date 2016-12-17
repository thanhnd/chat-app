package com.chatapp.mvp.signin;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.SignInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.SignInModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInInteractorImpl implements SignInInteractor {

    @Override
    public void login(SignInRequest signInRequest, final ApiCallback<ResponseModel<SignInModel>> loginCallback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<SignInModel>> call = service.signIn(signInRequest);
        call.enqueue(new Callback<ResponseModel<SignInModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<SignInModel>> call, Response<ResponseModel<SignInModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<SignInModel> signInModel = response.body();
                if (loginCallback != null) {
                    if (response.isSuccessful() && signInModel != null
                            && signInModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        AccountUtils.setSignInModel(signInModel.getResultSet());
                        loginCallback.onSuccess(signInModel);
                    } else {
                        loginCallback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<SignInModel>> call, Throwable t) {
                if (loginCallback != null) {
                    loginCallback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

}
