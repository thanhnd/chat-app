package com.chatapp.mvp.listnearby;

import com.chatapp.service.ApiService;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements ListNearbyMvp.Interactor {
    @Override
    public void getListNearBy(final ListNearbyRequest request, final AuthorizeApiCallback<ResponseModel<List<UserModel>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<List<UserModel>>> call = service.listNearby(authorization, request);
        call.enqueue(new Callback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserModel>>> call, Response<ResponseModel<List<UserModel>>> response) {
                Log.i(response.raw().toString());
                ResponseModel<List<UserModel>> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null) {
                        if (responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS) {
                            callback.onSuccess(responseModel);
                            return;
                        } else if (responseModel.isTokenExpired()){
                            callback.onTokenExpired();
                        }
                    }
                    callback.onFail(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserModel>>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
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

        ApiService service = ApiService.retrofit.create(ApiService.class);
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
