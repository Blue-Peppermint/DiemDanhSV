/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.SinhVien;

import Model.HocKy;
import Model.MyEnum;
import Model.SinhVien.BuoiHoc;
import Model.SinhVien.ListBuoiHoc;
import Model.Tuan;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chuon
 */
// renderer danh cho RollUpCanderRender cua SV. Se hien thi cac buoi hoc GV day
// neu SV co mat se co mau hocBackgroundColor
// neu SV vang mat se co mau vangKoPhepBackgroundColor
public class RollUpCalendarRenderer_SV extends JLabel implements TableCellRenderer {
    
    private static final Color borderHighlightColor = new Color(242, 242, 242);
    private static final Color borderShadowColor = new Color(230, 255, 255);
    private static final Color normalBackgroundColor = new Color(242, 242, 242); // mau Background cua null value + Column 0
    private static final String normalForegroundColorStr = "#00a3cc";
    private static final String infoForegroundColorStr = "#000000"; // thong tin chi tiet ve Tag
    private static final String tagForegroundColorStr = "#626e5e"; // mau cua tag
    private static final Color hocBackgroundColor = new Color(255, 214, 153); // mau backGround cua buoiHoc SV có học
    private static final Color hocMuonBackgroundColor = Color.WHITE; // mau backGround cua buoi hoc SV di hoc muon
    private static final Color vangKoPhepBackgroundColor = Color.RED; // mau backGround cua buoiHoc SV vang mat Không Phép    
    private static final Color vangCoPhepBackgroundColor = Color.WHITE; // mau backGround cua buoiHoc SV vang mat Có Phép 
    private static final Color hightlightBackgroundColor = new Color(26, 178, 255);
    private ListBuoiHoc listBuoiHoc; // de lay data ngay, ca, diemDanh cua SV
    private Tuan tuan; // de lay data xac dinh dang render tuan nao, tu do suy ra ngay, ca cua gia tri se render
    
    public RollUpCalendarRenderer_SV(ListBuoiHoc listBuoiHoc, Tuan tuan) {
        this.listBuoiHoc = listBuoiHoc;
        this.tuan = tuan;
        Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED,borderHighlightColor,borderShadowColor);
        setVerticalAlignment(CENTER);
        setBorder(border);
        setOpaque(true);
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

    private Date congNgay(Date date, int soNgay) {
        long dateInMiliS = date.getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        Date ketqua;
        ketqua = new Date(dateInMiliS + soNgay * oneDayinMiliS);
        return ketqua;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        //setText((value == null) ? "" : value.toString());
        if (value == null) {
            setText("");
            setBackground(normalBackgroundColor);
        } else{
            // neu la cot Sang + Chieu
            if (column == 0) {
                setText("<html><Strong><font color=" + normalForegroundColorStr + " size='5'>" + value.toString() + "</font></Strong></html>");
                //setBackground(new Color(230, 255, 255));
                setBackground(normalBackgroundColor);
            } else if (!listBuoiHoc.getListBuoiHoc().isEmpty() && tuan != null) {
                String[] buoiHocArray = getStrings(value.toString());
                String tenMonHoc = buoiHocArray[0];
                String lop = buoiHocArray[1];
                String tenNhom = buoiHocArray[2];
                setText("<html><strong><font size='4' color=" + infoForegroundColorStr + ">" + tenMonHoc + "</font><br>"
                        + "<i color=" + tagForegroundColorStr + ">Lớp: </i><font size='4' color=" + infoForegroundColorStr + ">" + lop + "</font><br>"
                        + "<i color=" + tagForegroundColorStr + ">Nhóm: </i><font size='4' color=" + infoForegroundColorStr + ">" + tenNhom + "</font></strong></html>");
                int ca = row;
                Date ngayHoc = congNgay(tuan.getNgayBDTuan(), column - 1);
                BuoiHoc buoiHoc = listBuoiHoc.getBuoiHoc(ngayHoc, ca);
                if (buoiHoc != null) {
                    setBackground(MyEnum.getColorDaiDien(buoiHoc.getiDdiemDanh()));
                } else {
                    System.out.println("RollUpCalendarRenderer_SV -> Loi buoiHoc ko ton tai");
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
