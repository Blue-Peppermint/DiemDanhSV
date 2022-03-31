/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListInfoStudent;
import Model.InfoStudent;
import Model.MyEnum;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chuon
 */
public class RollUpStudentTableModel extends AbstractTableModel {

    private static ListBuoiHoc listBuoiHoc;
    private ListInfoStudent listStudents;
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;
    // boolean chinhSua = true neu cho phep column diem danh editable
    // boolean chinhSua = false neu khong cho phep editable
    private boolean chinhSua;
    // dataChanged = true neu user co thay doi cac column diem danh va false khi nguoc lai      
    private boolean dataChanged;

    public RollUpStudentTableModel(ListBuoiHoc listBH, ListInfoStudent listS, boolean chinhSua) {
        // listBH la file goc, khong duoc phep thay doi. Nhung thay doi buoiHoc se duoc thuc hien tren listBuoiHoc
        // copy buoiHoc listStudents tu listBH
        listStudents = listS;
        // khoi tao clone listBuoiHoc
        this.listBuoiHoc = new ListBuoiHoc();
        for (BuoiHoc tmp : listBH.getListBuoiHoc()) {
            listBuoiHoc.getListBuoiHoc().add(tmp.clone());
        }
        listBuoiHoc.sortAscendingListBuoiHoc();
        // khoi tao so luong, kieu du lieu header column
        headerNames = new ArrayList<>();
        headerNames.add("STT");
        headerNames.add("HỌ");
        headerNames.add("TÊN");
        headerNames.add("GIỚI TÍNH");
        headerNames.add("MSSV");
        headerNames.add("LỚP");
        mClasses = new ArrayList<>();
        mClasses.add(Integer.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        mClasses.add(String.class);
        // kiem tra so luong column kieu String se them
        // moi thuoc tinh ngayHoc + ca trong cac phan tu khac biet se tao ra 1 column moi
        //List<Date> listHeader = getDistinctNgayHoc();
        ArrayList<BuoiHoc> listHeader = getListBuoiHoc();
        for (BuoiHoc buoiHoc : listHeader) {
            String ca = "";
            if (buoiHoc.getCa() == 0) {
                ca = "SA";
            } else {
                ca = "CH";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String columnName = simpleDateFormat.format(buoiHoc.getNgayHoc()) + " " + ca;
            headerNames.add(columnName);
            mClasses.add(Object.class);
        }
        this.chinhSua = chinhSua;
    }

    public ArrayList<BuoiHoc> getListBuoiHoc() {
        return this.listBuoiHoc.getListBuoiHoc();
    }

    public boolean isDataChanged() {
        return dataChanged;
    }

    @Override
    public int getRowCount() {
        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
            return listBuoiHoc.getListBuoiHoc().get(0).getListDiemDanhSV().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    private static int dem = 0;
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // de code nay chay on thi minh doan cach sap xep du lieu phai co format thong nhat + format show thong nhat
//<editor-fold defaultstate="collapsed" desc="preventive Plan">
//        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
//            String mssv = listBuoiHoc.getListBuoiHoc().get(0).getListDiemDanhSV().get(rowIndex).getMaSoSV();
//            if (columnIndex == 0) {
//                return rowIndex + 1;
//            } else if (columnIndex == 1) {
//                String ho = listStudents.getStudent(mssv).getHo();
//                return ho;
//            } else if (columnIndex == 2) {
//                String ten = listStudents.getStudent(mssv).getTen();
//                return ten;
//            } else if (columnIndex == 3) {
//                String gioiTinh = listStudents.getStudent(mssv).getGioiTinh();
//                return gioiTinh;
//            } else if (columnIndex == 4) {
//                return mssv;
//            } else if (columnIndex == 5) {
//                String lop = listStudents.getStudent(mssv).getLopNC();
//                return lop;
//            } // column > 5: ngay diem danh
//            else if (columnIndex > 5) {
//                // tim diemdanh cua mssv do, vao ngay tuong ung
//                int buoiHocIndex = columnIndex - 6;
//                BuoiHoc buoiHoc = listBuoiHoc.getListBuoiHoc().get(buoiHocIndex);
//                int idDiemDanh = buoiHoc.getListDiemDanhSV().get(rowIndex).getiDdiemDanh();
//                return idDiemDanh;
//            }
//        } else {
//            InfoStudent info = listStudents.getListInfoStudent().get(rowIndex);
//            if (columnIndex == 0) {
//                return rowIndex + 1;
//            } else if (columnIndex == 1) {
//                String ho = info.getHo();
//                return ho;
//            } else if (columnIndex == 2) {
//                String ten = info.getTen();
//                return ten;
//            } else if (columnIndex == 3) {
//                String gioiTinh = info.getGioiTinh();
//                return gioiTinh;
//            } else if (columnIndex == 4) {
//                return info.getMssv();
//            } else if (columnIndex == 5) {
//                String lop = info.getLopNC();
//                return lop;
//            }
//        }
//</editor-fold>
        
        InfoStudent info = listStudents.getListInfoStudent().get(rowIndex);
        if (columnIndex == 0) {
            return rowIndex + 1;
        } else if (columnIndex == 1) {
            String ho = info.getHo();
            return ho;
        } else if (columnIndex == 2) {
            String ten = info.getTen();
            return ten;
        } else if (columnIndex == 3) {
            String gioiTinh = info.getGioiTinh();
            return gioiTinh;
        } else if (columnIndex == 4) {
            return info.getMssv();
        } else if (columnIndex == 5) {
            String lop = info.getLopNC();
            return lop;
        } // column > 5: ngay diem danh
        else if (!listBuoiHoc.getListBuoiHoc().isEmpty() && columnIndex > 5) {
            // tim diemdanh cua mssv do, vao ngay tuong ung
            int buoiHocIndex = columnIndex - 6;
            BuoiHoc buoiHoc = listBuoiHoc.getListBuoiHoc().get(buoiHocIndex);
            int idDiemDanh = buoiHoc.getListDiemDanhSV().get(rowIndex).getiDdiemDanh();
            return idDiemDanh;
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
//    // column 1: MSSV
//        if(columnIndex == 0){
//            String mssv = (String) getValueAt(rowIndex,columnIndex);
//            for(BuoiHoc buoiHoc : listBuoiHoc){                
//                buoiHoc.getSVDiemDanh(mssv).setMaSoSV((String) o);
//            }
//        }
        // column > 5: ngay diem danh
        if (columnIndex > 5 && !listBuoiHoc.getListBuoiHoc().isEmpty()) {
            // tim diemdanh cua mssv do, vao ngay tuong ung
            int buoiHocIndex = columnIndex - 6;
            BuoiHoc buoiHoc = listBuoiHoc.getListBuoiHoc().get(buoiHocIndex);
            int selectedEditValue = MyEnum.getIDDiemDanh((String) o);
            buoiHoc.getListDiemDanhSV().get(rowIndex).setiDdiemDanh(selectedEditValue);
            dataChanged = true;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (this.chinhSua && columnIndex > 5 && !listBuoiHoc.getListBuoiHoc().isEmpty()) {
            return true;
        }
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

//    private List<Date> getDistinctNgayHoc() {
//        // tra ve danh sach cac column header can them 
//        ArrayList<Date> listKetQua = new ArrayList<>();
//        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
//            boolean exist = false;
//            for (BuoiHoc data : listBuoiHoc.getListBuoiHoc()) {
//                exist = false;
//                for (Date date : listKetQua) {
//                    if (data.getNgayHoc().equals(date)) {
//                        exist = true;
//                    }
//                }
//                if (exist == false) {
//                    listKetQua.add(data.getNgayHoc());
//                }
//            }
//        }
//
//        return listKetQua;
//    }
    // method nay hinh nhu bi thua. Boi vi cac buoi hoc lay tren sql da hoan toan khac nhau san roi
    public ArrayList<BuoiHoc> getDistinctBuoiHoc() {
        // tra ve danh sach cac buoi hoc khac nhau, theo ngay + ca
        ArrayList<BuoiHoc> listKetQua = new ArrayList<>();
        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
            boolean existAlready = false;
            for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
                existAlready = false;
                for (BuoiHoc data : listKetQua) {
                    if (buoiHoc.getNgayHoc().equals(data.getNgayHoc())
                            && buoiHoc.getCa() == data.getCa()) {
                        existAlready = true;
                    }
                }
                if (existAlready == false) {
                    listKetQua.add(buoiHoc);
                }
            }
        }
        return listKetQua;
    }
}
