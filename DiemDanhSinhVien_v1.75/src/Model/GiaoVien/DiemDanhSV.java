/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.MyEnum;
import java.util.Date;

/**
 *
 * @author chuon
 */
public class DiemDanhSV {
    private String maSoSV;
    private int iDdiemDanh; // 0: co mat; 1: hoc muon; 2: Vang khong phep; 3: Vang co phep

    public DiemDanhSV() {
        this.maSoSV = "";
        this.iDdiemDanh = -1;
    }
    
    public DiemDanhSV(String maSoSV, int iDdiemDanh) {
        this.maSoSV = maSoSV;
        this.iDdiemDanh = iDdiemDanh;
    }

    public String getMaSoSV() {
        return maSoSV;
    }

    public void setMaSoSV(String maSoSV) {
        this.maSoSV = maSoSV;
    }

    public int getiDdiemDanh() {
        return iDdiemDanh;
    }

    public void setiDdiemDanh(int iDdiemDanh) {
        this.iDdiemDanh = iDdiemDanh;
    }

    @Override
    public String toString() {
        return "DiemDanhSV{" + "maSoSV=" + maSoSV + ", iDdiemDanh=" + MyEnum.getChuThichEditorComBoBox(iDdiemDanh) + '}';
    }
    
}
