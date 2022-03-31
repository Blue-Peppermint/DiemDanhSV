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
public class NhomSV {
    private String maLopMH;
    private String tenNhom;
    private ArrayList<String> listMSSV;

    public NhomSV() {
                this.maLopMH = "";
        this.tenNhom = "";
        this.listMSSV = null;
    }

    public NhomSV(String maLopMH, String tenNhom, ArrayList<String> listMSSV) {
        this.maLopMH = maLopMH;
        this.tenNhom = tenNhom;
        this.listMSSV = listMSSV;
    }

    public String getMaLopMH() {
        return maLopMH;
    }

    public void setMaLopMH(String maLopMH) {
        this.maLopMH = maLopMH;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public ArrayList<String> getListMSSV() {
        return listMSSV;
    }

    public void setListMSSV(ArrayList<String> listMSSV) {
        this.listMSSV = listMSSV;
    }

    @Override
    public String toString() {
        return "NhomSV{" + "maLopMH=" + maLopMH + ", tenNhom=" + tenNhom + ", listMSSV=" + listMSSV + '}';
    }
    
    
    
    
    public void getSqlData(String maLop, String nhom){
        // SQL: Lay du lieu SQL dua vao maLopMH + tenNhom
    }
    
}
