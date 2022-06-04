package com.example.eggo_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eggo_project.RetrofitConnection.BillDTO;
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
    private EditText edit_electUse, edit_waterUse, edit_electFee, edit_waterFee, edit_publicFee, edit_totalFee;
    private String id, date, elect_Fee, water_Fee, public_Fee, elect_Use, water_Use, total_Fee;
    private Button btn_inquiry, btn_modify, btn_success;
    private RetrofitAPI retrofitAPI;
    private AlertDialog dialog;
    private BillDTO billDTO;
    private boolean change = false; // 조희 -> 수정

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
        id = loginData.getUserId();

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        btn_inquiry = (Button)findViewById(R.id.btn_inquiry);

        // 조회버튼 클릭시, 날짜에 해당하는 고지서 출력
        btn_inquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String year = text_year.getText().toString();
                final String month = text_month.getText().toString();
                date = year + month;
                change = true;

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
                                billDTO = result.getData();

                                elect_Fee = billDTO.getElectricityFee();
                                water_Fee = billDTO.getWaterFee();
                                public_Fee = billDTO.getPublicFee();
                                elect_Use = billDTO.getElectricityUsage();
                                water_Use = billDTO.getWaterUsage();
                                total_Fee = billDTO.getTotalFee();

                                electUse.setText(elect_Use);
                                waterUse.setText(water_Use);
                                electFee.setText(elect_Fee);
                                waterFee.setText(water_Fee);
                                publicFee.setText(public_Fee);
                                totalFee.setText(total_Fee);

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

        edit_electUse = findViewById(R.id.edit_electUse);
        edit_waterUse = findViewById(R.id.edit_waterUse);
        edit_electFee = findViewById(R.id.edit_electFee);
        edit_waterFee = findViewById(R.id.edit_waterFee);
        edit_publicFee = findViewById(R.id.edit_publicFee);
        edit_totalFee = findViewById(R.id.edit_totalFee);

        btn_modify = (Button) findViewById(R.id.btn_modify);
        btn_success = (Button) findViewById(R.id.btn_success);

        // 고지서 수정하기
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change) {
                    electUse.setVisibility(View.GONE);
                    waterUse.setVisibility(View.GONE);
                    electFee.setVisibility(View.GONE);
                    waterFee.setVisibility(View.GONE);
                    publicFee.setVisibility(View.GONE);
                    totalFee.setVisibility(View.GONE);

                    edit_electUse.setVisibility(View.VISIBLE);
                    edit_waterUse.setVisibility(View.VISIBLE);
                    edit_electFee.setVisibility(View.VISIBLE);
                    edit_waterFee.setVisibility(View.VISIBLE);
                    edit_publicFee.setVisibility(View.VISIBLE);
                    edit_totalFee.setVisibility(View.VISIBLE);

                    edit_electUse.setText(elect_Use);
                    edit_waterUse.setText(water_Use);
                    edit_electFee.setText(elect_Fee);
                    edit_waterFee.setText(water_Fee);
                    edit_publicFee.setText(public_Fee);
                    edit_totalFee.setText(total_Fee);

                    btn_success.setVisibility(View.VISIBLE);


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                    dialog = builder.setMessage("수정할 고지서를 조회해주세요.").setPositiveButton("확인",null).create();
                    dialog.show();
                }
            }
        });



        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elect_Use = edit_electUse.getText().toString();
                water_Use = edit_waterUse.getText().toString();
                elect_Fee = edit_electFee.getText().toString();
                water_Fee = edit_waterFee.getText().toString();
                public_Fee = edit_publicFee.getText().toString();
                total_Fee = edit_totalFee.getText().toString();

                if (elect_Use.equals("") || water_Use.equals("")
                        || elect_Fee.equals("") || water_Fee.equals("")
                        || public_Fee.equals("") || total_Fee.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                    dialog = builder.setMessage("빈칸을 모두 입력해주세요.").setPositiveButton("확인",null).create();
                    dialog.show();
                } else {

                    retrofitAPI.BillUpdate(id, date, elect_Use, water_Use, elect_Fee, water_Fee, public_Fee, total_Fee).enqueue(new Callback<RegResponse>() {
                        @Override
                        public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                            RegResponse result = response.body();

                            if (result.getResult().equals("success")) {
                                Toast.makeText(InquiryActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InquiryActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegResponse> call, Throwable t) {

                        }
                    });


                    edit_electUse.setVisibility(View.GONE);
                    edit_waterUse.setVisibility(View.GONE);
                    edit_electFee.setVisibility(View.GONE);
                    edit_waterFee.setVisibility(View.GONE);
                    edit_publicFee.setVisibility(View.GONE);
                    edit_totalFee.setVisibility(View.GONE);

                    electUse.setVisibility(View.VISIBLE);
                    waterUse.setVisibility(View.VISIBLE);
                    electFee.setVisibility(View.VISIBLE);
                    waterFee.setVisibility(View.VISIBLE);
                    publicFee.setVisibility(View.VISIBLE);
                    totalFee.setVisibility(View.VISIBLE);

                    electUse.setText(elect_Use);
                    waterUse.setText(water_Use);
                    electFee.setText(elect_Fee);
                    waterFee.setText(water_Fee);
                    publicFee.setText(public_Fee);
                    totalFee.setText(total_Fee);

                    btn_success.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    // 터치로 화면 내리기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}