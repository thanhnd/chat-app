package com.chatapp.mvp.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.utils.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class SettingActivity extends BaseActivity implements SettingMvp.View {

    @Bind(R.id.btn_show_distance)
    Button btnShowDistance;

    @Bind(R.id.cb_show_distance)
    CheckBox cbShowDistance;

    SettingMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        presenter = new SettingPresenterImpl(this);

        try {
            this.presenter.getShowHideDistance();
        } catch (RequireLoginException e) {
            Log.e(e);
        }
    }

    @OnClick({R.id.btn_show_distance, R.id.cb_show_distance})
    void onClickShowDistance() {
        boolean isChecked = cbShowDistance.isChecked();

        try {
            presenter.updateShowHideDistance(isChecked);
        } catch (RequireLoginException e) {
            Log.e(e);
        }
    }

    @OnClick(R.id.btn_log_out)
    public void clickLogOut() {
        logOut();
    }

    @Override
    public void onGetShowHideDistanceSuccess(boolean isOn) {
        cbShowDistance.setChecked(isOn);
    }

    @Override
    public void onUpdateShowHideDistanceSuccess(Object resultSet) {

    }
}
