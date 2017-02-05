package com.chatapp.mvp.userprofile;


import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileInteractorImpl implements UserProfileMvp.UserProfileInteractor {

    @Override
    public void getUserProfile(String userId, final AuthorizeApiCallback<ResponseModel<UserProfileModel>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<UserProfileModel>> call = service.getUserProfile(authorization, userId);
        call.enqueue(new Callback<ResponseModel<UserProfileModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserProfileModel>> call, Response<ResponseModel<UserProfileModel>> response) {
                Log.i(response.raw().toString());
                ResponseModel<UserProfileModel> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<UserProfileModel>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

    @Override
    public void addUserFavorite(String userId, final AuthorizeApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.addUserFavorite(authorization, new UserRequest(userId));
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                Log.i(response.raw().toString());
                ResponseModel<Object> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

    @Override
    public void requestAddFriend(String userId, String noted, final AuthorizeApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.requestAddFriend(authorization, new UserRequest(userId, noted));
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                Log.i(response.raw().toString());
                ResponseModel<Object> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
