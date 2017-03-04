package com.chatapp.mvp.verify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.updatebasicprofile.UpdateBasicProfileActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.utils.AccountUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyActivity extends BaseActivity implements VerifyMvp.VerifyView {

    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_PHONE = "extra_phone";
    @Bind(R.id.v_verify_email)
    View vVerifyEmail;
    @Bind(R.id.v_verify_phone)
    View vVerifyPhone;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.tv_sent_code_to_phone)
    TextView tvSentCodeToPhone;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.tv_error_code)
    TextView tvCodeError;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private VerifyMvp.VerifyPresent present;
    private String email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);

        edtCode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    processSubmit();

                    return true;
                }

                return false;
            }
        });



        present = new VerifyPresentImpl(this);

        Intent intent = getIntent();
        email = intent.getStringExtra(EXTRA_EMAIL);
        phone = intent.getStringExtra(EXTRA_PHONE);

        if (TextUtils.isEmpty(email)) {
            email = AccountUtils.getEmail();
        }

        if (TextUtils.isEmpty(phone)) {
            phone = AccountUtils.getPhone();
        }

        if (!TextUtils.isEmpty(email)) {

            tvEmail.setText(email);
            vVerifyEmail.setVisibility(View.VISIBLE);
            vVerifyPhone.setVisibility(View.GONE);

        } else if (!TextUtils.isEmpty(phone)) {

            tvSentCodeToPhone.setText(getString(R.string.msg_sent_verify_phone_code,
                    AccountUtils.getHiddenPhone(phone)));
            vVerifyEmail.setVisibility(View.GONE);
            vVerifyPhone.setVisibility(View.VISIBLE);

        } else {
            logOut();
        }
    }

    @OnClick(R.id.btn_submit)
    public void clickSubmit() {
        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel != null && !TextUtils.isEmpty(logInModel.getToken())) {
            processSubmit();
        } else {
            logOut();
        }
    }

    private void processSubmit() {

        String code = edtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            tvCodeError.setText("Please enter the verification code.");
            tvCodeError.setVisibility(View.VISIBLE);
            edtCode.requestFocus();
            return;
        } else {
            tvCodeError.setVisibility(View.GONE);
        }

        submitVerifyCode();
    }

    @OnClick(R.id.btn_request_send_code)
    public void clickResendCode() {

    }

    @Override
    public void onVerifySuccess() {
        startActivity(new Intent(VerifyActivity.this, UpdateBasicProfileActivity.class));
    }

    @Override
    public void onVerifyError() {

    }

    private void submitVerifyCode() {
        //Submit Verify code
        String code = edtCode.getText().toString().trim();
        Map<String, String> request = new HashMap<>();
        request.put("code", code);
        if (!TextUtils.isEmpty(code)) {
            try {
                present.submitVerifyForm(request);
            } catch (RequireLoginException e) {
                onRequiredLogin();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (getCallingActivity() == null) {
            logOut();
            return;
        }

        super.onBackPressed();
    }
}
