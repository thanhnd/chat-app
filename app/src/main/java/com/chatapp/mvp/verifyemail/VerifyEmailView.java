package com.chatapp.mvp.verifyemail;

import com.chatapp.mvp.base.BaseView;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyEmailView extends BaseView {
    void onVerifySuccess();

    void onVerifyError();
}
