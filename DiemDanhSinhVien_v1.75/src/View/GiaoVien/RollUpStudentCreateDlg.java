/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.BasicAttribute_Method;
import Model.GiaoVien.BuoiHoc;
import TableModel.GiaoVien.ComboBoxCellEditor_RollUp;
import Model.InfoStudent;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListInfoStudent;
import Model.GiaoVien.LopHoc;
import UnusedModel.ObjectRenderer;
import TableModel.GiaoVien.RollUpStudentTableModel;
import UnusedModel.TblRendererIDDiemDanh;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
public class RollUpStudentCreateDlg extends javax.swing.JDialog {

    RollUpCreatePanel rollUpCreatePanel;
    private LopHoc lopHoc;
    private ListBuoiHoc listBuoiHoc;
    private ListInfoStudent listStudents;
    private RollUpStudentTableModel tblModel;
    private boolean saved; // kiem tra xem co luu thanh cong buoi hoc ko? phat sinh do can tat RollUpInfomationDlg khi luu thanh cong ben RollUpStudentCreateDlg
    
    public RollUpStudentCreateDlg(java.awt.Frame parent, boolean modal, RollUpCreatePanel rollUpCreatePanel,LopHoc lopHoc, ListBuoiHoc listBuoiHoc) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Tạo Buổi Điểm Danh");
        this.rollUpCreatePanel = rollUpCreatePanel;
        this.lopHoc = lopHoc;
        this.listBuoiHoc = listBuoiHoc;
        firstSetUp();
    }

    public boolean isSaved() {
        return saved;
    }
    
    private void firstSetUp(){
        setAllData();
        showInfoClass();
        tblModel = new RollUpStudentTableModel(listBuoiHoc, listStudents, true);
        tblStudents.setModel(tblModel);
        setTblRenderer();
        setTblEditor();
        setTableProperties();
    }
    
    private void setAllData() {
        setListStudents();
        saved = false;
    }
    
    private void setListStudents() {
        listStudents = new ListInfoStudent();
        BuoiHoc buoiHoc = listBuoiHoc.getListBuoiHoc().get(0);
        ArrayList<InfoStudent> list = KetNoiSQL_GV.getListInfoStudent(buoiHoc.getMaLopHoc());
        if (!list.isEmpty()) {
            listStudents.getListInfoStudent().addAll(list);
        }

    }

    private void showInfoClass(){
        lblTenMonHoc.setText(lopHoc.getTenMonHoc());
        lblTenLop.setText(lopHoc.getLop());
        lblTenNhom.setText(listBuoiHoc.getListBuoiHoc().get(0).getTenNhom());
    }
    
    private void setTblRenderer() {
        int columnIDDiemDanh = 6;
        for (int i = 0; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            if (i == columnIDDiemDanh) {
                tblStudents.getColumnModel().getColumn(i).setCellRenderer(new TblRendererIDDiemDanh());
            } else {
                tblStudents.getColumnModel().getColumn(i).setCellRenderer(new ObjectRenderer());
            }
        }
    }

    private void setTblEditor() {
        for (int i = 6; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            tblStudents.getColumnModel().getColumn(i).setCellEditor(new ComboBoxCellEditor_RollUp());
        }
    }

    private void setTableProperties() {
        // tuy chinh cac propeties linh tinh cua table
        tblStudents.setAutoCreateRowSorter(true);
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        // tuy chinh chieu rong cua cac cot
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            //STT
            tblStudents.getColumnModel().getColumn(0).setMinWidth(75);
            tblStudents.getColumnModel().getColumn(0).setMaxWidth(75);
            // Ho
            tblStudents.getColumnModel().getColumn(1).setResizable(false);
            // Ten
            tblStudents.getColumnModel().getColumn(2).setResizable(false);
            // Gioi Tinh
            tblStudents.getColumnModel().getColumn(3).setResizable(false);
            // MSSV
            tblStudents.getColumnModel().getColumn(4).setResizable(false);
            // Lop
            tblStudents.getColumnModel().getColumn(5).setResizable(false);
            // ke tu cot diem danh thu 6
            for (int i = 6; i < tblStudents.getColumnModel().getColumnCount(); i++) {
                tblStudents.getColumnModel().getColumn(i).setMinWidth(125);
                tblStudents.getColumnModel().getColumn(i).setMaxWidth(125);
            }
        }
    }

    private void filter() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
        filters.add(RowFilter.regexFilter(BasicAttribute_Method.convertSpecialString(txtTen.getText()), 2));
        filters.add(RowFilter.regexFilter(BasicAttribute_Method.convertSpecialString(txtMSSV.getText()), 4));
        filters.add(RowFilter.regexFilter(BasicAttribute_Method.convertSpecialString(txtLop.getText()), 5));
        RowFilter rf = RowFilter.andFilter(filters);
        TableRowSorter sorter = new TableRowSorter(tblModel);
        sorter.setRowFilter(rf);
        tblStudents.setRowSorter(sorter);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMSSV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnDongY = new javax.swing.JButton();
        BtnThoat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTenMonHoc = new javax.swing.JLabel();
        lblTenLop = new javax.swing.JLabel();
        lblTenNhom = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMSSV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMSSV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMSSVKeyReleased(evt);
            }
        });
        getContentPane().add(txtMSSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 140, -1));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lớp");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 40, -1));

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tạo Buổi Điểm Danh");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 814, 33));

        txtLop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtLop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLopKeyReleased(evt);
            }
        });
        getContentPane().add(txtLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, 140, -1));

        tblStudents.setAutoCreateRowSorter(true);
        tblStudents.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Họ", "Tên", "Giới Tính", "MSSV", "Lớp", "Ngày diem danh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.setRowSelectionAllowed(false);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudents);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 794, 155));

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 794, 12));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 40, 20));

        txtTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });
        getContentPane().add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, 140, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MSSV");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 40, -1));

        btnDongY.setBackground(new java.awt.Color(255, 255, 255));
        btnDongY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/ok.png"))); // NOI18N
        btnDongY.setText("Đồng Ý");
        btnDongY.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });
        getContentPane().add(btnDongY, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, 100, 40));

        BtnThoat.setBackground(new java.awt.Color(255, 255, 255));
        BtnThoat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-edit-delete-icon-16.png"))); // NOI18N
        BtnThoat.setText("Thoát");
        BtnThoat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThoatActionPerformed(evt);
            }
        });
        getContentPane().add(BtnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, 100, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tên Môn Học:");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, -1, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Tên Lớp:");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 79, 30));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tên Nhóm:");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 79, 30));

        lblTenMonHoc.setBackground(new java.awt.Color(255, 255, 255));
        lblTenMonHoc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenMonHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenMonHoc.setText("jLabel8");
        lblTenMonHoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 270, 30));

        lblTenLop.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenLop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenLop.setText("jLabel9");
        lblTenLop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 270, 30));

        lblTenNhom.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenNhom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNhom.setText("jLabel10");
        lblTenNhom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 270, 30));

        jLabel8.setBackground(new java.awt.Color(255, 204, 204));
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setOpaque(true);
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 810, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMSSVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMSSVKeyReleased
        filter();
    }//GEN-LAST:event_txtMSSVKeyReleased

    private void txtLopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLopKeyReleased
        filter();
    }//GEN-LAST:event_txtLopKeyReleased

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        filter();
    }//GEN-LAST:event_txtTenKeyReleased

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        // lay listTongBuoiHoc da thay doi trong tblModel va add them vao SQL
        ArrayList<BuoiHoc> list = tblModel.getListBuoiHoc();
        // list tra ve se luon co size = 0 hoac size = 1
        for (BuoiHoc buoiHoc : list) {
            if (KetNoiSQL_GV.saveBuoiHoc(buoiHoc)) {
                //Update data goc de showUp Table
                rollUpCreatePanel.getListBuoiHoc().getListBuoiHoc().add(buoiHoc);
                saved = true;
                JOptionPane.showMessageDialog(this, "Điểm Danh Thành Công");
                RollUpMainPanel.setListBuoiHocUpdated(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Điểm Danh Thất Bại");
            }
        }
    }//GEN-LAST:event_btnDongYActionPerformed

    private void BtnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnThoatActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(RollUpStudentCreateDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RollUpStudentCreateDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RollUpStudentCreateDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RollUpStudentCreateDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                RollUpStudentCreateDlg dialog = new RollUpStudentCreateDlg(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnThoat;
    private javax.swing.JButton btnDongY;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTenLop;
    private javax.swing.JLabel lblTenMonHoc;
    private javax.swing.JLabel lblTenNhom;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMSSV;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
