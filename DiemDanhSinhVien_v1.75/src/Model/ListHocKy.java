/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GiaoVien.KetNoiSQL_GV;
import Model.SinhVien.KetNoiSQL_SV;
import java.util.ArrayList;

/**
 *
 * @author chuon
 */
/**
 * chuong trinh se chay dung neu data co cac luat sau: - Data HocKy moi nhat chi
 * duoc phep them khi HocKy cu da ket thuc
 */
public class ListHocKy {

    private ArrayList<HocKy> listHocKy; // lich tong quat ma nha truong sap xep cho GV theo cac hoc ky

    public ListHocKy() {
        this.listHocKy = new ArrayList<>();
    }

    public ListHocKy(ArrayList<HocKy> listHocKy) {
        this.listHocKy = listHocKy;
    }

    public ArrayList<HocKy> getListHocKy() {
        return listHocKy;
    }

    public void setListHocKy(ArrayList<HocKy> listHocKy) {
        this.listHocKy = listHocKy;
    }

    private void khoiTaolistHocKyKhoaMoiNhat_GV() {
        // khoi tao so luong list theo khoa hoc moi nhat
        listHocKy = new ArrayList<>();
        int sttHocKyMax = KetNoiSQL_GV.getHocKyMoiNhat().getSttHocKy();
        int namHocMax = KetNoiSQL_GV.getHocKyMoiNhat().getNamHoc();
        // nếu trong năm SV đang học học kỳ 2,3
        if (sttHocKyMax >= 2) {
            // khoi tao hoc ky 1
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax - 1);
            listHocKy.add(hocKyNew);
            for (int i = 2; i <= sttHocKyMax; i++) {
                hocKyNew = new HocKy();
                hocKyNew.setSttHocKy(i);
                hocKyNew.setNamHoc(namHocMax);
                listHocKy.add(hocKyNew);
            }
        } else {
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax);
            listHocKy.add(hocKyNew);
        }
    }

    private void themListHocKyKhoaMoiNhat_GV() {
        // set data danh sach tuan ung voi moi phan tu trong dsHocKy
        khoiTaolistHocKyKhoaMoiNhat_GV();
        int tuanHienTai = 1;
        // them danh sach tuan bat dau tu hoc ky 1, tuan 1
        for (int i = 0; i < listHocKy.size(); i++) {
            listHocKy.get(i).themDanhSachTuan(tuanHienTai);
            int lastIndex = listHocKy.get(i).getDsTuan().size() - 1;
            tuanHienTai = listHocKy.get(i).getDsTuan().get(lastIndex).getSttTuan() + 1;
        }
    }

    public void getDataKhoaMoiNhat_GV() {
        // chay on neu data dung format
        themListHocKyKhoaMoiNhat_GV();
    }

    private ArrayList<HocKy> khoiTaoToanBolistHocKy() {
        // khoi tao so luong hoc ky tong quat nhat. GV nao cung nhu GV nao ca, SV nao cung deu chay method nay khi muon lay FULL Data
        ArrayList<HocKy> list = new ArrayList<>();
        int sttHocKy = KetNoiSQL_GV.getHocKyLauNhat().getSttHocKy();
        int namHoc = KetNoiSQL_GV.getHocKyLauNhat().getNamHoc();
        HocKy hocKy = new HocKy();
        hocKy.setSttHocKy(sttHocKy);
        hocKy.setNamHoc(namHoc);
        list.add(hocKy);
        int sttHocKyMax = KetNoiSQL_GV.getHocKyMoiNhat().getSttHocKy();
        int namHocMax = KetNoiSQL_GV.getHocKyMoiNhat().getNamHoc();
        while (sttHocKy != sttHocKyMax || namHoc != namHocMax) {
            switch (sttHocKy) {
                case 1:
                    sttHocKy++;
                    namHoc++;
                    break;
                case 2:
                    sttHocKy++;
                    break;
                case 3:
                    sttHocKy = 1;
                    break;
            }
            HocKy hocKyMoi = new HocKy();
            hocKyMoi.setSttHocKy(sttHocKy);
            hocKyMoi.setNamHoc(namHoc);
            list.add(hocKyMoi);
        }
        return list;
    }

    private void themToanBoListHocKy_GV() {
        // code này đúng với luôn cả trường hợp GV dạy ko đúng luật
        // nghĩa là dạy nhảy cóc, kiểu ko dạy học kỳ 1, nhưng chỉ dạy học kỳ 2
        ArrayList<HocKy> list = khoiTaoToanBolistHocKy();
        int tuanHienTai = 1;
        int i = 0;
        // Day la ngoai le doi voi nhung truong hoc co GV day giua chung vao hocky 2 hoac 3
        // thi tuan bat dau cua nhung hocKy do cung duoc tinh la 1
        for (i = 0; i < list.size(); i++) {
            HocKy hocKy = list.get(i);
            if (hocKy.getSttHocKy() == 1) {
                break;
            } else {
                list.get(i).themDanhSachTuan(tuanHienTai);
                int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
                tuanHienTai = hocKy.getDsTuan().get(lastIndexDSTuan).getSttTuan() + 1;
            }
        }
        // Ke tu luc bat dau hocKy1 thi quay lai luat cu:
        // tuan 1 se duoc tinh ke tu hocKy1
        for (int j = i; j < list.size(); j++) {
            HocKy hocKy = list.get(j);
            if (hocKy.getSttHocKy() == 1) {
                tuanHienTai = 1;
            }
            hocKy.themDanhSachTuan(tuanHienTai);
            int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
            tuanHienTai = hocKy.getDsTuan().get(lastIndexDSTuan).getSttTuan() + 1;
        }
        // sau khi themDSTuan tat ca cac hocKy, kiem tra xem GV day hocKy nao thi moi add vao
        for (int j = 0; j < list.size(); j++) {
            HocKy hocKy = list.get(j);
            if (KetNoiSQL_GV.kiemTraGVDayHoc(hocKy.getSttHocKy(), hocKy.getNamHoc())) {
                listHocKy.add(hocKy);
            }
        }

    }

    public void getDataToanBo_GV() {
        // chay on neu nhu tat ca cac hoc ky, nam hoc deu co it nhat 1 lop hoc duoc GV day (co lich day)
        themToanBoListHocKy_GV();
    }

    private void khoiTaolistHocKyKhoaMoiNhat_SV() {
        // khoi tao so luong list theo khoa hoc moi nhat
        // luôn bắt đầu từ học kỳ 1
        listHocKy = new ArrayList<>();
        int sttHocKyMax = KetNoiSQL_SV.getHocKyMoiNhat().getSttHocKy();
        int namHocMax = KetNoiSQL_SV.getHocKyMoiNhat().getNamHoc();
        // nếu trong năm SV đang học học kỳ 2,3
        if (sttHocKyMax >= 2) {
            // khoi tao hoc ky 1
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax - 1);
            listHocKy.add(hocKyNew);
            for (int i = 2; i <= sttHocKyMax; i++) {
                hocKyNew = new HocKy();
                hocKyNew.setSttHocKy(i);
                hocKyNew.setNamHoc(namHocMax);
                listHocKy.add(hocKyNew);
            }
        } else {
            HocKy hocKyNew = new HocKy();
            hocKyNew.setSttHocKy(1);
            hocKyNew.setNamHoc(namHocMax);
            listHocKy.add(hocKyNew);
        }
    }

    private void themListHocKyKhoaMoiNhat_SV() {
        khoiTaolistHocKyKhoaMoiNhat_SV();
        if (!listHocKy.isEmpty() && (listHocKy.get(0).getNamHoc() == 0
                || listHocKy.get(0).getNamHoc() == -1)) {
            this.listHocKy = new ArrayList<>();
        } else {
            ArrayList<Integer> indexHocKyWillRemove = new ArrayList<>();
            int tuanHienTai = 1;
            // them danh sach tuan bat dau tu hoc ky 1, tuan 1
            for (int i = 0; i < listHocKy.size(); i++) {
                HocKy hocKy = listHocKy.get(i);
                // kiểm tra thử học kỳ này có tồn tại ko
                if (KetNoiSQL_GV.getNgayBDHocKyAll(hocKy.getSttHocKy(), hocKy.getNamHoc()) != null) {
                    hocKy.themDanhSachTuan(tuanHienTai);
                    int lastIndex = hocKy.getDsTuan().size() - 1;
                    tuanHienTai = hocKy.getDsTuan().get(lastIndex).getSttTuan() + 1;
                }
                else{
                    indexHocKyWillRemove.add(i);
                }
            }
            for(int indexHocKy: indexHocKyWillRemove){
                listHocKy.remove(indexHocKy);
            }
        }
    }

    public void getDataKhoaMoiNhat_SV() {
        themListHocKyKhoaMoiNhat_SV();
    }

    private void themToanBoListHocKy_SV() {
        // code này đúng với luôn cả trường hợp SV có hoc ky nghi hoc,xin bao luu ket qua
        ArrayList<HocKy> list = khoiTaoToanBolistHocKy();
        int tuanHienTai = 1;
        int i = 0;
        // Day la ngoai le doi voi nhung truong hoc co SV hoc giua chung vao hocky 2 hoac 3
        // thi tuan bat dau cua nhung hocKy do cung duoc tinh la 1
        for (i = 0; i < list.size(); i++) {
            HocKy hocKy = list.get(i);
            if (hocKy.getSttHocKy() == 1) {
                break;
            } else {
                list.get(i).themDanhSachTuan(tuanHienTai);
                int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
                tuanHienTai = hocKy.getDsTuan().get(lastIndexDSTuan).getSttTuan() + 1;
            }
        }
        // Ke tu luc bat dau hocKy1 thi quay lai luat cu:
        // tuan 1 se duoc tinh ke tu hocKy1
        for (int j = i; j < list.size(); j++) {
            HocKy hocKy = list.get(j);
            if (hocKy.getSttHocKy() == 1) {
                tuanHienTai = 1;
            }
            hocKy.themDanhSachTuan(tuanHienTai);
            int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
            tuanHienTai = hocKy.getDsTuan().get(lastIndexDSTuan).getSttTuan() + 1;
        }
        // sau khi themDSTuan tat ca cac hocKy, kiem tra xem SV hoc hocKy nao thi moi add vao
        for (int j = 0; j < list.size(); j++) {
            HocKy hocKy = list.get(j);
            if (KetNoiSQL_SV.kiemTraSVHoc(hocKy.getSttHocKy(), hocKy.getNamHoc())) {
                listHocKy.add(hocKy);
            }
        }
    }

    public void getDataToanBo_SV() {
        themToanBoListHocKy_SV();
    }
}
