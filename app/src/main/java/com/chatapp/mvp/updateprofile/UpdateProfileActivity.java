package com.chatapp.mvp.updateprofile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DateUtils;
import com.chatapp.utils.DialogUtils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProfileActivity extends BaseActivity implements UpdateProfileMvp.View {

    private static final int SELECT_PICTURE = 1;

    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.fab_camera)
    android.view.View mFabCamera;
    @Bind(R.id.edt_display_name)
    EditText edtDisplayName;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_height_and_weight)
    TextView tvHeightAndWeight;
    @Bind(R.id.tv_ethnicity)
    TextView tvEthnicity;
    @Bind(R.id.tv_body_type)
    TextView tvBodyType;
    @Bind(R.id.tv_my_tribes)
    TextView tvMyTribes;
    @Bind(R.id.tv_relationship_status)
    TextView tvRelationshipStatus;

    UpdateProfileMvp.Presenter presenter;
    MyProfileModel userModel;
    private long timestampDob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);
        userModel = AccountUtils.getMyProfileModel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        displayMyProfileInfo();
    }

    private void displayMyProfileInfo() {

        if (userModel != null) {

            if (!TextUtils.isEmpty(userModel.getAvatar())) {
                Picasso.with(this)
                        .load(userModel.getAvatar())
                        .error(R.drawable.london_flat)
                        .placeholder(R.drawable.london_flat)
                        .into(ivAvatar);
            }

            edtDisplayName.setText(userModel.getDisplayName());
            timestampDob = userModel.getBirthday();
            tvAge.setText(String.valueOf(userModel.getAge()));
            tvHeightAndWeight.setText(String.format("%s / %s", userModel.getHeight(), userModel.getWeight()));
            tvEthnicity.setText(String.valueOf(userModel.getEthinicityId()));
            tvBodyType.setText(String.valueOf(userModel.getBodyTypeId()));
            tvMyTribes.setText(String.valueOf(userModel.getMyTribesId()));
            tvRelationshipStatus.setText(String.valueOf(userModel.getRelationshipStatusId()));
        }
    }

    @OnClick(R.id.fab_camera)
    public void onClickCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @OnClick({R.id.v_date_of_birth})
    public void clickChooseDateOfBirth() {
        Calendar calendar = Calendar.getInstance();
        if (timestampDob > 0) {
            calendar.setTimeInMillis(timestampDob);
        }

        DialogUtils.showDatePickerDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);

                timestampDob = cal.getTimeInMillis();
                tvAge.setText(String.valueOf(DateUtils.getAge(timestampDob)));
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    presenter.uploadAvatar(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onUploadAvatarSuccess(String path) {
        userModel.setAvatar(path);
        Picasso.with(this)
                .load(path)
                .error(R.drawable.london_flat)
                .placeholder(R.drawable.london_flat)
                .into(ivAvatar);
    }

    @Override
    public void onUploadAvatarFail() {

    }
}
