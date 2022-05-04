package com.example.eggo_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity  {

    private Button btn_look, btn_bill_reg, btn_bill_in, btn_bill_pre, btn_eggo_ai, btn_energy_save;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 툴바 커스텀
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); // 메뉴 버튼 모양 설정


        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer);

        // 헤더 설정
        LinearLayout navigation_container = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.menu_header,null);
        navigation_container.setBackground(getResources().getDrawable(R.color.colorEggo));
        navigation_container.setPadding(20, 150, 40, 50);
        navigation_container.setOrientation(LinearLayout.VERTICAL);
        navigation_container.setGravity(Gravity.BOTTOM);
        navigation_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        final TextView tv_username = new TextView(this);
        tv_username.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_username.setTextSize(18);
        tv_username.setTypeface(Typeface.DEFAULT_BOLD);
        tv_username.setPadding(0, 2, 0, 2);
        param1.setMargins(20, 20, 20, 5);
        tv_username.setLayoutParams(param1);

        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView tv_useremail = new TextView(this);
        tv_useremail.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_useremail.setTextSize(15);
        tv_useremail.setTypeface(Typeface.DEFAULT_BOLD);
        tv_useremail.setPadding(0, 2, 0, 2);
        param2.setMargins(20, 0, 20, 10);
        tv_useremail.setLayoutParams(param2);



        // 네비게이션 헤더에 현재 로그인 중인 사용자를 보여주기 위해 데이터를 가져오는 코드
        // tv_username.setText(tempName + "님");
        // tv_useremail.setText(tempEmail);
        tv_username.setText("한세린" + "님");
        tv_useremail.setText("test12345@naver.com");

        // navigation_container에 만든 요소들을 담음
        navigation_container.addView(tv_username);
        navigation_container.addView(tv_useremail);

        navigationView.addHeaderView(navigation_container);

        // 네비게이션 메뉴에서 메뉴들이 눌렀을 때의 행동
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                // 고지서 등록
                if (id == R.id.menu_bill_reg) {
                    startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
                }
                // 고지서 조회
                if (id == R.id.menu_bill_in) {
                    startActivity(new Intent(HomeActivity.this, InquiryActivity.class));
                }
                // 고지서 예측
                if (id == R.id.menu_bill_pre) {
                    startActivity(new Intent(HomeActivity.this, PredictionActivity.class));
                }
                // Eggo AI
                if (id == R.id.menu_eggo_ai) {
                    startActivity(new Intent(HomeActivity.this, EggoaiActivity.class));
                }
                // 에너지 절약법
                if (id == R.id.menu_energy_save) {
                    startActivity(new Intent(HomeActivity.this, EnergyActivity.class));
                }

                return true;
            }
        });



        // 정보 수정하기
//        btn_modify = (Button)findViewById(R.id.btn_modify);
//        btn_modify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this,ModifyActivity.class);
//                startActivity(intent);
//            }
//        });


        // 자세히보기
        btn_look = (Button)findViewById(R.id.btn_look);
        btn_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });

        // 고지서 등록
        btn_bill_reg = (Button)findViewById(R.id.btn_bill_reg);
        btn_bill_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // 고지서 조회
        btn_bill_in = (Button)findViewById(R.id.btn_bill_in);
        btn_bill_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,InquiryActivity.class);
                startActivity(intent);
            }
        });

        // 고지서 예측
        btn_bill_pre = (Button)findViewById(R.id.btn_bill_pre);
        btn_bill_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,PredictionActivity.class);
                startActivity(intent);
            }
        });

        // EggoAI
        btn_eggo_ai = (Button)findViewById(R.id.btn_eggo_ai);
        btn_eggo_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,EggoaiActivity.class);
                startActivity(intent);
            }
        });

        //에너지 절약법
        btn_energy_save = (Button)findViewById(R.id.btn_energy_save);
        btn_energy_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,EnergyActivity.class);
                startActivity(intent);
            }
        });


    }

    // 메뉴 선택시 네비게이션 호출
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        //뒤로가기 버튼으로 네비게이션 드로어 닫기
        DrawerLayout drawer = findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
            alBuilder.setMessage("종료하시겠습니까?");

            // "예" 버튼을 누르면 실행되는 리스너
            alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            // "아니오" 버튼을 누르면 실행되는 리스너
            alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return; // 아무런 작업도 하지 않고 돌아간다
                }
            });
            alBuilder.setTitle("프로그램 종료");
            alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
        }
    }

}