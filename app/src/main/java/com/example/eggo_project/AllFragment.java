package com.example.eggo_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.eggo_project.RetrofitConnection.DataListResponse;
import com.example.eggo_project.RetrofitConnection.RetrofitAPI;
import com.example.eggo_project.RetrofitConnection.RetrofitClient;
import com.example.eggo_project.RetrofitConnection.UserDTO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFragment extends Fragment {

    private LineChart chart;
    private LineDataSet dataSet;
    private RetrofitAPI retrofitAPI;
    private List<UserDTO> userDto;
    private String userId;
    private ArrayList<Entry> values;
    private ArrayList<String> label;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_all, container, false);

        Bundle bundle = getArguments();
        String userId = bundle.getString("userId"); // 전달한 key 값

        // 그래프 정보 받기

        retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        retrofitAPI.BillDataList(userId).enqueue(new Callback<DataListResponse>() {
            @Override
            public void onResponse(Call<DataListResponse> call, Response<DataListResponse> response) {
                DataListResponse result = response.body();
                userDto = new ArrayList<>();

                if (result.getResult().equals("success")) {
                    userDto = result.getDataList();

                    values = new ArrayList<>();
                    label = new ArrayList<String>();

                    for (int i = 0; i < 6; i++) {

                        int totalFee = Integer.parseInt(userDto.get(6-i-1).getTotalFee());
                        int date = Integer.parseInt(userDto.get(6-i-1).getDate());
                        int month = 1;
                        if ( date > 202100 && date < 202200){
                            month = date - 202100;
                        } else if ( date > 202200) {
                            month = date - 202200;
                        }

                        values.add(new Entry(i, totalFee));
                        label.add(month + "월");
                    }

                    chart = rootView.findViewById(R.id.all_linechart);

                    dataSet = new LineDataSet(values, "Total Fee");
                    dataSet.setColor(Color.rgb(255, 155, 155));

                    LineData data = new LineData();
                    data.addDataSet(dataSet);

                    chart.setData(data);

                    chart.setExtraBottomOffset(15f); // 간격
                    chart.getDescription().setEnabled(false); // chart 밑에 description 표시 유무

                    ChartCustom();

                    MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
                    mv.setChartView(chart);
                    chart.setMarker(mv);

                    chart.invalidate(); // 차트 업데이트
                    chart.setTouchEnabled(true); // 차트 터치 disable

                }
            }

            @Override
            public void onFailure(Call<DataListResponse> call, Throwable t) {

            }
        });

        return rootView;
    }

    public void ChartCustom(){
        // Legend는 차트의 범례
        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10);
        legend.setTextSize(13);
        legend.setTextColor(Color.parseColor("#A3A3A3"));
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setYEntrySpace(5);
        legend.setWordWrapEnabled(true);
        legend.setXOffset(40f);
        legend.setYOffset(20f);
        legend.getCalculatedLineSizes();


        XAxis xAxis = chart.getXAxis();

        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label)); // x축 라벨 변경
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // x축 데이터 표시 위치
        xAxis.setGranularity(1f);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.rgb(118, 118, 118));
        xAxis.setSpaceMin(0.2f); // Chart 맨 왼쪽 간격 띄우기
        xAxis.setSpaceMax(0.2f); // Chart 맨 오른쪽 간격 띄우기

       // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setTextSize(14f);
        yAxisLeft.setTextColor(Color.rgb(163, 163, 163));
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setAxisLineWidth(2);
//        yAxisLeft.setAxisMinimum(0); // 최솟값
//        yAxisLeft.setAxisMaximum(100000); // 최댓값
//        yAxisLeft.setGranularity((float) RANGE[1][range]);

        // YAxis(Left) (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        YAxis yAxis = chart.getAxisRight();
        yAxis.setDrawLabels(false); // label 삭제
        yAxis.setTextColor(Color.rgb(163, 163, 163));
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisLineWidth(2);
//        yAxis.setAxisMinimum(0f); // 최솟값
//        yAxis.setAxisMaximum((float) RANGE[0][range]); // 최댓값
//        yAxis.setGranularity((float) RANGE[1][range]);

        dataSet.setLineWidth(3);
        dataSet.setCircleRadius(6);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircleHole(true);
        dataSet.setDrawCircles(true);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setDrawHighlightIndicators(false);
        dataSet.setColor(Color.rgb(255, 155, 155));
        dataSet.setCircleColor(Color.rgb(255, 155, 155));

    }

}
