package com.chatapp.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.edt_email)
    EditText edtEmail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnLinkToAgreeTermAndPolicies.setText(Html.fromHtml(getString(R.string.agree_to_terms_policies)));
        btnSwitchRegisterType.setText(Html.fromHtml(getString(R.string.register_with_your_phone)));
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
}
