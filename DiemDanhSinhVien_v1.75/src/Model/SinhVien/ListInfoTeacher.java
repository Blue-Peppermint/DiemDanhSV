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
public class ListInfoTeacher {
    private ArrayList<InfoTeacher> listInfoTeacher;

    public ListInfoTeacher() {
        this.listInfoTeacher = new ArrayList<>();
    }

    public ListInfoTeacher(ArrayList<InfoTeacher> listInfoTeacher) {
        this.listInfoTeacher = listInfoTeacher;
    }

    public ArrayList<InfoTeacher> getListInfoTeacher() {
        return listInfoTeacher;
    }

    public void setListInfoTeacher(ArrayList<InfoTeacher> listInfoTeacher) {
        this.listInfoTeacher = listInfoTeacher;
    }

    public InfoTeacher getInfoTeacher(String maGV) {
        for (InfoTeacher s : listInfoTeacher) {
            if (maGV.equals(s.getMaGV())) {
                return s;
            }
        }
        return null;
    }
    
    
}
