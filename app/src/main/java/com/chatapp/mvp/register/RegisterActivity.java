package com.chatapp.mvp.register;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterView {

    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_country)
    EditText edtCountryCode;
    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Bind(R.id.btn_switch_register_type)
    Button btnSwitchRegisterType;
    @Bind(R.id.btn_link_to_agree_terms_policies)
    Button btnLinkToAgreeTermAndPolicies;
    @Bind(R.id.v_register_by_email)
    View vRegisterByEmail;
    @Bind(R.id.v_register_by_phone)
    View vRegisterByPhone;

    boolean isRegisterByEmail = true;
    private RegisterPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnLinkToAgreeTermAndPolicies.setText(Html.fromHtml(getString(R.string.agree_to_terms_policies)));
        btnSwitchRegisterType.setText(Html.fromHtml(getString(R.string.register_with_your_phone)));
        present = new RegisterPresenterImpl(this);
    }

    @OnClick(R.id.btn_switch_register_type)
    public void clickSwitchRegisterType() {
        if (isRegisterByEmail) {
            vRegisterByEmail.setVisibility(View.GONE);
            vRegisterByPhone.setVisibility(View.VISIBLE);
            btnSwitchRegisterType.setText(Html.fromHtml(getString(R.string.register_with_your_email)));
        } else {
            vRegisterByEmail.setVisibility(View.VISIBLE);
            vRegisterByPhone.setVisibility(View.GONE);
            btnSwitchRegisterType.setText(Html.fromHtml(getString(R.string.register_with_your_phone)));
        }

        isRegisterByEmail = !isRegisterByEmail;
    }
    @OnClick(R.id.btn_submit)
    public void submitRegisterSubmit() {
        RegisterRequest request = new RegisterRequest();
        String password = edtPassword.getText().toString();
        request.setPassword(password);
        if(isRegisterByEmail) {
            String email = edtEmail.getText().toString();
            request.setEmail(email);
        } else {
            String code = edtCountryCode.getText().toString();
            String phone = edtPhone.getText().toString();
            request.setCountry(1);
            request.setMobile(phone);
        }
        present.submitRegisterFrom(request);
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onRegisterError() {
        DialogUtils.showGeneralErrorAlert(this, getString(R.string.general_error_message));
    }
}
