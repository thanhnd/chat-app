
package com.chatapp.mvp.login;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.LogInModel;

public interface LogInInteractor {
    void login(LogInRequest request,
               ApiCallback<ResponseModel<LogInModel>> callback);
}
