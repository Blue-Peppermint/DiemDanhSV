/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GiaoVien.KetNoiSQL_GV;
import java.util.ArrayList;
import java.util.Date;


public class HocKy {
    private int sttHocKy; // hoc ky thu may? dao dong tu 1-> 3
    private int namHoc; // hoc ky nay o nam bao nhieu
    private ArrayList<Tuan> dsTuan; // danh sach cac tuan cua hoc ky nay

    public HocKy() {
        sttHocKy = -1;
        namHoc = -1;
        dsTuan = new ArrayList<>();
    }

    public HocKy(int sttHocKy, int namHoc, ArrayList<Tuan> dsTuan) {
        this.sttHocKy = sttHocKy;
        this.namHoc = namHoc;
        this.dsTuan = dsTuan;
    }

    public int getSttHocKy() {
        return sttHocKy;
    }

    public void setSttHocKy(int sttHocKy) {
        this.sttHocKy = sttHocKy;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }

    public ArrayList<Tuan> getDsTuan() {
        return dsTuan;
    }

    public void setDsTuan(ArrayList<Tuan> dsTuan) {
        this.dsTuan = dsTuan;
    }
    
    private Date congNgay(Date date, int soNgay) {
        long dateInMiliS = date.getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        Date ketqua;
        ketqua = new Date(dateInMiliS + soNgay * oneDayinMiliS);
        return ketqua;
    }

    private Date truNgay(Date date, int soNgay) {
        long dateInMiliS = date.getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        Date ketqua;
        ketqua = new Date(dateInMiliS - soNgay * oneDayinMiliS);
        return ketqua;
    }

    public void themDanhSachTuan(int sttTuanDauTienHocKy) {
        int sttTuan = sttTuanDauTienHocKy;
        Date ngayBDTuan = KetNoiSQL_GV.getNgayBDHocKyAll(sttHocKy, namHoc);
        Date ngayKTTuan = congNgay(ngayBDTuan, 6);
        Tuan tmp = new Tuan(sttTuan, ngayBDTuan, ngayKTTuan);
        dsTuan.add(tmp);
        // trong luc them danh sach tuan chua xong
        Date ngayKTHocKy = KetNoiSQL_GV.getNgayKTHocKyAll(sttHocKy, namHoc);
        while (ngayKTTuan.compareTo(ngayKTHocKy) != 0) {
            sttTuan++;
            ngayBDTuan = congNgay(ngayBDTuan, 7);
            ngayKTTuan = congNgay(ngayBDTuan, 6);
            tmp = new Tuan(sttTuan, ngayBDTuan, ngayKTTuan);
            dsTuan.add(tmp);
        }
        // moi hoc ky keo dai them 5 tuan nua de cho phep GV diem danh buoi hoc bu
        for (int i = 0; i < 5; i++) {
            sttTuan++;
            ngayBDTuan = congNgay(ngayBDTuan, 7);
            ngayKTTuan = congNgay(ngayBDTuan, 6);
            tmp = new Tuan(sttTuan, ngayBDTuan, ngayKTTuan);
            dsTuan.add(tmp);
        }
    }

//    public Tuan getTuan(int sttTuan){
//        for(Tuan tmp : dsTuan){
//            if(tmp.getSttTuan() == sttTuan){
//                return tmp;
//            }
//        }
//        return null;
//    }
}
