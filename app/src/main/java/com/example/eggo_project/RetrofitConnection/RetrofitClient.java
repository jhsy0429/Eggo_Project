package com.example.eggo_project.RetrofitConnection;

import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "http://192.168.1.110:8080/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(1, TimeUnit.MINUTES) // 연결 타임아웃
                .readTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃
                .writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타임아웃
                .build();

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
