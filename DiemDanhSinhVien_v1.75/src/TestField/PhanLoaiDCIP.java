/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import java.util.*;
import java.net.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PhanLoaiDCIP {

    public static Date ngayThuHai(Date date) {
        Date mondayOfWeek;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            mondayOfWeek = new Date(date.getTime() - 6 * oneDayinMiliS);
        } else {
            mondayOfWeek = new Date(date.getTime() - ((dayOfWeek - 2) * oneDayinMiliS));
        }
        return mondayOfWeek;
    }

    public static void main(String[] args) {

        Date now = new Date();

        long nows = now.getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        Date tommorow = new Date(nows + 3 * oneDayinMiliS);

        Date mondayOfWeek;
//    Calendar cal = Calendar.getInstance();
//    cal.setTime(tommorow);
//    
//    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//    if(dayOfWeek == 1){
//        mondayOfWeek  = new Date(tommorow.getTime() - 6 * oneDayinMiliS);
//    }
//    else {
//        mondayOfWeek  = new Date(tommorow.getTime() - ((dayOfWeek - 2) * oneDayinMiliS));
//    }
//    
//    
//    cal.setTime(mondayOfWeek);

        for (int i = 0; i < 12; i++) {
            tommorow = new Date(nows - i * oneDayinMiliS);
            mondayOfWeek = ngayThuHai(tommorow);
            //System.out.println("now: " + tommorow);
           // System.out.println("Monday of this week: " + mondayOfWeek);
        }
        
        int tmp= 0;
        for(int i = 0; i < 5; i++){
            System.err.println("tmp>>>>>>>>>>>>" + tmp);
            System.err.println("i:" + i);
            tmp = i;
        }

    }
}
