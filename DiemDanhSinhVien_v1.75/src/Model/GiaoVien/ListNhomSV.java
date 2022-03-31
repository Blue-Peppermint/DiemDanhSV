/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import java.util.ArrayList;

/**
 *
 * @author chuon
 */
public class ListNhomSV {
    private ArrayList<NhomSV> listNhomSV; // lay danh sach sinh vien hoc cac nhom

    public ListNhomSV() {
        this.listNhomSV = new ArrayList<>();
    }

    public ListNhomSV(ArrayList<NhomSV> listNhomSV) {
        this.listNhomSV = listNhomSV;
    }

    public ArrayList<NhomSV> getListNhomSV() {
        return listNhomSV;
    }

    public void setListNhomSV(ArrayList<NhomSV> listNhomSV) {
        this.listNhomSV = listNhomSV;
    }

            public ArrayList<NhomSV> getListNhomSV(String maLopMH) {
        ArrayList<NhomSV> result = new ArrayList<>();
        for(NhomSV nhom : listNhomSV){
            if(maLopMH.equals(nhom.getMaLopMH())){
                result.add(nhom);
            }
        }

            return result;

    }
    public NhomSV getNhomSV(String maLopMH, String tenNhom) {
        NhomSV result = null;
        for (NhomSV tmp : listNhomSV) {
            if (tmp.getMaLopMH().equals(maLopMH) && tmp.getTenNhom().equals(tenNhom)) {
                result = tmp;
                return result;
            }
        }
        return result;
    }


}
