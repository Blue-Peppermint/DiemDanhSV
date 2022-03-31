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
public class ListGVDayHoc {
    private ArrayList<GVDayHoc> listGVDayHoc;

    public ListGVDayHoc() {
        this.listGVDayHoc = new ArrayList<>();
    }

    public ListGVDayHoc(ArrayList<GVDayHoc> listGVDayHoc) {
        this.listGVDayHoc = listGVDayHoc;
    }

    public ArrayList<GVDayHoc> getListGVDayHoc() {
        return listGVDayHoc;
    }

    public void setListGVDayHoc(ArrayList<GVDayHoc> listGVDayHoc) {
        this.listGVDayHoc = listGVDayHoc;
    }
    
    public void addGV(GVDayHoc gv){
        this.listGVDayHoc.add(gv);
    }

    @Override
    public String toString() {
        return "ListGVDayHoc{" + "listGVDayHoc=" + listGVDayHoc + '}';
    }
        
}
