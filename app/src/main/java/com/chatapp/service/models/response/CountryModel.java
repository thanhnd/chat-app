package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by thanhnguyen on 12/27/16.
 */

public class CountryModel extends RealmObject implements Serializable {
    @SerializedName("country_id")
    @Expose
    private int countryId;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phonecode")
    @Expose
    private String phonecode;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    @Override
    public String toString() {
        return name;
    }
}
