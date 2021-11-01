package com.example.managerproducts;

public class ProductApple {
    public int MaSP;
    public String TenSP;
    public String GiaSP;
    public byte[] imageSP;

    public ProductApple(int maSP, String tenSP, String giaSP, byte[] imageSP) {
        MaSP = maSP;
        TenSP = tenSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
    }
}
