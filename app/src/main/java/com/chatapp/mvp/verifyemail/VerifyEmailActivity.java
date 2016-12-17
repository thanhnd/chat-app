package com.chatapp.mvp.verifyemail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.MyProfileActivity;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.LogInModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyEmailActivity extends BaseActivity implements VerifyEmailView {

    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_PASSWORD = "extra_password";
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private VerifyEmailPresent present;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        ButterKnife.bind(this);
        present = new VerifyEmailPresentImpl(this);
        Intent intent = getIntent();
        email = intent.getStringExtra(EXTRA_EMAIL);
        password = intent.getStringExtra(EXTRA_PASSWORD);
        if (!TextUtils.isEmpty(email)) {
            tvEmail.setText(email);
        }
    }

    @OnClick(R.id.btn_submit)
    public void clickSubmit() {
        //Auto login first
        login();
    }
    private void login() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(email);
        logInRequest.setPassword(password);

        present.requestLogin(logInRequest);
    }

    @OnClick(R.id.btn_request_send_code)
    public void clickResendCode() {

    }

    @Override
    public void onVerifySuccess() {
        startActivity(new Intent(VerifyEmailActivity.this, MyProfileActivity.class));
    }

    @Override
    public void onVerifyError() {

    }

    @Override
    public void onLoginSuccess(LogInModel logInModel) {

        String token = logInModel.getToken();
        //Submit Verify code
        String code = edtCode.getText().toString();
        VerifyEmailRequest request = new VerifyEmailRequest();
        request.setCode(code);
        present.submitVerifyForm(token, request);
    }

    @Override
    public void onLoginError() {

    }
}
