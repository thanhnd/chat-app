
package com.chatapp.mvp.register;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.RegisterModel;

public interface RegisterInteractor {
    void register(RegisterRequest request, ApiCallback<RegisterModel> callback);
}
