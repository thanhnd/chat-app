package com.chatapp.mvp.listfavorites;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class InteractorImpl implements ListFavoritesMvp.Interactor {
    @Override
    public void getListFavorites(final ApiCallback<ResponseModel<List<UserModel>>> callback) {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<UserModel>>> call = service.listFavorites(authorization);
        call.enqueue(callback);
    }
}
