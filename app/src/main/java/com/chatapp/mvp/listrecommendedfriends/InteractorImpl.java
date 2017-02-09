package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements ListRecommendedFriendsMvp.Interactor {
    @Override
    public void getListRecommendedFriends(final ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<UserModel>>> call = service.listRecommendedFriends(AccountUtils.getAuthorization());
        call.enqueue(callback);
    }

    @Override
    public void acceptFriendRequest(String userId, final ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException {

        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.acceptFriendRequest(AccountUtils.getAuthorization(), new UserRequest(userId));
        call.enqueue(callback);
    }
}
