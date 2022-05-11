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
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.eggo_project.HttpConnection.SpringConnection;
import com.example.eggo_project.HttpConnection.UserDTO;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


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

        //아이디 중복 체크
        btn_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = edit_join_id.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("아이디 체크 시도");
                        SpringConnection sc = new SpringConnection();
                        String query = "?userId="+userID;

                        String Message = sc.HttpConnGETUser("Main/CheckId"+query);
                        System.out.println(Message);
                    }
                });
                th.start();




                if(validate) {
                    return; //검증완료
                }

                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요. ").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }

//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//
//                            if (success) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
//                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
//                                dialog.show();
//                                validate = true; //검증 완료
//                                btn_check.setEnabled(false);
//                                btn_check.setBackgroundColor(Color.GRAY);
//                                btn_check.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                            }
//                            else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
//                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
//                                dialog.show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
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


                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        UserDTO userDTO = new UserDTO(userID, userName, userEmail, userPassword);
                        String Message = sc.HttpConnPOSTUser("Main/SignUp", userDTO) + "님 회원가입 완료.";
                        System.out.println(Message);
                    }
                });
                th.start();
            }
        });
    }
}