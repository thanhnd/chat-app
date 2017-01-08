package com.chatapp.mvp.verify;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyInteractorImpl implements VerifyMvp.VerifyInteractor {

    @Override
    public void verify(VerifyEmailRequest request, final ApiCallback<ResponseModel<VerifyModel>> apiCallback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<VerifyModel>> call = service.verifyCode(authorization, request);
        call.enqueue(new Callback<ResponseModel<VerifyModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<VerifyModel>> call, Response<ResponseModel<VerifyModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<VerifyModel> responseModel = response.body();
                if (apiCallback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        apiCallback.onSuccess(responseModel);
                    } else {
                        apiCallback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<VerifyModel>> call, Throwable t) {
                if (apiCallback != null) {
                    apiCallback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

}
