package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

//  JoinResponse : 회원가입 요청에 대한 응답으로 돌아올 데이터
public class JoinResponse {
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
