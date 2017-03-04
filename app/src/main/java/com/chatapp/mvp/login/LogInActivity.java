package com.chatapp.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.forgotpassword.ForgotPasswordActivity;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.mvp.register.RegisterActivity;
import com.chatapp.mvp.updatebasicprofile.UpdateBasicProfileActivity;
import com.chatapp.mvp.verify.VerifyActivity;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;

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

    @Bind(R.id.tv_error_phone)
    TextView tvPhoneError;
    @Bind(R.id.tv_error_email)
    TextView tvEmailError;
    @Bind(R.id.tv_error_password)
    TextView tvPasswordError;

    private LoginMvp.LogInPresenter presenter;
    private boolean isLoginWithPhone = true;
    String phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    processLogin();

                    return true;
                }

                return false;
            }
        });

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
        processLogin();
    }

    private void processLogin() {
        if (validate()) {
            LogInRequest request = new LogInRequest();
            String password = edtPassword.getText().toString().trim();
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
    }

    private boolean validate() {
        boolean result = true;
        String password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            tvPasswordError.setText("Please enter your password.");
            tvPasswordError.setVisibility(View.VISIBLE);
            edtPassword.requestFocus();
            result = false;
        } else {
            tvPasswordError.setVisibility(View.GONE);
        }

        if (isLoginWithPhone) {
            phone = edtPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                tvPhoneError.setText("Please enter your phone number.");
                tvPhoneError.setVisibility(View.VISIBLE);
                edtPhone.requestFocus();
                result = false;
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                tvPhoneError.setText("Please enter a valid phone number.");
                tvPhoneError.setVisibility(View.VISIBLE);
                edtPhone.requestFocus();
                result = false;
            } else {
                tvPhoneError.setVisibility(View.GONE);
            }

        } else {
            email = edtEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                tvEmailError.setText("Please enter your email address.");
                tvEmailError.setVisibility(View.VISIBLE);
                edtEmail.requestFocus();
                result = false;
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tvEmailError.setText("Please enter a valid email address.");
                tvEmailError.setVisibility(View.VISIBLE);
                edtEmail.requestFocus();
                result = false;

            } else {
                tvEmailError.setVisibility(View.GONE);
            }

        }

        return result;
    }

    @OnClick(R.id.btn_go_to_register)
    public void clickGotoRegister(Button btnGotoRegister) {
        startActivityForResult(new Intent(LogInActivity.this, RegisterActivity.class), RC_TO_REGISTER);
    }

    @OnClick(R.id.btn_forgot_password)
    public void clickForgotPassword() {

        navigateToForgotPassword();
    }

    private void navigateToForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        intent.putExtra(ForgotPasswordActivity.EXTRA_IS_LOGIN_WITH_PHONE, isLoginWithPhone);

        if (isLoginWithPhone) {

            phone = edtPhone.getText().toString();
            if (!TextUtils.isEmpty(phone)) {
                intent.putExtra(ForgotPasswordActivity.EXTRA_PHONE, phone);
            }

        } else {

            email = edtEmail.getText().toString();
            if (!TextUtils.isEmpty(email)) {
                intent.putExtra(ForgotPasswordActivity.EXTRA_EMAIL, email);
            }
        }

        startActivity(intent);
    }

    @Override
    public void onLogInSuccess() {
        startLoginService(SharedPreferencesUtil.getQbUser());

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNotVerify() {
        Intent intent = new Intent(this, VerifyActivity.class);
        if (isLoginWithPhone) {
            AccountUtils.setPhone(phone);
            intent.putExtra(VerifyActivity.EXTRA_PHONE, phone);
        } else {
            AccountUtils.setPhone(email);
            intent.putExtra(VerifyActivity.EXTRA_EMAIL, email);
        }
        startActivity(intent);
    }

    @Override
    public void onNotConfirm() {
        Intent intent = new Intent(this, UpdateBasicProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLogInError() {
        DialogUtils.showGeneralErrorAlert(this, getString(R.string.identify_or_password_error_incorrect));
    }
}
