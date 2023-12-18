package com.uas.sipami.Model;

import com.google.gson.annotations.SerializedName;


import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("name")
    private String name;

    @SerializedName("nikNip") // Mengganti 'email' dengan 'nikNip'
    private String nikNip;

    @SerializedName("password")
    private String password;

    @SerializedName("jenisKelamin") // Menambahkan atribut 'jenisKelamin'
    private String jenisKelamin;

    @SerializedName("pekerjaan") // Menambahkan atribut 'pekerjaan'
    private String pekerjaan;

    @SerializedName("email") // Menambahkan atribut 'email'
    private String email;

    public RegisterRequest(String name, String nikNip, String password, String jenisKelamin, String pekerjaan, String email) {
        this.name = name;
        this.nikNip = nikNip;
        this.password = password;
        this.jenisKelamin = jenisKelamin;
        this.pekerjaan = pekerjaan;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNikNip() {
        return nikNip;
    }

    public void setNikNip(String nikNip) {
        this.nikNip = nikNip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

