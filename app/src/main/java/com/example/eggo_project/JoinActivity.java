package com.example.eggo_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.eggo_project.HttpConnection.SpringConnection;
import com.example.eggo_project.HttpConnection.UserDTO;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JoinActivity extends AppCompatActivity {

    private TextInputEditText edit_join_id, edit_join_pw, edit_join_pwck, edit_join_name, edit_join_email;
    private Button btn_check, btn_join2;
    private boolean validate = false;
    private AlertDialog dialog;

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

                //Retrofit GET
                Call<JsonObject> call = retrofitClient.retrofitAPI.getId(userID);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        // 서버와 통신 성공시
                        if (response.isSuccessful()){
                            String message = response.body().get("message").getAsString();
                            if(message == "이미 존재하는 아이디입니다.") {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage(message).setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = false;
                                return;
                            }
                            else if (message == "“사용 가능한 아이디입니다.”") {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage(message).setPositiveButton("확인",null).create();
                                dialog.show();
                                validate = true;
                                return;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        // 서버와 통신 실패시
                        t.printStackTrace();
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
                // EditText에 현재 입력되어 있는 값을 가져온다(get).
                final String userID = edit_join_id.getText().toString();
                final String userPassword = edit_join_pw.getText().toString();
                final String userName = edit_join_name.getText().toString();
                final String userEmail = edit_join_email.getText().toString();
                final String userPasswordck = edit_join_pwck.getText().toString();



//                //아이디 중복체크 했는지 확인
//                if (!validate) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
//                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
//                    dialog.show();
//                    return;
//                }
//
                //한 칸이라도 입력 안했을 경우
                if (userID.equals("") || userPassword.equals("") || userName.equals("") || userEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("정보를 모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                // 로그인 화면으로 이동



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
}