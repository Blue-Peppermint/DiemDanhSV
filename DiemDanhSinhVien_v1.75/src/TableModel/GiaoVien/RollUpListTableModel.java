/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListLopHoc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chuon
 */
public class RollUpListTableModel extends AbstractTableModel{

    private ListLopHoc listLopHoc; // lay thong tin cac lopHoc hoc ma GV day + vai tro GV trong lopHoc
    private static ListBuoiHoc listBuoiHoc; // chua maso lop hoc + thong tin diem danh    
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;
    
    public RollUpListTableModel(ListLopHoc listLopHoc, ListBuoiHoc listBuoiHoc) {
        this.listLopHoc = listLopHoc;
        this.listBuoiHoc = listBuoiHoc;
        headerNames = new ArrayList<>();
        headerNames.add("STT");
        headerNames.add("Mã Lớp Học");
        headerNames.add("Tên Môn Học");
        headerNames.add("Lớp");
        headerNames.add("Nhóm");
        headerNames.add("Ngày");
        headerNames.add("Ca");
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
        return listBuoiHoc.getListBuoiHoc().size();
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BuoiHoc buoiHoc = listBuoiHoc.getListBuoiHoc().get(rowIndex);
        String maLopHoc = buoiHoc.getMaLopHoc();        
        if (columnIndex == 0) {           
            return rowIndex + 1;
        } else if (columnIndex == 1) {          
            return maLopHoc;
        } else if (columnIndex == 2) {            
            String tenMonHoc = listLopHoc.getLopHoc(maLopHoc).getTenMonHoc();            
            return tenMonHoc;
        } else if (columnIndex == 3) {
            String lop = listLopHoc.getLopHoc(maLopHoc).getLop();
            return lop;
        } else if (columnIndex == 4) {
            String nhom = buoiHoc.getTenNhom();
            return nhom;
        }
        else if (columnIndex == 5) {
            String ngay = new SimpleDateFormat("EEEE, dd/MM/yyyy").format(buoiHoc.getNgayHoc());
            return ngay;
        }
        else if (columnIndex == 6) {
            String ca ="";
            if(buoiHoc.getCa() == 0){
                ca = "Sáng";
            }
            else{
                ca = "Chiều";
            }
            return ca;
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return mClasses.get(i);
    }

    @Override
    public String getColumnName(int i) {
        return headerNames.get(i);
    }
    
    
}
