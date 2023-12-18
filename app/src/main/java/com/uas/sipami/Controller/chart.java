package com.uas.sipami.Controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.ProgresDto;
import com.uas.sipami.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chart extends AppCompatActivity {

    private LineChart lineChart;
    private List<String> xValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.chart);

        Description description = new Description();
        description.setText("Students Record");
        description.setPosition(150f, 15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        ApiService apiService;
        apiService = ApiClient.createService(ApiService.class);

        // Replace these values with actual data
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMxMjMiLCJpc3MiOiJQb2xzdGF0IiwiaWF0IjoxNzAyNzM1MjI2LCJleHAiOjE3MDI4MjE2MjZ9.z9gte6xWee6u8-F1oVSQfki5H7CSFrI-vAakFvuCt3ID51JfGBBUx5WEHNCUtpwdhiuixvlTSeW8NlpYPkpd_Q";
        String nikNip = "123123";
        String kodeSurvei = "1";

        Call<List<ProgresDto>> call = apiService.getDataGrafik(token, nikNip, kodeSurvei);
        call.enqueue(new Callback<List<ProgresDto>>() {
            @Override
            public void onResponse(Call<List<ProgresDto>> call, Response<List<ProgresDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProgresDto> progresDtoList = response.body();
                    updateChart(progresDtoList);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<ProgresDto>> call, Throwable t) {
                // Handle network error
            }
        });
    }

    private void updateChart(List<ProgresDto> progresDtoList) {
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < progresDtoList.size(); i++) {
            ProgresDto progresDto = progresDtoList.get(i);
            entries.add(new Entry(i, progresDto.getJumlahCacah()));
            labels.add(progresDto.getTanggal()); // Menambahkan tanggal ke dalam list
        }

        LineDataSet dataSet = new LineDataSet(entries, "Maths");
        // Setelah membuat objek LineDataSet, Anda dapat mengatur berbagai properti untuk mengubah gaya grafik.
        // Berikut adalah beberapa contoh pengaturan properti, sesuaikan dengan preferensi desain Anda.
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(12f);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        // Mengatur sumbu x dengan menggunakan tanggal
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(labels.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Menambahkan rotasi label sumbu x
        xAxis.setLabelRotationAngle(-45); // Sesuaikan dengan sudut rotasi yang diinginkan

        // Mengaktifkan fitur scroll pada sumbu x
        lineChart.setScaleXEnabled(true);

        // Menetapkan jumlah maksimum label sumbu x yang dapat ditampilkan sekaligus
        lineChart.setVisibleXRangeMaximum(5f); // Sesuaikan dengan jumlah label yang diinginkan

        lineChart.invalidate();
    }


}
