/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnusedModel;

import TableModel.GiaoVien.RollUpStudentTableModel;
import Model.GiaoVien.ListBuoiHoc;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chuon
 */
// renderer nay danh cho jtable column = String.Class + Interger.Class va co tuong tac voi soBuoiVang
public class BooleanLookUpRenderer extends JCheckBox implements TableCellRenderer {
    private static final Color firstRowBackgroundColor = new Color(0xE8F2FE); //light blue
    private static final Color secondRowBackgroundColor = new Color(0xFFFFFF) ;
    private static final Color rowForegroundColor = Color.BLACK;
    private static final Color hightLightForegroundColor = Color.WHITE;
    private static final Color hightLightBackgroundColor = new Color(255, 77, 77);
    private static final Color selectedForegroundColor = Color.WHITE;
    private static final Color selectedBackgroundColor = new Color(51, 204, 255);
    private ListBuoiHoc listTongBuoiHoc;
    private int soBuoiVang;
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public BooleanLookUpRenderer(ListBuoiHoc listTongBuoiHoc, int soBuoiVang) {
        setLayout(new GridBagLayout());
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalAlignment(CENTER);
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
        if (value instanceof Boolean) {
            setSelected((Boolean) value);
        }
        if (!isSelected) {
            if (row % 2 == 1) {
                setBackground(firstRowBackgroundColor);
            } else {
                setBackground(secondRowBackgroundColor);
            }
            setForeground(rowForegroundColor);
            // soBuoiVang == -1 thi ko hightLight row
            if (soBuoiVang != -1) {
                RollUpStudentTableModel model = (RollUpStudentTableModel) table.getModel();
                int columnMSSV = 4;
                String mssv = (String) model.getValueAt(row, columnMSSV);
                boolean isHightLighted = listTongBuoiHoc.kiemTraSVVangKoPhep(mssv, soBuoiVang);
                if (isHightLighted) {
                    setBackground(hightLightBackgroundColor);
                    setForeground(hightLightForegroundColor);
                }
            }
        } else {
            setForeground(selectedForegroundColor);
            setBackground(selectedBackgroundColor);
        }

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }
        return this;
    }

}
