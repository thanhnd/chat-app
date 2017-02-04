package com.chatapp.mvp.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.utils.AccountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chatapp.Config.LIMIT_ADD_FRIEND_GREETING_CHARACTER;

public class UserProfileActivity extends BaseActivity implements UserProfileMvp.UserProfileView,
        AppBarLayout.OnOffsetChangedListener{

    public static final String EXTRA_USER_MODEL = "extra_user_model";

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    @Bind(R.id.iv_favorite_status)
    ImageView ivFavoriteStatus;
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

    @Bind(R.id.ib_chat)
    ImageButton ibChat;
    @Bind(R.id.ib_call)
    ImageButton ibCall;
    @Bind(R.id.ib_video)
    ImageButton ibVideo;

    @Bind(R.id.v_profile)
    View vProfile;
    @Bind(R.id.v_add_friend)
    View vAddFriend;
    @Bind(R.id.v_action)
    View vAction;

    @Bind(R.id.edt_greeting)
    EditText edtGreeting;

    @Bind(R.id.tv_limit_greeting)
    TextView tvLimitGreeting;

    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private MyProfileModel myProfileModel;
    private UserModel userModel;
    private UserProfileMvp.UserProfilePresent present;
    private boolean isShowAddFriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        present = new UserProfilePresenterImpl(this);

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        Intent intent = getIntent();
        myProfileModel = AccountUtils.getMyProfileModel();
        userModel = (UserModel) intent.getSerializableExtra(EXTRA_USER_MODEL);


        ivFavoriteStatus.setImageResource(userModel.isFavourite() ?
                R.drawable.ic_status_favorite_yes : R.drawable.ic_tab_favorite);

        tvLimitGreeting.setText("0/" + LIMIT_ADD_FRIEND_GREETING_CHARACTER);
        edtGreeting.setFilters(new InputFilter[] {new InputFilter.LengthFilter(LIMIT_ADD_FRIEND_GREETING_CHARACTER)});
        edtGreeting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int character = s.length();

                if (character > LIMIT_ADD_FRIEND_GREETING_CHARACTER)
                    return;

                if (character <= LIMIT_ADD_FRIEND_GREETING_CHARACTER)
                    tvLimitGreeting.setText(s.length() + "/" + LIMIT_ADD_FRIEND_GREETING_CHARACTER);
            }
        });

        tvOnlineStatus.setText("Online");
        tvOnlineStatus.setEnabled(true);
        present.getUserProfile(userModel.getUserId());
    }

    private void displayUserProfileDetails(@NonNull UserProfileModel userProfileModel) {
        if (!TextUtils.isEmpty(userProfileModel.getDisplayName())) {
            collapsingToolbarLayout.setTitle(userProfileModel.getDisplayName());
        }

        tvAge.setText(String.valueOf(userProfileModel.getAge()));
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
                ViewCompat.animate(ivFavoriteStatus).scaleY(0).scaleX(0).start();
                tvOnlineStatus.setVisibility(View.GONE);
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(ivFavoriteStatus).scaleY(1).scaleX(1).start();
                tvOnlineStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetUserProfileSuccess(UserProfileModel userProfileModel) {
        displayUserProfileDetails(userProfileModel);
    }

    @OnClick(R.id.iv_favorite_status)
    void onClickFavorite() {
        present.addUserFavorite(userModel.getUserId());
    }

    @OnClick(R.id.ib_chat)
    void onClickChat() {
        showAddFriendView();
    }

    @Override
    public void onBackPressed() {
        if (isShowAddFriend) {
            showProfileView();
        } else {
            super.onBackPressed();
        }

    }

    private void showAddFriendView() {
        isShowAddFriend = true;
        vAddFriend.setVisibility(View.VISIBLE);
        vProfile.setVisibility(View.GONE);
        vAction.setVisibility(View.GONE);
    }

    private void showProfileView() {
        isShowAddFriend = false;
        vAddFriend.setVisibility(View.GONE);
        vProfile.setVisibility(View.VISIBLE);
        vAction.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ib_call)
    void onClickCall() {
    }

    @OnClick(R.id.ib_video)
    void onClickVideo() {
    }
    @Override
    public void onAddUserFavoriteSuccess() {
        ivFavoriteStatus.setImageResource(R.drawable.ic_status_favorite_yes);
    }
}
