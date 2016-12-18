package com.chatapp.mvp.updatebasicprofile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBasicProfileActivity extends AppCompatActivity implements UpdateBasicProfileView {

    @Bind(R.id.edt_display_name)
    EditText edtDisplayName;

    @Bind(R.id.tv_dob)
    TextView tvDob;

    @Bind(R.id.tv_height_and_weight)
    TextView tvHeightAndWeight;

    @Bind(R.id.rg_unit_type)
    RadioGroup rgUnitType;

    private UpdateBasicProfilePresenter presenter;
    private long timestampDob;
    private int unitType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_basic_profile);
        ButterKnife.bind(this);

        presenter = new UpdateBasicProfilePresenterImpl();
        rgUnitType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                unitType = (i == R.id.rb_unit_type_cm_kg) ? 0 : 1;
            }
        });
    }

    @OnClick(R.id.btn_submit)
    void clickSubmit() {
        BasicProfileRequest request = new BasicProfileRequest();
        String displayName = edtDisplayName.getText().toString();
        request.setDisplayName(displayName);
        request.setBirthday(timestampDob);
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

    }

    @Override
    public void onUpdateBasicProfileSuccess() {

    }

    @Override
    public void onUpdateBasicProfileFail() {

    }
}
