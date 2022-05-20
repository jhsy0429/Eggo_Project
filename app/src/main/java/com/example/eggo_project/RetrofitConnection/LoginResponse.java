package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
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
}
