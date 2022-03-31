/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import java.util.ArrayList;

/**
 *
 * @author chuon
 */
public class LopHoc {
    private String maLopHoc;
    private int hocKy;
    private int namHoc;
    private String tenMonHoc;
    private String lop;
    private ArrayList<Integer> idVaiTro;
    
    public LopHoc() {
        this.maLopHoc = "";
        this.hocKy = -1;
        this.namHoc = -1;
        this.tenMonHoc = "";
        this.lop = "";
        idVaiTro = null;
    }

    public LopHoc(String maLopHoc, int hocKy, int namHoc, String tenMonHoc, String lop, ArrayList<Integer> idVaiTro) {
        this.maLopHoc = maLopHoc;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.tenMonHoc = tenMonHoc;
        this.lop = lop;
        this.idVaiTro = idVaiTro;
    }

    public LopHoc(String maLopHoc, int hocKy, int namHoc, String tenMonHoc, String lop) {
        this.maLopHoc = maLopHoc;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.tenMonHoc = tenMonHoc;
        this.lop = lop;
    }

      
    
    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public int getHocKy() {
        return hocKy;
    }

    public void setHocKy(int hocKy) {
        this.hocKy = hocKy;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public ArrayList<Integer> getIdVaiTro() {
        return idVaiTro;
    }

    public void setIdVaiTro(ArrayList<Integer> idVaiTro) {
        this.idVaiTro = idVaiTro;
    }

    @Override
    public String toString() {
        return "LopHoc{" + "maLopHoc=" + maLopHoc + ", hocKy=" + hocKy + ", namHoc=" + namHoc + ", tenMonHoc=" + tenMonHoc + ", lop=" + lop + ", idVaiTro=" + idVaiTro + '}';
    }
    
    
}
