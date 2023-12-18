package com.uas.sipami.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Adapter.SurveiMitraAdapter;
import com.uas.sipami.Controller.DetailSurveiActivity;
import com.uas.sipami.Controller.EditProfilActivity;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSurveiMitraActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SurveiMitraAdapter surveiAdapter;
    private ApiService apiService;
    private String nikNip;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_daftar_survei_mitra);

        recyclerView = findViewById(R.id.recyclerViewSurvei);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SurveiItemClickListener itemClickListener = new SurveiItemClickListener();
        surveiAdapter = new SurveiMitraAdapter(new ArrayList<>(), itemClickListener, this);

        recyclerView.setAdapter(surveiAdapter);

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        apiService = ApiClient.createService(ApiService.class);

        Call<List<SurveiDto>> call = apiService.getSurveyAdmin(token);
        call.enqueue(new Callback<List<SurveiDto>>() {
            @Override
            public void onResponse(Call<List<SurveiDto>> call, Response<List<SurveiDto>> response) {
                if (response.isSuccessful()) {
                    List<SurveiDto> surveyList = response.body();
                    token = getIntent().getStringExtra("TOKEN");
                    nikNip = getIntent().getStringExtra("NIKNIP");
                    surveiAdapter.setData(surveyList);
                    surveiAdapter.setTokenAndNikNip(token, nikNip);
                    surveiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListSurveiMitraActivity.this, "Gagal mengambil data survei", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SurveiDto>> call, Throwable t) {
                Toast.makeText(ListSurveiMitraActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Metode yang akan dipanggil saat ikon edit profil di-klik
    public void onEditProfileClicked(View view) {
        // Ketika ikon edit profil di-klik, buka halaman edit profil
        Intent editProfilIntent = new Intent(ListSurveiMitraActivity.this, EditProfilActivity.class);

        // Sertakan data yang diperlukan ke dalam intent
        editProfilIntent.putExtra("TOKEN", token);
        editProfilIntent.putExtra("NIKNIP", nikNip);

        startActivity(editProfilIntent);
    }


    private class SurveiItemClickListener implements SurveiMitraAdapter.OnItemClickListener {
        @Override
        public void onItemClick(SurveiDto survei) {
            Intent intent = new Intent(ListSurveiMitraActivity.this, DetailSurveiActivity.class);
            intent.putExtra("KODE_SURVEI", survei.getKodeSurvei());
            startActivity(intent);
        }
    }
}
