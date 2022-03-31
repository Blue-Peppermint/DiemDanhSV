/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.MyEnum;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author chuon
 */




public class ComboBoxCellEditor_ExportExcelSetting extends DefaultCellEditor {

    private static final Short[] COMBOBOX_ITEMS = new Short[]{IndexedColors.WHITE.index
    ,IndexedColors.BRIGHT_GREEN.index, IndexedColors.YELLOW1.index, IndexedColors.RED.index
    , IndexedColors.GREY_25_PERCENT.index, IndexedColors.SKY_BLUE.index,IndexedColors.GOLD.index
    ,IndexedColors.LIGHT_ORANGE.index};

    private static final JComboBox EDITOR_COMBOBOX = new JComboBox(COMBOBOX_ITEMS);

    boolean cellEditingStopped = false;

    public ComboBoxCellEditor_ExportExcelSetting() {
        super(EDITOR_COMBOBOX);
        EDITOR_COMBOBOX.setRenderer(new ComboBoxRenderer_ExportExcelSetting());
        // add 2 listener sau để ngăn chặn lúc không chọn gì trong comboBox cả cũng setValueAt + fireTableChange
        EDITOR_COMBOBOX.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    // gọi method stopCellEditing() với return true 
                    fireEditingStopped(); // ngừng việc edit, chạy vào method setValueAt 
                }
            }
        });
        EDITOR_COMBOBOX.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
                // set up truoc khi hien thi popUpMenu
                cellEditingStopped = false;
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                // sau khi popUpMenu bien mat
                cellEditingStopped = true;
                fireEditingCanceled(); // hủy việc edit = ko chạy vào method setValueAt
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) {
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return super.getCellEditorValue(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        if (o instanceof Short) {
            EDITOR_COMBOBOX.setSelectedItem((short) o);
        }
        return EDITOR_COMBOBOX;
    }

    @Override
    public boolean stopCellEditing() {
        // true: Dừng việc Edit lại, Thoát hoàn toàn khỏi DefaultCellEditor(Trạng Thái Mặc Định Ban Đầu)
        // false: Ko dừng việc Edit => Sẽ không bao giờ chuyển qua thao tác khác 
        return cellEditingStopped;
    }
}
