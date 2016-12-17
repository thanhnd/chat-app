package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class ResponseModel<T> {
    public static final int RESPONSE_CD_SUCCESS = 0;
    public static final int RESPONSE_CD_ERROR = 1;
    @SerializedName("response_cd")
    @Expose
    private int responseCd;
    @SerializedName("response_msg")
    @Expose
    private String responseMsg;
    @SerializedName("result_set")
    @Expose
    private T resultSet;

    public int getResponseCd() {
        return responseCd;
    }

    public void setResponseCd(int responseCd) {
        this.responseCd = responseCd;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public T getResultSet() {
        return resultSet;
    }

    public void setResultSet(T resultSet) {
        this.resultSet = resultSet;
    }

}
