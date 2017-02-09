package com.chatapp.mvp.splash;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteractorImpl implements SplashMvp.Interactor {

    @Override
    public void getListCommonParams(final ApiCallback<ResponseModel<ListParamsModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<ListParamsModel>> call = service.listCommon();
        call.enqueue(new Callback<ResponseModel<ListParamsModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ListParamsModel>> call, Response<ResponseModel<ListParamsModel>> response) {
                Log.d(response.raw().toString());
                ResponseModel<ListParamsModel> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() != ResponseModel.RESPONSE_CD_ERROR) {
                        callback.onSuccess(responseModel);
                    } else {
                        callback.onFailure(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ListParamsModel>> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(call, t);
                }
                Log.e(t);
            }
        });
    }
}
