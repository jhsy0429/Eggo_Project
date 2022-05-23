package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable {
    @SerializedName("UserId") private String userId;
    @SerializedName("Password") private String password;

    public LoginData(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
