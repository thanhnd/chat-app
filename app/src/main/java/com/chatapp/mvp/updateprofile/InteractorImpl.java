package com.chatapp.mvp.updateprofile;


import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteractorImpl implements UpdateProfileMvp.Interactor {

    @Override
    public void uploadAvatar(String authorization, MultipartBody.Part filePart, final AuthorizeApiCallback<ResponseModel<Object>> callback) {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = instance.uploadAvatar(authorization, filePart);
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call,
                                   Response<ResponseModel<Object>> response) {
                ResponseModel<Object> responseModel = response.body();
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
                com.chatapp.utils.Log.e(t);
            }
        });
    }
}
