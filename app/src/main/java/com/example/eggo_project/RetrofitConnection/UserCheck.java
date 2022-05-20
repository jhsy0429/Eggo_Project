package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

// UserCheck : 아이디 중복 체크시 응답으로 받을 데이터
public class UserCheck {
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
