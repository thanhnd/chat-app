package com.chatapp.mvp.base;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public class GeneralInteractorImmpl implements GeneralInteractor {
    @Override
    public void getMyProfile(final ApiCallback<ResponseModel<MyProfileModel>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<MyProfileModel>> call = service.getMyProfile(AccountUtils.getAuthorization());
        call.enqueue(callback);
    }
}
