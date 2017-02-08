package com.chatapp.mvp.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.updateprofile.UpdateProfileActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.AccountUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileActivity extends BaseActivity implements MyProfileMvp.MyProfileView, AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private static final int RC_UPDATE_PROFILE = 1;

    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.fab_edit)
    View mFabEdit;
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
    private MyProfileMvp.MyProfilePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);

        presenter = new MyProfilePresenterImpl(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        tvOnlineStatus.setText("Online");
        tvOnlineStatus.setEnabled(true);

        presenter.getMyProfile();

        displayMyProfileInfo();

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

            collapsingToolbarLayout.setTitle(userModel.getDisplayName());
            tvAge.setText(String.valueOf(userModel.getAge()));
            tvHeightAndWeight.setText(String.format("%s / %s", userModel.getHeight(), userModel.getWeight()));

            tvEthnicity.setText(userModel.getEnthicity());
            tvBodyType.setText(userModel.getBodyType());
            tvMyTribes.setText(userModel.getMyTribes());
            tvRelationshipStatus.setText(userModel.getRelationshipStatus());
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
                ViewCompat.animate(mFabEdit).scaleY(0).scaleX(0).start();
                tvOnlineStatus.setVisibility(View.GONE);
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFabEdit).scaleY(1).scaleX(1).start();
                tvOnlineStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.fab_edit)
    public void onClickEditFab() {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivityForResult(intent, RC_UPDATE_PROFILE);
    }

    @Override
    public void onGetMyProfileSuccess(MyProfileModel resultSet) {
        displayMyProfileInfo();
    }
}
