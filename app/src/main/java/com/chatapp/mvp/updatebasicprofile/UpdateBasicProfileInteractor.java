
package com.chatapp.mvp.updatebasicprofile;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;

public interface UpdateBasicProfileInteractor {
    void submit(UpdateBasicProfileView request,
               ApiCallback<ResponseModel<Object>> callback);
}
