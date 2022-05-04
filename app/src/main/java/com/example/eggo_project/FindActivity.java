package com.example.eggo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindActivity extends AppCompatActivity {

    Button btn_fid;
    Button btn_fpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        btn_fid = (Button)findViewById(R.id.btn_fid);
        //btn_fid Button의 Click이벤트(아이디 찾기 페이지로 이동)
        btn_fid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this,FindIdActivity.class);
                startActivity(intent);
            }
        });

        btn_fpw = (Button)findViewById(R.id.btn_fpw);
        btn_fpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this,FindPwActivity.class);
                startActivity(intent);
            }
        });


    }
}