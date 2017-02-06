package com.chatapp.mvp.updatebasicprofile;

import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/19/16.
 */

public class InteractorImpl implements UpdateBasicProfileMvp.Interactor {
    @Override
    public void submit(BasicProfileRequest request, final AuthorizeApiCallback<ResponseModel<Object>> apiCallback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiServiceHelper.getInstance();
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

    @Override
    public void uploadAvatar(String authorization, MultipartBody.Part filePart,
                             final AuthorizeApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback) {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<LinkedTreeMap<String, String>>> call = instance.uploadAvatar(authorization, filePart);
        call.enqueue(new Callback<ResponseModel<LinkedTreeMap<String, String>>>() {
            @Override
            public void onResponse(Call<ResponseModel<LinkedTreeMap<String, String>>> call,
                                   Response<ResponseModel<LinkedTreeMap<String, String>>> response) {
                ResponseModel<LinkedTreeMap<String, String>> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<LinkedTreeMap<String, String>>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                com.chatapp.utils.Log.e(t);
            }
        });

    }

    @Override
    public void getMyProfile(final AuthorizeApiCallback<ResponseModel<MyProfileModel>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<MyProfileModel>> call = service.getMyProfile(authorization);
        call.enqueue(new Callback<ResponseModel<MyProfileModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<MyProfileModel>> call, Response<ResponseModel<MyProfileModel>> response) {
                Log.i(response.raw().toString());
                ResponseModel<MyProfileModel> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null) {
                        if (responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS) {
                            callback.onSuccess(responseModel);
                            return;
                        } else if (responseModel.isTokenExpired()){
                            callback.onTokenExpired();
                            return;
                        }
                    }
                    callback.onFail(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<MyProfileModel>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
