/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.LichDayChinh;
import java.util.ArrayList;

/**
 *
 * @author chuon
 */
public class ListLichDayChinh {
    // chi chua data cua nhung lop GV day, co idVaiTro
    private ArrayList<LichDayChinh> listLichDay;

    public ListLichDayChinh() {
        this.listLichDay = new ArrayList<>();
    }

    public ListLichDayChinh(ArrayList<LichDayChinh> listLichDay) {
        this.listLichDay = listLichDay;
    }

    public ArrayList<LichDayChinh> getListLichDay() {
        return listLichDay;
    }

    public void setListLichDay(ArrayList<LichDayChinh> listLichDay) {
        this.listLichDay = listLichDay;
    }
    public ArrayList<LichDayChinh> getListLichDay(ArrayList<LopHoc> listLopHoc){
        // tra ve lich day cua cac lop hoc ma gv day, bao gom ca LT + TH
        ArrayList<LichDayChinh> result = new ArrayList<>();
        for(LopHoc lopHoc : listLopHoc){
            for(LichDayChinh lichDay: listLichDay){
                if(lopHoc.getMaLopHoc() == lichDay.getMaLopHoc()){
                    result.add(lichDay);
                }
            }
        }
        if(result.size() == 0){
            return null;
        }
        else {
            return result;
        }
    }
    
}
