package com.chatapp.mvp.searchuser;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchInteractorImpl implements SearchUserMvp.Interactor {

    @Override
    public void searchUser(String keyword, final ApiCallback<ResponseModel<List<UserModel>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<List<UserModel>>> call = service.search(authorization, keyword);
        call.enqueue(new Callback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserModel>>> call, Response<ResponseModel<List<UserModel>>> response) {
                Log.d(response.raw().toString());
                ResponseModel<List<UserModel>> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() != ResponseModel.RESPONSE_CD_ERROR) {
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
}
