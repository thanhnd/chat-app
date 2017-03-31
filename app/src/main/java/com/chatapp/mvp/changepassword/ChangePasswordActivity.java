package com.chatapp.mvp.changepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.Config;
import com.chatapp.R;
import com.chatapp.mvp.base.BaseChatActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.utils.DialogUtils;
import com.chatapp.utils.Log;
import com.chatapp.views.fragments.RetainedDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseChatActivity implements ChangePasswordMvp.View {

    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.edt_new_password)
    EditText edtNewPassword;
    @Bind(R.id.edt_confirm_password)
    EditText edtConfirmPassword;

    @Bind(R.id.tv_error_password)
    TextView tvPasswordError;

    @Bind(R.id.tv_error_new_password)
    TextView tvNewPasswordError;

    @Bind(R.id.tv_error_confirm_password)
    TextView tvConfirmPasswordError;

    ChangePasswordMvp.Presenter presenter;

    private String password, newPassword, confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        presenter = new PresenterImpl(this);

    }

    @OnClick(R.id.btn_change_password)
    public void onClickChangePassword() {
        if (validate()) {
            processUpdate();
        }
    }

    @Override
    public void onChangePasswordSuccess() {
        DialogUtils.showGeneralAlert(this, "Message", "Password changed successfully.", new RetainedDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(RetainedDialogFragment fragment) {
                logOut();
            }
        });
    }

    private boolean validate() {
        boolean result = true;

        password = edtPassword.getText().toString().trim();
        newPassword = edtNewPassword.getText().toString().trim();
        confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            tvPasswordError.setText("Please enter your password.");
            tvPasswordError.setVisibility(View.VISIBLE);
            edtPassword.requestFocus();
            result = false;
        } else {
            tvPasswordError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(newPassword)) {
            tvNewPasswordError.setText("Please enter your new password.");
            tvNewPasswordError.setVisibility(View.VISIBLE);
            tvNewPasswordError.requestFocus();
            result = false;
        } else if(newPassword.length() < Config.PASSWORD_MIN_LENGTH) {
            tvNewPasswordError.setText("Password at least 8 characters.");
            tvNewPasswordError.setVisibility(View.VISIBLE);
            tvNewPasswordError.requestFocus();
            result = false;
        } else {
            tvNewPasswordError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            tvConfirmPasswordError.setText("Please enter confirm password.");
            tvConfirmPasswordError.setVisibility(View.VISIBLE);
            edtConfirmPassword.requestFocus();
            result = false;
        } else if(!confirmPassword.equals(newPassword)) {
            tvConfirmPasswordError.setText("Confirm password doesn't match.");
            tvConfirmPasswordError.setVisibility(View.VISIBLE);
            edtConfirmPassword.requestFocus();
            result = false;
        } else {
            tvConfirmPasswordError.setVisibility(View.GONE);
        }

        return result;
    }

    private void processUpdate() {
        try {
            presenter.changePassword(password, newPassword);
        } catch (RequireLoginException e) {
            Log.e(e);
        }
    }

    @Override
    public void onSessionCreated(boolean success) {

    }
}
