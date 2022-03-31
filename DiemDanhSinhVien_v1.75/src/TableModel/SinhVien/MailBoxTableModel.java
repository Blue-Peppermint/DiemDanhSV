/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.SinhVien;

import Model.SinhVien.InfoTeacher;
import Model.SinhVien.ListInfoTeacher;
import Model.SinhVien.ListLopHoc;
import Model.SinhVien.ListMail;
import Model.SinhVien.Mail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chuon
 */
public class MailBoxTableModel extends AbstractTableModel {

    private ListLopHoc listLopHoc;
    private ListInfoTeacher listInfoTeacher;
    private static ListMail listMail; // lay thong tin cac thu SV nhan duoc tu GV
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;
    
    static int dem;
    public MailBoxTableModel(ListLopHoc listLopHoc, ListInfoTeacher listInfoTeacher, ListMail listMail) {
        this.listLopHoc = listLopHoc;
        this.listInfoTeacher = listInfoTeacher;
        this.listMail = listMail;
        this.listMail.sortDescendingListMail();
        headerNames = new ArrayList<>();
        headerNames.add("STT");
        headerNames.add("Tên Môn Học");
        headerNames.add("Lớp");
        headerNames.add("Giáo Viên Gửi");
        headerNames.add("Nội Dung");
        headerNames.add("Thời Gian Gửi");
        mClasses = new ArrayList<>();
        mClasses.add(Integer.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
    }

    @Override
    public int getRowCount() {
        if (!listMail.getListMail().isEmpty()) {
            int mailCount = listMail.getListMail().size();
            return mailCount;
        }
        else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // de code nay chay on thi minh doan cach sap xep du lieu phai co format thong nhat + format show thong nhat
        // dac biet cach sap xep mssv, sv hop ly ( doc columnIndex == 4 de hieu ro chi tiet )
        if (!listMail.getListMail().isEmpty()) {
            Mail mail = listMail.getListMail().get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    String tenMonHoc = listLopHoc.getLopHoc(mail.getMaLopHoc()).getTenMonHoc();
                    return tenMonHoc;
                case 2:
                    String lop = listLopHoc.getLopHoc(mail.getMaLopHoc()).getLop();
                    return lop;
                case 3:
                    String maGVGuiDen = mail.getMaGV();
                    for (InfoTeacher infoTeacher : listInfoTeacher.getListInfoTeacher()) {
                        if (infoTeacher.getMaGV().equalsIgnoreCase(maGVGuiDen)) {
                            return infoTeacher.getHoTen() + " - " + maGVGuiDen;
                        }
                    }
                    return "Lỗi Ko Lấy Được GV";
                case 4:
                    return mail.getNoiDung();
                case 5:
                    String daySent = new SimpleDateFormat("EEEE, dd/MM/yyyy - hh:mm:ss a").format(mail.getNgayGui());
                    return daySent;
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return mClasses.get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headerNames.get(columnIndex);
    }
}
