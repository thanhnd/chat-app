package com.chatapp.mvp.login;


import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class LogInInteractorImpl implements LoginMvp.LogInInteractor {

    @Override
    public void login(LogInRequest logInRequest, final BaseApiCallback<ResponseModel<LogInModel>> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<LogInModel>> call = service.signIn(logInRequest);
        call.enqueue(callback);
    }

    @Override
    public void sendVerifyCodeForgotPasswordWithPhone(String phone, BaseApiCallback<ResponseModel<Object>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, String> request = new HashMap<>();
        request.put("mobile", phone);
        Call<ResponseModel<Object>> call = service.sendVerifyCodeForgotPassword(request);
        call.enqueue(callback);
    }

    @Override
    public void sendVerifyCodeForgotPasswordWithEmail(String email, BaseApiCallback<ResponseModel<Object>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        Call<ResponseModel<Object>> call = service.sendVerifyCodeForgotPassword(request);
        call.enqueue(callback);
    }
}
