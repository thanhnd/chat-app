
package com.chatapp.mvp.forgotpassword;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;

import java.lang.ref.WeakReference;

public class PresenterImpl implements ForgotPasswordMvp.Presenter {

    private WeakReference<ForgotPasswordMvp.View> view;
    private GeneralInteractor interactor;

    public PresenterImpl(ForgotPasswordMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }
}
