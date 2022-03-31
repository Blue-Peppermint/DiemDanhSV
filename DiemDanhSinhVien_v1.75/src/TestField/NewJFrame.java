/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import Model.GiaoVien.BuoiHoc;
import Model.*;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
class Tmp{
    // table: header
    // stt listB.1 ListB.2 ListB.3 ....
    int stt;
    ArrayList<Integer> listB;

    public Tmp() {
                this.stt = -1;
        this.listB = null;
    }

    public Tmp(int stt, ArrayList<Integer> listB) {
        this.stt = stt;
        this.listB = listB;
    }
 
    
    public Tmp copy(){
        Tmp newTmp = new Tmp();
        newTmp.stt = this.stt;
        newTmp.listB = new ArrayList<>();
        for(int i : listB){
            newTmp.listB.add(i);
        }
        return newTmp;
    }
    
}

class TblModelDiemDanhSV extends AbstractTableModel {
    private ArrayList<BuoiHoc> listData;
    private ArrayList<Class> mClasses;
    private ArrayList<String> headerNames;
    
    public TblModelDiemDanhSV(List<BuoiHoc> list){      
        // khoi tao clone listData
        listData = new ArrayList<>();
        for(BuoiHoc tmp : list){
            listData.add(tmp.clone());
        }
        listData.get(0).getListDiemDanhSV();
        headerNames = new ArrayList<>();
        headerNames.add("MSSV");
        mClasses = new ArrayList<>();
        mClasses.add(String.class);
        // kiem tra so luong column kieu String se them
        // Trong listData, moi thuoc tinh ngayHoc trong cac phan tu khac biet se tao ra 1 column moi
        List<Date> listHeader = danhSachHeaderNgay();
        for(Date i : listHeader){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");            
            headerNames.add(simpleDateFormat.format(i));
            mClasses.add(Boolean.class);
        }
    }
    
    @Override
    public int getRowCount() {
        return listData.get(0).getListDiemDanhSV().size();
    }

    @Override
    public int getColumnCount() {
        return headerNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // de code nay chay on thi minh doan cach sap xep du lieu phai co format thong nhat + format show thong nhat
        // column 1: MSSV
        if(columnIndex == 0){
            String mssv = listData.get(0).getListDiemDanhSV().get(rowIndex).getMaSoSV();
            return mssv;
        } // column > 2: ngay diem danh
        else if (columnIndex > 0) {
            // tim diemdanh cua mssv do, vao ngay tuong ung
            int buoiHocIndex = columnIndex - 1;
            BuoiHoc buoiHoc = listData.get(buoiHocIndex);
           // Boolean diemDanh = buoiHoc.getListDiemDanhSV().get(rowIndex).isDiemDanh();
            //return diemDanh;
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        // column 1: MSSV
        if(columnIndex == 0){
            String mssv = (String) getValueAt(rowIndex,columnIndex);
            for(BuoiHoc buoiHoc : listData){                
                buoiHoc.getSVDiemDanh(mssv).setMaSoSV((String) o);
            }
        }
        // column > 2: ngay diem danh
        if (columnIndex > 0) {
            // tim diemdanh cua mssv do, vao ngay tuong ung
            int buoiHocIndex = columnIndex - 1;
            BuoiHoc buoiHoc = listData.get(buoiHocIndex);
           // buoiHoc.getListDiemDanhSV().get(rowIndex).setDiemDanh((boolean) o);
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex > 0){
            return true;
        }
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return mClasses.get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headerNames.get(columnIndex);
    } 
      
    private  List<Date> danhSachHeaderNgay() {
        // tra ve danh sach cac column header can them 
        ArrayList<Date> listKetQua = new ArrayList<>() ;
        boolean exist = false;
        for (BuoiHoc data : listData) {
            exist = false;
            for (Date date : listKetQua) {
                if (data.getNgayHoc().equals(date)) {
                    exist = true;
                }
            }
            if (exist == false) {
                listKetQua.add(data.getNgayHoc());
            }
        }
        return listKetQua;
    }
    
}

public class NewJFrame extends javax.swing.JFrame {

    DefaultTableModel model;
    TblModelDiemDanhSV modelTbl;
    private ArrayList<BuoiHoc> listData;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        listData = new ArrayList<>();
        String str = "<html><font size='5' color=blue> Welcome to</font> <font size='6'color=green> Tutorials Point</font></html>";
        
        //btnThem.setText(str);
        
