/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.MyEnum;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author chuon
 */
public class TblRenderer_ExportExcelSetting extends DefaultTableCellRenderer{
    
    private static int COLUMN_LOAI_DIEM_DANH = 0;
    private static int COLUMN_KY_HIEU_DIEM_DANH = 1;
    private static int COLUMN_BACKGROUND_COLOR = 2;
    private static final Color firstRowBackgroundColor = new Color(0xE8F2FE); //light blue
    private static final Color secondRowBackgroundColor = new Color(0xFFFFFF);
    private static final Color selectedForegroundColor = Color.WHITE;
    private static final Color selectedBackgroundColor = new Color(51, 204, 255);

    public TblRenderer_ExportExcelSetting() {
        setOpaque(true);
        setFont(new java.awt.Font("Tahoma", 0, 22));
        setForeground(Color.BLACK);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(Color.WHITE);
//        if (!isSelected) {
//            if (row % 2 == 1) {
//                setBackground(firstRowBackgroundColor);
//            } else {
//                setBackground(secondRowBackgroundColor);
//            }
//        } else {
//            setBackground(selectedBackgroundColor);
//        }
        if (value != null) {
            if (column == COLUMN_LOAI_DIEM_DANH) {
                setHorizontalAlignment(LEFT);
                setText((String) value);
            } else if (column == COLUMN_KY_HIEU_DIEM_DANH) {
                setHorizontalAlignment(CENTER);
                setText((String) value);
            } else if (column == COLUMN_BACKGROUND_COLOR) {
                setHorizontalAlignment(CENTER);
                String kyHieuDiemDanh = (String) table.getValueAt(row, COLUMN_KY_HIEU_DIEM_DANH);
                setText(kyHieuDiemDanh);
                // BRIGHT_GREEN: 102, 255, 102
                // YELLOW1: 255, 255, 153          
                // RED: Color.RED
                // GREY_25_PERCENT: 204, 204, 204
                // SKY_BLUE: 128, 212, 255
                // GOLD: 255, 221, 153
                // LIGHT_ORANGE: 255, 179, 26
                short indexColor = (short) value;
                if (indexColor == IndexedColors.WHITE.index) {
                    setBackground(Color.WHITE);
                } else if (indexColor == IndexedColors.BRIGHT_GREEN.index) {
                    setBackground(new Color(102, 255, 102));
                } else if (indexColor == IndexedColors.YELLOW1.index) {
                    setBackground(new Color(255, 255, 153));
                } else if (indexColor == IndexedColors.RED.index) {
                    setBackground(Color.RED);
                } else if (indexColor == IndexedColors.GREY_25_PERCENT.index) {
                    setBackground(new Color(204, 204, 204));
                } else if (indexColor == IndexedColors.SKY_BLUE.index) {
                    setBackground(new Color(128, 212, 255));
                } else if (indexColor == IndexedColors.GOLD.index) {
                    setBackground(new Color(255, 221, 153));
                } else if (indexColor == IndexedColors.LIGHT_ORANGE.index) {
                    setBackground(new Color(255, 179, 26));
                }
            }
        }        
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }
        return this;
    }
}
