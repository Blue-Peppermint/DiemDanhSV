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
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author chuon
 */
public class ComboBoxRenderer_ExportExcelSetting extends DefaultListCellRenderer {

    private Color background = new Color(0, 100, 255, 15);
    private Color defaultBackground = (Color) UIManager.get("List.background");

    public ComboBoxRenderer_ExportExcelSetting() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof Short){
            // BRIGHT_GREEN: 102, 255, 102
            // YELLOW1: 255, 255, 153          
            // RED: Color.RED
            // GREY_25_PERCENT: 204, 204, 204
            // SKY_BLUE: 128, 212, 255
            // GOLD: 255, 221, 153
            // LIGHT_ORANGE: 255, 179, 26
            short indexColor = (short) value;
            if (indexColor == IndexedColors.WHITE.index) {
                setText("WHITE");
                setBackground(Color.WHITE);
            } else if (indexColor == IndexedColors.BRIGHT_GREEN.index) {
                setText("GREEN");
                setBackground(new Color(102, 255, 102));
            } else if (indexColor == IndexedColors.YELLOW1.index) {
                setText("YELLOW");
                setBackground(new Color(255, 255, 153));
            } else if (indexColor == IndexedColors.RED.index) {
                setText("RED");
                setBackground(Color.RED);
            } else if (indexColor == IndexedColors.GREY_25_PERCENT.index) {
                setText("GREY");
                setBackground(new Color(204, 204, 204));
            } else if (indexColor == IndexedColors.SKY_BLUE.index) {
                setText("BLUE");
                setBackground(new Color(128, 212, 255));
            } else if (indexColor == IndexedColors.GOLD.index) {
                setText("GOLD");
                setBackground(new Color(255, 221, 153));
            } else if (indexColor == IndexedColors.LIGHT_ORANGE.index) {
                setText("ORANGE");
                setBackground(new Color(255, 179, 26));
            }
        }
        if (cellHasFocus) {
            setBorder(UIManager.getBorder(BorderFactory.createLineBorder(Color.BLACK)));
        } else {
            setBorder(noFocusBorder);
        }
        return this;

    }

}