package com.example.eggo_project;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.eggo_project.RetrofitConnection.LoginData;
import com.example.eggo_project.RetrofitConnection.LoginResponse;
import com.example.eggo_project.RetrofitConnection.RegResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserCheck;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class RegistrationActivity extends AppCompatActivity {

    Button btn_camera, btn_photo, btn_reg, btn_send;

    ImageView imageView;
    final private static String TAG = "RegistrationActivity";
    final static int TAKE_PICTURE = 1;
    private RetrofitAPI retrofitAPI;
    private RegResponse regResponse;
    private String mediaPath;
    private EditText text_date, electUse, waterUse, electFee, waterFee, publicFee, totalFee;
    private AlertDialog dialog;

    private static final int REQUEST_CODE = 0; // 사진 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_reg);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        imageView = (ImageView) findViewById(R.id.imageView);
        //btn_camera.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "권한 설정 완료");
            } else {
                Log.e(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // 사진 찍기 버튼을 눌러 카메라 화면으로 이동 및 촬영
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_camera:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });

        btn_photo = (Button) findViewById(R.id.btn_photo);
        // 갤러리에서 사진가져오기
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btn_send = (Button) findViewById(R.id.btn_send);
        // 사진 전송하기
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goImage();
            }
        });

        // 고지서 등록하기
        btn_reg = (Button) findViewById(R.id.btn_reg);

        LoginData loginData = (LoginData) getIntent().getSerializableExtra("userId");
        String userId = loginData.getUserId();

        text_date = findViewById(R.id.text_date);
        electUse = findViewById(R.id.electUse);
        waterUse = findViewById(R.id.waterUse);
        electFee = findViewById(R.id.electFee);
        waterFee = findViewById(R.id.waterFee);
        publicFee = findViewById(R.id.publicFee);
        totalFee = findViewById(R.id.totalFee);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = text_date.getText().toString();
                String elect_Use = electUse.getText().toString();
                String water_Use = waterUse.getText().toString();
                String elect_Fee = electFee.getText().toString();
                String water_Fee = waterFee.getText().toString();
                String public_Fee = publicFee.getText().toString();
                String total_Fee = totalFee.getText().toString();

                if (date.equals("") || elect_Use.equals("") || water_Use.equals("")
                        || elect_Fee.equals("") || water_Fee.equals("")
                        || public_Fee.equals("") || total_Fee.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    dialog = builder.setMessage("빈칸을 모두 입력해주세요.").setPositiveButton("확인",null).create();
                    dialog.show();

                } else if (!(date.equals("") && elect_Use.equals("") && water_Use.equals("")
                        && elect_Fee.equals("") && water_Fee.equals("")
                        && public_Fee.equals("") && total_Fee.equals(""))){

                    retrofitAPI.Register(userId, date, elect_Use, water_Use, elect_Fee, water_Fee, public_Fee, total_Fee).enqueue(new Callback<UserCheck>() {
                        @Override
                        public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                            UserCheck result = response.body();

                            if (result.getResult().equals("success")) {
                                Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else if (result.getResult().equals("fail")) {
                                Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserCheck> call, Throwable t) {

                        }
                    });

                }
            }
        });


    }

    // 카메라 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);

                        String path = "C:\\Users\\gjhsy\\Desktop\\retrofit_sample\\image.jpg";

                        //BitmapConvertFile(bitmap, path);
                        File file = bitmapToFile(bitmap, path);
                        //SaveBitmapToFileCache(bitmap, path, "image");


                        // 이미지 보내기
                        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);

                        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "image.jpg", fileBody);

//                        retrofitAPI.BillReg(filePart).enqueue(new Callback<RegResponse>() {
//                            @Override
//                            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
//                                RegResponse result = response.body();
//
//
//                                if(result.getResult().equals("success")){
//                                    Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                                else if (result.getResult().equals("fail")){
//                                    Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<RegResponse> call, Throwable t) {
//
//                            }
//                        });
                    }

                }
                break;
        }

        // 갤러리에서 사진호출 후 통신
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri uri = intent.getData();
                    Glide.with(getApplicationContext()).load(uri).into(imageView); // 이미지뷰띄우기

                    //커서 사용해서 경로 확인
                    Cursor cursor = getContentResolver().query(Uri.parse(uri.toString()), null, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    System.out.println(mediaPath);

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                    return;
            }
        }

    }

    private void BitmapConvertFile(Bitmap bitmap, String strFilePath) { // 파일 선언 -> 경로는 파라미터에서 받는다
        File file = new File(strFilePath);
        // OutputStream 선언 -> bitmap데이터를 OutputStream에 받아 File에 넣어주는 용도
        OutputStream out = null;
        try {
            // 파일 초기화
            file.createNewFile();
            // OutputStream에 출력될 Stream에 파일을 넣어준다
            out = new FileOutputStream(file);
            // bitmap 압축
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static File bitmapToFile( Bitmap bitmap, String path) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(path);
            file.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }

    // Bitmap to File
    public void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath, String filename) {

        File file = new File(strFilePath);

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

        File fileCacheItem = new File(strFilePath + filename);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void goImage(){
        File file = new File(mediaPath);

        // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        // RequestBody로 Multipart.Part 객체 생성
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        retrofitAPI.BillReg(filePart).enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                RegResponse result = response.body();

                if(result.getResult().equals("success")){
                    Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    RegResponse regResponse = new RegResponse();
                }
                else if (result.getResult().equals("fail")){
                    Toast.makeText(RegistrationActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {

            }
        });

        if(mediaPath != null) {

        }
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