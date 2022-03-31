/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.MyEnum;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author chuon
 */
public class ListBuoiHoc {

    private ArrayList<BuoiHoc> listBuoiHoc; // chua maso lopHoc hoc + thong tin diem danh

    public ListBuoiHoc() {
        this.listBuoiHoc = new ArrayList<>();
    }

    public ListBuoiHoc(ArrayList<BuoiHoc> listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
    }

    public ArrayList<BuoiHoc> getListBuoiHoc() {
        return listBuoiHoc;
    }

    public ArrayList<BuoiHoc> getListBuoiHoc(String maLopHoc, String tenNhom) {
        ArrayList<BuoiHoc> listResult = new ArrayList<>();
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            if (buoiHoc.getMaLopHoc().equals(maLopHoc) && buoiHoc.getTenNhom().equals(tenNhom)) {
                listResult.add(buoiHoc);
            }
        }
        return listResult;
    }

    public ArrayList<BuoiHoc> getListBuoiHoc(Date ngayBD, Date ngayKT) {
        ArrayList<BuoiHoc> listResult = new ArrayList<>();
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            if (buoiHoc.getNgayHoc().compareTo(ngayBD) >= 0
                    && buoiHoc.getNgayHoc().compareTo(ngayKT) <= 0) {
                listResult.add(buoiHoc);
            }
        }
        return listResult;
    }

    public void setListBuoiHoc(ArrayList<BuoiHoc> listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
    }

    public void sortDescendingListBuoiHoc() {
        // sap xep theo giam dan ngayHoc
        if (!listBuoiHoc.isEmpty()) {
            Collections.sort(listBuoiHoc, new Comparator<BuoiHoc>() {
                public int compare(BuoiHoc buoiHoc1, BuoiHoc buoiHoc2) {
                    Date ngayHoc1 = buoiHoc1.getNgayHoc();
                    Date ngayHoc2 = buoiHoc2.getNgayHoc();
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//                int year1 = Integer.parseInt(simpleDateFormat.format(ngayHoc1));
//                int year2 = Integer.parseInt(simpleDateFormat.format(ngayHoc2));
//                simpleDateFormat = new SimpleDateFormat("MM");
//                int month1 = Integer.parseInt(simpleDateFormat.format(ngayHoc1));
//                int month2 = Integer.parseInt(simpleDateFormat.format(ngayHoc2));
//                simpleDateFormat = new SimpleDateFormat("dd");
//                int day1 = Integer.parseInt(simpleDateFormat.format(ngayHoc1));
//                int day2 = Integer.parseInt(simpleDateFormat.format(ngayHoc2));
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(ngayHoc1);
                    cal2.setTime(ngayHoc2);
                    if (cal1.after(cal2)) {
                        return -1;
                    } else if (cal1.before(cal2)) {
                        return 1;
                    } else if (cal1.equals(cal2)) {
                        if (buoiHoc1.getCa() == 0) {
                            return 1;
                        }
                        return -1;
                    }
                    return 0;
                }

            });
        }

    }

    public void sortAscendingListBuoiHoc() {
        // sap xep theo tang dan ngayHoc + ca
        if (!listBuoiHoc.isEmpty()) {
            Collections.sort(listBuoiHoc, new Comparator<BuoiHoc>() {
                public int compare(BuoiHoc buoiHoc1, BuoiHoc buoiHoc2) {
                    Date ngayHoc1 = buoiHoc1.getNgayHoc();
                    Date ngayHoc2 = buoiHoc2.getNgayHoc();
                    if (ngayHoc1.compareTo(ngayHoc2) > 0) {
                        return 1;
                    } else if (ngayHoc1.compareTo(ngayHoc2) < 0) {
                        return -1;
                    } else if (ngayHoc1.equals(ngayHoc2)) {
                        // neu buoiHoc1 la ca sang
                        if (buoiHoc1.getCa() == 0) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                    return 0;
                }

            });
        }
    }

    public boolean kiemTraSVHoc(String mssv, int soBuoiHoc) {
        // kiem tra xem MSSV co that su di hoc hop le soBuoiHoc ?
        int demSoBuoiHoc = 0;
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            DiemDanhSV diemDanhSV = buoiHoc.getSVDiemDanh(mssv);
            if (diemDanhSV != null) {
                if (diemDanhSV.getiDdiemDanh() == MyEnum.CO_MAT.getIdDiemDanh()) {
                    demSoBuoiHoc++;
                }
            }
        }
        if (demSoBuoiHoc == soBuoiHoc) {
            return true;
        } else {
            return false;
        }
    }

    public boolean kiemTraSVHocMuon(String mssv, int soBuoiMuon) {
        // kiem tra xem MSSV co that su di hoc soBuoiMuon ?
        int demSoBuoiMuon = 0;
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            DiemDanhSV diemDanhSV = buoiHoc.getSVDiemDanh(mssv);
            if (diemDanhSV != null) {
                if (diemDanhSV.getiDdiemDanh() == MyEnum.HOC_MUON.getIdDiemDanh()) {
                    demSoBuoiMuon++;
                }
            }
        }
        if (demSoBuoiMuon == soBuoiMuon) {
            return true;
        } else {
            return false;
        }
    }

    public boolean kiemTraSVVangKoPhep(String mssv, int soBuoiVangKoPhep) {
        // kiem tra xem MSSV co that su vang soBuoiVangKoPhep ?
        int demSoBuoiVangKoPhep = 0;
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            DiemDanhSV diemDanhSV = buoiHoc.getSVDiemDanh(mssv);
            if (diemDanhSV != null) {
                if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_KO_PHEP.getIdDiemDanh()) {
                    demSoBuoiVangKoPhep++;
                }
            }
        }
        if (demSoBuoiVangKoPhep == soBuoiVangKoPhep) {
            return true;
        } else {
            return false;
        }
    }

    public boolean kiemTraSVVangCoPhep(String mssv, int soBuoiVangCoPhep) {
        // kiem tra xem MSSV co that su vang soBuoiVangCoPhep ?
        int demSoBuoiVangCoPhep = 0;
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            DiemDanhSV diemDanhSV = buoiHoc.getSVDiemDanh(mssv);
            if (diemDanhSV != null) {
                if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_CO_PHEP.getIdDiemDanh()) {
                    demSoBuoiVangCoPhep++;
                }
            }
        }
        if (demSoBuoiVangCoPhep == soBuoiVangCoPhep) {
            return true;
        } else {
            return false;
        }
    }

    public int tinhSoLuongMaximum1SVHoc() {
        // tính số lượng buổi học của 1 SV có mặt nhiều nhất
        // code này chỉ chạy đúng khi data của list buổi học là của cùng 1 lớp (maLopHoc + tenNhom giống hệt nhau)
        // Khởi tạo lưu trữ số lượng buổi mà SV Học đối với tất cả sinh viên của 1 lớp
        HashMap<String, Integer> soLuongBuoiSVHoc = new HashMap<>();
        for (DiemDanhSV diemDanhSV : listBuoiHoc.get(0).getListDiemDanhSV()) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.CO_MAT.getIdDiemDanh()) {
                soLuongBuoiSVHoc.put(diemDanhSV.getMaSoSV(), 1);
            } else {
                soLuongBuoiSVHoc.put(diemDanhSV.getMaSoSV(), 0);
            }
        }
        for (int buoiHoc_i = 1; buoiHoc_i < listBuoiHoc.size(); buoiHoc_i++) {
            BuoiHoc buoiHoc = listBuoiHoc.get(buoiHoc_i);
            Set<String> keySetMSSV = soLuongBuoiSVHoc.keySet();
            for (String mssv : keySetMSSV) {
                if (buoiHoc.getSVDiemDanh(mssv).getiDdiemDanh()
                        == MyEnum.CO_MAT.getIdDiemDanh()) {
                    soLuongBuoiSVHoc.put(mssv, soLuongBuoiSVHoc.get(mssv) + 1);
                }
            }
        }
        int maximumValue = Collections.max(soLuongBuoiSVHoc.values());
        return maximumValue;
    }

    public int tinhSoLuongMaximum1SVHocMuon() {
        // tính số lượng buổi học của 1 SV học muộn nhiều nhất
        HashMap<String, Integer> soLuongBuoiSVHocMuon = new HashMap<>();
        for (DiemDanhSV diemDanhSV : listBuoiHoc.get(0).getListDiemDanhSV()) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.HOC_MUON.getIdDiemDanh()) {
                soLuongBuoiSVHocMuon.put(diemDanhSV.getMaSoSV(), 1);
            } else {
                soLuongBuoiSVHocMuon.put(diemDanhSV.getMaSoSV(), 0);
            }
        }
        for (int buoiHoc_i = 1; buoiHoc_i < listBuoiHoc.size(); buoiHoc_i++) {
            BuoiHoc buoiHoc = listBuoiHoc.get(buoiHoc_i);
            Set<String> keySetMSSV = soLuongBuoiSVHocMuon.keySet();
            for (String mssv : keySetMSSV) {
                if (buoiHoc.getSVDiemDanh(mssv).getiDdiemDanh()
                        == MyEnum.HOC_MUON.getIdDiemDanh()) {
                    soLuongBuoiSVHocMuon.put(mssv, soLuongBuoiSVHocMuon.get(mssv) + 1);
                }
            }
        }
        int maximumValue = Collections.max(soLuongBuoiSVHocMuon.values());
        return maximumValue;
    }

    public int tinhSoLuongMaximum1SVVangKoPhep() {
        // tính số lượng buổi học của 1 SV vắng ko phép nhiều nhất
        HashMap<String, Integer> soLuongBuoiSVVangKoPhep = new HashMap<>();
        for (DiemDanhSV diemDanhSV : listBuoiHoc.get(0).getListDiemDanhSV()) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_KO_PHEP.getIdDiemDanh()) {
                soLuongBuoiSVVangKoPhep.put(diemDanhSV.getMaSoSV(), 1);
            } else {
                soLuongBuoiSVVangKoPhep.put(diemDanhSV.getMaSoSV(), 0);
            }
        }
        for (int buoiHoc_i = 1; buoiHoc_i < listBuoiHoc.size(); buoiHoc_i++) {
            BuoiHoc buoiHoc = listBuoiHoc.get(buoiHoc_i);
            Set<String> keySetMSSV = soLuongBuoiSVVangKoPhep.keySet();
            for (String mssv : keySetMSSV) {
                if (buoiHoc.getSVDiemDanh(mssv).getiDdiemDanh()
                        == MyEnum.VANG_KO_PHEP.getIdDiemDanh()) {
                    soLuongBuoiSVVangKoPhep.put(mssv, soLuongBuoiSVVangKoPhep.get(mssv) + 1);
                }
            }
        }
        int maximumValue = Collections.max(soLuongBuoiSVVangKoPhep.values());
        return maximumValue;
    }

    public int tinhSoLuongMaximum1SVVangCoPhep() {
        // tính số lượng buổi học của 1 SV vắng có phép nhiều nhất
        HashMap<String, Integer> soLuongBuoiSVVangCoPhep = new HashMap<>();
        for (DiemDanhSV diemDanhSV : listBuoiHoc.get(0).getListDiemDanhSV()) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_CO_PHEP.getIdDiemDanh()) {
                soLuongBuoiSVVangCoPhep.put(diemDanhSV.getMaSoSV(), 1);
            } else {
                soLuongBuoiSVVangCoPhep.put(diemDanhSV.getMaSoSV(), 0);
            }
        }
        for (int buoiHoc_i = 1; buoiHoc_i < listBuoiHoc.size(); buoiHoc_i++) {
            BuoiHoc buoiHoc = listBuoiHoc.get(buoiHoc_i);
            Set<String> keySetMSSV = soLuongBuoiSVVangCoPhep.keySet();
            for (String mssv : keySetMSSV) {
                if (buoiHoc.getSVDiemDanh(mssv).getiDdiemDanh()
                        == MyEnum.VANG_CO_PHEP.getIdDiemDanh()) {
                    soLuongBuoiSVVangCoPhep.put(mssv, soLuongBuoiSVVangCoPhep.get(mssv) + 1);
                }
            }
        }
        int maximumValue = Collections.max(soLuongBuoiSVVangCoPhep.values());
        return maximumValue;
    }

    public BuoiHoc getBuoiHoc(String maLopHoc, Date ngayHoc, int ca) {
        for (BuoiHoc buoiHoc : listBuoiHoc) {
            if (buoiHoc.getMaLopHoc().equals(maLopHoc)
                    && buoiHoc.getNgayHoc().equals(ngayHoc) && buoiHoc.getCa() == ca) {
                return buoiHoc;
            }
        }
        return null;
    }
}
