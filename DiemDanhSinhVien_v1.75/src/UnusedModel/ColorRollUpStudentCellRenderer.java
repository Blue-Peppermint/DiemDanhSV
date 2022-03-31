/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnusedModel;

import Model.GiaoVien.ListBuoiHoc;
import TableModel.GiaoVien.RollUpStudentTableModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chuon
 */

// class nay hinh nhu bi thua
// cell renderer danh rieng cho LookUpRollUpStudentDlg
public class ColorRollUpStudentCellRenderer extends DefaultTableCellRenderer {

    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private ListBuoiHoc listTongBuoiHoc;
    private int soBuoiVang;

    public ColorRollUpStudentCellRenderer(ListBuoiHoc listTongBuoiHoc, int soBuoiVang) {
        setOpaque(true);
        this.listTongBuoiHoc = listTongBuoiHoc;
        this.soBuoiVang = soBuoiVang;
    }

    public void setSoBuoiVang(int soBuoiVang) {
        this.soBuoiVang = soBuoiVang;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Integer) {
            setText(String.valueOf(value));
        } else if (value instanceof String) {
            setText((String) value);
        }

        if (!isSelected) {
            if (row % 2 == 1) {
                setBackground(new Color(0xE8F2FE)); //light blue
            } else {
                setBackground(new Color(0xFFFFFF));
            }
            setForeground(Color.black);
            // soBuoiVang == -1 thi ko hightLight row
            if (soBuoiVang != -1) {
                RollUpStudentTableModel model = (RollUpStudentTableModel) table.getModel();
                String mssv = (String) model.getValueAt(row, 3);
                boolean isHightLighted = listTongBuoiHoc.kiemTraSVVangKoPhep(mssv, soBuoiVang);
                if (isHightLighted) {
                    setBackground(new Color(255, 77, 77));
                    setForeground(Color.WHITE);
                }
            } else {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
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
