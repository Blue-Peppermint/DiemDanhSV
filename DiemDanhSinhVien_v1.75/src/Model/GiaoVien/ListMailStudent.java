/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author chuon
 */
public class ListMailStudent {

    private ArrayList<MailStudent> listMail;

    public ListMailStudent() {
        this.listMail = new ArrayList<>();
    }

    public ListMailStudent(ArrayList<MailStudent> listMail) {
        this.listMail = listMail;
    }

    public ArrayList<MailStudent> getListMail() {
        return listMail;
    }

    public void setListMail(ArrayList<MailStudent> listMail) {
        this.listMail = listMail;
    }

    public MailStudent getMailStudent(String ngayGui) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy - hh:mm:ss a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy - hh:mm:ss a");
        for (MailStudent mail : listMail) {
            String ngayGuiStr1 = ngayGui;
            String ngayGuiStr2 = simpleDateFormat.format(mail.getNgayGui());
            if (ngayGuiStr1.equals(ngayGuiStr2)) {
                return mail;
            }
        }
        return null;
    }

    public void sortDescendingListMail() {
        // sap xep theo giam dan ngay gui
        if (!listMail.isEmpty()) {
            Collections.sort(listMail, new Comparator<MailStudent>() {
                public int compare(MailStudent mail1, MailStudent mail2) {
                    Date ngayGui1 = mail1.getNgayGui();
                    Date ngayGui2 = mail2.getNgayGui();
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(ngayGui1);
                    cal2.setTime(ngayGui2);
                    if (cal1.after(cal2)) {
                        return -1;
                    } else if (cal1.before(cal2)) {
                        return 1;
                    } else if (cal1.equals(cal2)) {
                        return 0;
                    }
                    return 0;
                }
            });
        }

    }
    
    public ArrayList<MailStudent> getListMail(Date ngayBDGui, Date ngayKTGui){
        ArrayList<MailStudent> listMailKetQua = new ArrayList<>();
        for(MailStudent mail: listMail){
            if (mail.getNgayGui().compareTo(ngayBDGui) >= 0
                    && mail.getNgayGui().compareTo(ngayKTGui) <= 0) {
                listMailKetQua.add(mail);
            }
        }
        return listMailKetQua;
    }
}
