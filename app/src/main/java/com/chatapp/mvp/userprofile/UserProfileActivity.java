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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.chat.ui.activity.CallActivity;
import com.chatapp.chat.utils.Consts;
import com.chatapp.chat.utils.PushNotificationSender;
import com.chatapp.chat.utils.WebRtcSessionManager;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.base.BaseChatActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.utils.Log;
import com.chatapp.views.UserProfilePropertyView;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chatapp.Config.LIMIT_ADD_FRIEND_GREETING_CHARACTER;

public class UserProfileActivity extends BaseChatActivity implements UserProfileMvp.UserProfileView,
        AppBarLayout.OnOffsetChangedListener {

    public static final String EXTRA_USER_MODEL = "extra_user_model";

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;

    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.ib_favorite_status)
    ImageButton ibFavoriteStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_online_status)
    TextView tvOnlineStatus;

    @Bind(R.id.ib_chat)
    ImageButton ibChat;
    @Bind(R.id.ib_call)
    ImageButton ibCall;
    @Bind(R.id.ib_video)
    ImageButton ibVideo;

    @Bind(R.id.v_profile)
    ViewGroup vProfile;

    @Bind(R.id.v_profile_properties)
    ViewGroup vProfileProperties;

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
    private UserModel userModel;
    private UserProfileMvp.UserProfilePresent present;
    private boolean isShowAddFriend;
    private UserProfileModel userProfileModel;

    private String facebook, google, twitter, linkedin, instagram;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load(R.drawable.london_flat)
                .error(R.drawable.london_flat)
                .placeholder(R.drawable.london_flat)
                .into(ivAvatar);

        present = new UserProfilePresenterImpl(this);

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra(EXTRA_USER_MODEL);


        ibFavoriteStatus.setImageResource(userModel.isFavourite() ?
                R.drawable.ic_status_favorite_yes : R.drawable.ic_tab_favorite);

        tvLimitGreeting.setText("0/" + LIMIT_ADD_FRIEND_GREETING_CHARACTER);
        edtGreeting.setFilters(new InputFilter[]{new InputFilter.LengthFilter(LIMIT_ADD_FRIEND_GREETING_CHARACTER)});
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

        tvOnlineStatus.setText(userModel.getOnlineStatus());
        tvOnlineStatus.setEnabled(true);
        try {
            present.getUserProfile(userModel.getUserId());
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    private void displayUserProfileDetails(@NonNull UserProfileModel userProfileModel) {
        if (!TextUtils.isEmpty(userProfileModel.getAvatar())) {
            Picasso.with(this)
                    .load(userProfileModel.getAvatar())
                    .error(R.drawable.london_flat)
                    .placeholder(R.drawable.london_flat)
                    .into(ivAvatar);
        }

        collapsingToolbarLayout.setTitle(userProfileModel.getDisplayNameStr());

        if (userProfileModel.getAge() > 0) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.age), String.valueOf(userProfileModel.getAge()));
            vProfileProperties.addView(view);
        }

        if (userProfileModel.getHeight() > 0 && userProfileModel.getWeight() > 0) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.height_and_weight),
                    String.format("%s / %s", userProfileModel.getHeight(), userProfileModel.getWeight()));
            vProfileProperties.addView(view);
        }

        if (!TextUtils.isEmpty(userProfileModel.getEnthinicity())) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.ethnicity), userProfileModel.getEnthinicity());
            vProfileProperties.addView(view);
        }

        if (!TextUtils.isEmpty(userProfileModel.getBodyType())) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.body_type), userProfileModel.getBodyType());
            vProfileProperties.addView(view);
        }

        if (!TextUtils.isEmpty(userProfileModel.getMyTribes())) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.my_tribes), userProfileModel.getMyTribes());
            vProfileProperties.addView(view);
        }

        if (!TextUtils.isEmpty(userProfileModel.getRelationshipStatus())) {
            UserProfilePropertyView view = new UserProfilePropertyView(this);
            view.update(getString(R.string.relationship_status), userProfileModel.getRelationshipStatus());
            vProfileProperties.addView(view);
        }

        facebook = userProfileModel.getFacebook();
        google = userProfileModel.getGoogle();
        twitter = userProfileModel.getTwitter();
        linkedin = userProfileModel.getLinkin();
        instagram = userProfileModel.getInstagram();

        ibFacebook.setVisibility(TextUtils.isEmpty(facebook) ? View.GONE : View.VISIBLE);
        ibGoogle.setVisibility(TextUtils.isEmpty(google) ? View.GONE : View.VISIBLE);
        ibTwiter.setVisibility(TextUtils.isEmpty(twitter) ? View.GONE : View.VISIBLE);
        ibLinkedin.setVisibility(TextUtils.isEmpty(linkedin) ? View.GONE : View.VISIBLE);
        ibInstagram.setVisibility(TextUtils.isEmpty(instagram) ? View.GONE : View.VISIBLE);


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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                ViewCompat.animate(ibFavoriteStatus).scaleY(0).scaleX(0).start();
                tvOnlineStatus.setVisibility(View.GONE);
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(ibFavoriteStatus).scaleY(1).scaleX(1).start();
                tvOnlineStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetUserProfileSuccess(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
        displayUserProfileDetails(userProfileModel);
    }

    @OnClick(R.id.ib_favorite_status)
    void onClickFavorite() {

        try {

            if (!userModel.isFavourite()) {

                present.addFavorite(userModel.getUserId());

            } else {

                present.removeFavorite(userModel.getUserId());
            }

        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
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

    @OnClick(R.id.ib_chat)
    void onClickChat() {
        try {
            if (userProfileModel != null) {
                int chatId = Integer.parseInt(userProfileModel.getChatId());
                QBUser user = new QBUser(chatId);
                createDialog(userProfileModel.getAvatar(), user);
            }

        } catch (NumberFormatException e) {
            Log.e(e);

            showErrorDialog("Cannot chat with this user.");
        }
    }

    @OnClick(R.id.ib_call)
    void onClickCall() {
        if (!userModel.isFriend()) {
            showAddFriendView();

        } else {
            onClickCall(false);
            if (checker.lacksPermissions(Consts.PERMISSIONS[1])) {
                startPermissionsActivity(true);
            }
        }
    }

    @OnClick(R.id.ib_video)
    void onClickVideo() {
        if (!userModel.isFriend()) {
            showAddFriendView();
        } else {
            onClickCall(true);
            if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                startPermissionsActivity(false);
            }
        }
    }

    @OnClick(R.id.btn_add_friend)
    void onClickAddFriend() {
        if (!userModel.isFriend()) {
            String strGreeting = edtGreeting.getText().toString();
            strGreeting = strGreeting.trim();
            if (!TextUtils.isEmpty(strGreeting)) {
                try {
                    present.requestAddFriend(userModel.getUserId(), strGreeting);
                } catch (RequireLoginException e) {
                    onRequiredLogin();
                }
            }
        }
    }

    @Override
    public void onAddFavoriteSuccess() {
        userModel.setIsFavourite(UserModel.IS_FAVORITE);
        ibFavoriteStatus.setImageResource(R.drawable.ic_status_favorite_yes);
    }

    @Override
    public void onRequestAddFriendSuccess() {
        userModel.setIsFriend(UserModel.FRIEND_STATUS_IS_FRIEND);
        showDialog("Friend request", "Friend request sent. Awaiting response");
        onBackPressed();
    }

    @Override
    public void onRequestAddFriendFail() {

    }

    @Override
    public void onRemoveFavoriteSuccess() {
        userModel.setIsFavourite(UserModel.IS_NOT_FAVORITE);
        ibFavoriteStatus.setImageResource(R.drawable.ic_tab_favorite);
    }

    @Override
    public void onAddFavoriteFail() {

    }

    @Override
    public void onRemoveFavoriteFail() {

    }

    @Override
    public void onSessionCreated(boolean success) {

    }

    private void onClickCall(boolean isVideoCall) {

        try {
            if (userProfileModel != null) {
                int chatId = Integer.parseInt(userProfileModel.getChatId());
                startCall(isVideoCall, chatId);
            }

        } catch (NumberFormatException e) {
            Log.e(e);

            showErrorDialog("Cannot chat with this user.");
        }
    }

    private void startCall(boolean isVideoCall, int chatId) {
        Log.d("startCall()");
        ArrayList<Integer> opponentsList = new ArrayList<>();
        opponentsList.add(chatId);
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);
        QBUser currentUser = ChatHelper.getCurrentUser();
        PushNotificationSender.sendPushMessage(opponentsList, currentUser.getFullName());

        CallActivity.start(this, false);
        Log.d("conferenceType = " + conferenceType);
    }
}
