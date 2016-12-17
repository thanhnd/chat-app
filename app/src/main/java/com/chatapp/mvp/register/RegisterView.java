package com.chatapp.mvp.register;

import com.chatapp.mvp.base.BaseView;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface RegisterView extends BaseView {
    void onRegisterSuccess();

    void onRegisterError();
}
