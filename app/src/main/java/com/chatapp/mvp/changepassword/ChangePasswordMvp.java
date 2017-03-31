package com.chatapp.mvp.changepassword;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface ChangePasswordMvp {
    interface Presenter {
        void changePassword(String currentPassword, String newPassword) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onChangePasswordSuccess();
    }
}
