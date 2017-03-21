package com.chatapp.mvp.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.setting.SettingActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.mvp.updateprofile.UpdateProfileActivity;
import com.chatapp.service.models.response.CountryModel;
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

    @Bind(R.id.v_ethnicity)
    View vEthnicity;

    @Bind(R.id.v_height_and_weight)
    View vHeightAndWeight;

    @Bind(R.id.v_body_type)
    View vBodyType;
    @Bind(R.id.v_tribes)
    View vTribes;
    @Bind(R.id.v_relationship_status)
    View vRelationshipStatus;

    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.v_city)
    View vCity;

    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.v_state)
    View vState;

    @Bind(R.id.tv_country)
    TextView tvCountry;
    @Bind(R.id.v_country)
    View vCountry;

    @Bind(R.id.v_find_me_on)
    View vFindMeOn;

    @Bind(R.id.ib_facebook)
    ImageButton ibFacebook;

    @Bind(R.id.ib_google)
    ImageButton ibGoogle;

    @Bind(R.id.ib_twitter)
    ImageButton ibTwiter;

    @Bind(R.id.ib_linkedin)
    ImageButton ibLinkedin;

    @Bind(R.id.ib_instagram)
    ImageButton ibInstagram;


    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private MyProfileMvp.MyProfilePresenter presenter;

    private String facebook, google, twitter, linkedin, instagram;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load(R.drawable.london_flat)
                .error(R.drawable.london_flat)
                .placeholder(R.drawable.london_flat)
                .into(ivAvatar);

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

        try {
            presenter.getMyProfile();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }

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

            collapsingToolbarLayout.setTitle(userModel.getDisplayNameStr());
            tvAge.setText(String.valueOf(userModel.getAge()));

            tvHeightAndWeight.setText(AccountUtils.getDisplayHeightAndWeight(userModel));
            vHeightAndWeight.setVisibility(userModel.getHeight() > 0 && userModel.getWeight() > 0 ? View.VISIBLE : View.GONE);

            tvEthnicity.setText(userModel.getEnthinicity());
            vEthnicity.setVisibility(TextUtils.isEmpty(userModel.getEnthinicity()) ? View.GONE : View.VISIBLE);

            tvBodyType.setText(userModel.getBodyType());
            vBodyType.setVisibility(TextUtils.isEmpty(userModel.getBodyType()) ? View.GONE : View.VISIBLE);

            tvMyTribes.setText(userModel.getMyTribes());
            vTribes.setVisibility(TextUtils.isEmpty(userModel.getMyTribes()) ? View.GONE : View.VISIBLE);

            tvRelationshipStatus.setText(userModel.getRelationshipStatus());
            vRelationshipStatus.setVisibility(TextUtils.isEmpty(userModel.getRelationshipStatus()) ? View.GONE : View.VISIBLE);

            facebook = userModel.getFacebook();
            google = userModel.getGoogle();
            twitter = userModel.getTwitter();
            linkedin = userModel.getLinkin();
            instagram = userModel.getInstagram();

            ibFacebook.setVisibility(TextUtils.isEmpty(facebook) ? View.GONE : View.VISIBLE);
            ibGoogle.setVisibility(TextUtils.isEmpty(google) ? View.GONE : View.VISIBLE);
            ibTwiter.setVisibility(TextUtils.isEmpty(twitter) ? View.GONE : View.VISIBLE);
            ibLinkedin.setVisibility(TextUtils.isEmpty(linkedin) ? View.GONE : View.VISIBLE);
            ibInstagram.setVisibility(TextUtils.isEmpty(instagram) ? View.GONE : View.VISIBLE);

            String city = userModel.getCity();
            tvCity.setText(city);
            vCity.setVisibility(TextUtils.isEmpty(city) ? View.GONE : View.VISIBLE);

            String state = userModel.getState();
            tvState.setText(state);
            vState.setVisibility(TextUtils.isEmpty(state) ? View.GONE : View.VISIBLE);

            vCountry.setVisibility(View.GONE);

            int countryId = userModel.getCountryId();
            if (countryId > 0) {
                CountryModel countryModel = presenter.getCountry(countryId);
                if (countryModel != null) {
                    tvCountry.setText(countryModel.getName());
                    vCountry.setVisibility(View.VISIBLE);
                }
            }

            if (TextUtils.isEmpty(facebook)
                    && TextUtils.isEmpty(google)
                    && TextUtils.isEmpty(twitter)
                    && TextUtils.isEmpty(linkedin)
                    && TextUtils.isEmpty(instagram)) {
                vFindMeOn.setVisibility(View.GONE);
            } else {
                vFindMeOn.setVisibility(View.VISIBLE);
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_setting:
                onClickSetting();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
