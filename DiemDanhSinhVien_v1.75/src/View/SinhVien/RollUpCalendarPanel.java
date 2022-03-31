/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SinhVien;

import Model.GiaoVien.DiemDanhSV;
import Model.HocKy;
import Model.ListHocKy;
import Model.SinhVien.BuoiHoc;
import Model.SinhVien.KetNoiSQL_SV;
import Model.SinhVien.ListBuoiHoc;
import Model.SinhVien.ListLopHoc;
import Model.SinhVien.LopHoc;
import TableModel.SinhVien.RollUpCalendarRenderer_SV;
import Model.Tuan;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import Model.MyEnum;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author chuon
 */
public class RollUpCalendarPanel extends javax.swing.JPanel {

    private HocKy hocKy;
    private ListLopHoc listLopHoc;
    private ListBuoiHoc listBuoiHoc;
    private DefaultTableModel tblModel;
    private int indexTuan;//Index dao dong tu 0 -> n-1
    private RollUpCalendarRenderer_SV tblRenderer;
    
    /**
     * Creates new form RollUpCalendarPanel
     */
    public RollUpCalendarPanel() {
        initComponents();
        firstSetUp();
    }

    private void firstSetUp() {
        // nhung gi can cho lan setUp du lieu dau tien se them vao day
        setAllData();
        tblModel = (DefaultTableModel) tblLichDiemDanh.getModel();
        setTableRenderer();
        setTableProperties();
        if (hocKy.getDsTuan().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Sinh Viên Chưa Tham Gia Bất Kỳ Lớp Học Nào",
//                    "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        } else {          
            indexTuan = -1;
            setUpCBHocKy();
            setUpCBTuan(hocKy);
            setCBMoiNhat();
        }
    }

    private void setAllData() {
        // de cac ham theo dung thu tu nha :>
        setHocKy();
        setListLopHoc();
        setListBuoiHoc();
        if (!hocKy.getDsTuan().isEmpty()) {
            tblRenderer = new RollUpCalendarRenderer_SV(listBuoiHoc, hocKy.getDsTuan().get(indexTuan));
        }
        else {
            tblRenderer = new RollUpCalendarRenderer_SV(listBuoiHoc, null);
        }
    }

    private void setHocKy() {
        // lay data cua hoc ky moi nhat gv day
        // lay hoc ky moi nhat, chinh la LastValue trong thongTinDay.dsHocKy
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_SV();
        if (listHocKy.getListHocKy().isEmpty()) {
            hocKy = new HocKy();
        } else {
            int lastIndex = listHocKy.getListHocKy().size() - 1;
            hocKy = listHocKy.getListHocKy().get(lastIndex);
        }
    }

    private void setListLopHoc() {
        // lay thong tin cac lopHoc hoc ma GV day <tai thoi diem moi nhat>
        this.listLopHoc = new ListLopHoc();
        if (!hocKy.getDsTuan().isEmpty()) {
            int sttHocKy = hocKy.getSttHocKy();
            int namHoc = hocKy.getNamHoc();
            ArrayList<LopHoc> listLopHoc = KetNoiSQL_SV.getListLopHoc(sttHocKy, namHoc);
            if (!listLopHoc.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(listLopHoc);
            }
        }
    }

