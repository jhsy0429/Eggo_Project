package com.example.eggo_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_camera, btn_photo, btn_reg;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_reg);

        btn_camera = (Button)findViewById(R.id.btn_camera);
        imageView = (ImageView)findViewById(R.id.imageView);
        btn_camera.setOnClickListener(this);

        btn_photo = (Button)findViewById(R.id.btn_photo);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 촬영하기버튼 클릭 이벤트
            case R.id.btn_camera:
                // 카메라 기능을 Intent
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if(requestCode == 0 && resultCode == RESULT_OK) {
            // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();

            // Bitmap으로 컨비전
            Bitmap imageBitmap = (Bitmap)extras.get("data");

            // 이미지뷰에 Bitmap으로 이미지를 입력
            imageView.setImageBitmap(imageBitmap);
        }
    }
}