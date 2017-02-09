package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresenterImpl implements ListRecommendedFriendsMvp.Presenter {

    private WeakReference<ListRecommendedFriendsMvp.View> view;
    private ListRecommendedFriendsMvp.Interactor interactor;

    public PresenterImpl(ListRecommendedFriendsMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }

    @Override
    public void getListRecommendedFriends() throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getListRecommendedFriends(new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListRecommendedFriendsSuccess(response.getResultSet());
                }
            }
        });
    }

    @Override
    public void acceptFriendRequest(String userId) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.acceptFriendRequest(userId, new BaseApiCallback<ResponseModel<Object>>(view.get()) {

            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onAcceptFriendSuccess();
                }
            }
        });
    }

    @Override
    public void deleteRecommends(Set<UserModel> userModels) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        List<Map<String, String>> list = new ArrayList<>();
        for (UserModel userModel: userModels) {
            Map item = new HashMap<>();
            item.put("user_id", userModel.getUserId());
            list.add(item);
        }
        interactor.deleteRecomments(list, new BaseApiCallback<ResponseModel<Object>>(view.get()) {

            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onDeleteRecommendsSuccess();
                }
            }
        });
    }
}
