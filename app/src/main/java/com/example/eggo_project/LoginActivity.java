package com.example.eggo_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.eggo_project.RetrofitConnection.JoinResponse;
import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.LoginResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edit_id, edit_pwd;
    private Button btn_login, btn_join, btn_find;
    private AlertDialog dialog;
    private RetrofitAPI retrofitAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_id = findViewById(R.id.edit_id);
        edit_pwd = findViewById(R.id.edit_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_join = (Button)findViewById(R.id.btn_join);
        btn_find = (Button)findViewById(R.id.btn_find);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        //btn_join Button의 Click이벤트(회원가입 페이지로 이동)
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });

        //btn_find Button의 Click이벤트(ID/비밀번호 찾기 페이지로 이동)
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,FindActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


    }

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    if (result.getResult().equals("success")) {
                        result.setName(result.getName());
                        result.setEmail(result.getEmail());

                        Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
}