/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import java.util.ArrayList;
import java.util.Date;

//class Tmp{
//    public static int x;
//    public static int y;
//
//    public Tmp() {
//        Tmp.x = -1;
//        Tmp.y = -1;
//    }
//
//    public static int getX() {
//        return x;
//    }
//
//    public static void setX(int x) {
//        Tmp.x = x;
//    }
//
//    public static int getY() {
//        return y;
//    }
//
//    public static void setY(int y) {
//        Tmp.y = y;
//    }
//    
//}
//
//    
//
//public class NewClass {
//    public static void main(String[] args) {
//        System.out.println("gia tri x: " +Tmp.getX() +"Gia tri y: "+Tmp.getY());
//        Tmp.setX(-1); Tmp.setY(-3);
//        System.out.println("gia tri x: " +Tmp.getX() +"Gia tri y: "+Tmp.getY());
//        
//    }
//}

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

class NewClass {

    public static void main(String[] args) {      

        JFrame jFrame = new JFrame();
        jFrame.setTitle("JTableHeaderHide Test");
        String[] columnNames = {"Name", "Age", "City"};
        Object[][] data = {{"Raja \nBro", "35", "Hyderabad"}, {"Adithya", "25", "Chennai"},
                {"Vineet", "23", "Mumbai"},
                {"Archana", "32", "Pune"},
                {"Krishna", "30", "Kolkata"}};
           DefaultTableModel dm = new DefaultTableModel() {
      public Class getColumnClass(int columnIndex) {
        return String.class;
      }
    };
           
        JTable table = new JTable(dm);
        
        dm.setColumnCount(4);
        dm.addColumn(new Object[]{
        "Name", "Age", "City"
        });
        
        dm.addRow(data[0]);
        dm.addRow(data[1]);
        table.setRowHeight(table.getRowHeight() * 3);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Cho phep table sap xep
        table.setAutoCreateRowSorter(true);
        
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());

        jFrame.add(scrollPane, BorderLayout.CENTER);
        jFrame.setSize(375, 250);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}



 class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

  public MultiLineCellRenderer() {
    setLineWrap(true);
    setWrapStyleWord(true);
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
      
//    if (isSelected) {
//      setForeground(table.getSelectionForeground());
//      setBackground(table.getSelectionBackground());
//    } else {
//      setForeground(table.getForeground());
//      setBackground(table.getBackground());
//    }
//    setFont(table.getFont());
//    if (hasFocus) {
//      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
//      if (table.isCellEditable(row, column)) {
//        setForeground(UIManager.getColor("Table.focusCellForeground"));
//        setBackground(UIManager.getColor("Table.focusCellBackground"));
//      }
//    } else {
//      setBorder(new EmptyBorder(1, 2, 1, 2));
//    }

    setEditable(false);
    setText((value == null) ? "" : value.toString());
    return this;
  }


}