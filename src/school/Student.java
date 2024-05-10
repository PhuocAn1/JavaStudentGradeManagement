/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school;

/**
 *
 * @author ADMIN
 */
public class Student {
    private String maHS, hanhKiem, hoTen, maTK, maLop, gioiTinh;
    private int namSinh;
    
    public Student() {
        maHS = "";
        hanhKiem = "";
        hoTen = "";
        maTK = "";
        maLop = "";
        namSinh = 2000;
        gioiTinh = "Nam";
    }
    
    public Student(String maHS, String hoTen, int namSinh, String hanhKiem, String maTK, String maLop, String gioiTinh) {
        this.maHS = maHS;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.hanhKiem = hanhKiem;
        this.maTK = maTK;
        this.maLop = maLop;
        this.gioiTinh = gioiTinh;
    }

    public String getMaHS() {
        return maHS;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public String getHanhKiem() {
        return hanhKiem;
    }

    public void setHanhKiem(String hanhKiem) {
        this.hanhKiem = hanhKiem;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaTK() {
        return maTK;
    }
    
    public String getGioiTInh() {
        return gioiTinh;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }
    
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
