
package com.chatapp.mvp.verifyemail;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.VerifyModel;

public interface VerifyEmailInteractor {
    void verify(String authorization, VerifyEmailRequest request,
               ApiCallback<ResponseModel<VerifyModel>> loginCallback);
    void login(LogInRequest request,
                ApiCallback<ResponseModel<LogInModel>> loginCallback);
}
