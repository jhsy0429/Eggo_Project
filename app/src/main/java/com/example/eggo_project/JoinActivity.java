package com.example.eggo_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eggo_project.RetrofitConnection.JoinData;
import com.example.eggo_project.RetrofitConnection.JoinResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserCheck;
import com.google.android.material.textfield.TextInputEditText;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinActivity extends AppCompatActivity {

    private TextInputEditText edit_join_id, edit_join_pw, edit_join_pwck, edit_join_name, edit_join_email;
    private Button btn_check, btn_join2;
    private boolean validate = false;
    private AlertDialog dialog;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edit_join_id = findViewById(R.id.edit_join_id);
        edit_join_pw = findViewById(R.id.edit_join_pw);
        edit_join_pwck = findViewById(R.id.edit_join_pwck);
        edit_join_name = findViewById(R.id.edit_join_name);
        edit_join_email = findViewById(R.id.edit_join_email);
        btn_check = (Button)findViewById(R.id.btn_check);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        //아이디 중복 체크
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = edit_join_id.getText().toString();

                Call<UserCheck> call = retrofitAPI.checkId(userID);
                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        if (response.isSuccessful()) {
                            UserCheck result = response.body();
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                            if (result.getResult().equals("success")) {
                                dialog = builder.setMessage(result.getMessage()).setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = true;
                            }
                            else if (result.getResult().equals("fail")) {
                                dialog = builder.setMessage(result.getMessage()).setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = false;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {

                    }
                });


                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요. ").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }

            }
        });



        //가입하기 버튼 클릭 시 수행
        btn_join2 = (Button)findViewById(R.id.btn_join2);

        btn_join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoin();


            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 회원가입 데이터 유효성 검사
    private void attemptJoin() {
        edit_join_id.setError(null);
        edit_join_pw.setError(null);
        edit_join_pwck.setError(null);
        edit_join_email.setError(null);
        edit_join_name.setError(null);

        final String userID = edit_join_id.getText().toString();
        final String userName = edit_join_name.getText().toString();
        final String userEmail = edit_join_email.getText().toString();
        final String userPassword = edit_join_pw.getText().toString();
        final String userPasswordck = edit_join_pwck.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (userPassword.isEmpty()) {
            edit_join_pw.setError("비밀번호를 입력해주세요.");
            focusView = edit_join_pw;
            cancel = true;
        } else if (!isPasswordValid(userPassword)) {
            edit_join_pw.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = edit_join_pw;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (userEmail.isEmpty()) {
            edit_join_email.setError("이메일을 입력해주세요.");
            focusView = edit_join_email;
            cancel = true;
        } else if (!isEmailValid(userEmail)) {
            edit_join_email.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = edit_join_email;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (userName.isEmpty()) {
            edit_join_name.setError("이름을 입력해주세요.");
            focusView = edit_join_name;
            cancel = true;
        }

        // 비밀번호 체크
        if(!userPassword.equals(userPasswordck)) {
            edit_join_pwck.setError("비밀번호가 일치하지 않습니다.");
            focusView = edit_join_pwck;
            cancel = true;
        }

        //아이디 중복체크 했는지 확인
        if (!validate) {
            edit_join_id.setError("사용가능한 아이디인지 검사해주세요.");
            focusView = edit_join_id;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            retrofitAPI.SignUp(userID, userName, userEmail, userPassword).enqueue(new Callback<JoinResponse>() {
                @Override
                public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                    JoinResponse result = response.body();

                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    if (result.getResult().equals("success")) {
                        Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else if (result.getResult().equals("fail")) {
                        dialog = builder.setMessage(result.getMessage()).setPositiveButton("확인",null).create();
                        dialog.show();
                    }
                }
                @Override
                public void onFailure(Call<JoinResponse> call, Throwable t) {

                }
            });
        }
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
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