package com.uas.sipami.Model;

import java.io.Serializable;
import java.util.Date;

public class SurveiDto implements Serializable {
    private Long id;
    private String namaSurvei;
    private String kodeSurvei;
    private String tanggalMulai;
    private String tanggalSelesai;
    public SurveiDto( String namaSurvei, String kodeSurvei, String tanggalMulai, String tanggalSelesai) {

        this.namaSurvei = namaSurvei;
        this.kodeSurvei = kodeSurvei;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
    }
    public SurveiDto( ) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaSurvei() {
        return namaSurvei;
    }

    public void setNamaSurvei(String namaSurvei) {
        this.namaSurvei = namaSurvei;
    }

    public String getKodeSurvei() {
        return kodeSurvei;
    }

    public void setKodeSurvei(String kodeSurvei) {
        this.kodeSurvei = kodeSurvei;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }
}

