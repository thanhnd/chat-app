
package com.chatapp.mvp.updatebasicprofile;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.ResponseModel;

public interface UpdateBasicProfileInteractor {
    void submit(BasicProfileRequest request,
               ApiCallback<ResponseModel<Object>> callback);
}
