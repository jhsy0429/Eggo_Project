package com.example.eggo_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.RegResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryActivity extends AppCompatActivity {

    private TextInputEditText text_year, text_month;
    private TextView electFee, waterFee, publicFee, electUse, waterUse, totalFee;
    private Button btn_inquiry;
    private RetrofitAPI retrofitAPI;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_in);

        text_year = findViewById(R.id.text_year);
        text_month = findViewById(R.id.text_month);

        electFee = (TextView)findViewById(R.id.in_electFee);
        waterFee = (TextView)findViewById(R.id.in_waterFee);
        publicFee = (TextView)findViewById(R.id.in_publicFee);
        electUse = (TextView)findViewById(R.id.in_electUse);
        waterUse = (TextView)findViewById(R.id.in_waterUse);
        totalFee = (TextView)findViewById(R.id.in_totalFee);

        LoginData loginData = (LoginData) getIntent().getSerializableExtra("id");
        String id = loginData.getUserId();

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        btn_inquiry = (Button)findViewById(R.id.btn_inquiry);

        // 조회버튼 클릭시, 날짜에 해당하는 고지서 출력
        btn_inquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String year = text_year.getText().toString();
                final String month = text_month.getText().toString();
                String date = year + month;

                if(year.equals("")||month.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                    dialog = builder.setMessage("날짜를 입력해주세요.").setPositiveButton("확인",null).create();
                    dialog.show();

                } else if(!(year.equals("")&&month.equals(""))) {

                    retrofitAPI.BillIn(id, date).enqueue(new Callback<RegResponse>() {
                        @Override
                        public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                            RegResponse result = response.body();

                            if (result.getResult().equals("success")) {
                                String elect_Fee = result.getElectricityFee();
                                String water_Fee = result.getWaterFee();
                                String public_Fee = result.getPublicFee();
                                String elect_Use = result.getElectricityUsage();
                                String water_Use = result.getWaterUsage();
                                String total_Fee = result.getTotalFee();

                                electUse.setText("전기사용량 : " + elect_Use);
                                waterUse.setText("수도사용량 : " + water_Use);
                                electFee.setText("전기요금 : " + elect_Fee);
                                waterFee.setText("수도요금 : " + water_Fee);
                                publicFee.setText("공공요금 : " + public_Fee);
                                totalFee.setText("총요금 : " + total_Fee);

                            }
                            else if (result.getResult().equals("fail")) {

                            }
                        }

                        @Override
                        public void onFailure(Call<RegResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });


    }
}