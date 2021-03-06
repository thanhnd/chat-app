package com.chatapp.mvp.listnearby;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements ListNearbyMvp.Interactor {
    @Override
    public void getListNearBy(final ListNearbyRequest request, final ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<UserModel>>> call = service.listNearby(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }
}
