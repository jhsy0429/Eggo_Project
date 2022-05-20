package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

// JoinData : 회원가입 요청 시 보낼 데이터
public class JoinData {
    @SerializedName("UserId") private String userId;
    @SerializedName("Name") private String name;
    @SerializedName("Email") private String email;
    @SerializedName("Password") private String password;

    public JoinData(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
