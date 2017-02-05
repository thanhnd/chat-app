package com.chatapp.mvp.updatebasicprofile;

import android.text.TextUtils;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/19/16.
 */
public class UpdateBasicProfilePresenterImpl implements UpdateBasicProfileMvp.UpdateBasicProfilePresenter {
    private WeakReference<UpdateBasicProfileMvp.UpdateBasicProfileView> view;
    private UpdateBasicProfileMvp.UpdateBasicProfileInteractor interactor;

    public UpdateBasicProfilePresenterImpl(UpdateBasicProfileMvp.UpdateBasicProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new UpdateBasicProfileInteractorImpl();
    }

    @Override
    public void submit(BasicProfileRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.submit(request, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onTokenExpired() {

            }

            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onUpdateBasicProfileSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onUpdateBasicProfileFail();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });
    }
}
