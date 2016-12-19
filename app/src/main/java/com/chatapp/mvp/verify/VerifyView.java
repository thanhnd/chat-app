package com.chatapp.mvp.verify;

import com.chatapp.mvp.base.BaseView;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyView extends BaseView {
    void onVerifySuccess();
    void onVerifyError();
}
