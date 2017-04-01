package com.chatapp.mvp.updatebasicprofile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DateUtils;
import com.chatapp.utils.DialogUtils;
import com.chatapp.utils.Log;
import com.chatapp.views.fragments.ChooseHeightAndWeightDialogFragment;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chatapp.utils.AccountUtils.UNIT_TYPE_CM_KG;
import static com.chatapp.utils.AccountUtils.UNIT_TYPE_FT_LB;

public class UpdateBasicProfileActivity extends BaseActivity implements UpdateBasicProfileMvp.View {

    private static final int SELECT_PICTURE = 1;

    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    @Bind(R.id.edt_display_name)
    EditText edtDisplayName;

    @Bind(R.id.tv_dob)
    TextView tvDob;

    @Bind(R.id.tv_height_and_weight)
    TextView tvHeightAndWeight;

    @Bind(R.id.rg_unit_type)
    RadioGroup rgUnitType;

    @Bind(R.id.tv_error_display_name)
    TextView tvDisplayNameError;

    @Bind(R.id.tv_error_height_and_weight)
    TextView tvHeightAndWeightError;

    @Bind(R.id.tv_error_dob)
    TextView tvDobError;

    private UpdateBasicProfileMvp.ProfilePresenter presenter;
    private long timestampDob;
    private int unitType = UNIT_TYPE_CM_KG, height, weight;
    private String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_basic_profile);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load(R.drawable.london_flat)
                .error(R.drawable.london_flat)
                .placeholder(R.drawable.london_flat)
                .into(ivAvatar);

        presenter = new PresenterImpl(this);
        rgUnitType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                unitType = (i == R.id.rb_unit_type_cm_kg) ? UNIT_TYPE_CM_KG : UNIT_TYPE_FT_LB;
                displayHeightAndWeight();
            }
        });

        try {
            presenter.getMyProfile();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    @OnClick(R.id.btn_submit)
    void clickSubmit() {
        processSubmit();
    }

    private void processSubmit() {
        if (validate()) {
            BasicProfileRequest request = new BasicProfileRequest();
            displayName = edtDisplayName.getText().toString();
            request.setDisplayName(displayName);
            if (timestampDob > 0) {
                request.setBirthday(timestampDob);
            }

            request.setUnitSystem(unitType);

            if (height > 0 ) {
                request.setHeight(height);
            }

            if (weight > 0) {
                request.setWeight(weight);
            }

            try {
                presenter.submit(request);
            } catch (RequireLoginException e) {
                onRequiredLogin();
            }
        }
    }

    private boolean validate() {
        boolean result = true;
        displayName = edtDisplayName.getText().toString();


        if (TextUtils.isEmpty(displayName)) {
            tvDisplayNameError.setText("Please enter your display name.");
            tvDisplayNameError.setVisibility(View.VISIBLE);
            edtDisplayName.requestFocus();
            result = false;
        } else {
            tvDisplayNameError.setVisibility(View.GONE);
        }

        if (timestampDob == 0) {
            tvDobError.setText("Please enter your birthday.");
            tvDobError.setVisibility(View.VISIBLE);
            result = false;
        } else if(DateUtils.getAge(timestampDob) < 18) {
            tvDobError.setText("Your age's at least 18 years old.");
            tvDobError.setVisibility(View.VISIBLE);
            result = false;
        } else {
            tvDobError.setVisibility(View.GONE);
        }

//        if (height == 0 || weight == 0) {
//            tvHeightAndWeightError.setText("Please enter your height and weight.");
//            tvHeightAndWeightError.setVisibility(View.VISIBLE);
//            result = false;
//        } else {
//            tvHeightAndWeightError.setVisibility(View.GONE);
//        }

        return result;
    }

    @OnClick({R.id.v_date_of_birth, R.id.tv_dob})
    void clickChooseDateOfBirth() {
        DialogUtils.showDatePickerDialog(this, Calendar.getInstance(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);

                timestampDob = cal.getTimeInMillis();
                Date date = cal.getTime();
                tvDob.setText(DateUtils.displayDate(date));
            }
        });
    }

    @OnClick({R.id.v_height_and_weight, R.id.tv_height_and_weight})
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

    @OnClick(R.id.fab_camera)
    public void onClickCamera() {
        processUpLoad();
    }

    private void displayHeightAndWeight() {
        if (height > 0 || weight > 0) {
            tvHeightAndWeight.setText(AccountUtils.getDisplayHeightAndWeight(height, weight, unitType));
        }
    }

    @Override
    public void onUpdateBasicProfileSuccess() {
        updateQuickbloxUser();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void updateQuickbloxUser() {
        boolean isNeedUpdate = false;
        QBUser qbUser = new QBUser();
        final QBUser currentUser = ChatHelper.getCurrentUser();

        if (currentUser != null) {
            qbUser.setId(currentUser.getId());
            if (!TextUtils.isEmpty(displayName)
                    && !displayName.equals(currentUser.getFullName())) {
                qbUser.setFullName(displayName);
                isNeedUpdate = true;
            }

            if (isNeedUpdate) {
                QBUsers.updateUser(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {

                        currentUser.setFullName(displayName);

                        Log.d();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e(e);
                    }
                });
            }
        }
    }

    @Override
    public void onUpdateBasicProfileFail() {

    }

    @Override
    public void onUploadAvatarSuccess(String path) {
        Picasso.with(this)
                .load(path)
                .error(R.drawable.london_flat)
                .placeholder(R.drawable.london_flat)
                .into(ivAvatar);

        updateQuickbloxUserAvatar(path);
    }

    private void updateQuickbloxUserAvatar(final String path) {
        QBUser qbUser = new QBUser();
        final QBUser currentUser = ChatHelper.getCurrentUser();

        if (currentUser != null) {
            qbUser.setId(currentUser.getId());
            qbUser.setCustomData(path);
            QBUsers.updateUser(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    currentUser.setCustomData(path);
                    Log.d();
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e(e);
                }
            });
        }
    }

    @Override
    public void onUploadAvatarFail() {

    }

    @Override
    public void onGetMyProfileSuccess(MyProfileModel myProfileModel) {
        displayMyProfileInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        presenter.uploadAvatar(selectedImageUri);
                    } catch (RequireLoginException e) {
                        onRequiredLogin();
                    }
                }
            }
        }
    }

    private void displayMyProfileInfo() {
        MyProfileModel userModel = AccountUtils.getMyProfileModel();
        if (userModel != null) {
            if (!TextUtils.isEmpty(userModel.getAvatar())) {
                Picasso.with(this)
                        .load(userModel.getAvatar())
                        .error(R.drawable.london_flat)
                        .placeholder(R.drawable.london_flat)
                        .into(ivAvatar);
            }

            edtDisplayName.setText(userModel.getDisplayName());
            if (userModel.getBirthday() > 0) {
                Date date = new Date(userModel.getBirthday());
                tvDob.setText(DateUtils.displayDate(date));
            }

            if (userModel.getHeight() > 0 && userModel.getWeight() > 0) {
                height = (int)userModel.getHeight();
                weight = (int)userModel.getWeight();
                displayHeightAndWeight();
            }
        }
    }
}
