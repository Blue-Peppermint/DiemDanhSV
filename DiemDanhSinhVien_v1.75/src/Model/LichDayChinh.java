/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author chuon
 */
public class LichDayChinh{
    private String maLopHoc;
    private int thuHoc;
    private int ca;
    private Date ngayBD;
    private Date ngayKT;
    private String tenNhom;
    
    public LichDayChinh() {
        maLopHoc = null;      
        thuHoc = -1;
        ca = -1 ;
        ngayBD = null;
        ngayKT = null;
        tenNhom = null; 
    }

    public LichDayChinh(String maLopHoc, int thuHoc, int ca, Date ngayBD, Date ngayKT, String tenNhom) {
        this.maLopHoc = maLopHoc;
        this.thuHoc = thuHoc;
        this.ca = ca;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.tenNhom = tenNhom;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public int getThuHoc() {
        return thuHoc;
    }

    public void setThuHoc(int thuHoc) {
        this.thuHoc = thuHoc;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    @Override
    public String toString() {
        return "LichDayChinh{" + "maLopHoc=" + maLopHoc + ", thuHoc=" + thuHoc + ", ca=" + ca + ", ngayBD=" + ngayBD + ", ngayKT=" + ngayKT + ", tenNhom=" + tenNhom + '}';
    }
    
}
