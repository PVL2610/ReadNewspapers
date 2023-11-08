package com.example.readnewspapes;

import android.graphics.Bitmap;

public class ItemTinTuc {
    private Bitmap hinh;
    private String tieude;
    private String mota;
    private String ngay;

    public ItemTinTuc(Bitmap hinh, String tieude, String mota, String ngay) {
        this.hinh = hinh;
        this.tieude = tieude;
        this.mota = mota;
        this.ngay = ngay;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
