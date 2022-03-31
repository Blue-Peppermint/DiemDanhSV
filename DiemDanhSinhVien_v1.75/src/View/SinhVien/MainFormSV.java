/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SinhVien;

import Model.SinhVien.*;
import View.LogInDialog;

/**
 *
 * @author chuon
 */
public class MainFormSV extends javax.swing.JFrame {

    private LogInDialog logInDlg;
    private TimeTablePanel timeTablePanel;
    private RollUpCalendarPanel rollUpCalendarPanel;
    private MailPanel mailPanel;
    private Info_SVPanel infoPanel;

    /**
     * Creates new form MailPanel
     */
    public MainFormSV(LogInDialog loginDialog) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Quản Lý Điểm Danh PTIT");
        this.logInDlg = loginDialog;
        timeTablePanel = new TimeTablePanel();
        mainTPL.add("Thời Gian Biểu", timeTablePanel);
        rollUpCalendarPanel = new RollUpCalendarPanel();
        mainTPL.add("Điểm Danh", rollUpCalendarPanel);
        mailPanel = new MailPanel();
        mainTPL.add("Hộp Thư", mailPanel);
        mainTPL.setSelectedComponent(rollUpCalendarPanel);
    }

    public MainFormSV() {
        initComponents();
        setLocationRelativeTo(null);
        KetNoiSQL_SV.setMssv("SV001");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnThongTin = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnThoiGianBieu = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDiemDanh = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnHopThu = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnDangXuat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTPL = new javax.swing.JTabbedPane();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnThongTin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Person-Male-Light-icon-16.png"))); // NOI18N
        btnThongTin.setText("THÔNG TIN");
        btnThongTin.setFocusable(false);
        btnThongTin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongTin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongTinActionPerformed(evt);
            }
        });
        jToolBar1.add(btnThongTin);
        jToolBar1.add(jSeparator1);

        btnThoiGianBieu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThoiGianBieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/calendar-background-icon-16.png"))); // NOI18N
        btnThoiGianBieu.setText("THỜI GIAN BIỂU");
        btnThoiGianBieu.setFocusable(false);
        btnThoiGianBieu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThoiGianBieu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoiGianBieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoiGianBieuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnThoiGianBieu);
        jToolBar1.add(jSeparator2);

        btnDiemDanh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDiemDanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/diemdanh.jpg"))); // NOI18N
        btnDiemDanh.setText("ĐIỂM DANH");
        btnDiemDanh.setFocusable(false);
        btnDiemDanh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiemDanh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDiemDanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiemDanhActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDiemDanh);
        jToolBar1.add(jSeparator3);

        btnHopThu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHopThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Circle-icons-mail.svg.png"))); // NOI18N
        btnHopThu.setText("HỘP THƯ");
        btnHopThu.setFocusable(false);
        btnHopThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHopThu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHopThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHopThuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHopThu);
        jToolBar1.add(jSeparator4);

        btnDangXuat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/logout-icon-16.png"))); // NOI18N
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.setFocusable(false);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDangXuat);

        jScrollPane1.setViewportView(mainTPL);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(13, 13, 13))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        this.dispose();
        KetNoiSQL_SV.setMssv("");
        logInDlg.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnThoiGianBieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoiGianBieuActionPerformed
//        if (timeTablePanel == null) {
//            timeTablePanel = new TimeTablePanel();
//            mainTPL.add("Thời Gian Biểu", timeTablePanel);
//        }
        mainTPL.setSelectedComponent(timeTablePanel);
    }//GEN-LAST:event_btnThoiGianBieuActionPerformed

    private void btnDiemDanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiemDanhActionPerformed
//        if (rollUpCalendarPanel == null) {
//            rollUpCalendarPanel = new RollUpCalendarPanel();
//            mainTPL.add("Điểm Danh", rollUpCalendarPanel);
//        }
        mainTPL.setSelectedComponent(rollUpCalendarPanel);
    }//GEN-LAST:event_btnDiemDanhActionPerformed

    private void btnHopThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHopThuActionPerformed
//        if (mailPanel == null) {
//            mailPanel = new MailPanel();
//            mainTPL.add("Hộp Thư", mailPanel);
//        }
        mainTPL.setSelectedComponent(mailPanel);
    }//GEN-LAST:event_btnHopThuActionPerformed

    private void btnThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongTinActionPerformed
        if (infoPanel == null) {
            infoPanel = new Info_SVPanel(this);
            mainTPL.add("Thông Tin", infoPanel);
        }
        mainTPL.setSelectedComponent(infoPanel);
    }//GEN-LAST:event_btnThongTinActionPerformed

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
            java.util.logging.Logger.getLogger(MainFormSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFormSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFormSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFormSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFormSV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDiemDanh;
    private javax.swing.JButton btnHopThu;
    private javax.swing.JButton btnThoiGianBieu;
    private javax.swing.JButton btnThongTin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTabbedPane mainTPL;
    // End of variables declaration//GEN-END:variables
}
