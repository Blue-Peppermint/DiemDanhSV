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
public class InfoTeacher {
    private String maGV;
    private String hoTen;

    public InfoTeacher() {
        this.maGV = "";
        this.hoTen = "";
    }

    public InfoTeacher(String maGV, String hoTen) {
        this.maGV = maGV;
        this.hoTen = hoTen;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Override
    public String toString() {
        return "InfoTeacher{" + "maGV=" + maGV + ", hoTen=" + hoTen + '}';
    }
    
}
