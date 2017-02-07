package com.chatapp.service.models.response;

import android.text.TextUtils;

import com.chatapp.utils.CacheUtil;
import com.chatapp.utils.DateUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class MyProfileModel {

    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("country_id")
    @Expose
    private int countryId;
    @SerializedName("birthday")
    @Expose
    private long birthday;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("ethinicity_id")
    @Expose
    private int ethinicityId;
    private String ethinicity;
    @SerializedName("body_type_id")
    @Expose
    private int bodyTypeId;
    private String bodyType;
    @SerializedName("my_tribes_id")
    @Expose
    private int myTribesId;
    private String myTribes;
    @SerializedName("relationship_status_id")
    @Expose
    private int relationshipStatusId;
    private String relationshipStatus;
    @SerializedName("city_id")
    @Expose
    private int cityId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("google")
    @Expose
    private String google;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("linkin")
    @Expose
    private String linkin;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getEthinicityId() {
        return ethinicityId;
    }

    public String getEnthicity() {
        if (TextUtils.isEmpty(ethinicity) && CacheUtil.getListParamsModel() != null) {
            ParamModel param = CacheUtil.getParam(getEthinicityId(),
                    CacheUtil.getListParamsModel().getListEthnicity());
            if (param != null) {
                ethinicity = param.getName();
            }
        }

        return ethinicity;
    }

    public void setEthinicityId(int ethinicityId) {
        this.ethinicityId = ethinicityId;
    }

    public int getBodyTypeId() {
        return bodyTypeId;
    }

    public String getBodyType() {
        if (TextUtils.isEmpty(bodyType)) {
            ParamModel param = CacheUtil.getParam(bodyTypeId,
                    CacheUtil.getListParamsModel().getListBodyType());
            if (param != null) {
                bodyType = param.getName();
            }
        }

        return bodyType;
    }

    public void setBodyTypeId(int bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public int getMyTribesId() {
        return myTribesId;
    }

    public String getMyTribes() {
        if (TextUtils.isEmpty(myTribes)) {
            ParamModel param = CacheUtil.getParam(myTribesId,
                    CacheUtil.getListParamsModel().getListTribes());
            if (param != null) {
                myTribes = param.getName();
            }
        }

        return myTribes;
    }

    public void setMyTribesId(int myTribesId) {
        this.myTribesId = myTribesId;
    }

    public int getRelationshipStatusId() {
        return relationshipStatusId;
    }

    public String getRelationshipStatus() {
        if (TextUtils.isEmpty(relationshipStatus)) {
            ParamModel param = CacheUtil.getParam(relationshipStatusId,
                    CacheUtil.getListParamsModel().getListRelationship());
            if (param != null) {
                relationshipStatus = param.getName();
            }
        }

        return relationshipStatus;
    }

    public void setRelationshipStatusId(int relationshipStatusId) {
        this.relationshipStatusId = relationshipStatusId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkin() {
        return linkin;
    }

    public void setLinkin(String linkin) {
        this.linkin = linkin;
    }

    public int getAge() {
        return DateUtils.getAge(birthday);
    }
}


