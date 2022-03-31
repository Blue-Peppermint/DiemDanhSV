/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnusedModel;

/**
 *
 * @author chuon
 */
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.LopHoc;
import Model.HocKy;
import java.util.ArrayList;
import java.util.Date;

// Class su dung rieng cho TimeTablePanel

public class ThongTinDay {
    //private String maGV;
    //private ArrayList<Integer> idVaiTro;
    private ArrayList<HocKy> dsHocKy; // lich tong quat ma nha truong sap xep cho GV theo cac hoc ky
    private ArrayList<LopHoc> dsLopHoc; // lay thong tin cac lop hoc ma GV day + vai tro GV trong lop

    public ThongTinDay() {
                this.dsHocKy = null;
        this.dsLopHoc = null;
    }

    public ThongTinDay(ArrayList<HocKy> dsHocKy, ArrayList<LopHoc> dsLopHoc) {
        this.dsHocKy = dsHocKy;
        this.dsLopHoc = dsLopHoc;
    }

    public ArrayList<HocKy> getDsHocKy() {
        return dsHocKy;
    }

    public void setDsHocKy(ArrayList<HocKy> dsHocKy) {
        this.dsHocKy = dsHocKy;
    }

    public ArrayList<LopHoc> getDsLopHoc() {
        return dsLopHoc;
    }

    public void setDsLopHoc(ArrayList<LopHoc> dsLopHoc) {
        this.dsLopHoc = dsLopHoc;
    }  
    
    private void khoiTaoDSHocKy() {
        // khoi tao so luong dsHocKy phu hop va setData sttHocKy + namHoc
        dsHocKy = new ArrayList<>();
        int sttHocKyMax = KetNoiSQL_GV.getHocKyMoiNhat().getSttHocKy();
        int namHocMax = KetNoiSQL_GV.getHocKyMoiNhat().getNamHoc();
        // nếu trong năm SV đang học học kỳ 2,3
        if (sttHocKyMax >= 2) {           
            // khoi tao hoc ky 1
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax - 1) ;
            dsHocKy.add(hocKyNew);
            for (int i = 2; i <= sttHocKyMax; i++) {
                hocKyNew = new HocKy();                
                hocKyNew.setSttHocKy(i);
                hocKyNew.setNamHoc(namHocMax);
                dsHocKy.add(hocKyNew);
            }
        } else {
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax) ;
            dsHocKy.add(hocKyNew);
        }
    }

    private void themDSHocKy() {
        // set data danh sach tuan ung voi moi phan tu trong dsHocKy
        khoiTaoDSHocKy();
        int tuanHienTai = 1;
        // them danh sach tuan bat dau tu hoc ky 1, tuan 1
        for (int i = 0; i < dsHocKy.size(); i++) {
            dsHocKy.get(i).themDanhSachTuan(tuanHienTai);
            int lastIndex = dsHocKy.get(i).getDsTuan().size() - 1;            
            tuanHienTai = dsHocKy.get(i).getDsTuan().get(lastIndex).getSttTuan() + 1;
        }
    }

    public void themDSLopHoc() {
        // set data cho danh sach lop hoc theo tung hoc ky, nam hoc
        for (int i = 0; i < dsHocKy.size(); i++) {
            int sttHocKy = dsHocKy.get(i).getSttHocKy();
            int namHoc = dsHocKy.get(i).getNamHoc();
            dsLopHoc = new ArrayList<>();
            dsLopHoc = KetNoiSQL_GV.getListLopHoc(sttHocKy, namHoc);
        }
    }

    public void getAllData(){
        themDSHocKy();
        themDSLopHoc();
    }
    
        public LopHoc getLopHoc(String maLopHoc) {
        LopHoc ketQua = null;
        for (LopHoc tmp : dsLopHoc) {
            if (tmp.getMaLopHoc().equals(maLopHoc)) {
                ketQua = tmp;
                return ketQua;
            }
        }
        return ketQua;
    }
}