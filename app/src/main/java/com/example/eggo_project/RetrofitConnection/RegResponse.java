package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegResponse implements Serializable {
    @SerializedName("date") private String date; // 날짜
    @SerializedName("electUse") private String electUse; // 전기사용량
    @SerializedName("waterUse") private String waterUse; // 수도사용량
    @SerializedName("electFee") private String electFee; // 전기요금
    @SerializedName("waterFee") private String waterFee; // 수도요금
    @SerializedName("publicFee") private String publicFee; // 공공요금
    @SerializedName("totalFee") private String totalFee; // 총요금
    @SerializedName("predictedFee") private String predictedFee; // 이번달 예측
    @SerializedName("rateOfchange") private  String rateOfchange; // 저번달 대비 변화율



    @SerializedName("data") private BillDTO data;

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    public String getResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }


    public String getDate() {
        return date;
    }
    public String getElectricityUsage() {
        return electUse;
    }
    public String getWaterUsage() {
        return waterUse;
    }
    public String getElectricityFee() {
        return electFee;
    }
    public String getWaterFee() {
        return waterFee;
    }
    public String getPublicFee() {
        return publicFee;
    }
    public String getTotalFee() {
        return totalFee;
    }
    public String getPredictedFee() {
        return predictedFee;
    }
    public String getRateOfchange() {
        return rateOfchange;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public void setElectricityUsage(String electUse) {
        this.electUse = electUse;
    }
    public void setWaterUsage(String waterUse) {
        this.waterUse = waterUse;
    }
    public void setElectricityFee(String electFee) {
        this.electFee = electFee;
    }
    public void setWaterFee(String waterFee) {
        this.waterFee = waterFee;
    }
    public void setPublicFee(String publicFee) {
        this.publicFee = publicFee;
    }
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
    public void setPredictedFee(String predictedFee) {
        this.predictedFee = predictedFee;
    }
    public void setRateOfchange(String rateOfchange) {
        this.rateOfchange = rateOfchange;
    }

    public BillDTO getData() {return data;}
    public void setData(BillDTO data) {this.data = data;}
}
