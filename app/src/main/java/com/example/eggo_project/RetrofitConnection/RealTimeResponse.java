package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

public class RealTimeResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("electFee")
    private String electFee;

    @SerializedName("electUse")
    private String electUse;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getElectFee() {
        return electFee;
    }

    public void setElectFee(String electFee) {
        this.electFee = electFee;
    }

    public String getElectUse() {
        return electUse;
    }

    public void setElectUse(String electUse) {
        this.electFee = electUse;
    }
}
