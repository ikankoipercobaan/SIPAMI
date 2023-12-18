package com.uas.sipami.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.R;
import com.uas.sipami.Model.LoginRequest;
import com.uas.sipami.Model.LoginResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNikNip;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textViewRegister = findViewById(R.id.textViewRegister);
        editTextNikNip = findViewById(R.id.editTextNikNip);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        apiService = ApiClient.createService(ApiService.class);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign up click
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity_mitra.class);
                startActivity(intentRegister);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil nilai username dan password dari EditText
                String nikNip = editTextNikNip.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Memeriksa apakah username dan password kosong
                if (nikNip.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    // Kirim permintaan login ke server menggunakan nilai username dan password
                    performLogin(nikNip, password);
                }
            }
        });
    }

    private void performLogin(String email, String password) {
        // Membuat objek AuthRequest dengan username dan password
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Mengirim permintaan login ke server menggunakan Retrofit
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Login berhasil
                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    LoginResponse loginResponse = response.body();
                    String accessToken = loginResponse.getAccessToken();
                    String nikNip = loginResponse.getNikNip();
//                    Toast.makeText(LoginActivity.this, accessToken, Toast.LENGTH_SHORT).show();

                    List<String> roles = loginResponse.getRoles();
                    if (roles.contains("ROLE_ADMIN")) {
//                        Toast.makeText(LoginActivity.this, "Role : Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ListSurveiAdminActivity.class);
                        intent.putExtra("TOKEN", accessToken);
                        intent.putExtra("NIKNIP", nikNip);
                        startActivity(intent);

                    } else if (roles.contains("ROLE_USER")) {
//                        Toast.makeText(LoginActivity.this, "Role : User", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ListSurveiMitraActivity.class);
                        intent.putExtra("TOKEN", accessToken);
                        intent.putExtra("NIKNIP", nikNip);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Role salah : "+roles, Toast.LENGTH_SHORT).show();

                    }
                    finish(); // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
                } else {
                    // Login gagal
                    Toast.makeText(LoginActivity.this, "Login gagal. Periksa kembali email dan password.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle kegagalan jaringan
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
