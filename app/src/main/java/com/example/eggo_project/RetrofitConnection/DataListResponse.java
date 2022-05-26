package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataListResponse {

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

    @SerializedName("dataList")
    private List<UserDTO> dataList;

    public List<UserDTO> getDataList() { return dataList; }
    public void setDataList(List<UserDTO> dataList){
        this.dataList = dataList;
    }

}
