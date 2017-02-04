package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.response.LogInModel;
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
public class InteractorImpl implements ListRecommendedFriendsMvp.Interactor {
    @Override
    public void getListRecommendedFriends(final ApiCallback<ResponseModel<List<UserModel>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<List<UserModel>>> call = service.listRecommendedFriends(authorization);
        call.enqueue(new Callback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserModel>>> call, Response<ResponseModel<List<UserModel>>> response) {
                ResponseModel<List<UserModel>> responseModel = response.body();
                Log.d(response.raw().toString());
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
            public void onFailure(Call<ResponseModel<List<UserModel>>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

    @Override
    public void acceptFriendRequest(String userId, final ApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<Object>> call = service.acceptFriendRequest(authorization, new UserRequest(userId));
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                ResponseModel<Object> responseModel = response.body();
                Log.d(response.raw().toString());
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
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
