/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import Model.MyEnum;
import java.util.Date;

/**
 *
 * @author chuon
 */
public class BuoiHoc {

    private GVDayHoc gvDayHoc;
    private Date ngayHoc;
    private int ca; // ca sang hoac chieu 0 -> 1
    private int iDdiemDanh; // 0: co mat; 1: hoc muon; 2: Vang khong phep; 3: Vang co phep

    public BuoiHoc() {
        this.gvDayHoc = new GVDayHoc();
        this.ngayHoc = null;
        this.ca = -1;
        this.iDdiemDanh = -1;
    }

    public BuoiHoc(GVDayHoc gvDayHoc, Date ngayHoc, int ca, int iDdiemDanh) {
        this.gvDayHoc = gvDayHoc;
        this.ngayHoc = ngayHoc;
        this.ca = ca;
        this.iDdiemDanh = iDdiemDanh;
    }

    public GVDayHoc getGvDayHoc() {
        return gvDayHoc;
    }

    public void setGvDayHoc(GVDayHoc gvDayHoc) {
        this.gvDayHoc = gvDayHoc;
    }

    public Date getNgayHoc() {
        return ngayHoc;
    }

    public void setNgayHoc(Date ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public int getiDdiemDanh() {
        return iDdiemDanh;
    }

    public void setiDdiemDanh(int iDdiemDanh) {
        this.iDdiemDanh = iDdiemDanh;
    }

    @Override
    public String toString() {
        return "BuoiHoc{" + "gvDayHoc=" + gvDayHoc + ", ngayHoc=" + ngayHoc + ", ca=" + ca + ", iDdiemDanh=" + MyEnum.getChuThichEditorComBoBox(iDdiemDanh) + '}';
    }
    
}
