package com.chatapp.mvp.listfavorites;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListFavoritesMvp {

    interface Presenter {
        void getListFavorites(boolean isFirstPage) throws RequireLoginException;
    }

    interface Interactor {
        void getListFavorites(ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onGetListFavoritesSuccess(List<UserModel> resultSet);
        void navigateToUserProfile(UserModel userModel);
    }

}
