/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;
import Model.SinhVien.*;
import Model.*;
import Model.GiaoVien.DiemDanhSV;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.SinhVien.KetNoiSQL_SV;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author chuon
 */
public class TestSQL {

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int hocKy = 2;
        int namHoc = 2021;
//        KetNoiSQL_SV.setMssv("SV001");
////        HocKy hocKy1 = KetNoiSQL_SV.getHocKyMoiNhat();
////        ArrayList<LopHoc> listLopHoc = KetNoiSQL_SV.getListLopHoc(hocKy1.getSttHocKy(), hocKy1.getNamHoc());
//        
//        ArrayList<LopHoc> listLopHoc = KetNoiSQL_SV.getListLopHoc();
//        for (LopHoc lop : listLopHoc) {
//            System.out.println("");
//            System.out.println(lop);
//            for(BuoiHoc buoi : KetNoiSQL_SV.getListBuoiHoc(lop.getMaLopHoc())){
//                System.out.println(buoi);
//            }
//        }

        KetNoiSQL_GV.setMaGV("GV001");

        for (Model.GiaoVien.LopHoc lop : KetNoiSQL_GV.getListLopHoc(hocKy, namHoc)) {
            System.out.println("");
            for (Model.GiaoVien.BuoiHoc buoiHoc : KetNoiSQL_GV.getListBuoiHoc(lop.getMaLopHoc())) {
                for(DiemDanhSV diemDanh : buoiHoc.getListDiemDanhSV()){
                    diemDanh.setiDdiemDanh(1);
                }
                boolean t = KetNoiSQL_GV.updateBuoiHoc(buoiHoc);
                if(t == true){
                    System.out.println("Ok");
                    System.out.println(buoiHoc);
                }
                else {
                    System.out.println("Deo on");
                }
                System.out.println("");
            }

        }

    }
}
