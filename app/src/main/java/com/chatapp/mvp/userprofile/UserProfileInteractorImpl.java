package com.chatapp.mvp.userprofile;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.utils.AccountUtils;

import retrofit2.Call;

public class UserProfileInteractorImpl implements UserProfileMvp.UserProfileInteractor {

    @Override
    public void getUserProfile(String userId, final ApiCallback<ResponseModel<UserProfileModel>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<UserProfileModel>> call = service.getUserProfile(authorization, userId);
        call.enqueue(callback);
    }

    @Override
    public void addFavorite(String userId, final ApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.addFavorite(authorization, new UserRequest(userId));
        call.enqueue(callback);
    }

    @Override
    public void requestAddFriend(String userId, String noted, final ApiCallback<ResponseModel<Object>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.requestAddFriend(authorization, new UserRequest(userId, noted));
        call.enqueue(callback);
    }

    @Override
    public void removeFavorite(String userId, final ApiCallback<ResponseModel<Object>> callback) {

        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }

        String authorization = logInModel.getToken();

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.removeFavorite(authorization, new UserRequest(userId));
        call.enqueue(callback);
    }
}
