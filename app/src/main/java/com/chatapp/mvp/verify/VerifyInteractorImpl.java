package com.chatapp.mvp.verify;


import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;
import com.chatapp.utils.AccountUtils;

import java.util.Map;

import retrofit2.Call;

public class VerifyInteractorImpl implements VerifyMvp.VerifyInteractor {

    @Override
    public void verify(Map<String, String> request, final ApiCallback<ResponseModel<VerifyModel>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();

        Call<ResponseModel<VerifyModel>> call = service.verifyCode(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

}
