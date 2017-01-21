package com.chatapp.service.models.response;

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
    private String ethinicityId;
    @SerializedName("body_type_id")
    @Expose
    private String bodyTypeId;
    @SerializedName("my_tribes_id")
    @Expose
    private String myTribesId;
    @SerializedName("relationship_status_id")
    @Expose
    private String relationshipStatusId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
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

    public String getEthinicityId() {
        return ethinicityId;
    }

    public void setEthinicityId(String ethinicityId) {
        this.ethinicityId = ethinicityId;
    }

    public String getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(String bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public String getMyTribesId() {
        return myTribesId;
    }

    public void setMyTribesId(String myTribesId) {
        this.myTribesId = myTribesId;
    }

    public String getRelationshipStatusId() {
        return relationshipStatusId;
    }

    public void setRelationshipStatusId(String relationshipStatusId) {
        this.relationshipStatusId = relationshipStatusId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
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
        Long time= (System.currentTimeMillis() - birthday) / 1000;
        return Math.round(time) / 31536000;
    }

}


