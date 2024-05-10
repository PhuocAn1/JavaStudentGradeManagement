/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school;

/**
 *
 * @author ADMIN
 */
public class GiaoVien {
    private String MaGV, HoTen, MaTK, GioiTinh;
    private int NamSinh;
    
    public GiaoVien() {
        
    }
    
    public GiaoVien(String MaGV, String HoTen, String MaTK, String GioiTinh, int NamSinh) {
        this.MaGV = MaGV;
        this.HoTen = HoTen;
        this.MaTK = MaTK;
        this.GioiTinh = GioiTinh;
        this.NamSinh = NamSinh;
    }

    public String getMaGV() {
        return MaGV;
    }

    public void setMaGV(String MaGV) {
        this.MaGV = MaGV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String MaTK) {
        this.MaTK = MaTK;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public int getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(int NamSinh) {
        this.NamSinh = NamSinh;
    }
    
    
}
