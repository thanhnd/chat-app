package com.chatapp.service.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thanhnguyen on 12/19/16.
 */

public class BasicProfileRequest {

    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("birthday")
    @Expose
    private long birthday;
    @SerializedName("weight")
    @Expose
    private float weight;
    @SerializedName("height")
    @Expose
    private float height;
    @SerializedName("unit_system")
    @Expose
    private int unitSystem;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getUnitSystem() {
        return unitSystem;
    }

    public void setUnitSystem(int unitSystem) {
        this.unitSystem = unitSystem;
    }

}