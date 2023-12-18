package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.ActionBar;

public class DetailSurveiActivity extends AppCompatActivity {

    private TextView textViewNamaSurvei, textViewKodeSurvei, textViewTanggalMulai, getTextViewTanggalSelesai;
    private Button buttonEdit;

    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_survei);

        // Inisialisasi TextView
        textViewNamaSurvei = findViewById(R.id.nameTextView);
        textViewKodeSurvei = findViewById(R.id.kodeTextView);
        textViewTanggalMulai = findViewById(R.id.startDateTextView);
        getTextViewTanggalSelesai = findViewById(R.id.endDateTextView);
        buttonEdit = findViewById(R.id.buttonEdit);

        String token = getIntent().getStringExtra("TOKEN");
        String nikNip = getIntent().getStringExtra("NIKNIP");
        String kodeSurvei = getIntent().getStringExtra("KODE_SURVEI");

        fetchDataFromApi(token, kodeSurvei);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Menambahkan listener untuk tombol "Edit"
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode untuk memulai aktivitas EditSurveiActivity
                startEditSurveiActivity();
            }
        });
    }

    private void fetchDataFromApi(String token, String kodeSurvei) {
        apiService = ApiClient.createService(ApiService.class);

        Call<SurveiDto> call = apiService.getDetailSurveiByKode(token, kodeSurvei);
        call.enqueue(new Callback<SurveiDto>() {
            @Override
            public void onResponse(Call<SurveiDto> call, Response<SurveiDto> response) {
                if (response.isSuccessful()) {
                    SurveiDto survei = response.body();
                    // Set data ke TextView
                    textViewNamaSurvei.setText(survei.getNamaSurvei());
                    textViewKodeSurvei.setText("(" + survei.getKodeSurvei() + ")");

                    textViewTanggalMulai.setText(survei.getTanggalMulai());
                    getTextViewTanggalSelesai.setText(survei.getTanggalSelesai());
                } else {
                    Toast.makeText(DetailSurveiActivity.this, "Gagal mengambil data survei", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurveiDto> call, Throwable t) {
                Toast.makeText(DetailSurveiActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Metode untuk memulai EditSurveiActivity
    private void startEditSurveiActivity() {
        Intent intent = new Intent(this, EditSurveiActivity.class);
        // Mengirimkan data yang dibutuhkan ke EditSurveiActivity
        intent.putExtra("TOKEN", getIntent().getStringExtra("TOKEN"));
        intent.putExtra("NIKNIP", getIntent().getStringExtra("NIKNIP"));
        intent.putExtra("KODE_SURVEI", getIntent().getStringExtra("KODE_SURVEI"));
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Aksi yang diambil ketika tombol kembali ditekan
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
