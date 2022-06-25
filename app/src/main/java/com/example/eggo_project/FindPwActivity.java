package com.example.eggo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindPwActivity extends AppCompatActivity {

    private Button btn_login2, btn_pw_find;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);


        btn_login2 = (Button)findViewById(R.id.btn_login2);

        //btn_login2 Button의 Click이벤트(첫 페이지로 이동)
        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPwActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_pw_find = (Button)findViewById(R.id.btn_pw_find);
        
        btn_pw_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                dialog = builder.setMessage("이메일로 임시 비밀번호를 발송하였습니다.").setPositiveButton("확인",null).create();
                dialog.show();
                return;
            }
        });
    }
}