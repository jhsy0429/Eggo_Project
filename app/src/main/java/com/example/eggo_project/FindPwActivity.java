package com.example.eggo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindPwActivity extends AppCompatActivity {

    Button btn_login2;

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
    }
}