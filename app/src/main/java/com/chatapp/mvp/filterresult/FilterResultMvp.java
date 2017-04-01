package com.chatapp.mvp.filterresult;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.UserModel;

import java.util.List;
import java.util.Map;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface FilterResultMvp {

    interface Presenter {
        void searchUser(Map query) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onSearchSuccess(List<UserModel> resultSet);
        void navigateToUserProfile(UserModel userModel);
    }
}
