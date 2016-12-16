package com.chatapp.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterByEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_REGISTER_BY_PHONE = 1;
    private static final int RC_CREATE_ACCOUNT = 2;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Bind(R.id.btn_create_account)
    Button btnGoToCreateAccount;
    @Bind(R.id.btn_to_create_account_by_phone)
    Button btnGoToCreateAccountByPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_email);
        ButterKnife.bind(this);

        btnGoToCreateAccount.setOnClickListener(this);
        btnGoToCreateAccountByPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_account:
                startActivityForResult(
                        new Intent(RegisterByEmailActivity.this, VerifyEmailActivity.class),
                        RC_CREATE_ACCOUNT);
                break;
            case R.id.btn_to_create_account_by_phone:


                if (getCallingActivity().getClassName()
                        .equals(RegisterByPhoneActivity.class.getName())) {
                    finish();
                } else {
                    startActivityForResult(
                            new Intent(RegisterByEmailActivity.this, RegisterByPhoneActivity.class),
                            RC_REGISTER_BY_PHONE);
                }
                break;
            default:
                break;
        }
    }
}
