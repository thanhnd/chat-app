package com.chatapp.mvp.updateprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProfileActivity extends BaseActivity implements UpdateProfileMvp.View {

    private static final int SELECT_PICTURE = 1;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);
        userModel = AccountUtils.getMyProfileModel();

        displayMyProfileInfo();
    }

    private void displayMyProfileInfo() {

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

    @OnClick(R.id.fab_camera)
    public void onClickCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
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
    public void onUploadAvatarSuccess() {

    }

    @Override
    public void onUploadAvatarFail() {

    }
}
