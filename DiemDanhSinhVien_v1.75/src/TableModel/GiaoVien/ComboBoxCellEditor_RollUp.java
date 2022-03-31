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
import javax.swing.table.TableCellEditor;

/**
 *
 * @author chuon
 */
// editor kieu combobox danh cho cac column diem danh
public class ComboBoxCellEditor_RollUp extends DefaultCellEditor {

    private static String[] chuThichDiemDanh = new String[]{MyEnum.CO_MAT.getChuThichEditorComBoBox(),
        MyEnum.HOC_MUON.getChuThichEditorComBoBox(), MyEnum.VANG_KO_PHEP.getChuThichEditorComBoBox(),
        MyEnum.VANG_CO_PHEP.getChuThichEditorComBoBox()};

    private static final JComboBox EDITOR_COMBOBOX = new JComboBox(chuThichDiemDanh);

    boolean cellEditingStopped = false;

    public ComboBoxCellEditor_RollUp() {
        super(EDITOR_COMBOBOX);
        EDITOR_COMBOBOX.setRenderer(new ComboBoxRenderer_RollUp());
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
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        if (o instanceof Integer) {
            int selectedComboBoxEditorIndex = (int) o;
            EDITOR_COMBOBOX.setSelectedIndex(selectedComboBoxEditorIndex);
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
