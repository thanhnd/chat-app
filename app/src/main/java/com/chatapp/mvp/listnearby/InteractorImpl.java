package com.chatapp.mvp.listnearby;

import com.chatapp.service.ApiService;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.ListDataUserModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements ListNearbyMvp.Interactor {
    @Override
    public void getListNearBy(final ListNearbyRequest request, final AuthorizeApiCallback<ResponseModel<ListDataUserModel>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<ListDataUserModel>> call = service.listNearby(authorization, request);
        call.enqueue(new Callback<ResponseModel<ListDataUserModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ListDataUserModel>> call, Response<ResponseModel<ListDataUserModel>> response) {
                Log.i(response.raw().toString());
                ResponseModel<ListDataUserModel> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<ListDataUserModel>> call, Throwable t) {
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
