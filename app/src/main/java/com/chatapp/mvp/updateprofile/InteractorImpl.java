package com.chatapp.mvp.updateprofile;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

public class InteractorImpl implements UpdateProfileMvp.Interactor {

    @Override
    public void uploadAvatar(MultipartBody.Part filePart,
                             final ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            callback.onTokenExpired();
            return;
        }
        String authorization = logInModel.getToken();
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<LinkedTreeMap<String, String>>> call = instance.uploadAvatar(authorization, filePart);
        call.enqueue(callback);
    }

    @Override
    public void submit(Map<String, Object> request, final ApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            callback.onTokenExpired();
            return;
        }
        String authorization = logInModel.getToken();
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = instance.updateProfile(authorization, request);
        call.enqueue(callback);
    }
}
