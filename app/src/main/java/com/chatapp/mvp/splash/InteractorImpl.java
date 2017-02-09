package com.chatapp.mvp.splash;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ResponseModel;

import retrofit2.Call;

public class InteractorImpl implements SplashMvp.Interactor {

    @Override
    public void getListCommonParams(final ApiCallback<ResponseModel<ListParamsModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<ListParamsModel>> call = service.listCommon();
        call.enqueue(callback);
    }
}
