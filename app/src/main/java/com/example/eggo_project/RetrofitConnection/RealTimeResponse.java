package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

public class RealTimeResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("electFee")
    private String electFee;

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
}
