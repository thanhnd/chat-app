package com.chatapp.mvp.listfriends;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListFavorites {

    interface Present {
        void getListFavorites();
    }

    interface Interactor {
        void getListFavorites(ApiCallback<ResponseModel<List<UserModel>>> callback);
    }

    interface View extends BaseView {
        void onGetListFavoritesSuccess(List<UserModel> resultSet);
    }


}
