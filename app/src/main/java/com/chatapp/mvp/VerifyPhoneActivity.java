package com.chatapp.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.edt_code)
    EditText edtCode;

    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        ButterKnife.bind(this);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                startActivity(new Intent(VerifyPhoneActivity.this, MyProfileActivity.class));
                break;
            case R.id.btn_request_send_code:
                break;
            default:
                break;
        }
    }
}
