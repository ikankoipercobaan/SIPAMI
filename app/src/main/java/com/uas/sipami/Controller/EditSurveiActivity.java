package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSurveiActivity extends AppCompatActivity {

    private EditText editTextNamaSurvei, editTextTanggalMulai, editTextTanggalSelesai;
    private Button buttonSimpan;
    private ApiService apiService;
    private String token;
    private String nikNip;
    private String kodeSurvei;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survei);

        // Inisialisasi elemen UI
        editTextNamaSurvei = findViewById(R.id.editTextNamaSurvei);
        editTextTanggalMulai = findViewById(R.id.editTextTanggalMulai);
        editTextTanggalSelesai = findViewById(R.id.editTextTanggalSelesai);
        buttonSimpan = findViewById(R.id.buttonSimpan);

        // Ambil data dari Intent
        token = getIntent().getStringExtra("TOKEN");
        nikNip = getIntent().getStringExtra("NIKNIP");
        kodeSurvei = getIntent().getStringExtra("KODE_SURVEI");

        // Ambil data survei untuk mengisi formulir pengeditan
        String namaSurvei = editTextNamaSurvei.getText().toString();
        String tanggalMulai = editTextTanggalMulai.getText().toString();
        String tanggalSelesai = editTextTanggalSelesai.getText().toString();
        SurveiDto surveiDto = new SurveiDto(namaSurvei, kodeSurvei, tanggalMulai, tanggalSelesai);

        fetchDataForEditing(token, kodeSurvei);

        // Tambahkan listener untuk tombol Simpan
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode untuk menyimpan pembaruan ke API
                saveChangesToApi();
            }
        });

        // Inisialisasi Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menampilkan tombol "Up" (panah ke kiri) di ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void fetchDataForEditing(String token, String kodeSurvei) {
        apiService = ApiClient.createService(ApiService.class);

        Call<SurveiDto> call = apiService.getDetailSurveiByKode(token, kodeSurvei);
        call.enqueue(new Callback<SurveiDto>() {
            @Override
            public void onResponse(Call<SurveiDto> call, Response<SurveiDto> response) {
                if (response.isSuccessful()) {
                    SurveiDto survei = response.body();
                    // Isi formulir pengeditan dengan data survei yang ada
                    editTextNamaSurvei.setText(survei.getNamaSurvei());
                    editTextTanggalMulai.setText(survei.getTanggalMulai());
                    editTextTanggalSelesai.setText(survei.getTanggalSelesai());
                    // Isi elemen UI lainnya sesuai kebutuhan
                } else {
                    Toast.makeText(EditSurveiActivity.this, "Gagal mengambil data survei", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurveiDto> call, Throwable t) {
                Toast.makeText(EditSurveiActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveChangesToApi() {
        // Mengambil data yang telah diubah dari elemen UI
        String namaSurvei = editTextNamaSurvei.getText().toString().trim();
        String tanggalMulai = editTextTanggalMulai.getText().toString().trim();
        String tanggalSelesai = editTextTanggalSelesai.getText().toString().trim();

        // Mengecek apakah data yang diubah tidak kosong
        if (namaSurvei.isEmpty() || tanggalMulai.isEmpty() || tanggalSelesai.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek SurveiDto untuk menyimpan data yang diubah
        SurveiDto updatedSurvei = new SurveiDto();
        updatedSurvei.setNamaSurvei(namaSurvei);
        updatedSurvei.setTanggalMulai(tanggalMulai);
        updatedSurvei.setTanggalSelesai(tanggalSelesai);

        // Menggunakan ApiService dan Retrofit untuk menyimpan data yang diubah ke API
        apiService = ApiClient.createService(ApiService.class);
        Call<SurveiDto> call = apiService.updateSurvei(token, kodeSurvei, updatedSurvei);
        call.enqueue(new Callback<SurveiDto>() {
            @Override
            public void onResponse(Call<SurveiDto> call, Response<SurveiDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditSurveiActivity.this, "Data survei berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    // Anda dapat menambahkan logika tambahan atau memulai aktivitas lain setelah penyimpanan berhasil
                } else {
                    Toast.makeText(EditSurveiActivity.this, "Gagal menyimpan perubahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurveiDto> call, Throwable t) {
                Toast.makeText(EditSurveiActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Intent untuk membuka MainActivity atau halaman lainnya
                Intent intent = new Intent(this, ListSurveiAdminActivity.class);
                intent.putExtra("TOKEN", token);
                intent.putExtra("NIKNIP", nikNip);
                startActivity(intent);
                finish(); // Jika ingin menutup aktivitas saat ini setelah navigasi
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
