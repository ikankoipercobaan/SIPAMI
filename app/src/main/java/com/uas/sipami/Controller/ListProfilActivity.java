package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.Adapter.ProfilAdapter;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R; // Replace with your actual R class import
import com.uas.sipami.API.ApiService; // Replace with your actual Retrofit service import

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// ListProfilActivity.java
public class ListProfilActivity extends AppCompatActivity implements ProfilAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private ProfilAdapter profilAdapter;
    private ApiService apiService;
    private TextView textViewNamaSurvei, textViewKodeSurvei, textViewTanggalMulai, getTextViewTanggalSelesai;
    private Button buttonEdit;
    private String token; // Token yang akan Anda gunakan
    private String nikNip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profil);

        // Get token and nikNip from intent
        token = getIntent().getStringExtra("TOKEN");
        nikNip = getIntent().getStringExtra("NIKNIP");
        String kodeSurvei = getIntent().getStringExtra("KODE_SURVEI");

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewProfil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi ProfilAdapter dengan token
        profilAdapter = new ProfilAdapter(this, this, token,kodeSurvei);
        recyclerView.setAdapter(profilAdapter);

        // Fetch data from the server
        fetchDataFromServer(token);
        fetchDataFromServerSurvei( token, kodeSurvei);

// ...

// ...

// Inisialisasi TextView
        textViewNamaSurvei = findViewById(R.id.nameTextView);
        textViewKodeSurvei = findViewById(R.id.kodeTextView);
        textViewTanggalMulai = findViewById(R.id.startDateTextView);
        getTextViewTanggalSelesai = findViewById(R.id.endDateTextView);

// Inisialisasi Button
        Button buttonEditSurvei = findViewById(R.id.buttonEditSurvei);

        buttonEditSurvei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kode untuk menangani klik tombol di sini
                Intent intent = new Intent(ListProfilActivity.this, EditSurveiActivity.class);
                        intent.putExtra("TOKEN", getIntent().getStringExtra("TOKEN"));
                        intent.putExtra("NIKNIP", getIntent().getStringExtra("NIKNIP"));
                        intent.putExtra("KODE_SURVEI", getIntent().getStringExtra("KODE_SURVEI"));
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemClick(int position) {
        // Handle item click here
//        Toast.makeText(this, "Item clicked at position " + position, Toast.LENGTH_SHORT).show();
    }
    private void fetchDataFromServerSurvei(String token, String kodeSurvei) {
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
//                    Toast.makeText(ListProfilActivity.this, "berhasil", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ListProfilActivity.this, "Gagal mengambil data survei", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurveiDto> call, Throwable t) {
                Toast.makeText(ListProfilActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchDataFromServer(String token) {
        apiService = ApiClient.createService(ApiService.class);
        Call<List<RegisterRequest>> call = apiService.getAllUser(token);

        call.enqueue(new Callback<List<RegisterRequest>>() {
            @Override
            public void onResponse(Call<List<RegisterRequest>> call, Response<List<RegisterRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(ListProfilActivity.this, "Berhasil Mengambil data Profil", Toast.LENGTH_SHORT).show();
                    profilAdapter.setProfilList(response.body());
                } else {
                    int statusCode = response.code();
//                    Toast.makeText(ListProfilActivity.this, "Gagal mengambil data survei. Kode status: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RegisterRequest>> call, Throwable t) {
                Toast.makeText(ListProfilActivity.this, "Gagal mengambil data survei :(: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ListProfilActivity", "Error fetching data from server", t);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbarPantau);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Menambahkan listener untuk tombol "Edit"
//        buttonEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("ListProfilActivity", "Button Edit clicked");
//                // Atau gunakan Toast
//                Toast.makeText(ListProfilActivity.this, "Button Edit clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void startEditSurveiActivity() {
//        Intent intent = new Intent(this, EditSurveiActivity.class);
//        // Mengirimkan data yang dibutuhkan ke EditSurveiActivity
//        intent.putExtra("TOKEN", getIntent().getStringExtra("TOKEN"));
//        intent.putExtra("NIKNIP", getIntent().getStringExtra("NIKNIP"));
//        intent.putExtra("KODE_SURVEI", getIntent().getStringExtra("KODE_SURVEI"));
//        startActivity(intent);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Aksi yang diambil ketika tombol kembali ditekan

                // Membuat Intent untuk mentransfer data ke Activity lain
                Intent intent = new Intent(this, ListSurveiAdminActivity.class);

                // Menambahkan token dan NIK/NIP ke Intent
                intent.putExtra("TOKEN", token);
                intent.putExtra("NIKNIP", nikNip);

                // Memulai Activity tujuan dengan Intent yang telah dikonfigurasi
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

