package com.chatapp.mvp.myprofile;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.MyProfileModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface MyProfileMvp {
    interface MyProfileInteractor {
    }

    interface MyProfilePresenter {
        void getMyProfile();
    }

    interface MyProfileView extends BaseView {
        void onGetMyProfileSuccess(MyProfileModel resultSet);
    }
}
