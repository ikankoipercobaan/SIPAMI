package com.uas.sipami.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.MainActivity;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AlertDialog;
public class EditProfilActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextPekerjaan;
    private EditText editTextEmail;
    private RegisterRequest userResponse;
    private String nikNip;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        Toolbar toolbar = findViewById(R.id.toolbar4);

        Intent intent = getIntent();
        nikNip = intent.getStringExtra("NIKNIP");
        token = intent.getStringExtra("TOKEN");

//        Toast.makeText(this, "niknip :" + nikNip, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "token :" + token, Toast.LENGTH_SHORT).show();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        EditText editTextNikNip = findViewById(R.id.editTextNikNip);
        editTextNama = findViewById(R.id.editTextNama);
        editTextPekerjaan = findViewById(R.id.editTextPekerjaan);
        EditText editTextJenisKelamin = findViewById(R.id.editTextjenisKelamin);
        editTextEmail = findViewById(R.id.editTextEmail);

        ApiService apiService = ApiClient.createService(ApiService.class);
        Call<RegisterRequest> call = apiService.getUserByNiknip(token, nikNip);

        call.enqueue(new Callback<RegisterRequest>() {
            @Override
            public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
//                 Toast.makeText(EditProfilActivity.this, nikNip , Toast.LENGTH_SHORT).show();

                if (response.isSuccessful() && response.body() != null) {
                    userResponse = response.body();
                    editTextNikNip.setText(userResponse.getNikNip());
                    editTextNama.setText(userResponse.getName());
                    editTextPekerjaan.setText(userResponse.getPekerjaan());
                    editTextEmail.setText(userResponse.getEmail());
                    editTextJenisKelamin.setText(userResponse.getJenisKelamin());

                    int statusCode = response.code();
                    // Toast.makeText(EditProfilActivity.this, "berhasil mengambil data pengguna. Kode status: " + statusCode, Toast.LENGTH_SHORT).show();

                } else {
                    int statusCode = response.code();
                    Toast.makeText(EditProfilActivity.this, "Gagal mengambil data pengguna. Kode status: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterRequest> call, Throwable t) {
                Toast.makeText(EditProfilActivity.this, "Gagal mengambil data pengguna " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("EditProfilActivity", "Error fetching user data from server", t);
            }

        });

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> onUpdateButtonClick());
        ImageButton imageButtonPassword = findViewById(R.id.imageButton_password);
        imageButtonPassword.setOnClickListener(view -> onPasswordButtonClick());

        ImageView imageViewLogout = findViewById(R.id.imageViewLogout);
        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementasi aksi logout dan navigasi ke halaman pembukaan
                showLogoutConfirmationDialog();
            }
        });


    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Logout");
        builder.setMessage("Apakah Anda yakin ingin logout?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Jika pengguna memilih "Ya", navigasi ke halaman pembukaan
                navigateToOpeningPage();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Jika pengguna memilih "Tidak", tutup dialog tanpa melakukan apa pun
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void navigateToOpeningPage() {
        // Implementasikan navigasi ke halaman pembukaan di sini
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional, untuk menutup activity saat ini jika diperlukan
    }

    private void onUpdateButtonClick() {
        // Validate fields
        if (TextUtils.isEmpty(editTextNama.getText().toString())
                || TextUtils.isEmpty(editTextPekerjaan.getText().toString())
                || TextUtils.isEmpty(editTextEmail.getText().toString())) {
            Toast.makeText(EditProfilActivity.this, "Lengkapi Semua Data", Toast.LENGTH_SHORT).show();
        } else {
            // Fields are not empty, proceed with the update
            updateUser();
        }
    }

    private void updateUser() {
        // Get the updated values from EditText fields
        String updatedNama = editTextNama.getText().toString();
        String updatedPekerjaan = editTextPekerjaan.getText().toString();
        String updatedEmail = editTextEmail.getText().toString();

        // Update userResponse object
        userResponse.setName(updatedNama);
        userResponse.setPekerjaan(updatedPekerjaan);
        userResponse.setEmail(updatedEmail);

        // Update user data on the server
        updateUserData(userResponse);
    }

    private void updateUserData(RegisterRequest userUpdated) {
        ApiService apiService = ApiClient.createService(ApiService.class);
        Call<RegisterRequest> call = apiService.updateUser(token, nikNip, userUpdated);

        call.enqueue(new Callback<RegisterRequest>() {
            @Override
            public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the successful response, e.g., show a success message
                    Toast.makeText(EditProfilActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle the error response, e.g., show an error message
                    Toast.makeText(EditProfilActivity.this, "Failed to update data. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterRequest> call, Throwable t) {
                // Handle the failure, e.g., show an error message
                Toast.makeText(EditProfilActivity.this, "Error updating data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Buat objek Intent baru
            Intent resultIntent = new Intent();

            // Tambahkan data nikNip dan token ke dalam Intent
            resultIntent.putExtra("NIKNIP_RESULT", nikNip);
            resultIntent.putExtra("TOKEN_RESULT", token);

            // Setel hasil dan selesaikan aktivitas
            setResult(RESULT_OK, resultIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onPasswordButtonClick() {
        // Tambahkan logika yang diinginkan saat tombol password diklik
//        Toast.makeText(this, "Password button clicked", Toast.LENGTH_SHORT).show();

        // Contoh: Pindah ke UpdatePasswordActivity
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        intent.putExtra("NIKNIP", nikNip);
        intent.putExtra("TOKEN", token);
        startActivity(intent);
    }
}
