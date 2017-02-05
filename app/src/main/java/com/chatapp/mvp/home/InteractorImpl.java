package com.chatapp.mvp.home;

import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.ApiService;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements HomeMvp.Interactor {
    @Override
    public void updatLocation(double latitude, double longitude, final AuthorizeApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, Double> location = new HashMap<>();
        location.put("longitude", longitude);
        location.put("latitude", latitude);
        Call<ResponseModel<Object>> call = service.updateLonLat(authorization, location);
        call.enqueue(new Callback<ResponseModel<Object>>() {
            @Override
            public void onResponse(Call<ResponseModel<Object>> call, Response<ResponseModel<Object>> response) {
                ResponseModel<Object> responseModel = response.body();
                Log.d(response.raw().toString());
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS) {
                        callback.onSuccess(responseModel);
                    } else {
                        callback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Object>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }
}
