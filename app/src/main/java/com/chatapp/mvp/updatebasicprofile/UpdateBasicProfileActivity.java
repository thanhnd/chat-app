package com.chatapp.mvp.updatebasicprofile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;
import com.chatapp.views.fragments.ChooseHeightAndWeightDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBasicProfileActivity extends BaseActivity implements UpdateBasicProfileMvp.UpdateBasicProfileView {


    private static final int UNIT_TYPE_CM_KG = 0;
    private static final int UNIT_TYPE_FT_LB = 1;
    @Bind(R.id.edt_display_name)
    EditText edtDisplayName;

    @Bind(R.id.tv_dob)
    TextView tvDob;

    @Bind(R.id.tv_height_and_weight)
    TextView tvHeightAndWeight;

    @Bind(R.id.rg_unit_type)
    RadioGroup rgUnitType;

    private UpdateBasicProfileMvp.UpdateBasicProfilePresenter presenter;
    private long timestampDob;
    private int unitType = UNIT_TYPE_CM_KG, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_basic_profile);
        ButterKnife.bind(this);

        presenter = new UpdateBasicProfilePresenterImpl(this);
        rgUnitType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                unitType = (i == R.id.rb_unit_type_cm_kg) ? UNIT_TYPE_CM_KG : UNIT_TYPE_FT_LB;
                displayHeightAndWeight();
            }
        });
    }

    @OnClick(R.id.btn_submit)
    void clickSubmit() {
        BasicProfileRequest request = new BasicProfileRequest();
        String displayName = edtDisplayName.getText().toString();
        request.setDisplayName(displayName);
        request.setBirthday(timestampDob);
        request.setUnitSystem(unitType);
        request.setHeight(height);
        request.setWeight(weight);

        presenter.submit(request);
    }

    @OnClick(R.id.v_date_of_birth)
    void clickChooseDateOfBirth() {
        DialogUtils.showDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);

                timestampDob = cal.getTimeInMillis();
                tvDob.setText(new SimpleDateFormat("d MMMM yyyy").format(cal.getTime()).toString());
            }
        });
    }

    @OnClick(R.id.v_height_and_weight)
    void clickChooseHeightAndWeight() {
        DialogUtils.showChooseHeightAndWeightDialog(this, height, weight,
                new ChooseHeightAndWeightDialogFragment.OnHeightAndWeightSetListener() {
            @Override
            public void onHeightAndWeightSet(int h, int w) {
                height = h;
                weight = w;
                displayHeightAndWeight();
            }
        });
    }

    private void displayHeightAndWeight() {
        if (height > 0 || weight > 0) {
            tvHeightAndWeight.setText(
                    String.format(Locale.getDefault(), unitType == UNIT_TYPE_CM_KG ?
                            "%d cm / %d kg" : "%d ft / %d lb", height, weight));
        }
    }

    @Override
    public void onUpdateBasicProfileSuccess() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUpdateBasicProfileFail() {

    }

    @Override
    public void onBackPressed() {
        AccountUtils.logOut();

        super.onBackPressed();
    }
}
