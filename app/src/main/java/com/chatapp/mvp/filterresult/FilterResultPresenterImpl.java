
package com.chatapp.mvp.filterresult;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class FilterResultPresenterImpl implements FilterResultMvp.Presenter {

    private WeakReference<FilterResultMvp.View> view;
    private GeneralInteractor interactor;

    public FilterResultPresenterImpl(FilterResultMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void searchUser(Map query) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.applyFilter(query, new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> responseModel) {
                view.get().onSearchSuccess(responseModel.getResultSet());
            }
        });

    }
}
