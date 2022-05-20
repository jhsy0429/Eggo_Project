package com.example.eggo_project.RetrofitConnection;


import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Name")
    private String Name;

    private boolean isRight = false; //중복체크

    public UserDTO(){

    }
    public UserDTO(String UserId, String Name, String Email, String Password){
        this.UserId=UserId;
        this.Name=Name;
        this.Email=Email;
        this.Password=Password;
    }

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


    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }


}