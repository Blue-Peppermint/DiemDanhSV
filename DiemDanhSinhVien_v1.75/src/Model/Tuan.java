/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author chuon
 */
public class Tuan {

    private int sttTuan; // stt bat dau tu 1-> n
    private Date ngayBDTuan;
    private Date ngayKTTuan;

    public Tuan() {
        sttTuan = -1;
        ngayBDTuan = null;
        ngayKTTuan = null;
    }

    public Tuan(int sttTuan, Date ngayBD, Date ngayCuoi) {
        this.sttTuan = sttTuan;
        this.ngayBDTuan = ngayBD;
        this.ngayKTTuan = ngayCuoi;
    }

    public int getSttTuan() {
        return sttTuan;
    }

    public void setSttTuan(int sttTuan) {
        this.sttTuan = sttTuan;
    }

    public Date getNgayBDTuan() {
        return ngayBDTuan;
    }

    public void setNgayBDTuan(Date ngayBDTuan) {
        this.ngayBDTuan = ngayBDTuan;
    }

    public Date getNgayKTTuan() {
        return ngayKTTuan;
    }

    public void setNgayKTTuan(Date ngayKTTuan) {
        this.ngayKTTuan = ngayKTTuan;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Tuan{" + "sttTuan=" + sttTuan + ", ngayBDTuan=" 
                + simpleDateFormat.format(ngayBDTuan) 
                + ", ngayKTTuan=" + simpleDateFormat.format(ngayKTTuan) + '}';
    }
    
}
