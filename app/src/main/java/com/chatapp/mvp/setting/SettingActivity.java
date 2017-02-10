package com.chatapp.mvp.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class SettingActivity extends BaseActivity implements SettingMvp.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_log_out)
    public void clickLogOut() {
        logOut();
    }
}
