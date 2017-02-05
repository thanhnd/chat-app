package com.chatapp.mvp.register;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInteractorImpl implements RegisterMvp.RegisterInteractor {

    @Override
    public void login(LogInRequest request, final ApiCallback<ResponseModel<LogInModel>> apiCallback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<LogInModel>> call = service.signIn(request);
        call.enqueue(new Callback<ResponseModel<LogInModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<LogInModel>> call, Response<ResponseModel<LogInModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<LogInModel> responseModel = response.body();
                if (apiCallback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() != ResponseModel.RESPONSE_CD_ERROR) {
                        apiCallback.onSuccess(responseModel);

                    } else {
                        apiCallback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<LogInModel>> call, Throwable t) {
                if (apiCallback != null) {
                    apiCallback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

    @Override
    public void register(RegisterRequest request, final ApiCallback<ResponseModel<RegisterModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<RegisterModel>> call = service.register(request);
        call.enqueue(new Callback<ResponseModel<RegisterModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<RegisterModel>> call, Response<ResponseModel<RegisterModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<RegisterModel> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS) {
                        callback.onSuccess(responseModel);
                    } else {
                        callback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<RegisterModel>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

    @Override
    public void getVerifyCode(final ApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.getVerifyCode(authorization);
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                Log.d(response.raw().toString());
                ResponseModel<Object> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        callback.onSuccess(responseModel);
                    } else {
                        callback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
