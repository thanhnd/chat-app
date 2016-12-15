package com.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterByPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_REGISTER_BY_EMAIL = 1;
    private static final int RC_CREATE_ACCOUNT = 2;
    @Bind(R.id.edt_country)
    EditText edtCountry;
    @Bind(R.id.edt_phone)
    EditText edtPhone;


    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;
    @Bind(R.id.btn_to_create_account_by_email)
    Button btnGoToCreateAccountByEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_phone);
        ButterKnife.bind(this);

        btnCreateAccount.setOnClickListener(this);
        btnGoToCreateAccountByEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_account:
                startActivityForResult(
                        new Intent(RegisterByPhoneActivity.this, VerifyPhoneActivity.class),
                        RC_CREATE_ACCOUNT);
                break;
            case R.id.btn_to_create_account_by_email:
                if (getCallingActivity().getClassName()
                        .equals(RegisterByEmailActivity.class.getName())) {
                    finish();
                } else {
                    startActivityForResult(
                            new Intent(RegisterByPhoneActivity.this, RegisterByEmailActivity.class),
                            RC_REGISTER_BY_EMAIL);
                }

                break;
            default:
                break;
        }
    }
}
