/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

/**
 *
 * @author chuon
 */
public class GVDayHoc {

    private String maLopHoc;
    private String tenNhom;
    private String maGV;

    public GVDayHoc() {
        maLopHoc = "";
        tenNhom = "";
        maGV = "";
    }

    public GVDayHoc(String maLopHoc, String tenNhom, String maGV) {
        this.maLopHoc = maLopHoc;
        this.tenNhom = tenNhom;
        this.maGV = maGV;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    @Override
    public String toString() {
        return "GVDayHoc{" + "maLopHoc=" + maLopHoc + ", tenNhom=" + tenNhom + ", maGV=" + maGV + '}';
    }

    
}
