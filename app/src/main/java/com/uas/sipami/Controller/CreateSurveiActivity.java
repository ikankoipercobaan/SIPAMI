package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSurveiActivity extends AppCompatActivity {

    private EditText editTextNamaSurvei;
    private EditText editTextKodeSurvei;
    private EditText editTextTanggalMulai;
    private EditText editTextTanggalSelesai;
    private Button buttonKirim;
    private ApiService apiService;
    private String token;
    private String nikNip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        editTextNamaSurvei = findViewById(R.id.editTextNamaSurvei);
        editTextKodeSurvei = findViewById(R.id.editTextKodeSurvei);
        editTextTanggalMulai = findViewById(R.id.editTextDateMulai);
        editTextTanggalSelesai = findViewById(R.id.editTextDateSelesai);
        buttonKirim = findViewById(R.id.buttonKirim);
        token = getIntent().getStringExtra("TOKEN");
        nikNip = getIntent().getStringExtra("NIKNIP");

        apiService = ApiClient.createService(ApiService.class);

        buttonKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSurvey(token, nikNip);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        // Enable the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handle click events on the back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk membuka ListSurveiAdminActivity
                Intent intent = new Intent(CreateSurveiActivity.this, ListSurveiAdminActivity.class);
                // Menambahkan data yang mungkin dibutuhkan oleh ListSurveiAdminActivity
                intent.putExtra("TOKEN", token);
                intent.putExtra("NIKNIP", nikNip);
                // Menjalankan Intent
                startActivity(intent);
                // Menutup aktivitas saat ini (CreateSurveiActivity)
                finish();
            }
        });
    }

    private void createSurvey(String token, String nikNip) {
        String namaSurvei = editTextNamaSurvei.getText().toString();
        String kodeSurvei = editTextKodeSurvei.getText().toString();
        String tanggalMulaiString = editTextTanggalMulai.getText().toString();
        String tanggalSelesaiString = editTextTanggalSelesai.getText().toString();

        SurveiDto surveiDto = new SurveiDto(namaSurvei, kodeSurvei, tanggalMulaiString, tanggalSelesaiString);

        Call<SurveiDto> call = apiService.createSurvey(token, surveiDto);
        call.enqueue(new Callback<SurveiDto>() {
            @Override
            public void onResponse(@NonNull Call<SurveiDto> call, @NonNull Response<SurveiDto> response) {
                if (response.isSuccessful()) {
                    SurveiDto createdSurvey = response.body();
                    Toast.makeText(CreateSurveiActivity.this, "Survei berhasil dibuat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateSurveiActivity.this, "Gagal membuat survei", Toast.LENGTH_SHORT).show();
                    Log.e("CreateSurveyActivity", "Gagal membuat survei: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SurveiDto> call, @NonNull Throwable t) {
                Toast.makeText(CreateSurveiActivity.this, "Gagal membuat survei", Toast.LENGTH_SHORT).show();
                Log.e("CreateSurveyActivity", "Gagal membuat survei", t);
            }
        });
    }
}
