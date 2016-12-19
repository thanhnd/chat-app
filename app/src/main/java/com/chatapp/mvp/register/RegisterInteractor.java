
package com.chatapp.mvp.register;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;

public interface RegisterInteractor {
    void login(LogInRequest request,
               ApiCallback<ResponseModel<LogInModel>> loginCallback);
    void register(RegisterRequest request,
                  ApiCallback<ResponseModel<RegisterModel>> callback);
    void getVerifyCode(ApiCallback<ResponseModel<Object>> callback);
}
