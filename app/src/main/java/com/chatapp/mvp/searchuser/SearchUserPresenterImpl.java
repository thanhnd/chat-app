
package com.chatapp.mvp.searchuser;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchUserPresenterImpl implements SearchUserMvp.Presenter {

    private WeakReference<SearchUserMvp.View> view;
    private SearchUserMvp.Interactor interactor;

    public SearchUserPresenterImpl(SearchUserMvp.View listCountriesView) {
        this.view = new WeakReference<>(listCountriesView);
        this.interactor = new SearchUserInteractorImpl();
    }

    @Override
    public void searchUser(String keyword) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.searchUser(keyword, new ApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        view.get().onSearchSuccess(responseModel.getResultSet());
                    }
                }
            }

            @Override
            public void onFail(Response<ResponseModel<List<UserModel>>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel<List<UserModel>> responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null) {
                        if (!TextUtils.isEmpty(responseModel.getResponseMsg())) {
                            view.get().showErrorDialog(responseModel.getResponseMsg());
                        } else {
                            view.get().onSearchSuccess(responseModel.getResultSet());
                        }
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<List<UserModel>>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });

    }
}
