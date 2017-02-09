package com.chatapp.mvp.login;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;

import retrofit2.Call;

public class LogInInteractorImpl implements LoginMvp.LogInInteractor {

    @Override
    public void login(LogInRequest logInRequest, final ApiCallback<ResponseModel<LogInModel>> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<LogInModel>> call = service.signIn(logInRequest);
        call.enqueue(callback);
    }
}
