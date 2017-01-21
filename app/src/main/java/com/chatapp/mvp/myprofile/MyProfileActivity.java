package com.chatapp.mvp.myprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyProfileActivity extends BaseActivity implements MyProfileMvp.MyProfileView, AppBarLayout.OnOffsetChangedListener {

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
    private MyProfileModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        userModel = AccountUtils.getMyProfileModel();
        tvOnlineStatus.setText("Online");
        tvOnlineStatus.setEnabled(true);

        displayMyProfileInfo();
    }

    private void displayMyProfileInfo() {
        userModel = AccountUtils.getMyProfileModel();
        if (userModel != null) {
            collapsingToolbarLayout.setTitle(userModel.getDisplayName());
            tvAge.setText(String.valueOf(userModel.getBirthday()));
            tvHeightAndWeight.setText(String.format("%s / %s", userModel.getHeight(), userModel.getWeight()));
            tvEthnicity.setText(String.valueOf(userModel.getEthinicityId()));
            tvBodyType.setText(String.valueOf(userModel.getBodyTypeId()));
            tvMyTribes.setText(String.valueOf(userModel.getMyTribesId()));
            tvRelationshipStatus.setText(String.valueOf(userModel.getRelationshipStatusId()));
        }
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
}
