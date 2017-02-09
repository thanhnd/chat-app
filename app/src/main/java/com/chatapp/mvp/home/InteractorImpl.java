package com.chatapp.mvp.home;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements HomeMvp.Interactor {
    @Override
    public void updatLocation(double latitude, double longitude, final ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, Double> location = new HashMap<>();
        location.put("longitude", longitude);
        location.put("latitude", latitude);
        Call<ResponseModel<Object>> call = service.updateLonLat(AccountUtils.getAuthorization(), location);
        call.enqueue(callback);
    }
}
