package com.uas.sipami.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.Model.RegisterResponse;
import com.uas.sipami.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity_mitra extends AppCompatActivity {
    private EditText nameEditText;
    private EditText nikNipEditText;
    private RadioGroup jenisKelaminRadioGroup;
    private EditText pekerjaanEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button buttonRegister;
    private ApiService apiService;
    private ImageView backLoginImageView; // Pindahkan deklarasi ke sini

    private String jenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mitra);

        // Inisialisasi komponen-komponen
        nameEditText = findViewById(R.id.editTextNama);
        nikNipEditText = findViewById(R.id.editTextNikNip);
        jenisKelaminRadioGroup = findViewById(R.id.radioGroupJenisKelamin);
        pekerjaanEditText = findViewById(R.id.editTextPekerjaan);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        apiService = ApiClient.createService(ApiService.class);
        backLoginImageView = findViewById(R.id.backlogin); // Inisialisasi di sini

        jenisKelaminRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Mendapatkan RadioButton yang dipilih
                RadioButton radioButton = findViewById(checkedId);

                // Pastikan radioButton tidak null sebelum mencoba mendapatkan nilai
                if (radioButton != null) {
                    // Mendapatkan nilai dari RadioButton yang dipilih
                    jenisKelamin = radioButton.getText().toString();

                    // Lakukan sesuatu dengan nilai jenis kelamin, misalnya tampilkan atau simpan ke variabel lain
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nilai dari setiap EditText
                String name = nameEditText.getText().toString().trim();
                String nikNip = nikNipEditText.getText().toString().trim();
                String pekerjaan = pekerjaanEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Memeriksa apakah ada yang kosong
                if (TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(nikNip) ||
                        TextUtils.isEmpty(jenisKelamin) ||
                        TextUtils.isEmpty(pekerjaan) ||
                        TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password)) {
                    // Jika ada yang kosong, tampilkan pesan untuk melengkapi data
                    Toast.makeText(RegisterActivity_mitra.this, "Lengkapi semua data !!!", Toast.LENGTH_SHORT).show();
                } else {
                    // Semua data telah diisi, panggil fungsi performRegistration
                    performRegistration(name, nikNip, jenisKelamin, pekerjaan, email, password);
                }
            }
        });

        backLoginImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke halaman LoginActivity
                Intent loginIntent = new Intent(RegisterActivity_mitra.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Optional: Menutup aktivitas saat kembali ke halaman login
            }
        });
    }

    private void performRegistration(String name, String nikNip, String jenisKelamin, String pekerjaan, String email, String password) {

        RegisterRequest registerRequest = new RegisterRequest(name, nikNip, password, jenisKelamin, pekerjaan, email);


        Call<RegisterResponse> call = apiService.register(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    // Registrasi berhasil
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(RegisterActivity_mitra.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    Log.d("RegisterResponse", registerResponse.toString());


                    // Setelah berhasil registrasi, mungkin ingin melakukan otomatis login atau ke halaman lain
                    startActivity(new Intent(RegisterActivity_mitra.this, LoginActivity.class));
                    finish(); // Menutup RegistrationActivity agar tidak bisa kembali dengan tombol back
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("RegisterError", errorBody);
                        Toast.makeText(RegisterActivity_mitra.this, "Registrasi gagal. Periksa kembali data registrasi.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                // Handle kegagalan jaringan
                Toast.makeText(RegisterActivity_mitra.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
