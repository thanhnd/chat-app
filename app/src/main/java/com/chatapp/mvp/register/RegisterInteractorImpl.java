package com.chatapp.mvp.register;


import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import retrofit2.Call;

public class RegisterInteractorImpl implements RegisterMvp.RegisterInteractor {

    @Override
    public void login(LogInRequest request, final ApiCallback<ResponseModel<LogInModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<LogInModel>> call = service.signIn(request);
        call.enqueue(callback);
    }

    @Override
    public void register(RegisterRequest request, final ApiCallback<ResponseModel<RegisterModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<RegisterModel>> call = service.register(request);
        call.enqueue(callback);
    }

    @Override
    public void getVerifyCode(final ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.getVerifyCode(AccountUtils.getAuthorization());
        call.enqueue(callback);
    }
}