    private void setListBuoiHoc() {
        // moi lopHoc se lay data cac buoi hoc tuong ung da diem danh
        this.listBuoiHoc = new ListBuoiHoc();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<BuoiHoc> listBuoiHoc = KetNoiSQL_SV.getListBuoiHoc(lopHoc.getMaLopHoc());
            if (!listBuoiHoc.isEmpty()) {
                this.listBuoiHoc.getListBuoiHoc().addAll(listBuoiHoc);
            }
        }
    }
    
    private void setTableRenderer(){
        // chi chay ham nay khi tblRender da duoc khoi tao, set hocKy xong xuoi
        // chay truoc khi show table
        tblLichDiemDanh.setDefaultRenderer(Object.class, tblRenderer);
    }
    
    private void setTableProperties() {
        // chay truoc khi show table        
        tblLichDiemDanh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLichDiemDanh.setCellSelectionEnabled(true);
        tblLichDiemDanh.getTableHeader().setReorderingAllowed(false);
        if (tblLichDiemDanh.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < tblLichDiemDanh.getColumnModel().getColumnCount(); i++) {
                tblLichDiemDanh.getColumnModel().getColumn(i).setResizable(false);
            }
        }
    }

    private void setUpCBHocKy() {
        int sttHocKy = hocKy.getSttHocKy();
        String s = "Học Kỳ " + String.valueOf(sttHocKy);
        comboHocKy.addItem(s);
    }

    private void setUpCBTuan(HocKy hocKy) {
        // ung voi hoc ky, se dua ra du lieu tuan tuong ung
        comboTuan.removeAllItems();
        for (Tuan tmp : hocKy.getDsTuan()) {
            int sttTuan = tmp.getSttTuan();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayBDTuan = simpleDateFormat.format(tmp.getNgayBDTuan());
            String ngayKTTuan = simpleDateFormat.format(tmp.getNgayKTTuan());
            String s = "Tuần " + sttTuan + " [Từ " + ngayBDTuan + " -- Đến "
                    + ngayKTTuan + "]";
            comboTuan.addItem(s);
        }
        indexTuan = 0;
    }

    private void setCBMoiNhat() {
        // set selectedIndex cho cac comboBox. Chi chay method nay khi da setUpCBHocKy() + setUpCBTuan()
        // luon show hoc ky moi nhat
        // neu ngay hien tai chua den ngay trong khung gio lich day thi show tuan 1
        // neu ngay hien tai van nam trong khung gio lich day thi show tuan do
        // neu ngay hien tai da qua khung gio lich day thi show tuan cuoi cung
        int selectIndexCBTuan = 0;
        HocKy hocKyMoiNhat = hocKy;
        Tuan tuanBDHocKyMoiNhat = hocKyMoiNhat.getDsTuan().get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(TimeTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (tuanBDHocKyMoiNhat.getNgayBDTuan().compareTo(ngayHienTai) > 0) {
            comboTuan.setSelectedIndex(selectIndexCBTuan);
            indexTuan = selectIndexCBTuan;
            return;
        }
        int lastIndexDSTuan = hocKyMoiNhat.getDsTuan().size() - 1;
        Tuan tuanKtHocKyMoiNhat = hocKyMoiNhat.getDsTuan().get(lastIndexDSTuan);
        if (tuanKtHocKyMoiNhat.getNgayKTTuan().compareTo(ngayHienTai) < 0) {
            selectIndexCBTuan = lastIndexDSTuan;
            comboTuan.setSelectedIndex(selectIndexCBTuan);
            indexTuan = selectIndexCBTuan;
            return;
        }
        for (int indexTuan = 0; indexTuan < hocKyMoiNhat.getDsTuan().size(); indexTuan++) {
            Tuan tuan = hocKyMoiNhat.getDsTuan().get(indexTuan);
            if (tuan.getNgayBDTuan().compareTo(ngayHienTai) <= 0
                    && tuan.getNgayKTTuan().compareTo(ngayHienTai) >= 0) {
                selectIndexCBTuan = indexTuan;
            }
        }
        comboTuan.setSelectedIndex(selectIndexCBTuan);
        indexTuan = selectIndexCBTuan;
    }

    private void setBuoiHocOnTable(BuoiHoc buoiHoc) {
        //show Buoi Hoc len JTable mong muon
        Date ngayHoc = buoiHoc.getNgayHoc();
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayHoc);
        int rowIndex = buoiHoc.getCa();
        int columnIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        LopHoc lopHoc = listLopHoc.getLopHoc(buoiHoc.getGvDayHoc().getMaLopHoc());
        String s = lopHoc.getTenMonHoc() + "\n" + lopHoc.getLop() + "\n" + buoiHoc.getGvDayHoc().getTenNhom();
        tblModel.setValueAt(s, rowIndex, columnIndex);

    }

    private void showTable(int indexTuan) {
        // show table cac buoi diem danh tuong ung voi hoc ky + tuan    
        // kiem tra listBuoiHoc da du data chua?                
        tblModel.setRowCount(0);
        // muon setValueAt thi phai co du 2 row
        tblModel.addRow(new Object[]{
            "SÁNG"
        });
        tblModel.addRow(new Object[]{
            "CHIỀU"
        });
        Tuan tuan = hocKy.getDsTuan().get(indexTuan);
        for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
            Date ngayBDTuan = tuan.getNgayBDTuan();
            Date ngayKTTuan = tuan.getNgayKTTuan();
            Date ngayHoc = buoiHoc.getNgayHoc();
            if (ngayBDTuan.compareTo(ngayHoc) <= 0 && ngayKTTuan.compareTo(ngayHoc) > 0) {
                setBuoiHocOnTable(buoiHoc);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLichDiemDanh = new javax.swing.JTable();
        btnTuanTruoc = new javax.swing.JButton();
        btnTuanKe = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboTuan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 204, 204));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLichDiemDanh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblLichDiemDanh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblLichDiemDanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Sáng", null, null, null, null, null, null, null},
                {"Chiều", null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLichDiemDanh.setRowHeight(tblLichDiemDanh.getRowHeight()*4);
        tblLichDiemDanh.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblLichDiemDanh);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 990, 270));

        btnTuanTruoc.setBackground(new java.awt.Color(255, 255, 255));
        btnTuanTruoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTuanTruoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-Last-icon-16.png"))); // NOI18N
        btnTuanTruoc.setText("Tuần Trước");
        btnTuanTruoc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTuanTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuanTruocActionPerformed(evt);
            }
        });
        add(btnTuanTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 70, -1, 30));

        btnTuanKe.setBackground(new java.awt.Color(255, 255, 255));
        btnTuanKe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTuanKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-First-icon-16.png"))); // NOI18N
        btnTuanKe.setText("Tuần Kế");
        btnTuanKe.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTuanKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuanKeActionPerformed(evt);
            }
        });
        add(btnTuanKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 110, 30));

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 1000, 10));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chọn học kỳ:");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Chọn tuần:");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);

        comboTuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTuanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboTuan, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboTuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        jLabel6.setBackground(Color.RED);
        jLabel6.setText(MyEnum.VANG_CO_PHEP.getChuThichEditorComBoBox());
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 120, -1, -1));

        jLabel3.setBackground(MyEnum.CO_MAT.getColorDaiDien());
        jLabel3.setForeground(jLabel3.getBackground());
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        jLabel4.setBackground(new Color(255, 214, 153));
        jLabel4.setText(MyEnum.CO_MAT.getChuThichEditorComBoBox());
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));

        jLabel5.setBackground(MyEnum.VANG_KO_PHEP.getColorDaiDien());
        jLabel5.setForeground(jLabel5.getBackground());
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("jLabel5");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 120, -1, -1));

        jLabel7.setBackground(MyEnum.HOC_MUON.getColorDaiDien());
        jLabel7.setForeground(jLabel7.getBackground());
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("jLabel7");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, -1, -1));

        jLabel8.setText(MyEnum.HOC_MUON.getChuThichEditorComBoBox());
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, -1, -1));

        jLabel9.setBackground(MyEnum.VANG_CO_PHEP.getColorDaiDien());
        jLabel9.setForeground(jLabel9.getBackground());
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("jLabel9");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 120, -1, -1));

        jLabel10.setText(MyEnum.VANG_KO_PHEP.getChuThichEditorComBoBox());
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, -1, -1));

        btnLamMoi.setBackground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm Mới");
        btnLamMoi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 100, 40));

        jLabel11.setBackground(new java.awt.Color(255, 0, 0));
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setOpaque(true);
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 990, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void btnTuanTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuanTruocActionPerformed
        if (indexTuan > 0) {
            indexTuan--;
            comboTuan.setSelectedIndex(indexTuan);           
        }
    }//GEN-LAST:event_btnTuanTruocActionPerformed

    private void btnTuanKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuanKeActionPerformed
        if (indexTuan < comboTuan.getItemCount() - 1) {
            indexTuan++;
            comboTuan.setSelectedIndex(indexTuan);
        }
    }//GEN-LAST:event_btnTuanKeActionPerformed

    private void comboTuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTuanActionPerformed
        if (tblRenderer != null) {
            indexTuan = comboTuan.getSelectedIndex();
            tblRenderer.setTuan(hocKy.getDsTuan().get(indexTuan));
            showTable(indexTuan);
        }
    }//GEN-LAST:event_comboTuanActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // refresh giao dien khi GV chinh sua lai buoi diem danh + refresh ve mac dinh
        setListBuoiHoc();
        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
            indexTuan = comboTuan.getSelectedIndex();
            tblRenderer = new RollUpCalendarRenderer_SV(listBuoiHoc, hocKy.getDsTuan().get(indexTuan));
            setTableRenderer();
            showTable(indexTuan);
        }
    }//GEN-LAST:event_btnLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTuanKe;
    private javax.swing.JButton btnTuanTruoc;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboTuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLichDiemDanh;
    // End of variables declaration//GEN-END:variables
}
