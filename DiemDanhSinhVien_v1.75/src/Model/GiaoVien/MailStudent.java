/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chuon
 */

public class MailStudent {
    private String maLopHoc;
    private Date ngayGui;
    private String noiDung;
    private ArrayList<String> listMSSV;
    // Có 2 loại thông báo: loại 1: gửi cho 1 nhóm lớp học(vd: Thực Hành 1,...)
    // loại 2: gửi cho 1 cá nhân SV nào đó hoặc tập hợp nhóm SV nào đó
    private boolean thongBaoTuDo; // true nếu là loại 2, false nếu là loại 1 

    public MailStudent() {
                this.maLopHoc = "";
        this.ngayGui = null;
        this.noiDung = "";
        this.listMSSV = null;
        thongBaoTuDo = false;
    }

    public MailStudent(String maLopHoc, Date ngayGui, String noiDung, ArrayList<String> listMSSV, boolean thongBaoTuDo) {
        this.maLopHoc = maLopHoc;
        this.ngayGui = ngayGui;
        this.noiDung = noiDung;
        this.listMSSV = listMSSV;
        this.thongBaoTuDo = thongBaoTuDo;
    }

    
    
    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
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

    public ArrayList<String> getListMSSV() {
        return listMSSV;
    }

    public void setListMSSV(ArrayList<String> listMSSV) {
        this.listMSSV = listMSSV;
    }

    public boolean isThongBaoTuDo() {
        return thongBaoTuDo;
    }

    public void setThongBaoTuDo(boolean thongBaoTuDo) {
        this.thongBaoTuDo = thongBaoTuDo;
    }

    @Override
    public String toString() {
        return "MailStudent{" + "maLopHoc=" + maLopHoc + ", ngayGui=" + ngayGui + ", noiDung=" + noiDung + ", listMSSV=" + listMSSV + '}';
    }
    
}
