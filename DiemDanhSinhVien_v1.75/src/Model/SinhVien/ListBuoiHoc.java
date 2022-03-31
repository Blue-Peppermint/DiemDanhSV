/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chuon
 */
public class ListBuoiHoc {
    private ArrayList<BuoiHoc> listBuoiHoc;

    public ListBuoiHoc() {
        listBuoiHoc = new ArrayList<>();
    }

    public ListBuoiHoc(ArrayList<BuoiHoc> listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
    }

    public ArrayList<BuoiHoc> getListBuoiHoc() {
        return listBuoiHoc;
    }

    public void setListBuoiHoc(ArrayList<BuoiHoc> listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
    }
    
    public BuoiHoc getBuoiHoc(Date ngayHoc, int ca){
        if(!listBuoiHoc.isEmpty()){
            for(BuoiHoc buoiHoc: listBuoiHoc){
                if(buoiHoc.getNgayHoc().equals(ngayHoc) && buoiHoc.getCa() == ca){
                    return buoiHoc;
                }
            }
        }
        return null;
    }
}
