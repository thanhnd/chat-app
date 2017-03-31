package com.chatapp.mvp.setting;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;

/**
 * Created by thanhnguyen on 2/10/17.
 */

public class SettingMvp {
    interface View extends BaseView {

        void onGetShowHideDistanceSuccess(boolean isOn);

        void onUpdateShowHideDistanceSuccess(Object resultSet);
    }

    interface Presenter {

        void updateShowHideDistance(boolean isOn) throws  RequireLoginException;

        void getShowHideDistance() throws RequireLoginException;
    }
}
