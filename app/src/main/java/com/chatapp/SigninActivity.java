package com.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SigninActivity extends AppCompatActivity {

    private static final int RC_TO_REGISTER = 1;

    @Bind(R.id.rg_login_type)
    RadioGroup rgLoginType;
    @Bind(R.id.v_input_email)
    View vInputEmail;
    @Bind(R.id.v_input_phone)
    View vInputPhone;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        rgLoginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rd_login_by_email) {
                    vInputEmail.setVisibility(View.VISIBLE);
                    vInputPhone.setVisibility(View.GONE);
                } else {
                    vInputEmail.setVisibility(View.GONE);
                    vInputPhone.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void clickLogin(Button btnLogin) {
        startActivity(new Intent(SigninActivity.this, MyProfileActivity.class));
    }

    @OnClick(R.id.btn_go_to_register)
    public void clickGotoRegister(Button btnGotoRegister) {
        startActivityForResult(new Intent(SigninActivity.this, RegisterByEmailActivity.class), RC_TO_REGISTER);
    }
}
