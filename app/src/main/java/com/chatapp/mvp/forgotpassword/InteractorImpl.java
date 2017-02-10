package com.chatapp.mvp.forgotpassword;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class InteractorImpl implements ForgotPasswordMvp.Interactor {
    @Override
    public void submitVerifyCode(Map<String, String> request, ApiCallback<ResponseModel> callback) {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel> call = instance.confirmCodeForgotPassword(request);
        call.enqueue(callback);
    }

    @Override
    public void submitPassword(Map<String, String> request, BaseApiCallback<ResponseModel> callback) {
        ApiService instance = ApiServiceHelper.getInstance();
        Call<ResponseModel> call = instance.changePassword(request);
        call.enqueue(callback);
    }
}