        //txtArea.setText(str);
        String them = "Chen Vao Ne";
        //lblTest.setText("<html><font size='5' color=blue> Welcome to</font> <br>" + them + "<br> <font size='6'color=green> Tutorials Point</font></html>");
        //ImageIcon icon = new ImageIcon("Resources/check.png");
        File path = new File("./Resource/check.png");
        //ImageIcon icon = new ImageIcon(getClass().getResource("/Resource/check.png"));
        //ImageIcon icon = (ImageIcon) lblTest.getIcon();
//        Image iconModified =  icon.getImage().getScaledInstance(lblTest.getHeight(),
//                lblTest.getHeight(), Image.SCALE_SMOOTH);
//        icon = new ImageIcon(iconModified);
        ImageIcon icon = new ImageIcon("..\\check.png");
        lblTest.setIcon(icon);
        
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String stringNgay1 = "12/3/2000";
//        String stringNgay2 = "16/3/2000";
//        Date ngayDiemDanh1 = null;
//        Date ngayDiemDanh2 = null;
//        try {
//            ngayDiemDanh1 = simpleDateFormat.parse(stringNgay1);
//            ngayDiemDanh2 = simpleDateFormat.parse(stringNgay2);
//        } catch (ParseException ex) {
//            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        ArrayList<DiemDanhSV> listDiemDanhSV = new ArrayList<>();
//        ArrayList<DiemDanhSV> listDiemDanhSV1 = new ArrayList<>();
//        String mssv1 = "N18DCCN025";
//        Boolean b1 = true;
//        DiemDanhSV diemDanhSV = new DiemDanhSV(mssv1, b1);
//        DiemDanhSV diemDanhSV1 = new DiemDanhSV(mssv1, b1);
//        listDiemDanhSV.add(diemDanhSV);
//        listDiemDanhSV1.add(diemDanhSV1);
//        
//        
//        mssv1 = "N18DCCN027";
//        b1 = false;
//        diemDanhSV = new DiemDanhSV(mssv1, b1);
//        diemDanhSV1 = new DiemDanhSV(mssv1, b1);
//        listDiemDanhSV.add(diemDanhSV);
//        listDiemDanhSV1.add(diemDanhSV1);
//        
//        BuoiHoc buoiHoc = new BuoiHoc();
//        buoiHoc.setNgayHoc(ngayDiemDanh1);
//        buoiHoc.setListDiemDanhSV(listDiemDanhSV);
//        listData.add(buoiHoc);
//        
//        
//        buoiHoc = new BuoiHoc();
//        buoiHoc.setNgayHoc(ngayDiemDanh2);
//        buoiHoc.setListDiemDanhSV(listDiemDanhSV1);
//        
//        listData.add(buoiHoc);
//        
//        modelTbl = new TblModelDiemDanhSV(listData);
//       
//        table.setModel(modelTbl);
//        
//        table.getModel().addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                System.out.println("Column: " + e.getColumn() + "Row: " + e.getFirstRow());
//                
//            }
//        });
//        
//        table.getTableHeader().setResizingAllowed(false); 
        String arr = "Tai Sao";
        if(arr.contains("Sao")){
            System.out.println("Ton tai");
        }
        else{
            System.out.println("Ko Ton tai");
        }
        
        model = new DefaultTableModel(){
            Class mClasses [] = {String.class, String.class, Boolean.class, String.class};
            @Override
            public Class<?> getColumnClass(int i) {
                return mClasses[i];
            }
            
            
        };
        model.addColumn("TEAM");
        model.addColumn("GS");
        model.addColumn("GA");
        model.addColumn("GD");
        model.addRow(new Object[]{
            "Tai Sao", "that la",true,5
        });
                model.addRow(new Object[]{
            "Tai Vi", "that ky quac",true,5
        });
                        model.addRow(new Object[]{
            "Vi Gi", "vai quac",false, "N18DCCN025, N18DCCN037"
        });
                        table.setColumnSelectionAllowed(false);
                        table.setRowSelectionAllowed(false);
          table.setCellSelectionEnabled(true);
       table.setModel(model);
       model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                System.out.println("Thay doi cell");
              filter();
            }
        });
       combo.addItem("None");
       combo.addItem("that la");
       combo.addItem("that ky quac");
       combo.addItem("vai quac");
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        lblMSSV = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTeam = new javax.swing.JTextField();
        txtGS = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt = new javax.swing.JTextField();
        combo = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        lblTest = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "1", "2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tableComponentHidden(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setResizable(false);
        }

        lblMSSV.setText("team");

        jLabel2.setText("GS");

        txtTeam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTeamKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTeamKeyReleased(evt);
            }
        });

        txtGS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGSKeyReleased(evt);
            }
        });

        btnThem.setText("Them");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jLabel3.setText("text");

        combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActionPerformed(evt);
            }
        });

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane2.setViewportView(txtArea);

        lblTest.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 116, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(58, 58, 58)
                                        .addComponent(btnThem))
                                    .addComponent(txtGS, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(145, 145, 145)
                                        .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMSSV)
                                .addGap(18, 18, 18)
                                .addComponent(txtTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(102, 102, 102))
                    .addComponent(jScrollPane1)))
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(lblTest, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, lblMSSV});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt, txtGS, txtTeam});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMSSV)
                    .addComponent(txtTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtGS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(jLabel3)
                    .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(lblTest, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(389, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showOnTable(String s, int row, int column) {
        //modelTbl.setValueAt(s, row, column);
    }
    
    private void filter(){
        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
        filters.add(RowFilter.regexFilter(txtTeam.getText().trim(),0));
        filters.add(RowFilter.regexFilter(txtGS.getText().trim(), 1));
        if(!combo.getSelectedItem().toString().trim().equals("None")){
                    filters.add(RowFilter.regexFilter(combo.getSelectedItem().toString().trim(), 1));
            
        }

        
        RowFilter rf = RowFilter.andFilter(filters);    
        TableRowSorter sorter = new TableRowSorter(model);                       
        sorter.setRowFilter(rf);
        table.setRowSorter(sorter);
    }
    
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

    }//GEN-LAST:event_btnThemActionPerformed

    private void tableComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tableComponentHidden

    private void txtTeamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTeamKeyPressed
        
    
    }//GEN-LAST:event_txtTeamKeyPressed

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased

    }//GEN-LAST:event_tableKeyReleased

    private void txtTeamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTeamKeyReleased
            filter();
    }//GEN-LAST:event_txtTeamKeyReleased

    private void txtGSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGSKeyReleased
        filter();
    }//GEN-LAST:event_txtGSKeyReleased

    private void comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboActionPerformed
       filter();
    }//GEN-LAST:event_comboActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMSSV;
    private javax.swing.JLabel lblTest;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtGS;
    private javax.swing.JTextField txtTeam;
    // End of variables declaration//GEN-END:variables
}
