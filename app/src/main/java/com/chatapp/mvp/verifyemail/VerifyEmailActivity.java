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
import com.chatapp.service.models.request.VerifyRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyEmailActivity extends BaseActivity implements VerifyEmailView {

    public static final String EXTRA_EMAIL = "extra_email";
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private VerifyEmailPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        ButterKnife.bind(this);
        present = new VerifyEmailPresentImpl();
        Intent intent = getIntent();
        String email = intent.getStringExtra(EXTRA_EMAIL);
        if (!TextUtils.isEmpty(email)) {
            tvEmail.setText(email);
        }
    }

    @OnClick(R.id.btn_submit)
    public void clickSubmit() {
        String code = edtCode.getText().toString();
        VerifyRequest request = new VerifyRequest();
        request.setCode(code);
        present.submitVerifyForm(request);
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
}
