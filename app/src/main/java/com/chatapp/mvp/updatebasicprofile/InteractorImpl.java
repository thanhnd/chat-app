package com.chatapp.mvp.updatebasicprofile;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by thanhnguyen on 12/19/16.
 */

public class InteractorImpl implements UpdateBasicProfileMvp.Interactor {
    @Override
    public void submit(BasicProfileRequest request, final ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.updateBasicProfile(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

    @Override
    public void uploadAvatar(MultipartBody.Part filePart,
                             final ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback) throws RequireLoginException {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel<LinkedTreeMap<String, String>>> call = instance.uploadAvatar(AccountUtils.getAuthorization(), filePart);
        call.enqueue(callback);
    }
}
