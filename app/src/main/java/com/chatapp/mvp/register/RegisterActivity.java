package com.chatapp.mvp.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.Config;
import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.listcountries.ListCountriesActivity;
import com.chatapp.mvp.verify.VerifyActivity;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterMvp.RegisterView {

    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_country)
    TextView edtCountryCode;
    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Bind(R.id.tv_error_password)
    TextView tvPasswordError;
    @Bind(R.id.tv_error_email)
    TextView tvEmailError;
    @Bind(R.id.tv_error_phone)
    TextView tvPhoneError;
    @Bind(R.id.tv_error_country)
    TextView tvCountryError;

    @Bind(R.id.btn_switch_register_type)
    Button btnSwitchRegisterType;
    @Bind(R.id.btn_link_to_agree_terms_policies)
    Button btnLinkToAgreeTermAndPolicies;
    @Bind(R.id.v_register_by_email)
    View vRegisterByEmail;
    @Bind(R.id.v_register_by_phone)
    View vRegisterByPhone;

    private RegisterMvp.RegisterPresent present;
    boolean isRegisterByEmail = true;
    String email, phone, countryCode, password;
    private CountryModel selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    processRegister();

                    return true;
                }

                return false;
            }
        });

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
        processRegister();
    }

    private void processRegister() {
        if (validate()) {
            RegisterRequest request = new RegisterRequest();
            password = edtPassword.getText().toString().trim();

            request.setPassword(password);
            if(isRegisterByEmail) {
                email = edtEmail.getText().toString().trim();
                request.setEmail(email);
            } else {
                if (selectedCountry != null) {
                    request.setCountry(selectedCountry.getCountryId());
                    phone = edtPhone.getText().toString();
                    request.setMobile(phone);
                }
            }
            present.submitRegisterForm(request);
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
        } else if(password.length() < Config.PASSWORD_MIN_LENGTH) {
            tvPasswordError.setText("Password at least 8 characters.");
            tvPasswordError.setVisibility(View.VISIBLE);
            edtPassword.requestFocus();
            result = false;
        } else {
            tvPasswordError.setVisibility(View.GONE);
        }

        if (isRegisterByEmail) {
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

        } else {
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

            if (selectedCountry == null) {
                tvCountryError.setText("Please choose country code");
                tvCountryError.setVisibility(View.VISIBLE);
                result = false;
            } else {
                tvCountryError.setVisibility(View.GONE);
            }
        }

        return result;
    }

    @OnClick(R.id.edt_country)
    public void clickCountryCode() {
        Intent intent = new Intent(this, ListCountriesActivity.class);
        startActivityForResult(intent, RC_GET_COUNTRY_CODE);
    }

    @Override
    public void onRegisterSuccess() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setPassword(password);
        if (isRegisterByEmail) {
            logInRequest.setEmail(email);
        } else {
            logInRequest.setMobile(phone);
            logInRequest.setEmail("");
        }

        present.requestLogin(logInRequest);
    }

    @Override
    public void onRegisterError() {
        DialogUtils.showGeneralErrorAlert(this, getString(R.string.general_error_message));
    }

    @Override
    public void onLoginSuccess(LogInModel logInModel) {
        Intent intent = new Intent(this, VerifyActivity.class);
        if (isRegisterByEmail) {
            AccountUtils.setEmail(email);
            intent.putExtra(VerifyActivity.EXTRA_EMAIL, email);
        } else {
            AccountUtils.setPhone(phone);
            intent.putExtra(VerifyActivity.EXTRA_PHONE, phone);
        }
        startActivity(intent);
    }

    @Override
    public void onLoginError() {

    }

    @Override
    public void onGetVerifyCodeSuccess() {

    }

    @Override
    public void onGetVerifyCodeFail() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_GET_COUNTRY_CODE) {
                selectedCountry = (CountryModel) data.getSerializableExtra(ListCountriesActivity.SELECTED_COUNTRY);
                edtCountryCode.setText(String.format(Locale.getDefault(), "%s | %s",
                        selectedCountry.getPhonecode(), selectedCountry.getName()));
            }
        }
    }
}
