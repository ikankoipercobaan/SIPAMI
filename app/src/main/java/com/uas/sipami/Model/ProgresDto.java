package com.uas.sipami.Model;

import java.util.Date;

public class ProgresDto {

    private Long id;
    private String nikNip;
    private String kodeSurvei;
    private String tanggal;
    private Integer jumlahCacah;

    // Konstruktor default
    public ProgresDto() {
    }

    // Konstruktor dengan parameter
    public ProgresDto(String nikNip, String kodeSurvei, String tanggal, Integer jumlahCacah) {
        this.nikNip = nikNip;
        this.kodeSurvei = kodeSurvei;
        this.tanggal = tanggal;
        this.jumlahCacah = jumlahCacah;
    }

    // Getter dan setter untuk id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter dan setter untuk nikNip
    public String getNikNip() {
        return nikNip;
    }

    public void setNikNip(String nikNip) {
        this.nikNip = nikNip;
    }

    // Getter dan setter untuk kodeSurvei
    public String getKodeSurvei() {
        return kodeSurvei;
    }

    public void setKodeSurvei(String kodeSurvei) {
        this.kodeSurvei = kodeSurvei;
    }

    // Getter dan setter untuk tanggal
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    // Getter dan setter untuk jumlahCacah
    public Integer getJumlahCacah() {
        return jumlahCacah;
    }

    public void setJumlahCacah(Integer jumlahCacah) {
        this.jumlahCacah = jumlahCacah;
    }
}
