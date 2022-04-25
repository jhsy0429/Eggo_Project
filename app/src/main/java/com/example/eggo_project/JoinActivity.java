package com.example.eggo_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class JoinActivity extends AppCompatActivity {

    private EditText edit_join_id, edit_join_pw, edit_join_pwck, edit_join_name, edit_join_email;
    private Button btn_check, btn_join2;

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


        //가입하기 버튼 클릭 시 수행
        btn_join2 = (Button)findViewById(R.id.btn_join2);
        //btn_join2 Button의 Click이벤트(로그인 페이지로 이동)
        btn_join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에 현재 입력되어 있는 값을 가져온다(get).
                String userID = edit_join_id.getText().toString();
                String userPassword = edit_join_pw.getText().toString();
                String userName = edit_join_name.getText().toString();
                String userEmail = edit_join_email.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");  //php의 success
                            if(success) { // 회원가입에 성공한 경우
                                Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { // 회원가입에 실패한 경우
                                Toast.makeText(getApplicationContext(), "가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(registerRequest);



            }
        });
    }
}