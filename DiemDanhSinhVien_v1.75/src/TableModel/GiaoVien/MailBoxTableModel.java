/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.ListMailStudent;
import Model.GiaoVien.ListNhomSV;
import Model.GiaoVien.MailStudent;
import Model.GiaoVien.NhomSV;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chuon
 */
public class MailBoxTableModel extends AbstractTableModel{
    private ListLopHoc listLopHoc;
    private ListNhomSV listNhomSV; // xac dinh listMSSV trong Mail thuoc Nhom nao?
    private static ListMailStudent listMail; // lay thong tin cac thu GV da gui cho SV
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;

    public MailBoxTableModel(ListLopHoc listLopHoc, ListNhomSV listNhomSV, ListMailStudent listMail) {
        this.listLopHoc = listLopHoc;
        this.listNhomSV = listNhomSV;
        this.listMail = listMail;
        this.listMail.sortDescendingListMail();
        headerNames = new ArrayList<>();
        headerNames.add("STT");
        headerNames.add("Mã Lớp MH");
        headerNames.add("Tên Môn Học");
        headerNames.add("Lớp");
        headerNames.add("Gửi Đến");
        headerNames.add("Nội Dung");
        headerNames.add("Thời Gian Gửi");
        mClasses = new ArrayList<>();
        mClasses.add(Integer.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);    
    }
    
    
    @Override
    public int getRowCount() {
        int mailCount = listMail.getListMail().size();
        return mailCount;
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // de code nay chay on thi minh doan cach sap xep du lieu phai co format thong nhat + format show thong nhat
        // dac biet cach sap xep mssv, sv hop ly ( doc columnIndex == 4 de hieu ro chi tiet )
        MailStudent mail = listMail.getListMail().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return mail.getMaLopHoc();
            case 2:
                String tenMonHoc = listLopHoc.getLopHoc(mail.getMaLopHoc()).getTenMonHoc();
                return tenMonHoc;
            case 3:
                String lop = listLopHoc.getLopHoc(mail.getMaLopHoc()).getLop();
                return lop;
            case 4:
                // neu gui den toan bo nhom nao do thi se the hien ra: nhom LT, nhom TH1 ,...
                // neu gui den nhung ca nhan gv chon thi se the hien ra: N18DCCN025, N18DCCN037
                // neu GV gui den cac ca nhan tu chon
                if(mail.isThongBaoTuDo()){
                    String listMSSV = "";
                    for(String mssv : mail.getListMSSV()){
                        listMSSV += mssv +" ";
                    }
                    return listMSSV;
                }
                else{
                    String group = searchWhichGroup(mail);
                    // neu GV gui den 1 nhom LT, TH1, ...
                    if( group != ""){
                        return group;
                    }
                }   break;
            case 5:
                return mail.getNoiDung();
            case 6:
                String daySent = new SimpleDateFormat("EEEE, dd/MM/yyyy - hh:mm:ss a").format(mail.getNgayGui());
                return daySent;
            default:
                break;
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
    
    private ArrayList<NhomSV> getNhomSV(String maLopMH){
        // tra ve cac NhomSV thong qua maLopMH
        ArrayList<NhomSV> result = new ArrayList<>();
        for(NhomSV nhom: listNhomSV.getListNhomSV()){
            if(maLopMH.equals(nhom.getMaLopMH())){
                result.add(nhom);
            }
        }
        if(result.size() == 0){
            return null;
        }
        else{
            return result;
        }
    }
    
    private String searchWhichGroup(MailStudent mail){
        // kiem tra xem mail nay co gui cho 1 nhom ko? va nhom do ten la gi?
        // neu ko phai gui cho 1 nhom thi se tra ve ""
        String result ="";
        ArrayList<NhomSV> groupsCompare = getNhomSV(mail.getMaLopHoc());
        for(NhomSV group : groupsCompare){
            // khi so sanh 2 arrayList bang equals mac dinh, 
            //de chung bang nhau thi phai co cung size, cac phan tu theo index tuong ung cung phai bang nhau
            if(mail.getListMSSV().equals(group.getListMSSV())){
                result = group.getTenNhom();
                return result;
            }
        }       
        return result;
    }  
}
