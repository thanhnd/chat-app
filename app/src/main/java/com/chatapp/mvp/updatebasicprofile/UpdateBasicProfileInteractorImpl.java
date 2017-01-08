package com.chatapp.mvp.updatebasicprofile;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/19/16.
 */

public class UpdateBasicProfileInteractorImpl implements UpdateBasicProfileMvp.UpdateBasicProfileInteractor {
    @Override
    public void submit(BasicProfileRequest request, final ApiCallback<ResponseModel<Object>> apiCallback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<Object>> call = service.updateBasicProfile(authorization, request);
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                Log.d(response.raw().toString());
                ResponseModel<Object> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (apiCallback != null) {
                    apiCallback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
