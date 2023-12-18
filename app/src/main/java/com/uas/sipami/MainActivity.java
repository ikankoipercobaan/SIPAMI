// src/main/java/com/uas/sipami/MainActivity.java
package com.uas.sipami;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.uas.sipami.Adapter.ImageSliderAdapter;
import com.uas.sipami.Controller.LoginActivity;
import com.uas.sipami.Controller.RegisterActivity_mitra;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(imageSliderAdapter);

// Implementasi onClickListener untuk tombol Login
        findViewById(R.id.btnLoginOpening).setOnClickListener(v -> {
            // Pindah ke halaman LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

// Implementasi onClickListener untuk tombol Register
        findViewById(R.id.btnRegisterOpening).setOnClickListener(v -> {
            // Pindah ke halaman RegisterActivity
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity_mitra.class);
            startActivity(registerIntent);
        });
    }
}
