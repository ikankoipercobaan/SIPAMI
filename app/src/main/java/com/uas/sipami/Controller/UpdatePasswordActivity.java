package com.uas.sipami.Controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.UpdatePasswordResponse;
import com.uas.sipami.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText editTextPasswordLama;
    private EditText editTextPasswordBaru;
    private EditText editTextPasswordKonfirm;
    private ImageButton buttonEdit;
    private ApiService apiService;
    private String token;
    private String nikNip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pw);

        editTextPasswordLama = findViewById(R.id.editTextPasswordLama);
        editTextPasswordBaru = findViewById(R.id.editTextPasswordBaru);
        editTextPasswordKonfirm = findViewById(R.id.editTextPasswordKonfirm);
        buttonEdit = findViewById(R.id.imageButton);
        apiService = ApiClient.createService(ApiService.class);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordLama = editTextPasswordLama.getText().toString().trim();
                String passwordBaru = editTextPasswordBaru.getText().toString().trim();
                String passwordKonfirm = editTextPasswordKonfirm.getText().toString().trim();

                token = getIntent().getStringExtra("TOKEN");
                nikNip = getIntent().getStringExtra("NIKNIP");

                if (passwordLama.isEmpty() || passwordBaru.isEmpty() || passwordKonfirm.isEmpty()) {
                    Toast.makeText(UpdatePasswordActivity.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                } else if (!passwordBaru.equals(passwordKonfirm)) {
                    Toast.makeText(UpdatePasswordActivity.this, "Password baru dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmationDialog(token, passwordLama, passwordBaru, nikNip);
                    // Panggil fungsi untuk mengirim data ke server atau melakukan aksi yang sesuai
                }
            }
        });
    }

    // Metode ini harus diimplementasikan sesuai kebutuhan aplikasi Anda
    private void performUpdatePassword(String token, String currentPassword, String newPassword, String nikNip) {
        ApiService apiService = ApiClient.createService(ApiService.class);

        Call<UpdatePasswordResponse> call = apiService.updatePassword(token, nikNip, currentPassword, newPassword);

        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdatePasswordResponse updatePasswordResponse = response.body();
                    // Proses response sesuai kebutuhan
                    String message = updatePasswordResponse.getMessage();
                    Toast.makeText(UpdatePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error response
                    Toast.makeText(UpdatePasswordActivity.this, "Failed to update password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(UpdatePasswordActivity.this, "Error updating password: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Panggil metode onBackPressed() ketika tombol kembali ditekan
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog(String token, String currentPassword, String newPassword, String nikNip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin melakukan update password?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Panggil fungsi untuk mengirim data ke server atau melakukan aksi yang sesuai
                    performUpdatePassword(token, currentPassword, newPassword, nikNip);
                })
                .setNegativeButton("Tidak", (dialog, which) -> {
                    // Do nothing, user canceled the operation
                })
                .show();
    }
}
