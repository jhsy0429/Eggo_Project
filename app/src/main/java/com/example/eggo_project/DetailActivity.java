package com.example.eggo_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eggo_project.RetrofitConnection.DetailData;
import com.example.eggo_project.RetrofitConnection.DetailResponse;
import com.example.eggo_project.RetrofitConnection.LoginResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 객체에서 응답데이터 받아오기
        DetailData detailData = (DetailData) getIntent().getSerializableExtra("detail");

        String electFee = detailData.getElectricityFee();
        String waterFee = detailData.getWaterFee();
        String publicFee = detailData.getPublicFee();
        String individualFee = detailData.getIndividualFee();

        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList yValues = new ArrayList();


        // 전기, 수도, 공공, 개별(전체 - 전기,수도,공공)
        yValues.add(new PieEntry(Integer.parseInt(electFee),"전기요금:"+electFee));
        yValues.add(new PieEntry(Integer.parseInt(waterFee),"수도요금:"+waterFee));
        yValues.add(new PieEntry(Integer.parseInt(publicFee),"공공요금:"+publicFee));
        yValues.add(new PieEntry(Integer.parseInt(individualFee),"개별요금:"+individualFee));

        Description description = new Description();
        description.setText("총요금 한눈에 비교하기"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }
}