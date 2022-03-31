/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import Model.MyEnum;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 *
 * @author chuon
 */

public class ComboBoxRenderer_RollUp extends DefaultListCellRenderer {

    private Color background = new Color(0, 100, 255, 15);
    private Color defaultBackground = (Color) UIManager.get("List.background");

    public ComboBoxRenderer_RollUp() {
        
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof String){
            String item = String.valueOf(value);
            setText(item);
            int idDiemDanh = MyEnum.getIDDiemDanh(item);
            setIcon(MyEnum.getIcon(idDiemDanh));
        }
        if (!isSelected) {
            this.setBackground(index % 2 == 0 ? background : defaultBackground);
        }
        return this;

    }

}