package com.chatapp.mvp.listnearby;

import com.chatapp.service.ApiService;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.ListNearByModel;
import com.chatapp.service.models.response.LogInModel;
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
    public void getListNearBy(final ListNearbyRequest request, final AuthorizeApiCallback<ResponseModel<ListNearByModel>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<ListNearByModel>> call = service.listNearby(authorization, request);
        call.enqueue(new Callback<ResponseModel<ListNearByModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ListNearByModel>> call, Response<ResponseModel<ListNearByModel>> response) {
                Log.i(response.raw().toString());
                ResponseModel<ListNearByModel> responseModel = response.body();
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
            public void onFailure(Call<ResponseModel<ListNearByModel>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
