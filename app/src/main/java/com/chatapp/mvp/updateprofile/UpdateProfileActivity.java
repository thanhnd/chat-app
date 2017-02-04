package com.chatapp.mvp.updateprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UpdateProfileActivity extends BaseActivity implements UpdateProfileMvp.UpdateProfileView {

    @Bind(R.id.fab_camera)
    View mFabCamera;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        displayMyProfileInfo();
    }

    private void displayMyProfileInfo() {
        MyProfileModel userModel = AccountUtils.getMyProfileModel();
        if (userModel != null) {
            edtDisplayName.setText(userModel.getDisplayName());
            tvAge.setText(String.valueOf(userModel.getAge()));
            tvHeightAndWeight.setText(String.format("%s / %s", userModel.getHeight(), userModel.getWeight()));
            tvEthnicity.setText(String.valueOf(userModel.getEthinicityId()));
            tvBodyType.setText(String.valueOf(userModel.getBodyTypeId()));
            tvMyTribes.setText(String.valueOf(userModel.getMyTribesId()));
            tvRelationshipStatus.setText(String.valueOf(userModel.getRelationshipStatusId()));
        }
    }
}
