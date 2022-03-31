/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import Model.MyEnum;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class NewClass1 {
  public static void main(String[] argv) throws Exception {
    JTable table = new JTable();
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    model.addColumn("A", new Object[] { "item1" });
    model.addColumn("B", new Object[] { "item2" });

    String[] values = new String[] { "1", "2", "3" };

    TableColumn col = table.getColumnModel().getColumn(0);
    col.setCellEditor(new MyComboBoxEditor());
    //col.setCellRenderer(new MyComboBoxRenderer(values));
  }
}

class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
    
        private static final String[] diemDanh = new String[]{MyEnum.CO_MAT.getChuThichEditorComBoBox(),
        MyEnum.HOC_MUON.getChuThichEditorComBoBox(), MyEnum.VANG_CO_PHEP.getChuThichEditorComBoBox(),
        MyEnum.VANG_KO_PHEP.getChuThichEditorComBoBox()};
    
//  public MyComboBoxRenderer(String[] items) {
//    super(items);
//  }
    public MyComboBoxRenderer() {
        super(diemDanh);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setFont(new java.awt.Font("Tahoma", 0, 22));
        if (value instanceof Integer) {
            int cellValue = (int) value;
            setSelectedItem(MyEnum.getChuThichEditorComBoBox(cellValue));
        }
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            //super.setBackground(table.getSelectionBackground());
            setBackground(Color.LIGHT_GRAY);
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
    }
}

class MyRenderer extends DefaultTableCellRenderer{

    public MyRenderer() {
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        setBackground(Color.WHITE);
        if(o instanceof Integer){
            int cellValue = (int)o;
            setText(MyEnum.getChuThichEditorComBoBox(cellValue));
        }
        return this;
    }
    
}

class MyComboBoxEditor extends DefaultCellEditor {

    private static final String[] diemDanh = new String[]{MyEnum.CO_MAT.getChuThichEditorComBoBox(),
        MyEnum.HOC_MUON.getChuThichEditorComBoBox(), MyEnum.VANG_CO_PHEP.getChuThichEditorComBoBox(),
        MyEnum.VANG_KO_PHEP.getChuThichEditorComBoBox()};
    private static JComboBox combo = new JComboBox(diemDanh);
    boolean cellEditingStopped = false;
        
    public MyComboBoxEditor() {
        super(combo);
        // add 2 listener sau để ngăn chặn lúc không chọn gì trong comboBox cả cũng setValueAt + fireTableChange
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){                   
                    // gọi method stopCellEditing() với return true 
                    fireEditingStopped(); // ngừng việc edit, chạy vào method setValueAt 
                }
            }
        });
        combo.addPopupMenuListener(new PopupMenuListener() {
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

//    @Override
//    public Object getCellEditorValue() {
//        return combo.getSelectedItem();
//    }

      
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        if (o instanceof Integer) {
            int cellValue = (int) o;
            combo.setSelectedIndex(cellValue);
        }       
        return combo;
    }

    
    
    @Override
    public boolean stopCellEditing() {
        // true: Dừng việc Edit lại, Thoát hoàn toàn khỏi DefaultCellEditor(Trạng Thái Mặc Định Ban Đầu)
        // false: Ko dừng việc Edit => Sẽ không bao giờ chuyển qua thao tác khác 
        return cellEditingStopped;
    }
    

    
}



class myTblModel extends AbstractTableModel{
    
    private ArrayList<Prototype> values;
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;
    
    public myTblModel(ArrayList<Prototype> values) {
        this.values = values;
        headerNames = new ArrayList<>();
        headerNames.add("Mot");
        headerNames.add("Hai");
        headerNames.add("Ba");
        mClasses = new ArrayList<>();
        mClasses.add(Object.class);
        mClasses.add(Object.class);
        mClasses.add(Object.class);
    }
    
    
    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        //System.out.println("getValueAt: " + String.valueOf(values.get(col)));
        Prototype pTmp = values.get(row);
        if(col == 0){
            return pTmp.a;
        }
        if(col == 1){
            return pTmp.b;
        }
        if(col == 2){
            return pTmp.c;
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int row, int col) {
        if (o instanceof String) {
            int selectedIDDiemDanh = MyEnum.getIDDiemDanh((String) o);
            Prototype pTmp = values.get(row);
            if (col == 0) {
                 pTmp.a = selectedIDDiemDanh;
            }
            if (col == 1) {
                pTmp.b = selectedIDDiemDanh;
            }
            if (col == 2) {
                pTmp.c = selectedIDDiemDanh;
            }
            fireTableCellUpdated(row, col);
        }                 
    }
       
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

}
