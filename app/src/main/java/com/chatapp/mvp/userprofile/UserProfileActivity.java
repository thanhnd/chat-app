package com.chatapp.mvp.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.utils.AccountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity implements UserProfileMvp.UserProfileView,
        AppBarLayout.OnOffsetChangedListener{

    public static final String EXTRA_USER_MODEL = "extra_user_model";

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    @Bind(R.id.fab_edit)
    View mFab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_online_status)
    TextView tvOnlineStatus;
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


    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private MyProfileModel myProfileModel;
    private UserModel userModel;
    private UserProfileMvp.UserProfilePresent present;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        present = new UserProfilePresenterImpl(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        Intent intent = getIntent();
        myProfileModel = AccountUtils.getMyProfileModel();
        userModel = (UserModel) intent.getSerializableExtra(EXTRA_USER_MODEL);

        tvOnlineStatus.setText("Online");
        tvOnlineStatus.setEnabled(true);
        present.getUserProfile(userModel.getUserId());
    }

    private void displayUserProfileDetails(@NonNull UserProfileModel userProfileModel) {
        if (!TextUtils.isEmpty(userProfileModel.getDisplayName())) {
            collapsingToolbarLayout.setTitle(userProfileModel.getDisplayName());
        }

        tvAge.setText(String.valueOf(userProfileModel.getBirthday()));
        tvHeightAndWeight.setText(String.format("%s / %s", userProfileModel.getHeight(), userProfileModel.getWeight()));
        tvEthnicity.setText(String.valueOf(userProfileModel.getEthinicityId()));
        tvBodyType.setText(String.valueOf(userProfileModel.getBodyTypeId()));
        tvMyTribes.setText(String.valueOf(userProfileModel.getMyTribesId()));
        tvRelationshipStatus.setText(String.valueOf(userProfileModel.getRelationshipStatusId()));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
                tvOnlineStatus.setVisibility(View.GONE);
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
                tvOnlineStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetUserProfileSuccess(UserProfileModel userProfileModel) {
        displayUserProfileDetails(userProfileModel);
    }
}
