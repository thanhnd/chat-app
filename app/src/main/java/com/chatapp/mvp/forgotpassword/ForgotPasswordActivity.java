package com.chatapp.mvp.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordMvp.View {

    public static final String EXTRA_PHONE = "extra_phone";
    public static final String EXTRA_EMAIL = "extra_email";

    @Bind(R.id.edt_code)
    EditText edtCode;

    @Bind(R.id.v_verify_code)
    View vVerifyCode;

    @Bind(R.id.v_new_password)
    View vNewPassword;

    @Bind(R.id.v_verify_phone)
    View vVerifyPhone;

    @Bind(R.id.v_verify_email)
    View vVerifyEmail;

    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Bind(R.id.tv_email)
    TextView tvEmail;

    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Bind(R.id.edt_confirm_password)
    EditText edtConfirmPassword;

    ForgotPasswordMvp.Presenter presenter;

    private String phone, email;
    private boolean loginByPhone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);

        Intent intent = getIntent();
        phone = intent.getStringExtra(EXTRA_PHONE);
        email = intent.getStringExtra(EXTRA_EMAIL);
        initView();
    }

    private void initView() {
        loginByPhone = !TextUtils.isEmpty(phone);

        toggleVerifyUI();
    }

    private void toggleVerifyUI() {

        if (loginByPhone) {

            vVerifyPhone.setVisibility(View.VISIBLE);
            vVerifyEmail.setVisibility(View.GONE);
            tvPhone.setText(phone);

        } else {

            vVerifyPhone.setVisibility(View.GONE);
            vVerifyEmail.setVisibility(View.VISIBLE);
            tvEmail.setText(email);
        }
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {

        String code = edtCode.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            presenter.submitVerifyCode(code);
        }
    }

    @Override
    public void onSubmitCodeSuccess() {
        vVerifyCode.setVisibility(View.GONE);
        vNewPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSubmitPasswordSuccess() {

    }

    @OnClick(R.id.btn_update)
    public void onClickUpdate() {
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtPassword.getText().toString().trim();

        Map<String, String> request = new HashMap<>();
        request.put("password", password);
        if (loginByPhone) {
            request.put("phone", phone);
        } else {
            request.put("email", email);
        }
        if (confirmPassword.equals(password)) {
            presenter.changePassword(request);
        }

    }
}
