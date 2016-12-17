
package com.chatapp.mvp.verify;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.VerifyModel;

public interface VerifyInteractor {
    void verify(VerifyEmailRequest request,
               ApiCallback<ResponseModel<VerifyModel>> loginCallback);
    void login(LogInRequest request,
                ApiCallback<ResponseModel<LogInModel>> loginCallback);
}
