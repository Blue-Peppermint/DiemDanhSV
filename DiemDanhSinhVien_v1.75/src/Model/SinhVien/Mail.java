/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import java.util.Date;

/**
 *
 * @author chuon
 */
public class Mail {

    private String maLopHoc;
    private String maGV;
    private Date ngayGui;
    private String noiDung;

    public Mail() {
        this.maLopHoc = "";
        this.ngayGui = null;
        this.noiDung = "";
        this.maGV = "";
    }

    public Mail(String maLopHoc, String maGV, Date ngayGui, String noiDung) {
        this.maLopHoc = maLopHoc;
        this.maGV = maGV;
        this.ngayGui = ngayGui;
        this.noiDung = noiDung;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public Date getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(Date ngayGui) {
        this.ngayGui = ngayGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    @Override
    public String toString() {
        return "Mail{" + "maLopHoc=" + maLopHoc + ", maGV=" + maGV + ", ngayGui=" + ngayGui + ", noiDung=" + noiDung + '}';
    }


}
