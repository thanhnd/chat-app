package com.chatapp.mvp.listchat;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListChatMvp {

    interface Presenter {
        void getListChat() throws RequireLoginException;
    }

    interface Interactor {
        void getListChat(ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onGetListChatSuccess(List<UserModel> resultSet);
    }


}
