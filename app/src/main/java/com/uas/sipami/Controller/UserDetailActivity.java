package com.uas.sipami.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.ProgresDto;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.MenuItem; // Pastikan Anda mengimpor kelas MenuItem
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar; // Pastikan Anda mengimpor kelas Toolbar

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    private LineChart lineChart;

    private ApiService apiService;

    private String nikNip;
    private String kodeSurvei;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Terima data dari Intent
        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra("USER_ID");
            nikNip = intent.getStringExtra("NIKNIP");
            kodeSurvei = intent.getStringExtra("KODE_SURVEI");
            token = intent.getStringExtra("TOKEN");
//            Toast.makeText(UserDetailActivity.this,"token:" +token, Toast.LENGTH_SHORT).show();




            // Pemanggilan Retrofit untuk mendapatkan data pengguna berdasarkan nikNip
            apiService = ApiClient.createService(ApiService.class);
            Call<RegisterRequest> call = apiService.getUserByNiknip(token, nikNip);
            call.enqueue(new Callback<RegisterRequest>() {
                @Override
                public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Dapatkan data pengguna
                        RegisterRequest userResponse = response.body();
                        TextView nameTextView = findViewById(R.id.nameTextView);
                        TextView nikNipTextView = findViewById(R.id.nikNipTextView);
                        TextView jenisKelaminTextView = findViewById(R.id.jenisKelaminTextView);
                        TextView pekerjaanTextView = findViewById(R.id.pekerjaanTextView);
                        TextView emailTextView = findViewById(R.id.emailTextView);

                        // Setel nilai teks pada TextView sesuai data dari objek RegisterRequest
                        nameTextView.setText(userResponse.getName());
                        nikNipTextView.setText(userResponse.getNikNip());
                        jenisKelaminTextView.setText(userResponse.getJenisKelamin());
                        pekerjaanTextView.setText(userResponse.getPekerjaan());
                        emailTextView.setText(userResponse.getEmail());

                        int statusCode = response.code();
                        // Toast.makeText(UserDetailActivity.this, "berhasil mengambil data pengguna. Kode status: " + statusCode, Toast.LENGTH_SHORT).show();

                    } else {
                        int statusCode = response.code();
                        Toast.makeText(UserDetailActivity.this, "Gagal mengambil data pengguna. Kode status: " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterRequest> call, Throwable t) {
                    Toast.makeText(UserDetailActivity.this, "Gagal mengambil data pengguna " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("UserDetailActivity", "Error fetching user data from server", t);
                }
            });

            // Tambahkan toolbar ke ActionBar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Aktifkan tombol panah kiri di toolbar
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }

        lineChart = findViewById(R.id.chart);
        // Kode Retrofit Anda untuk mendapatkan data grafik

        Call<List<ProgresDto>> callGrafik = apiService.getDataGrafik(token, nikNip, kodeSurvei);
        callGrafik.enqueue(new Callback<List<ProgresDto>>() {
            @Override
            public void onResponse(Call<List<ProgresDto>> call, Response<List<ProgresDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProgresDto> progresDtoList = response.body();
                    // Panggil metode updateChart untuk menampilkan grafik
                    int statusCode = response.code();
//                    Toast.makeText(UserDetailActivity.this, token, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(UserDetailActivity.this, "berhasil ambil data grafik : " + statusCode, Toast.LENGTH_SHORT).show();

                    updateChart(progresDtoList);
                } else {
                    // Tangani kesalahan jika tidak berhasil mendapatkan data grafik
                    // ...
                }
            }
            @Override
            public void onFailure(Call<List<ProgresDto>> call, Throwable t) {
                // Tangani kesalahan jaringan
                // ...
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Tangani kembali ke tampilan sebelumnya di sini
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
