package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class UserModel implements Serializable {

    private static final int IS_FAVORITE = 1;
    private static final int FRIEND_STATUS_NOT_FRIEND = 0;
    private static final int FRIEND_STATUS_HAS_REQUESTED = 1;
    private static final int FRIEND_STATUS_IS_FRIEND = 2;
    private static final int IS_ONLINE = 1;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("feet_away")
    @Expose
    private int feetAway;
    @SerializedName("is_friend")
    @Expose
    private int isFriend;
    @SerializedName("is_favourite")
    @Expose
    private int isFavourite;
    @SerializedName("is_online")
    @Expose
    private int isOnline;

    @SerializedName("noted")
    @Expose
    private String noted;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFeetAway() {
        return feetAway;
    }

    public void setFeetAway(int feetAway) {
        this.feetAway = feetAway;
    }

    public boolean isFriend() {
        return isFriend == FRIEND_STATUS_IS_FRIEND;
    }
    public boolean isRequestedFriend() {
        return isFriend == FRIEND_STATUS_HAS_REQUESTED;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public boolean isFavourite() {
        return isFavourite == IS_FAVORITE;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean isOnline() {
        return isOnline == IS_ONLINE;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getNoted() {
        return noted;
    }

    public void setNoted(String noted) {
        this.noted = noted;
    }
}