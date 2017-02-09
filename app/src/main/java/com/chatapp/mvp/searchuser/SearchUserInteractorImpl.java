package com.chatapp.mvp.searchuser;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.util.List;

import retrofit2.Call;

public class SearchUserInteractorImpl implements SearchUserMvp.Interactor {

    @Override
    public void searchUser(String keyword, final ApiCallback<ResponseModel<List<UserModel>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<UserModel>>> call = service.search(authorization);
        call.enqueue(callback);
    }
}
