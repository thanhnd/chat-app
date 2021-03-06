package com.chatapp.mvp.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chatapp.R;
import com.chatapp.mvp.login.LogInActivity;
import com.chatapp.mvp.register.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGNIN = 1;
    private static final int RC_REGISTER_BY_EMAIL = 2;

    @Bind(R.id.btn_login_here)
    Button btnLoginHere;
    @Bind(R.id.btn_go_to_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        btnLoginHere.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        Fragment fragment = Fragment.instantiate(this, OnboardingFragment.class.getName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_here:
                startActivityForResult(new Intent(this, LogInActivity.class), RC_SIGNIN);
                break;
            case R.id.btn_go_to_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), RC_REGISTER_BY_EMAIL);
                break;
            default:
                break;
        }
    }
}
