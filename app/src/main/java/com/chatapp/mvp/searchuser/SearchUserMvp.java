package com.chatapp.mvp.searchuser;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface SearchUserMvp {

    interface Presenter {
        void searchUser(String keyword) throws RequireLoginException;
    }

    interface Interactor {
        void searchUser(String keyword, ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onSearchSuccess(List<UserModel> resultSet);
    }
}
