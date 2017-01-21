package com.chatapp.mvp.userprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;

public class UserProfileActivity extends BaseActivity implements UserProfileMvp.UserProfileView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }
}
