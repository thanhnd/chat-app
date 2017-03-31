
package com.chatapp.mvp.changepassword;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

public class PresenterImpl implements ChangePasswordMvp.Presenter {

    private WeakReference<ChangePasswordMvp.View> view;
    private GeneralInteractor interactor;

    public PresenterImpl(ChangePasswordMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }


    @Override
    public void changePassword(String currentPassword, String newPassword) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.updatePassword(currentPassword, newPassword, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                if (view.get() != null) {
                    view.get().onChangePasswordSuccess();
                }
            }
        });
    }
}
