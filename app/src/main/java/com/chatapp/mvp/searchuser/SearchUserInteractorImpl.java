package com.chatapp.mvp.searchuser;


import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class SearchUserInteractorImpl implements SearchUserMvp.Interactor {

    @Override
    public void searchUser(String keyword, final ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, String> request = new HashMap<>();
        request.put("keyword", keyword);
        Call<ResponseModel<List<UserModel>>> call = service.search(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }
}
