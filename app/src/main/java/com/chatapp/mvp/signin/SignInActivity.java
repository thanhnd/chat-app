package com.chatapp.mvp.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chatapp.R;
import com.chatapp.mvp.RegisterActivity;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.request.SignInRequest;
import com.chatapp.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements SignInView {

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

    private boolean isLoginWithPhone = true;
    private SignInPresenter presenter;

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
                    isLoginWithPhone = false;
                } else {
                    vInputEmail.setVisibility(View.GONE);
                    vInputPhone.setVisibility(View.VISIBLE);
                    isLoginWithPhone = true;
                }
            }
        });

        presenter = new SignInPresenterImpl(this);
    }

    @OnClick(R.id.btn_login)
    public void clickLogin(Button btnLogin) {
        SignInRequest request = new SignInRequest();
        String password = edtPassword.getText().toString();
        request.setPassword(password);
        if (isLoginWithPhone) {
            String phone = edtPhone.getText().toString();
            request.setMobile(phone);
        } else {
            String email = edtEmail.getText().toString();
            request.setEmail(email);
        }
        presenter.login(request);
    }

    @OnClick(R.id.btn_go_to_register)
    public void clickGotoRegister(Button btnGotoRegister) {
        startActivityForResult(new Intent(SignInActivity.this, RegisterActivity.class), RC_TO_REGISTER);
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onSignInError() {
        DialogUtils.showGeneralErrorAlert(this, getString(R.string.identify_or_password_error_incorrect));
    }
}
