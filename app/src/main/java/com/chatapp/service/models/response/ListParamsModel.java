package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thanhnguyen on 12/27/16.
 */

public class ListParamsModel {

    @SerializedName("listRelationship")
    @Expose
    private List<ParamModel> listRelationship;

    @SerializedName("listTribes")
    @Expose
    private List<ParamModel> listTribes;

    @SerializedName("listBodyType")
    @Expose
    private List<ParamModel> listBodyType;

    @SerializedName("listEthnicity")
    @Expose
    private List<ParamModel> listEthnicity;

    public List<ParamModel> getListRelationship() {
        return listRelationship;
    }

    public void setListRelationship(List<ParamModel> listRelationship) {
        this.listRelationship = listRelationship;
    }

    public List<ParamModel> getListTribes() {
        return listTribes;
    }

    public void setListTribes(List<ParamModel> listTribes) {
        this.listTribes = listTribes;
    }

    public List<ParamModel> getListBodyType() {
        return listBodyType;
    }

    public void setListBodyType(List<ParamModel> listBodyType) {
        this.listBodyType = listBodyType;
    }

    public List<ParamModel> getListEthnicity() {
        return listEthnicity;
    }

    public void setListEthnicity(List<ParamModel> listEthnicity) {
        this.listEthnicity = listEthnicity;
    }
}
