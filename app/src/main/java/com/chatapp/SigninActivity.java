package com.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_TO_REGISTER = 1;

    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_to_register)
    Button btnToCreateAccount;
    @Bind(R.id.btn_forgot_password)
    Button btnForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        btnToCreateAccount.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(SigninActivity.this, MyProfileActivity.class));
                break;
            case R.id.btn_to_register:
                startActivityForResult(new Intent(SigninActivity.this, RegisterByEmailActivity.class), RC_TO_REGISTER);
                break;
            default:
                break;
        }
    }
}
