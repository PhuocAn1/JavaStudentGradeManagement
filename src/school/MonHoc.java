/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school;

/**
 *
 * @author ADMIN
 */
public class MonHoc {
    private String MaMon;
    private String TenMon;

    public MonHoc(String MaMon, String TenMon) {
        this.MaMon = MaMon;
        this.TenMon = TenMon;
    }
    
    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    @Override
    public String toString() {
        return TenMon;
    }
     
}
