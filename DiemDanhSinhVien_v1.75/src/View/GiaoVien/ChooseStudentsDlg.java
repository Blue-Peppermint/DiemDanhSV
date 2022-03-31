/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.BasicAttribute_Method;
import Model.BooleanRenderer;
import Model.InfoStudent;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.ListInfoStudent;
import Model.GiaoVien.ListNhomSV;
import Model.GiaoVien.LopHoc;
import Model.GiaoVien.NhomSV;
import UnusedModel.ObjectRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
public class ChooseStudentsDlg extends javax.swing.JDialog {

    private ArrayList<String> listMSSVChoosen;
    private LopHoc lopHoc;
    private ListNhomSV listNhomSV;
    private ListInfoStudent listInfoStudent;
    private DefaultTableModel tblModel;

    // gia su nhung thuoc tinh tren da day du du lieu
    // những SV đã được chọn sẽ hiển thị màu nền foreground, backGround khác
    public ChooseStudentsDlg(java.awt.Frame parent, boolean modal,
            LopHoc lopHoc, ListNhomSV listNhomSV) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.lopHoc = lopHoc;
        this.listNhomSV = listNhomSV;
        firstSetUp();
    }

    public ArrayList<String> getListMSSVChoosen() {
        return listMSSVChoosen;
    }

    private void firstSetUp() {
        setListInfoStudent();
        listMSSVChoosen = null;
        tblModel = (DefaultTableModel) tblStudents.getModel();
        setTableProperties();
        showTable();
    }

    private void setListInfoStudent() {
        listInfoStudent = new ListInfoStudent();
        ArrayList<InfoStudent> list = KetNoiSQL_GV.getListInfoStudent(lopHoc.getMaLopHoc());
        if (!list.isEmpty()) {
            listInfoStudent.getListInfoStudent().addAll(list);
        }
    }

    private void setTableProperties() {
        // tuy chinh cac propeties linh tinh cua table
        // font + rowHeight trong CellRenderer duoc uu tien hon nen 2 ham duoi day vo dung
        //        tblStudents.setFont(new java.awt.Font("Tahoma", 0, 18));
        //        tblStudents.setRowHeight(20);
        for (int i = 0; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            if (i > 6) {
                tblStudents.getColumnModel().getColumn(i).setCellRenderer(new BooleanRenderer());
            } else {
                tblStudents.getColumnModel().getColumn(i).setCellRenderer(new ObjectRenderer());
            }
        }
        tblStudents.setAutoCreateRowSorter(true);
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // no cell Selection Mode
        //tblStudents.setCellSelectionEnabled(true);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        // tuy chinh chieu rong cua cac cot
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            //STT
            tblStudents.getColumnModel().getColumn(0).setMinWidth(50);
            tblStudents.getColumnModel().getColumn(0).setMaxWidth(50);
            // Ho
            tblStudents.getColumnModel().getColumn(1).setResizable(false);
            // Ten
            tblStudents.getColumnModel().getColumn(2).setResizable(false);
            // Gioi Tinh
            tblStudents.getColumnModel().getColumn(3).setMinWidth(75);
            tblStudents.getColumnModel().getColumn(3).setMaxWidth(75);
            // MSSV
            tblStudents.getColumnModel().getColumn(4).setResizable(false);
            // Lop
            tblStudents.getColumnModel().getColumn(5).setResizable(false);
            // ke tu cot thu 6
            for (int i = 6; i < tblStudents.getColumnModel().getColumnCount(); i++) {
                tblStudents.getColumnModel().getColumn(i).setMinWidth(125);
                tblStudents.getColumnModel().getColumn(i).setMaxWidth(125);
            }
        }
        tblStudents.repaint();
    }

    private boolean checkIfTeachPractice(String maLopMH) {
        // kiem tra neu GV do day thuc hanh lop do
        boolean isPractice = false;
        ArrayList<Integer> idVaiTro = lopHoc.getIdVaiTro();
        for (int i : idVaiTro) {
            if (i == 1) {
                isPractice = true;
            }
        }
        return isPractice;
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

    private void showTable() {
        // xuat danh sach cac sinh vien GV day trong ma lop MH do
        // neu gv do co day thuc hanh thi show chi tiet luon sv do thuc hanh nhom nao
        // con khong thi cu show nhom ly thuyet
        tblModel.setRowCount(0);
        int count = 0;
        String maLopMH = lopHoc.getMaLopHoc();
        if (checkIfTeachPractice(maLopMH)) {
            ArrayList<NhomSV> listGroup = listNhomSV.getListNhomSV(maLopMH);
            for (NhomSV nhom : listGroup) {
                String compare = "Thực Hành";
                if (nhom.getTenNhom().contains(compare)) {
                    ArrayList<String> listMSSV = nhom.getListMSSV();
                    for (String mssv : listMSSV) {
                        InfoStudent info = listInfoStudent.getStudent(mssv);
                        Vector vt = new Vector();
                        vt.add(++count);
                        vt.add(info.getHo());
                        vt.add(info.getTen());
                        vt.add(info.getGioiTinh());
                        vt.add(mssv);
                        vt.add(info.getLopNC());
                        vt.add(nhom.getTenNhom());
                        vt.add(false);
                        tblModel.addRow(vt);
                    }
                }
            }
        } else {
            ArrayList<NhomSV> listGroup = listNhomSV.getListNhomSV(maLopMH);
            for (NhomSV nhom : listGroup) {
                String compare = "Lý Thuyết";
                if (nhom.getTenNhom().contains(compare)) {
                    ArrayList<String> listMSSV = nhom.getListMSSV();
                    for (String mssv : listMSSV) {
                        InfoStudent info = listInfoStudent.getStudent(mssv);
                        Vector vt = new Vector();
                        vt.add(++count);
                        vt.add(info.getHo());
                        vt.add(info.getTen());
                        vt.add(info.getGioiTinh());
                        vt.add(mssv);
                        vt.add(info.getLopNC());
                        vt.add(nhom.getTenNhom());
                        vt.add(false);
                        tblModel.addRow(vt);
                    }
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtMSSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        txtLop = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnDongY = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("HọTên");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, 60, 20));

        txtTen.setText(" ");
        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });
        getContentPane().add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 230, -1));

        txtMSSV.setText(" ");
        txtMSSV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMSSVKeyReleased(evt);
            }
        });
        getContentPane().add(txtMSSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 380, 230, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MSSV");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 380, 60, 20));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lớp");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 60, 20));

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Họ", "Tên", "Giới Tính", "MSSV", "Lớp", "Nhóm", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblStudents.setShowGrid(true);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudents);

        jScrollPane2.setViewportView(jScrollPane1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1260, 310));

        txtLop.setText(" ");
        txtLop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLopKeyReleased(evt);
            }
        });
        getContentPane().add(txtLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, 230, -1));

        jSeparator1.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 440, 330, 10));

        btnDongY.setBackground(new java.awt.Color(255, 255, 255));
        btnDongY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/ok.png"))); // NOI18N
        btnDongY.setText("Đồng Ý");
        btnDongY.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });
        getContentPane().add(btnDongY, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, 90, 30));

        btnHuy.setBackground(new java.awt.Color(255, 255, 255));
        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-Close-icon-16.png"))); // NOI18N
        btnHuy.setText("Hủy Bỏ");
        btnHuy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        getContentPane().add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 460, 90, 30));

        jLabel5.setBackground(new java.awt.Color(255, 153, 153));
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 330, 170));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        filter();
    }//GEN-LAST:event_txtTenKeyReleased

    private void txtMSSVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMSSVKeyReleased
        filter();
    }//GEN-LAST:event_txtMSSVKeyReleased

    private void txtLopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLopKeyReleased
        filter();
    }//GEN-LAST:event_txtLopKeyReleased

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        listMSSVChoosen = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tblModel.getRowCount(); rowIndex++) {
            int columnChon = 7;
            Boolean ischoosen = (Boolean) tblModel.getValueAt(rowIndex, columnChon);
            if (ischoosen) {
                int columnMSSV = 4;
                listMSSVChoosen.add(tblModel.getValueAt(rowIndex, columnMSSV).toString());
            }
        }
        // neu khong chon gi ca
        if (listMSSVChoosen.isEmpty()) {
            listMSSVChoosen = null;
            JOptionPane.showMessageDialog(this, "Ban khong chon gi ca");
        } else {
            // tat dlg
            this.dispose();
        }
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        listMSSVChoosen = null;
        // tat dlg
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
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
//            java.util.logging.Logger.getLogger(ChooseStudentsDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ChooseStudentsDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ChooseStudentsDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ChooseStudentsDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                ChooseStudentsDlg dialog = new ChooseStudentsDlg(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDongY;
    private javax.swing.JButton btnHuy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMSSV;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
