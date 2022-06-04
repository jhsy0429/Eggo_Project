package com.example.eggo_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.RegResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictionActivity extends AppCompatActivity {

    private TextView text_bill_pre, text_bill_com;
    private String id, predictedFee, rateOfchange;
    private RetrofitAPI retrofitAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pre);

        text_bill_pre = findViewById(R.id.text_bill_pre);
        text_bill_com = findViewById(R.id.text_bill_com);

        LoginData loginData = (LoginData) getIntent().getSerializableExtra("id");
        id = loginData.getUserId();

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        retrofitAPI.BillPre(id).enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                RegResponse result = response.body();

                if (result.getResult().equals("success")) {
                    predictedFee = result.getPredictedFee();
                    rateOfchange = result.getRateOfchange();

                    text_bill_pre.setText(predictedFee + " 원");
                    text_bill_com.setText(rateOfchange + " %");

                } else if (result.getResult().equals("fail")) {
                    Toast.makeText(PredictionActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {

            }
        });
    }
}
