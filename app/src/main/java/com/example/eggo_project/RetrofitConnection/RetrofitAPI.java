package com.example.eggo_project.RetrofitConnection;

import android.graphics.Paint;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitAPI {

    // 아이디 중복 검사
    @GET("/main/checkId")
    Call<UserCheck> checkId(@Query("userId") String UserId);

    // 회원가입
    @FormUrlEncoded
    @POST("/main/register")
    Call<JoinResponse> SignUp(
            @Field("UserId") String userId,
            @Field("Name") String name,
            @Field("Email") String email,
            @Field("Password") String password
            );

    // 로그인
    @FormUrlEncoded
    @POST("/main/login")
    Call<LoginResponse> SignIn(
            @Field("UserId") String userId,
            @Field("Name") String name,
            @Field("Email") String email,
            @Field("Password") String password
            );

    // 이미지 전송
    @Multipart
    @POST("/scanPhoto")
    Call<RegResponse> BillReg(@Part MultipartBody.Part billImg);

    // 자세히보기
    @GET("/bill/pieGraph")
    Call<DetailResponse> DetailLook(@Query("userId") String userId);

    // 좌표값 보내주기

    // 고지서 조회하기
    @GET("/bill/getData")
    Call<RegResponse> BillIn(
            @Query("userId") String userId,
            @Query("date") String date);
}


