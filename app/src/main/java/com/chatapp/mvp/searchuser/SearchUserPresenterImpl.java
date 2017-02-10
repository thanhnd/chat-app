
package com.chatapp.mvp.searchuser;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchUserPresenterImpl implements SearchUserMvp.Presenter {

    private WeakReference<SearchUserMvp.View> view;
    private SearchUserMvp.Interactor interactor;

    public SearchUserPresenterImpl(SearchUserMvp.View listCountriesView) {
        this.view = new WeakReference<>(listCountriesView);
        this.interactor = new SearchUserInteractorImpl();
    }

    @Override
    public void searchUser(String keyword) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.searchUser(keyword, new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> responseModel) {
                view.get().onSearchSuccess(responseModel.getResultSet());
            }
        });

    }
}
