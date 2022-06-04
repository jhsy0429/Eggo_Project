package com.example.eggo_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;

import com.example.eggo_project.RetrofitConnection.DataListResponse;
import com.example.eggo_project.RetrofitConnection.JoinResponse;
import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.LoginResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserDTO;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edit_id, edit_pwd;
    private Button btn_login, btn_join;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_id = findViewById(R.id.edit_id);
        edit_pwd = findViewById(R.id.edit_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_join = (Button)findViewById(R.id.btn_join);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        //btn_join Button의 Click이벤트(회원가입 페이지로 이동)
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 시도
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    // 로그인 데이터 유효성 검사
    private void attemptLogin() {
        edit_id.setError(null);
        edit_pwd.setError(null);

        String id = edit_id.getText().toString();
        String password = edit_pwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            edit_pwd.setError("비밀번호를 입력해주세요.");
            focusView = edit_pwd;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            edit_pwd.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = edit_pwd;
            cancel = true;
        }

        // 아이디의 유효성 검사
        if (id.isEmpty()) {
            edit_id.setError("아이디를 입력해주세요.");
            focusView = edit_id;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            retrofitAPI.SignIn(id,"123","123", password).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse result = response.body();

                    if (result.getResult().equals("success")) {
                        String name = result.getName();
                        String email = result.getEmail();

                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setName(name);
                        loginResponse.setEmail(email);

                        LoginData loginData = new LoginData(id, password);
                        String userid = loginData.getUserId();
                        loginData.setUserId(userid);

//                        // 그래프를 위한 데이터 조회
//                        getDataList(id);

                        String activityName = "activityLogin";

                        Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user", loginResponse);
                        intent.putExtra("id", loginData);

                        startActivity(intent);
                    }
                    else if (result.getResult().equals("fail")) {
                        Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    // 터치로 화면 내리기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}