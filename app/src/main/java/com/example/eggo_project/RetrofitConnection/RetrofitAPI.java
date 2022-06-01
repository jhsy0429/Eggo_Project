package com.example.eggo_project.RetrofitConnection;

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

    // 고지서 등록
    @FormUrlEncoded
    @POST("/bill/register")
    Call<UserCheck> Register(
            @Field("UserId") String userId,
            @Field("date") String date,
            @Field("electUse") String electUse,
            @Field("waterUse") String waterUse,
            @Field("electFee") String electFee,
            @Field("waterFee") String waterFee,
            @Field("publicFee") String publicFee,
            @Field("totalFee") String totalFee
            );

    // 이미지 전송
    @Multipart
    @POST("/scanPhotoTest")
    Call<RegResponse> BillReg(@Part MultipartBody.Part billImg);

    // 자세히 보기(원그래프)
    @GET("/bill/pieGraph")
    Call<DetailResponse> DetailLook(@Query("userId") String userId);

    // 그래프 정보 받기(꺾은 선 그래프)
    @GET("/bill/getDataList")
    Call<DataListResponse> BillDataList(@Query("userId") String userId);

    // 고지서 조회하기
    @GET("/bill/getData")
    Call<RegResponse> BillIn(
            @Query("userId") String userId,
            @Query("date") String date);

    // 고지서 수정하기
    @FormUrlEncoded
    @POST("/bill/update")
    Call<RegResponse> BillUpdate(
            @Field("UserId") String userId,
            @Field("date") String date,
            @Field("electUse") String electUse,
            @Field("waterUse") String waterUse,
            @Field("electFee") String electFee,
            @Field("waterFee") String waterFee,
            @Field("publicFee") String publicFee,
            @Field("totalFee") String totalFee
    );

    // 실시간 요금 검색(1시간)
    @GET("/realTimeData")
    Call<RealTimeResponse> RealTimeData(
            @Query("region1") String region1,
            @Query("region2") String region2,
            @Query("region3") String region3);

    // 고지서 예측하기
    @GET("/predictFee")
    Call<RegResponse> BillPre(@Query("userId") String userId);
}


