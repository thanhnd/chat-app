package com.chatapp.mvp.register;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInteractorImpl implements RegisterInteractor {

    @Override
    public void register(RegisterRequest request, final ApiCallback<RegisterModel> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<RegisterModel> call = service.register(request);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                Log.d(response.raw().toString());
                RegisterModel registerModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && registerModel != null
                            && registerModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS) {
                        callback.onSuccess(registerModel);
                    } else {
                        callback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

}
