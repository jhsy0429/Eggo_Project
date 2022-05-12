package com.example.eggo_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class InquiryActivity extends AppCompatActivity {

    TextInputEditText text_year, text_month;
    Button btn_inquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_in);

        text_year = findViewById(R.id.text_year);
        text_month = findViewById(R.id.text_month);

        btn_inquiry = (Button)findViewById(R.id.btn_inquiry);

        // 조회버튼 클릭시, 날짜에 해당하는 고지서 출력
        btn_inquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String year = text_year.getText().toString();
                final String month = text_month.getText().toString();



            }
        });


    }
}