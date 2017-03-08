package com.chatapp.mvp.updateprofile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.chatapp.R;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.base.BaseChatActivity;
import com.chatapp.mvp.listcountries.ListCountriesActivity;
import com.chatapp.service.models.response.CountryModel;
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

    @Bind(R.id.tv_error_display_name)
    TextView tvDisplayNameError;

    @Bind(R.id.tv_error_height_and_weight)
    TextView tvHeightAndWeightError;

    @Bind(R.id.tv_error_dob)
    TextView tvDobError;

    @Bind(R.id.edt_state_province)
    EditText edtState;

    @Bind(R.id.edt_city)
    EditText edtCity;

    @Bind(R.id.edt_country_region)
    TextView edtCountry;

    @Bind(R.id.edt_facebook)
    EditText edtFacebook;

    @Bind(R.id.edt_google)
    EditText edtGoogle;

    @Bind(R.id.edt_twitter)
    EditText edtTwitter;

    @Bind(R.id.edt_instagram)
    EditText edtInstagram;

    @Bind(R.id.edt_linkedin)
    EditText edtLinkedin;


    UpdateProfileMvp.Presenter presenter;
    MyProfileModel userModel;
    private long timestampDob;
    private int height, weight;
    ParamModel ethnicityParam, bodyTypeParam, myTribesParam, relationshipStatusParam;
    private List<ParamModel> ethnicities, bodyTypes, tribes, relationships;
    private String displayName;
    private CountryModel selectedCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);
        userModel = AccountUtils.getMyProfileModel();

        initView();

        displayMyProfileInfo();
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
    protected void onStart() {
        super.onStart();
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
            tvHeightAndWeight.setText(AccountUtils.getDisplayHeightAndWeight(height, weight, userModel.getUnitSystem()));

            ethnicityParam = new ParamModel(userModel.getEthinicityId());
            spnEthnicity.setSelection(ethnicities.indexOf(ethnicityParam) + 1);

            myTribesParam = new ParamModel(userModel.getBodyTypeId());
            spnTribes.setSelection(tribes.indexOf(myTribesParam) + 1);

            bodyTypeParam = new ParamModel(userModel.getBodyTypeId());
            spnBodyType.setSelection(bodyTypes.indexOf(bodyTypeParam) + 1);

            relationshipStatusParam = new ParamModel(userModel.getEthinicityId());
            spnRelationshipStatus.setSelection(ethnicities.indexOf(relationshipStatusParam) + 1);

            edtCity.setText(userModel.getCity());
            edtState.setText(userModel.getState());

            int countryId= userModel.getCountryId();
            if (countryId > 0) {
                selectedCountry = presenter.getCountry(countryId);
                if (selectedCountry != null) {
                    edtCountry.setText(selectedCountry.getName());
                }
            }

            edtFacebook.setText(userModel.getFacebook());
            edtGoogle.setText(userModel.getGoogle());
            edtTwitter.setText(userModel.getTwitter());
            edtInstagram.setText(userModel.getInstagram());
            edtLinkedin.setText(userModel.getLinkin());
        }
    }

    @OnClick(R.id.fab_camera)
    public void onClickCamera() {
        processUpLoad();
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
        if (validate()) {
            processSubmit();
        }
    }

    private void processSubmit() {
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

        String state = edtState.getText().toString().trim();
        String city = edtCity.getText().toString().trim();

        if (!TextUtils.isEmpty(state)) {
            request.put("state", state);
        }

        if (!TextUtils.isEmpty(city)) {
            request.put("city", city);
        }

        if (selectedCountry != null) {
            request.put("country_id", selectedCountry.getCountryId());
        }

        String facebook = edtFacebook.getText().toString().trim();
        String google = edtGoogle.getText().toString().trim();
        String twitter = edtTwitter.getText().toString().trim();
        String instagram = edtInstagram.getText().toString().trim();
        String linkedIn = edtLinkedin.getText().toString().trim();
        if (!TextUtils.isEmpty(facebook)) {
            request.put("facebook", facebook);
        }

        if (!TextUtils.isEmpty(google)) {
            request.put("google", google);
        }

        if (!TextUtils.isEmpty(twitter)) {
            request.put("twitter", twitter);
        }

        if (!TextUtils.isEmpty(instagram)) {
            request.put("instagram", instagram);
        }

        if (!TextUtils.isEmpty(linkedIn)) {
            request.put("linkin", linkedIn);
        }

        try {

            presenter.submit(request);

        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    private boolean validate() {
        boolean result = true;
        String displayName = edtDisplayName.getText().toString();
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

        if (height == 0 || weight == 0) {
            tvHeightAndWeightError.setText("Please enter your height and weight.");
            tvHeightAndWeightError.setVisibility(View.VISIBLE);
            result = false;
        } else {
            tvHeightAndWeightError.setVisibility(View.GONE);
        }

        return result;
    }

    @OnClick(R.id.edt_country_region)
    public void clickCountry() {
        Intent intent = new Intent(this, ListCountriesActivity.class);
        startActivityForResult(intent, RC_GET_COUNTRY_CODE);
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
            } else if (requestCode == RC_GET_COUNTRY_CODE) {
                selectedCountry = (CountryModel) data.getSerializableExtra(ListCountriesActivity.SELECTED_COUNTRY);
                edtCountry.setText(selectedCountry.getName());
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
    public void onSessionCreated(boolean success) {

    }
}
