/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

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
public class ListMail {

    private ArrayList<Mail> listMail;

    public ListMail() {
        this.listMail = new ArrayList<>();
    }

    public ListMail(ArrayList<Mail> listMail) {
        this.listMail = listMail;
    }

    public ArrayList<Mail> getListMail() {
        return listMail;
    }

    public void setListMail(ArrayList<Mail> listMail) {
        this.listMail = listMail;
    }

    public void sortDescendingListMail() {
        if (!listMail.isEmpty()) {
            Collections.sort(listMail, new Comparator<Mail>() {
                public int compare(Mail mail1, Mail mail2) {
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

    public Mail getMail(String ngayGui) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy - hh:mm:ss a");
        for (Mail mail : listMail) {
            String ngayGuiStr1 = ngayGui;
            String ngayGuiStr2 = simpleDateFormat.format(mail.getNgayGui());
            if (ngayGuiStr1.equals(ngayGuiStr2)) {
                return mail;
            }
        }
        return null;
    }

    public ArrayList<Mail> getListMail(Date ngayBDGui, Date ngayKTGui) {
        ArrayList<Mail> listMailKetQua = new ArrayList<>();
        for (Mail mail : listMail) {
            if (mail.getNgayGui().compareTo(ngayBDGui) >= 0
                    && mail.getNgayGui().compareTo(ngayKTGui) <= 0) {
                listMailKetQua.add(mail);
            }
        }
        return listMailKetQua;
    }

}
