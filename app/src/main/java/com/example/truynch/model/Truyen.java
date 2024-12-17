package com.example.truynch.model;

public class Truyen {
    private String idTruyen;
    private String tenTruyen;
    private String noidungsoluoc;
    private String tacGia;
    private String trangThai;
    private String hinhAnh;
    private int id_tk;

    // Constructor
    public Truyen(String idTruyen, String tenTruyen,String noidungsoluoc, String tacGia, String trangThai, String hinhAnh,int id_tk) {
        this.idTruyen = idTruyen;
        this.tenTruyen = tenTruyen;
        this.noidungsoluoc = noidungsoluoc;
        this.tacGia = tacGia;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.id_tk = id_tk;
    }

    // Getter và Setter cho các thuộc tính
    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getNoidungsoluoc() {
        return noidungsoluoc;
    }

    public void setNoidungsoluoc(String noidungsoluoc) {
        this.noidungsoluoc = noidungsoluoc;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getId_tk() {
        return id_tk;
    }

    public void setId_tk(int id_tk) {
        this.id_tk = id_tk;
    }
}
