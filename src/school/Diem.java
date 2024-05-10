/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school;

/**
 *
 * @author ADMIN
 */
public class Diem {
    private String maHS, maMon, hocKy, namHoc;
    private float diemMieng, diem15Phut, diem1Tiet, giuaKy, cuoiKy, diemTB;
    
    public Diem() {
        this.maHS = "";
        this.maMon = "";
        this.hocKy = "";
        this.namHoc = "";
        this.diemMieng = 0.0f;
        this.diem15Phut = 0.0f;
        this.diem1Tiet = 0.0f;
        this.giuaKy = 0.0f;
        this.cuoiKy = 0.0f;
        this.diemTB = 0.0f;
    }
    
    public Diem(String maHS, String maMon, String hocKy, String namHoc, float diemMieng, float diem15Phut, float diem1Tiet, float giuaKy, float cuoiKy, float diemTB) {
        this.maHS = maHS;
        this.maMon = maMon;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.diemMieng = diemMieng;
        this.diem15Phut = diem15Phut;
        this.diem1Tiet = diem1Tiet;
        this.giuaKy = giuaKy;
        this.cuoiKy = cuoiKy;
        this.diemTB = diemTB;
    }
    
    public void capNhatDiem(String loaiDiem, float soDiem) {
        
        switch (loaiDiem) {
            case "Mieng":
                diemMieng = soDiem;
                break;
            case "15Phut":
                diem15Phut = soDiem;
                break;
            case "1Tiet":
                diem1Tiet = soDiem;
                break;
            case "GiuaKy":
                giuaKy = soDiem;
                break;
            case "CuoiKy":
                cuoiKy = soDiem;
                break;
            default:
                System.out.println("Loi khong co loai diem!!!!");
        }
        
        diemTB = (diemMieng + diem15Phut + diem1Tiet * 2 + giuaKy * 2 + cuoiKy * 3) / 9;
    }
    
    public String getMaHS() {
        return maHS;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }

    public float getDiemMieng() {
        return diemMieng;
    }

    public void setDiemMieng(float diemMieng) {
        this.diemMieng = diemMieng;
    }

    public float getDiem15Phut() {
        return diem15Phut;
    }

    public void setDiem15Phut(float diem15Phut) {
        this.diem15Phut = diem15Phut;
    }

    public float getDiem1Tiet() {
        return diem1Tiet;
    }

    public void setDiem1Tiet(float diem1Tiet) {
        this.diem1Tiet = diem1Tiet;
    }

    public float getGiuaKy() {
        return giuaKy;
    }

    public void setGiuaKy(float giuaKy) {
        this.giuaKy = giuaKy;
    }

    public float getCuoiKy() {
        return cuoiKy;
    }

    public void setCuoiKy(float cuoiKy) {
        this.cuoiKy = cuoiKy;
    }

    public float getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(float diemTB) {
        this.diemTB = diemTB;
    }
    
    
}
