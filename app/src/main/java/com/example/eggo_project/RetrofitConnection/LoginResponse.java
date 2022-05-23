package com.example.eggo_project.RetrofitConnection;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;


    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
