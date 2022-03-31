/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chuon
 */

// Renderer danh cho cac table Calendar binh thuong
public class CalendarRenderer1 extends JLabel implements TableCellRenderer {

    public static enum Mode{
        TIMETABLE, ROLLUPCALENDAR
    };
    private final Mode mode;
    private static final Color borderHighlightColor = new Color(242, 242, 242);
    private static final Color borderShadowColor = new Color(230, 255, 255);
    private static final Color normalBackgroundColor = new Color(242, 242, 242); // mau Background cua null value + Column 0
    private static final String normalForegroundColorStr = "#00a3cc";
    private static final String infoForegroundColorStr = "#000000"; // thong tin chi tiet ve Tag
    private static final String tagForegroundColorStr = "#626e5e"; // mau cua tag
    private static final Color valueTimeTableBackgroundColor = new Color(179, 255, 255);
    private static final Color valueRollUpBackgroundColor = new Color(255, 214, 153);
    private static final Color hightlightBackgroundColor = new Color(26, 178, 255);
    
    public CalendarRenderer1(Mode mode) {
        this.mode = mode;
        Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED,borderHighlightColor,borderShadowColor);
        setVerticalAlignment(CENTER);
        setBorder(border);
        setOpaque(true);
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

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Font + RowHeight vói cellRenderer có extends JLabel thì khi setText theo kiểu HTML dưới đây
        // RowHeight của các cell sẽ được tự động căng chỉnh
        if (value == null) {
            setText("");
            setBackground(normalBackgroundColor);
        } else {
            // neu la cot Sang + Chieu
            if (column == 0) {
                setText("<html><Strong><font color=" + normalForegroundColorStr + " size='5'>" + value.toString() + "</font></Strong></html>");
                //setBackground(new Color(230, 255, 255));
                setBackground(normalBackgroundColor);
            } else {
                String[] buoiHocArray = getStrings(value.toString());
                String tenMonHoc = buoiHocArray[0];
                String lop = buoiHocArray[1];
                String tenNhom = buoiHocArray[2];               
                setText("<html><strong><font size='4' color="+infoForegroundColorStr+">" + tenMonHoc + "</font><br>"
                        + "<i color="+tagForegroundColorStr+">Lớp: </i><font size='4' color="+infoForegroundColorStr+">" + lop + "</font><br>"
                        + "<i color="+tagForegroundColorStr +">Nhóm: </i><font size='4' color="+infoForegroundColorStr+">" + tenNhom + "</font></strong></html>");
                //setBackground(new Color(255, 255, 153));
                //setBackground(new Color(230, 255, 255));
                if(mode == Mode.TIMETABLE){
                    setBackground(valueTimeTableBackgroundColor);
                }
                else{
                    setBackground(valueRollUpBackgroundColor);
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
