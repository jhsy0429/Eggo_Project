package com.example.eggo_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
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
import androidx.fragment.app.Fragment;

import com.example.eggo_project.RetrofitConnection.DataListResponse;
import com.example.eggo_project.RetrofitConnection.DetailData;
import com.example.eggo_project.RetrofitConnection.DetailResponse;
import com.example.eggo_project.RetrofitConnection.JoinResponse;
import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.LoginResponse;
import com.example.eggo_project.RetrofitConnection.RegResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserDTO;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.CookieJar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity  {

    private Button btn_look, btn_bill_reg, btn_bill_in, btn_eggo_ai, btn_bill_pre, btn_logout;
    private TextView text_name, text_fee;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private AlertDialog dialog;

    private TabLayout tabs;
    private AllFragment fragment_all;
    private ElectFragment fragment_elect;
    private WaterFragment fragment_water;

    private String userName, userEmail, id;
    public static String format_yyyyMM = "yyyyMM";
    private String date, lastMonth;
    private List<UserDTO> userDto;
    private DetailData detailData;
    private RetrofitAPI retrofitAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 로그인 응답으로 가져온 회원 이름, 이메일
        LoginResponse loginResponse = (LoginResponse) getIntent().getSerializableExtra("user");
        userName = loginResponse.getName();
        userEmail = loginResponse.getEmail();

        // intent에 저장된 id
        LoginData loginData = (LoginData) getIntent().getSerializableExtra("id");
        id = loginData.getUserId();

        text_name = findViewById(R.id.text_name);
        text_name.setText(userName);

        // 현재 날짜 받아오기(년도,월)
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat(format_yyyyMM, Locale.getDefault());
        String current = format.format(currentTime);


        // 저번달 날짜 받기
        try {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM", Locale.KOREAN);
            Calendar cal = Calendar.getInstance();
            Date sMonth = sdf.parse(current);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, -1);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 저번달 요금 등록
        text_fee = findViewById(R.id.text_fee);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        retrofitAPI.BillDataList(id).enqueue(new Callback<DataListResponse>() {
            @Override
            public void onResponse(Call<DataListResponse> call, Response<DataListResponse> response) {
                DataListResponse result = response.body();
                userDto = new ArrayList<>();

                if (result.getResult().equals("success")) {
                    userDto = result.getDataList();
                    date = userDto.get(0).getDate();

                    if (lastMonth.equals(date)){
                        text_fee.setText(userDto.get(0).getTotalFee());
                    } else {
                        text_fee.setText("등록해주세요.");
                    }
                }
            }

            @Override
            public void onFailure(Call<DataListResponse> call, Throwable t) {

            }
        });



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
        navigation_container.setBackground(getResources().getDrawable(R.color.primaryColor));
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
        tv_username.setText(userName + "님");
        tv_useremail.setText(userEmail);

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
                    Intent intent = new Intent(HomeActivity.this, InquiryActivity.class);
                    intent.putExtra("id", loginData);
                    startActivity(intent);
                }

                // 고지서 예측
                if (id == R.id.menu_bill_pre) {
                    Intent intent = new Intent(HomeActivity.this, PredictionActivity.class);
                    intent.putExtra("id", loginData);
                    startActivity(intent);
                }

                // Eggo AI
                if (id == R.id.menu_eggo_ai) {
                    startActivity(new Intent(HomeActivity.this, EggoaiActivity.class));
                }


                return true;
            }
        });



        // 자세히보기
        btn_look = (Button)findViewById(R.id.btn_look);
        btn_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastMonth.equals(date)) {
                    retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
                    retrofitAPI.DetailLook(id).enqueue(new Callback<DetailResponse>() {
                        @Override
                        public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                            DetailResponse result = response.body();

                            if (result.getResult().equals("success")){
                                detailData = result.getData();

                                String electFee = detailData.getElectricityFee();
                                String waterFee = detailData.getWaterFee();
                                String publicFee = detailData.getPublicFee();
                                String individualFee = detailData.getPublicFee();

                                DetailData detailData2 = new DetailData();
                                detailData2.setElectricityFee(electFee);
                                detailData2.setWaterFee(waterFee);
                                detailData2.setPublicFee(publicFee);
                                detailData2.setIndividualFee(individualFee);

                                Intent intent = new Intent(HomeActivity.this,DetailActivity.class);
                                intent.putExtra("detail", detailData2);
                                startActivity(intent);
                            }
                            else if(result.getResult().equals("fail")) {

                            }
                        }

                        @Override
                        public void onFailure(Call<DetailResponse> call, Throwable t) {

                        }
                    });
                } else {
                    AlertDialog.Builder alBuilder = new AlertDialog.Builder(HomeActivity.this);
                    alBuilder.setMessage("고지서를 등록해주세요.");
                }

            }
        });

        // 고지서 등록
        btn_bill_reg = (Button)findViewById(R.id.btn_bill_reg);
        btn_bill_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RegistrationActivity.class);
                intent.putExtra("userId", loginData);
                startActivity(intent);
            }
        });

        // 고지서 조회
        btn_bill_in = (Button)findViewById(R.id.btn_bill_in);
        btn_bill_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,InquiryActivity.class);
                intent.putExtra("id", loginData);
                startActivity(intent);
            }
        });

        // 고지서 예측
        btn_bill_pre = (Button)findViewById(R.id.btn_bill_pre);
        btn_bill_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PredictionActivity.class);
                intent.putExtra("id", loginData);
                startActivity(intent);
            }
        });

        // EggoAI
        btn_eggo_ai = (Button)findViewById(R.id.btn_eggo_ai);
        btn_eggo_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(HomeActivity.this);
                alBuilder.setMessage("고지서를 등록해주세요.");

                Intent intent = new Intent(HomeActivity.this,EggoaiActivity.class);
                startActivity(intent);
            }
        });

        // 로그아웃
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });



        // 프레그먼트 설정

        fragment_all = new AllFragment();
        Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
        bundle.putString("userId", id); // key , value
        fragment_all.setArguments(bundle);

        fragment_elect = new ElectFragment();
        fragment_elect.setArguments(bundle);

        fragment_water = new WaterFragment();
        fragment_water.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_all).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체"));
        tabs.addTab(tabs.newTab().setText("전기"));
        tabs.addTab(tabs.newTab().setText("수도"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragment_all;
                else if(position == 1)
                    selected = fragment_elect;
                else if(position == 2)
                    selected = fragment_water;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    public void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);

                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeActivity.this, "로그아웃 취소", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

}