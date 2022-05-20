package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;

// 서버에 보낼 데이터
public class LoginRequest {
    @SerializedName("UserId") private String UserId;
    @SerializedName("Name") private String Name;
    @SerializedName("Email") private String Email;
    @SerializedName("Password") private String Password;

    public String getUserId(){
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }


    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }


    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }


    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }


    public LoginRequest(String UserId, String Name, String Email, String Password) {
        this.UserId = UserId;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
    }
}
