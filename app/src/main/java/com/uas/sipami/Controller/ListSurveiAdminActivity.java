package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Adapter.SurveiAdminAdapter;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSurveiAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SurveiAdminAdapter surveiAdapter;
    private ApiService apiService;
    private String nikNip;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_daftar_survei_admin);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerViewSurvei);

        // Set layout manager untuk RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Buat adapter dan set adapter pada RecyclerView
        surveiAdapter = new SurveiAdminAdapter(new ArrayList<>(), new SurveiAdminAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SurveiDto survei) {
                // Panggil metode untuk menangani klik pada item
                handleSurveiItemClick(survei);
            }
        });

        recyclerView.setAdapter(surveiAdapter);

        // Ambil data survei dari API
        fetchDataFromApi();

        // Ambil data dari Intent
        Intent intent = getIntent();
        if (intent != null) {
            // Periksa apakah ekstra dengan kunci "TOKEN" ada
            if (intent.hasExtra("TOKEN")) {
                token = intent.getStringExtra("TOKEN");
            }

            // Periksa apakah ekstra dengan kunci "NIKNIP" ada
            if (intent.hasExtra("NIKNIP")) {
                nikNip = intent.getStringExtra("NIKNIP");
            }
        } else {
            // Handle jika intent null
            Toast.makeText(this, "Intent null", Toast.LENGTH_SHORT).show();
        }
        FloatingActionButton fabAddSurvei = findViewById(R.id.fabAddSurvei);
        fabAddSurvei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update this line to match the correct class name
//                Toast.makeText(this, "On clik jalan", Toast.LENGTH_SHORT).show();
//                Toast.makeText(ListSurveiAdminActivity.this, "ini bisa klik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListSurveiAdminActivity.this, CreateSurveiActivity.class);
                intent.putExtra("TOKEN", token);
                intent.putExtra("NIKNIP", nikNip);
                startActivity(intent);
            }
        });



    }
    private void fetchDataFromApi() {
        apiService = ApiClient.createService(ApiService.class);



        Call<List<SurveiDto>> call = apiService.getSurveyAdmin(token);
        call.enqueue(new Callback<List<SurveiDto>>() {
            @Override
            public void onResponse(Call<List<SurveiDto>> call, Response<List<SurveiDto>> response) {
                if (response.isSuccessful()) {
                    List<SurveiDto> surveyList = response.body();
                    // Perbarui adapter yang sudah ada dengan data baru
                    surveiAdapter.setData(surveyList);
                    surveiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListSurveiAdminActivity.this, "Gagal mengambil data survei", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SurveiDto>> call, Throwable t) {
                Toast.makeText(ListSurveiAdminActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onEditProfileClicked(View view) {
        // Ketika ikon edit profil di-klik, buka halaman edit profil
        Intent editProfilIntent = new Intent(ListSurveiAdminActivity.this, EditProfilActivity.class);

        // Sertakan data yang diperlukan ke dalam intent
        editProfilIntent.putExtra("TOKEN", token);
//        Toast.makeText(ListSurveiAdminActivity.this,"list survei admin token:" +token, Toast.LENGTH_SHORT).show();

        editProfilIntent.putExtra("NIKNIP", nikNip);

        startActivity(editProfilIntent);
    }


    // Metode untuk menangani klik pada item survei
    private void handleSurveiItemClick(SurveiDto survei) {
        Intent intent = new Intent(this, ListProfilActivity.class);
        intent.putExtra("KODE_SURVEI", survei.getKodeSurvei());
        intent.putExtra("TOKEN", token);
        intent.putExtra("NIKNIP", nikNip);
        startActivity(intent);
    }
}
