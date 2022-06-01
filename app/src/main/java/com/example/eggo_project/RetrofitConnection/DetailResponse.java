package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class DetailResponse implements Serializable {
    @SerializedName("data")
    private DetailData data;

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

    public DetailData getData() { return data; }
    public void setDataList(DetailData data){
        this.data = data;
    }


}
