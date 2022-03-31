/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.InfoStudent;
import java.util.ArrayList;

/**
 *
 * @author chuon
 */


public class ListInfoStudent {
    private ArrayList<InfoStudent> listInfoStudent;

    public ListInfoStudent() {
        listInfoStudent = new ArrayList<>();
    }

    public ListInfoStudent(ArrayList<InfoStudent> listStudents) {
        this.listInfoStudent = listStudents;
    }

    public ArrayList<InfoStudent> getListInfoStudent() {
        return listInfoStudent;
    }

    public void setListInfoStudent(ArrayList<InfoStudent> listInfoStudent) {
        this.listInfoStudent = listInfoStudent;
    }
    
    public InfoStudent getStudent(String maSoSV){
        for(InfoStudent s: listInfoStudent){
            if(maSoSV.equals(s.getMssv())){
                return s;
            }
        }
        return null;
    }
    
    
}
