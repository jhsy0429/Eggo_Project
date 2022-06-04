package com.example.eggo_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.example.eggo_project.RetrofitConnection.RealTimeResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EggoaiActivity extends AppCompatActivity {

    private Button btn_search;
    private TextInputEditText text_si, text_gu, text_dong;
    private TextView text_pre;
    private RetrofitAPI retrofitAPI;
    private AlertDialog dialog;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eggo_ai);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        text_si = findViewById(R.id.text_si);
        text_gu = findViewById(R.id.text_gu);
        text_dong = findViewById(R.id.text_dong);
        text_pre = (TextView)findViewById(R.id.text_pre);

        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog( EggoaiActivity.this);
                progress.setMessage("Uploading...");
                progress.show();

                String si = text_si.getText().toString().trim();
                String gu = text_gu.getText().toString().trim();
                String dong = text_dong.getText().toString().trim();

                if (si.equals("") || gu.equals("") || dong.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EggoaiActivity.this);
                    dialog = builder.setMessage("주소를 입력해주세요.").setPositiveButton("확인",null).create();
                    dialog.show();
                } else if (!(si.equals("") && gu.equals("") && dong.equals(""))){
                    retrofitAPI.RealTimeData(si, gu, dong).enqueue(new Callback<RealTimeResponse>() {
                        @Override
                        public void onResponse(Call<RealTimeResponse> call, Response<RealTimeResponse> response) {
                            progress.dismiss();
                            RealTimeResponse result = response.body();

                            if (result.getResult().equals("success")) {
                                String electUse = result.getElectUse();
                                System.out.println(electUse);

                                text_pre.setText("예측된 전기량(kw) : " + electUse);
                            }
                            else if (result.getResult().equals("fail")) {
                                text_pre.setText(result.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<RealTimeResponse> call, Throwable t) {

                        }
                    });
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