package com.chatapp.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.mvp.register.RegisterActivity;
import com.chatapp.mvp.updatebasicprofile.UpdateBasicProfileActivity;
import com.chatapp.mvp.verify.VerifyActivity;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.utils.DialogUtils;
import com.chatapp.views.fragments.ConfirmDialogFragment;
import com.chatapp.views.fragments.RetainedDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends BaseActivity implements LoginMvp.LogInView {

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

    private LoginMvp.LogInPresenter presenter;
    private boolean isLoginWithPhone = true;
    String phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        presenter = new LogInPresenterImpl(this);
    }

    @OnClick(R.id.btn_login)
    public void clickLogin(Button btnLogin) {
        LogInRequest request = new LogInRequest();
        String password = edtPassword.getText().toString();
        request.setPassword(password);
        if (isLoginWithPhone) {
            phone = edtPhone.getText().toString();
            request.setMobile(phone);
        } else {
            email = edtEmail.getText().toString();
            request.setEmail(email);
        }
        presenter.login(request);
    }

    @OnClick(R.id.btn_go_to_register)
    public void clickGotoRegister(Button btnGotoRegister) {
        startActivityForResult(new Intent(LogInActivity.this, RegisterActivity.class), RC_TO_REGISTER);
    }

    @OnClick(R.id.btn_forgot_password)
    public void clickForgotPassword() {

        if (isLoginWithPhone) {
            final String phone = edtPhone.getText().toString();
            if (!TextUtils.isEmpty(phone)) {
                String message = getString(R.string.msg_forgot_password_confirm);
                DialogUtils.showConfirmDialog(this, phone, message, "Confirm", "Cancel", new ConfirmDialogFragment.OnConfirmListener() {
                    @Override
                    public void onConfirm(RetainedDialogFragment fragment) {
                        fragment.dismiss();
                        presenter.onConfirmPhoneNumber(phone);
                    }
                }, null);
            }
        }
    }

    @Override
    public void onLogInSuccess() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNotVerify() {
        Intent intent = new Intent(this, VerifyActivity.class);
        if (isLoginWithPhone) {
            intent.putExtra(VerifyActivity.EXTRA_PHONE, phone);
        } else {
            intent.putExtra(VerifyActivity.EXTRA_EMAIL, email);
        }
        startActivity(intent);
    }

    @Override
    public void onNotConfirm() {
        Intent intent = new Intent(this, UpdateBasicProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendVerifyCodeForgotPasswordWithPhoneSuccess() {

    }

    @Override
    public void onLogInError() {
        DialogUtils.showGeneralErrorAlert(this, getString(R.string.identify_or_password_error_incorrect));
    }
}
