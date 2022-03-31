/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnusedModel;

import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.LopHoc;
import Model.GiaoVien.MailStudent;
import Model.GiaoVien.NhomSV;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chuon
 */


public class GVDayLopHoc {
    private ArrayList<LopHoc> dsLopHoc; // lay thong tin cac lop hoc ma GV day + vai tro GV trong lop
    private ArrayList<NhomSV> dsNhomSV; // lay danh sach sinh vien hoc cac nhom
    private ArrayList<BuoiHoc> dsBuoiHoc; // lay thong tin cac buoi hoc diem danh
    private ArrayList<MailStudent> dsThu; // lay thong tin cac thu GV da gui cho SV
//    private String maGV;
//    private ArrayList<Integer> idVaiTro;

//    public GVDayLopHoc() {
//                this.dsLopHoc = null;
//        this.dsNhomSV = null;
//        this.dsBuoiHoc = null;
//        this.dsThu = null;
//    }
//
//    public GVDayLopHoc(ArrayList<LopHoc> dsLopHoc, ArrayList<NhomSV> dsNhomSV, ArrayList<BuoiHoc> dsBuoiHoc, ArrayList<MailStudent> dsGuiThu) {
//        this.dsLopHoc = dsLopHoc;
//        this.dsNhomSV = dsNhomSV;
//        this.dsBuoiHoc = dsBuoiHoc;
//        this.dsThu = dsGuiThu;
//    }

    public ArrayList<LopHoc> getDsLopHoc() {
        return dsLopHoc;
    }

    public void setDsLopHoc(ArrayList<LopHoc> dsLopHoc) {
        this.dsLopHoc = dsLopHoc;
    }

    public ArrayList<NhomSV> getDsNhomSV() {
        return dsNhomSV;
    }

    public void setDsNhomSV(ArrayList<NhomSV> dsNhomSV) {
        this.dsNhomSV = dsNhomSV;
    }

    public ArrayList<BuoiHoc> getDsBuoiHoc() {
        return dsBuoiHoc;
    }

    public void setDsBuoiHoc(ArrayList<BuoiHoc> dsBuoiHoc) {
        this.dsBuoiHoc = dsBuoiHoc;
    }

    public ArrayList<MailStudent> getDsThu() {
        return dsThu;
    }

    public void setDsThu(ArrayList<MailStudent> dsThu) {
        this.dsThu = dsThu;
    }

//    public ArrayList<NhomSV> getDsNhomSV(String maLopMH) {
//        ArrayList<NhomSV> result = new ArrayList<>();
//        for(NhomSV nhom : dsNhomSV){
//            if(maLopMH.equals(nhom.getMaLopMH())){
//                result.add(nhom);
//            }
//        }
//        if(result.size() == 0){
//            return null;
//        }
//        else{
//            return result;
//        }
//    }
    
    
    
    
     
//    public LopHoc getLopHoc(String maLopHoc) {
//        LopHoc ketQua = null;
//        for (LopHoc tmp : dsLopHoc) {
//            if (tmp.getMaLopHoc().equals(maLopHoc)) {
//                ketQua = tmp;
//                return ketQua;
//            }
//        }
//        return ketQua;
//    }
//
//    public LopHoc getLopHoc(String tenMonHoc, String lop) {
//        LopHoc ketQua = null;
//        for (LopHoc tmp : dsLopHoc) {
//            if (tmp.getTenMonHoc().equals(tenMonHoc) &&tmp.getLop().equals(lop)) {
//                ketQua = tmp;
//                return ketQua;
//            }
//        }
//        return ketQua;
//    }
    
    
//    public NhomSV getNhomSV(String maLopMH, String tenNhom){
//        NhomSV ketQua = null;
//        for (NhomSV tmp : dsNhomSV) {
//            if (tmp.getMaLopMH().equals(maLopMH) && tmp.getTenNhom().equals(tenNhom)) {
//                ketQua = tmp;
//                return ketQua;
//            }
//        }
//        return ketQua;
//    }
    
    public int searchIndexBuoiHoc(BuoiHoc buoiHoc){
        // kiem tra buoiHoc da ton tai trong dsBuoiHoc chua?
        int countIndex = -1;
        for(BuoiHoc tmp : dsBuoiHoc){
            countIndex++;
           if(buoiHoc.getMaLopHoc().equals(tmp.getMaLopHoc()) 
                   && buoiHoc.getTenNhom().equals(tmp.getTenNhom())
                   && buoiHoc.getCa() == tmp.getCa() 
                   && buoiHoc.getNgayHoc().equals(tmp.getNgayHoc())){
               return countIndex;
           }
        }
        return -1;
    }
    
    public boolean saveListBuoiHoc(ArrayList<BuoiHoc> list){
        // neu BuoiHoc trong list chua ton tai thi add vao dsBuoiHoc
        // neu BuoiHoc trong list da ton tai thi xoa cai cu, add cai moi vao dsBuoiHoc
        boolean isSave = false;
        for(BuoiHoc buoiHoc : list){
            int index = searchIndexBuoiHoc(buoiHoc);
            if(index == -1){
                dsBuoiHoc.add(buoiHoc);
                KetNoiSQL_GV.saveBuoiHoc(buoiHoc);
                isSave = true;
            }
            else{
                dsNhomSV.remove(index);
                KetNoiSQL_GV.updateBuoiHoc(buoiHoc);
                isSave = true;
            }
        }       
        return isSave;
    }
}
