/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnusedModel;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author chuon
 */
// renderer nay danh cho jtable column = String.Class + Interger.Class
public class ObjectRenderer extends DefaultTableCellRenderer {

//    // Mode CELL: To Mau cho Cell, ROW: To mau cho Row khi select value
//    public static enum Mode {
//        CELL, ROW
//    }
//    private final Mode mode;
    private static final Color firstRowBackgroundColor = new Color(0xE8F2FE); //light blue
    private static final Color secondRowBackgroundColor = new Color(0xFFFFFF);
    private static final Color rowForegroundColor = Color.BLACK;
    private static final Color seletectedForegroundColor = Color.WHITE;
    private static final Color seletectedBackgroundColor = new Color(51, 204, 255);
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public ObjectRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
                // isSelected vs hasFocus: + đều giống nhau ở chỗ, giá trị sẽ thay đổi phụ thuộc vào row mình chọn
        // + khác nhau: Lúc renderer jtable thì sẽ render lần lượt từng column trong row, hết column thì xuống row kế tiếp
        // thì giá trị của isSelected + hasFocus sẽ không giống nhau mỗi lần render từng cell như vậy, cụ thể:
        // isSelected luôn true với mọi column ở trong row được chọn
        // hasFocus chỉ true với một mình column ở trong row được chọn (chỉ true với 1 cell được chọn thôi)
        // bởi vì căn bản khi chọn vào 1 row nào đó trong jtable thì sẽ có 2 hiệu ứng: set UI hàng được chọn(isSelected), set UI cell được chọn (hasFocus)
        // setFont + RowHeight
        setFont(new java.awt.Font("Tahoma", 0, 18));
        if (value instanceof Integer) {
            setText(String.valueOf(value));
        } else if (value instanceof String) {
            setText((String) value);
        }
        if (!isSelected) {
            if (row % 2 == 1) {
                setBackground(firstRowBackgroundColor);
            } else {
                setBackground(secondRowBackgroundColor);
            }
            setForeground(rowForegroundColor);
        } else {
            setForeground(seletectedForegroundColor);
            setBackground(seletectedBackgroundColor);
        }

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }

        return this;

    }
}
