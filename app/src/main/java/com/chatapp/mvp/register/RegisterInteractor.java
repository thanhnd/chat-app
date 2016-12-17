
package com.chatapp.mvp.register;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;

public interface RegisterInteractor {
    void register(RegisterRequest request, ApiCallback<ResponseModel<RegisterModel>> callback);
}
