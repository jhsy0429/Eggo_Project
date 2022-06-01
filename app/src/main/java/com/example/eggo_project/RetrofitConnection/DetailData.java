package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailData implements Serializable {
    @SerializedName("electFee") private String electFee; // 전기요금
    @SerializedName("waterFee") private String waterFee; // 수도요금
    @SerializedName("publicFee") private String publicFee; // 공공요금
    @SerializedName("individualFee") private String individualFee; // 개별요금


    public String getElectricityFee() {
        return electFee;
    }
    public String getWaterFee() {
        return waterFee;
    }
    public String getPublicFee() {
        return publicFee;
    }
    public String getIndividualFee() {
        return individualFee;
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
    public void setIndividualFee(String individualFee) {
        this.individualFee = individualFee;
    }

}
