package com.example.eggo_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggo_project.RetrofitConnection.JoinData;
import com.example.eggo_project.RetrofitConnection.JoinResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserCheck;
import com.example.eggo_project.RetrofitConnection.UserDTO;
import com.example.eggo_project.RetrofitConnection.UserPost;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinActivity extends AppCompatActivity {

    private TextInputEditText edit_join_id, edit_join_pw, edit_join_pwck, edit_join_name, edit_join_email;
    private Button btn_check, btn_join2;
    private boolean validate = false;
    private AlertDialog dialog;
    private RetrofitClient retrofitClient;

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

        RetrofitClient retrofitClient = new RetrofitClient();

        //아이디 중복 체크
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = edit_join_id.getText().toString();

                Call<UserCheck> call = retrofitClient.retrofitAPI.checkId(userID);
                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        if (response.isSuccessful()) {
                            UserCheck result = response.body();
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                            if (result.getResult().equals("success")) {
                                dialog = builder.setMessage("사용가능한 아이디입니다.").setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = true;
                            }
                            else if (result.getResult().equals("fail")) {
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = false;
                            }
                            else {
                                dialog = builder.setMessage(result.getResult()).setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = true;
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {

                    }
                });




                // Httpconnection 통신
//                Thread th = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("아이디 체크 시도");
//                        SpringConnection sc = new SpringConnection();
//                        String query = "?userId="+userID;
//
//                        //String Message = sc.HttpConnGETUser("Main/CheckId"+query);
//                        String Message = sc.HttpConnGETUser("Main");
//                        System.out.println(Message);
//
//
//                    }
//                });
//                th.start();




                if(validate) {
                    return; //검증완료
                }

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
        //btn_join2 Button의 Click이벤트(로그인 페이지로 이동)
        btn_join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoin();



//                //아이디 중복체크 했는지 확인
//                if (!validate) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
//                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
//                    dialog.show();
//                    return;
//                }
//
                //한 칸이라도 입력 안했을 경우
//                if (userID.equals("") || userPassword.equals("") || userName.equals("") || userEmail.equals("")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
//                    dialog = builder.setMessage("정보를 모두 입력해주세요.").setNegativeButton("확인", null).create();
//                    dialog.show();
//                    return;
//                }

                // Retrofit POST





//                HashMap<String, String> user = new HashMap<>();
//                user.put("UserID", userID);
//                user.put("Name", userName);
//                user.put("Email", userEmail);
//                user.put("Password", userPassword);
//                Call<UserDTO> call = retrofitClient.retrofitAPI.SignUp(user);
//                call.enqueue(new Callback<UserDTO>() {
//                    @Override
//                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                        if(response.isSuccessful()) {
//                            // 로그인 화면으로 이동
//                            Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserDTO> call, Throwable t) {
//
//                    }
//                });






                // Httpconnection 통신
//                Thread th = new Thread(new Runnable(){
//                    @Override
//                    public void run() {
//                        SpringConnection sc = new SpringConnection();
//                        UserDTO userDTO = new UserDTO(userID, userName, userEmail, userPassword);
//                        String Message = sc.HttpConnPOSTUser("Main/SignUp", userDTO) + "님 회원가입 완료.";
//                        System.out.println(Message);
//                    }
//                });
//                th.start();
            }
        });
    }

    // 회원가입 데이터 유효성 검사
    private void attemptJoin() {
        edit_join_pw.setError(null);
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

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData(userID, userName, userEmail, userPassword));
            showProgress(true);
        }
    }

    private void startJoin(JoinData data) {
        Call<JoinResponse> call = retrofitClient.retrofitAPI.SignUp(data);

        call.enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getResult() == "success") {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        edit_join_pw.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}