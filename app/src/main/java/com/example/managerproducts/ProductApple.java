package com.example.managerproducts;

public class ProductApple {
    public int MaSP;
    public String TenSP;
    public String DungLuongSP;
    public String GiaSP;
    public byte[] imageSP;

    public ProductApple(int maSP, String tenSP, String dungLuongSP, String giaSP, byte[] imageSP) {
        MaSP = maSP;
        TenSP = tenSP;
        DungLuongSP = dungLuongSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
    }
}
