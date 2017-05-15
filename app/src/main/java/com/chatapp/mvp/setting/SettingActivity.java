package com.chatapp.mvp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;

import com.chatapp.Config;
import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.base.BrowserActivity;
import com.chatapp.mvp.changepassword.ChangePasswordActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;
import com.chatapp.utils.Log;
import com.chatapp.views.fragments.ChooseUnitDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class SettingActivity extends BaseActivity implements SettingMvp.View {

    @Bind(R.id.btn_hide_distance)
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
        setTitle(R.string.action_settings);
    }

    @OnClick(R.id.btn_choose_unit_system)
    void onClickChooseUnitSystem() {
        final MyProfileModel myProfileModel = AccountUtils.getMyProfileModel();

        final int unitSystem = myProfileModel.getUnitSystem();

        DialogUtils.showUnitSystemDialog(this, unitSystem, new ChooseUnitDialogFragment.OnUnitSystemSetListener() {
            @Override
            public void onSetUnitSystem(int unitValue) {
                try {
                    presenter.updateUnitSystem(unitValue);
                    myProfileModel.setUnitSystem(unitValue);
                } catch (RequireLoginException e) {
                    onTokenExpired();
                }
            }
        });
    }

    @OnClick({R.id.btn_hide_distance, R.id.cb_show_distance})
    void onClickHideDistance() {
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

    @OnClick(R.id.btn_go_to_about)
    public void clickGoToAbout() {

        openBrowser(Config.URL_ABOUT);
    }

    private void openBrowser(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra(BrowserActivity.ARG_LOAD_WEB_URL, url);
        startActivity(intent);
    }

    @OnClick(R.id.btn_go_to_term)
    public void clickGoToTerm() {
        openBrowser(Config.URL_TERM);
    }

    @OnClick(R.id.btn_go_to_privacy)
    public void clickGotoPrivacy() {
        openBrowser(Config.URL_PRIVACY);
    }

    @OnClick(R.id.btn_go_to_change_password)
    public void clickGoToChangePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivityForResult(intent, RC_CHANGE_PASSWORD);
    }

    @Override
    public void onGetShowHideDistanceSuccess(boolean isOn) {
        cbShowDistance.setChecked(isOn);
    }

    @Override
    public void onUpdateShowHideDistanceSuccess(Object resultSet) {

    }

    @Override
    public void onUpdateUnitSystemSuccess(Object resultSet) {

    }
}
