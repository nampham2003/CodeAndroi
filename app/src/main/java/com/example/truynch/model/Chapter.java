package com.example.truynch.model;

public class Chapter {
    private String idTruyen;
    private String chuong;
    private String noiDung;

    // Constructor
    public Chapter(String idTruyen, String chuong, String noiDung) {
        this.idTruyen = idTruyen;
        this.chuong = chuong;
        this.noiDung = noiDung;
    }

    // Getter for 'chuong'
    public String getChuong() {
        return chuong;
    }

    // Other getters
    public String getIdTruyen() {
        return idTruyen;
    }

    public String getNoiDung() {
        return noiDung;
    }
}
