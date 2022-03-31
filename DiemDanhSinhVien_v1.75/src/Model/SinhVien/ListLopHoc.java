/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import java.util.ArrayList;

/**
 *
 * @author chuon
 */
public class ListLopHoc {
      private ArrayList<LopHoc> listLopHoc;

    public ListLopHoc() {
        this.listLopHoc = new ArrayList<>();
    }

    public ListLopHoc(ArrayList<LopHoc> listLopHoc) {
        this.listLopHoc = listLopHoc;
    }

    public ArrayList<LopHoc> getListLopHoc() {
        return listLopHoc;
    }

    public void setListLopHoc(ArrayList<LopHoc> listLopHoc) {
        this.listLopHoc = listLopHoc;
    }

    public LopHoc getLopHoc(String maLopHoc) {
        LopHoc ketQua = null;
        for (LopHoc tmp : listLopHoc) {
            if (tmp.getMaLopHoc().equals(maLopHoc)) {
                ketQua = tmp;
                return ketQua;
            }
        }
        return ketQua;
    }

    public LopHoc getLopHoc(String tenMonHoc, String lop) {
        LopHoc ketQua = null;
        for (LopHoc tmp : listLopHoc) {
            if (tmp.getTenMonHoc().equals(tenMonHoc) && tmp.getLop().equals(lop)) {
                ketQua = tmp;
                return ketQua;
            }
        }
        return ketQua;
    }

}
