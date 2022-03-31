/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import Model.LichDayChinh;
import java.util.ArrayList;

/**
 *
 * @author chuon
 */
public class ListLichDayChinh {

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
}
