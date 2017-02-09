package com.chatapp.mvp.updateprofile;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

import static com.chatapp.utils.AccountUtils.getAuthorization;

public class InteractorImpl implements UpdateProfileMvp.Interactor {

    @Override
    public void uploadAvatar(MultipartBody.Part filePart,
                             final ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback)
            throws RequireLoginException {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<LinkedTreeMap<String, String>>> call = instance.uploadAvatar(getAuthorization(), filePart);
        call.enqueue(callback);
    }

    @Override
    public void submit(Map<String, Object> request, final ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException {

        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = instance.updateProfile(getAuthorization(), request);
        call.enqueue(callback);
    }
}
