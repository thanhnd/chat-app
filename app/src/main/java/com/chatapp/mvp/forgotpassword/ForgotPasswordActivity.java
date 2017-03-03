package com.chatapp.mvp.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.utils.DialogUtils;
import com.chatapp.views.fragments.ConfirmDialogFragment;
import com.chatapp.views.fragments.RetainedDialogFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordMvp.View {

    public static final String EXTRA_PHONE = "extra_phone";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_IS_LOGIN_WITH_PHONE = "is_login_with_phone";

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

    @Bind(R.id.v_finish)
    View vFinish;

    @Bind(R.id.v_enter_email)
    View vEnterEmail;

    @Bind(R.id.v_enter_phone)
    View vEnterPhone;

    @Bind(R.id.edt_email)
    EditText edtEmail;

    @Bind(R.id.edt_phone)
    EditText edtPhone;

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
    private boolean isLoginWithPhone;

    private View[] steps;
    private int currentStep;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        presenter = new PresenterImpl(this);

        Intent intent = getIntent();
        phone = intent.getStringExtra(EXTRA_PHONE);
        email = intent.getStringExtra(EXTRA_EMAIL);
        isLoginWithPhone = intent.getBooleanExtra(EXTRA_IS_LOGIN_WITH_PHONE, true);
        initView();
    }

    private void initView() {
        if (isLoginWithPhone) {

            steps = new View[] {vEnterPhone, vVerifyCode, vNewPassword, vFinish};

            vVerifyPhone.setVisibility(View.VISIBLE);
            vVerifyEmail.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(phone)) {
                tvPhone.setText(phone);
                edtPhone.setText(phone);
            }

        } else {

            steps = new View[] {vEnterEmail, vVerifyCode, vNewPassword, vFinish};
            vVerifyPhone.setVisibility(View.GONE);
            vVerifyEmail.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(email)) {
                edtEmail.setText(email);
            }
        }

        showStep(currentStep);
    }

    private void showStep(int stepIndex) {
        for(int i = 0; i < steps.length; i++) {
            View step = steps[i];
            step.setVisibility(i == stepIndex ? View.VISIBLE: View.GONE);
        }
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {

        code = edtCode.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            presenter.submitVerifyCode(code);
        }
    }

    @OnClick(R.id.btn_submit_email)
    public void onClickSubmitEmail() {

        email = edtEmail.getText().toString().trim();
        if (!TextUtils.isEmpty(email)) {
            Map<String, String> request = new HashMap<>();
            request.put("email", email);
            presenter.sendVerifyCodeForgotPassword(request);
        }
    }

    @OnClick(R.id.btn_submit_phone)
    public void onClickSubmitPhone() {
        showConfirmPhoneDialog();
    }

    @Override
    public void onSubmitCodeSuccess() {
        showStep(++ currentStep);
    }

    @Override
    public void onSubmitPasswordSuccess() {
        showStep(++ currentStep);
    }

    @Override
    public void sendVerifyCodeForgotPasswordSuccess() {
        tvEmail.setText(email);
        showStep(++ currentStep);
    }

    @OnClick(R.id.btn_update)
    public void onClickUpdate() {
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtPassword.getText().toString().trim();

        Map<String, String> request = new HashMap<>();
        request.put("password", password);
        request.put("code", code);
        if (isLoginWithPhone) {
            request.put("mobile", phone);
        } else {
            request.put("email", email);
        }
        if (confirmPassword.equals(password)) {
            presenter.changePassword(request);
        }
    }

    private void showConfirmPhoneDialog() {
        phone = edtPhone.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            String message = getString(R.string.msg_forgot_password_confirm);
            DialogUtils.showConfirmDialog(this, phone, message,
                    getString(R.string.Confirm), getString(R.string.cancel),
                    new ConfirmDialogFragment.OnConfirmListener() {

                        @Override
                        public void onConfirm(RetainedDialogFragment fragment) {
                            fragment.dismiss();

                            phone = edtPhone.getText().toString().trim();
                            if (!TextUtils.isEmpty(phone)) {

                                Map<String, String> request = new HashMap<>();
                                request.put("mobile", phone);
                                presenter.sendVerifyCodeForgotPassword(request);
                            }
                        }
                    }, null);
        }
    }

    @OnClick(R.id.btn_finish)
    public void onClickFinish() {
        finish();
    }
}
