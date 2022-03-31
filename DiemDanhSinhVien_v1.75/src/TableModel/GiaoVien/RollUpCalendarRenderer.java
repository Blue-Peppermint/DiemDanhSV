/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListLopHoc;
import Model.Tuan;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**

  @author chuon
 */
// renderer danh cho table calendar co hightLight nhieu mau khac nhau
public class RollUpCalendarRenderer extends JLabel implements TableCellRenderer {

    private static final Color borderHighlightColor = new Color(242, 242, 242);
    private static final Color borderShadowColor = new Color(230, 255, 255);
    private static final Color normalBackgroundColor = new Color(242, 242, 242); // mau Background cua null value + Column 0
    private static final String normalForegroundColorStr = "#00a3cc";
    private static final String infoForegroundColorStr = "#000000"; // thong tin chi tiet ve Tag
    private static final String tagForegroundColorStr = "#626e5e"; // mau cua tag
    private static final Color existedBackgroundColor = new Color(255, 214, 153); // mau background cua buoiHoc da diem danh
    private static final Color noneExistBackgroundColor = new Color(179, 255, 255); // mau background cua lich day
    private static final Color hightlightBackgroundColor = new Color(26, 178, 255);
    private static Tuan tuan;
    private ListLopHoc listLopHoc;
    private ListBuoiHoc listBuoiHoc;

    public RollUpCalendarRenderer(Tuan tuan, ListLopHoc listLopHoc, ListBuoiHoc listBuoiHoc) {
        Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED,borderHighlightColor,borderShadowColor);
        setVerticalAlignment(CENTER);
        setBorder(border);
        setOpaque(true);
        this.tuan = tuan;
        this.listLopHoc = listLopHoc;
        this.listBuoiHoc = listBuoiHoc;
    }

    public void setTuan(Tuan tuan) {
        this.tuan = tuan;
    }
    
    private String[] getStrings(String BuoiHoc) {
        // tach lay thong tin can thiet trong chuoi lay tu table
        // vd Format:
        // Trí tuệ nhân tạo
        // D18CQCN01-N
        // Thực hành 1
        String[] ketQua = new String[3];
        for (int i = 0; i < ketQua.length; i++) {
            ketQua[i] = "";
        }
        int index = 0;
        for (int i = 0; i < BuoiHoc.length(); i++) {
            if (BuoiHoc.charAt(i) == '\n') {
                index++;
            } else {
                ketQua[index] += BuoiHoc.charAt(i);
            }
        }
        return ketQua;
    }

    private boolean checkExistBuoiHoc(String tenMonHoc, String lop,
            String tenNhom, Date ngayHoc, int ca) {
        // kiem tra info render co phai la 1 buoi hoc khong? hay chi la lich day thoi?
        for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
            String maLopHoc = listLopHoc.getLopHoc(tenMonHoc, lop).getMaLopHoc();
            if (buoiHoc.getMaLopHoc().equals(maLopHoc)
                    && buoiHoc.getTenNhom().equals(tenNhom)
                    && buoiHoc.getNgayHoc().equals(ngayHoc)
                    && buoiHoc.getCa() == ca) {
                return true;
            }
        }
        return false;
    }

    private Date congNgay(Date date, int soNgay) {
        long dateInMiliS = date.getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        Date ketqua;
        ketqua = new Date(dateInMiliS + soNgay * oneDayinMiliS);
        return ketqua;
    }

// <editor-fold>
//
//    public Component getTableCellRendererComponent(JTable table, Object value,
//            boolean isSelected, boolean hasFocus, int row, int column) {
//        setText((value == null) ? "" : value.toString());
//
//        if (value != null && column > 0) {
//            String[] buoiHocArray = getStrings(value.toString());
//            String tenMonHoc = buoiHocArray[0];
//            String lop = buoiHocArray[1];
//            String tenNhom = buoiHocArray[2];
//            Date ngayHoc = congNgay(tuan.getNgayBDTuan(), column - 1);
//            int ca = row;
//            if (checkExistBuoiHoc(tenMonHoc, lop, tenNhom, ngayHoc, ca)) {
//                setBackground(Color.RED);
//            } else {
//                setBackground(Color.CYAN);
//            }
//        } else {
//            setBackground(Color.WHITE);
//        }
//        
//        
//        
//        
//        if (hasFocus) {
//            // Neu Chon vao nhung Value column 0, thi set background cung mau voi mau cac gia tri null
//            if (column == 0) {
//                setBackground(Color.WHITE);
//            } else {
//                setBackground(Color.lightGray);
//            }
//
//        }
//        return this;
//    }
//
// </editor-fold>
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Font + RowHeight vói cellRenderer có extends JLabel thì khi setText theo kiểu HTML dưới đây
        // RowHeight của các cell sẽ được tự động căng chỉnh
        //setText((value == null) ? "" : value.toString());
        if (value == null) {
            setText("");
            setBackground(normalBackgroundColor);
        } else {
            // neu la cot Sang + Chieu
            if (column == 0) {
                setText("<html><Strong><font color=" + normalForegroundColorStr + " size='5'>" + value.toString() + "</font></Strong></html>");
                setBackground(normalBackgroundColor);
            } else {
                String[] buoiHocArray = getStrings(value.toString());
                String tenMonHoc = buoiHocArray[0];
                String lop = buoiHocArray[1];
                String tenNhom = buoiHocArray[2];
                Date ngayHoc = congNgay(tuan.getNgayBDTuan(), column - 1);
                int ca = row;
                // neu buoi hoc da duoc diem danh thi hightLight mau do
                if (checkExistBuoiHoc(tenMonHoc, lop, tenNhom, ngayHoc, ca)) {
                    setText("<html><strong><font size='4' color=" + infoForegroundColorStr + ">" + tenMonHoc + "</font><br>"
                            + "<i color=" + tagForegroundColorStr + ">Lớp: </i><font size='4' color=" + infoForegroundColorStr + ">" + lop + "</font><br>"
                            + "<i color=" + tagForegroundColorStr + ">Nhóm: </i><font size='4' color=" + infoForegroundColorStr + ">" + tenNhom + "</font></strong></html>");
                    setBackground(existedBackgroundColor);                                       
                } else {
                    setText("<html><strong><font size='4' color=" + infoForegroundColorStr + ">" + tenMonHoc + "</font><br>"
                            + "<i color=" + tagForegroundColorStr + ">Lớp: </i><font size='4' color=" + infoForegroundColorStr + ">" + lop + "</font><br>"
                            + "<i color=" + tagForegroundColorStr + ">Nhóm: </i><font size='4' color=" + infoForegroundColorStr + ">" + tenNhom + "</font></strong></html>");
                    setBackground(noneExistBackgroundColor);
                }
            }
        }
        if (hasFocus) {
            // Neu Chon vao nhung Value column 0, thi set background cung mau voi mau cac gia tri null
            if (column == 0) {
                setBackground(normalBackgroundColor);
            } else {
                setBackground(hightlightBackgroundColor);
            }

        }
        
        return this;
    }
}
