
package com.chatapp.mvp.signin;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.SignInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.SignInModel;

public interface SignInInteractor {
    void login(SignInRequest signInRequest,
               ApiCallback<ResponseModel<SignInModel>> loginCallback);
}
