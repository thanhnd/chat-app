
package com.chatapp.mvp.changepassword;

import android.os.Bundle;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.Log;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.lang.ref.WeakReference;

public class PresenterImpl implements ChangePasswordMvp.Presenter {

    private WeakReference<ChangePasswordMvp.View> view;
    private GeneralInteractor interactor;

    public PresenterImpl(ChangePasswordMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }


    @Override
    public void changePassword(final String currentPassword, final String newPassword) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.updatePassword(currentPassword, newPassword, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                onChangePasswordSuccess(currentPassword, newPassword);

            }
        });
    }

    public void onChangePasswordSuccess(final String currentPassword, final String newPassword) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        final QBUser user = new QBUser();
        final QBUser currentUser = com.chatapp.chat.utils.chat.ChatHelper.getCurrentUser();
        if (currentUser != null) {
            user.setId(currentUser.getId());
            user.setOldPassword(currentPassword);
            user.setPassword(newPassword);

            updateQuickbloxUser(user);
        }
    }

    private void updateQuickbloxUser(QBUser user) {
        QBUsers.updateUser(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.d();
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onChangePasswordSuccess();
                }
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e(e);
            }
        });
    }
}
