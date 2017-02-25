package com.chatapp.mvp.updateprofile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.base.BaseChatActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ParamModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.CacheUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProfileActivity extends BaseChatActivity implements UpdateProfileMvp.View {

    private static final int SELECT_PICTURE = 1;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 101;

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

    @Bind(R.id.spn_ethnicity)
    Spinner spnEthnicity;
    @Bind(R.id.spn_body_type)
    Spinner spnBodyType;
    @Bind(R.id.spn_my_tribes)
    Spinner spnTribes;
    @Bind(R.id.spn_relationship_status)
    Spinner spnRelationshipStatus;

    UpdateProfileMvp.Presenter presenter;
    MyProfileModel userModel;
    private long timestampDob;
    private int height, weight;
    ParamModel ethnicityParam, bodyTypeParam, myTribesParam, relationshipStatusParam;
    private List<ParamModel> ethnicities, bodyTypes, tribes, relationships;
    private String displayName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);
        userModel = AccountUtils.getMyProfileModel();

        initView();
    }

    private void initView() {
        ethnicities = CacheUtil.getListParamsModel().getListEthnicity();
        spnEthnicity.setAdapter(getSpinnerAdapter(ethnicities));
        spnEthnicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ethnicityParam = (ParamModel) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bodyTypes = CacheUtil.getListParamsModel().getListBodyType();
        spnBodyType.setAdapter(getSpinnerAdapter(bodyTypes));
        spnBodyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bodyTypeParam = (ParamModel) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tribes = CacheUtil.getListParamsModel().getListTribes();
        spnTribes.setAdapter(getSpinnerAdapter(tribes));
        spnTribes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myTribesParam = (ParamModel) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        relationships = CacheUtil.getListParamsModel().getListRelationship();
        spnRelationshipStatus.setAdapter(getSpinnerAdapter(relationships));
        spnRelationshipStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relationshipStatusParam = (ParamModel) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private SpinnerAdapter getSpinnerAdapter(List<ParamModel> params) {
        ArrayAdapter<ParamModel> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item);
        ParamModel noneParam = new ParamModel(0, "Not set");
        dataAdapter.add(noneParam);
        dataAdapter.addAll(params);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return dataAdapter;
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
            height = userModel.getHeight();
            weight = userModel.getWeight();
            tvHeightAndWeight.setText(String.format("%s / %s", height, weight));

            ethnicityParam = new ParamModel(userModel.getEthinicityId());
            spnEthnicity.setSelection(ethnicities.indexOf(ethnicityParam) + 1);

            myTribesParam = new ParamModel(userModel.getBodyTypeId());
            spnTribes.setSelection(tribes.indexOf(myTribesParam) + 1);

            bodyTypeParam = new ParamModel(userModel.getBodyTypeId());
            spnBodyType.setSelection(bodyTypes.indexOf(bodyTypeParam) + 1);

            relationshipStatusParam = new ParamModel(userModel.getEthinicityId());
            spnRelationshipStatus.setSelection(ethnicities.indexOf(relationshipStatusParam) + 1);
        }
    }

    @OnClick(R.id.fab_camera)
    public void onClickCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            openGallery();
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @OnClick({R.id.v_date_of_birth, R.id.tv_age})
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

    private void displayHeightAndWeight() {
        if (height > 0 || weight > 0) {
            tvHeightAndWeight.setText(
                    String.format(Locale.getDefault(), "%d / %d", height, weight));
        }
    }

    @OnClick(R.id.btn_submit)
    void clickSubmit() {
        Map<String, Object> request = new HashMap();
        displayName = edtDisplayName.getText().toString().trim();

        if (!TextUtils.isEmpty(displayName)) {
            request.put("display_name", displayName);
        }

        if (timestampDob > 0) {
            request.put("birthday", timestampDob);
        }
        if (weight > 0) {
            request.put("weight", weight);
        }
        if (height > 0) {
            request.put("height", height);
        }

        if (ethnicityParam != null) {
            request.put("ethinicity_id", ethnicityParam.getId());
        }

        if (bodyTypeParam != null) {
            request.put("body_type_id", bodyTypeParam.getId());
        }

        if (myTribesParam != null) {
            request.put("my_tribes_id", myTribesParam.getId());
        }

        if (relationshipStatusParam != null) {
            request.put("relationship_status_id", relationshipStatusParam.getId());
        }

        try {

            presenter.submit(request);

        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
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

    @Override
    public void onUpdateProfileSuccess() {
        updateQuickbloxUser();
        showDialog("Update Profile", "Successfully update profile");
    }

    private void updateQuickbloxUser() {
        boolean isNeedUpdate = false;
        QBUser qbUser = new QBUser();
        final QBUser currentUser = ChatHelper.getCurrentUser();

        if (currentUser != null) {
            qbUser.setId(currentUser.getId());
            if (!TextUtils.isEmpty(displayName)
                    && !displayName.equals(userModel.getDisplayName())) {
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
    public void onUpdateProfileFail() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Need access your phone to get pictures!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onSessionCreated(boolean success) {

    }
}
