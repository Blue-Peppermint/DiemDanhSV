/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

/**
 *
 * @author chuon
 */
import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.LopHoc;
import Model.HocKy;
import Model.ListHocKy;
import Model.GiaoVien.ListBuoiHoc;
import Model.Tuan;
import Model.CalendarRenderer1;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

// chi hien thi hoc ky dang day. sttTuan duoc sap xep dua vao lich day truong sap xep
public class RollUpCalendarPanel extends javax.swing.JPanel {

    private HocKy hocKy;
    private ListLopHoc listLopHoc; // lay thong tin cac lopHoc hoc ma GV day + vai tro GV trong lopHoc
    private ListBuoiHoc listBuoiHoc; // chua maso lopHoc hoc + thong tin diem danh   
    private DefaultTableModel tblModel;
    private int indexTuan;//Index dao dong tu 0 -> n-1

    public RollUpCalendarPanel() {
        initComponents();
        firstSetUp();
    }

    public void updateListBuoiHocAndShowTable(ListBuoiHoc listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
        indexTuan = comboTuan.getSelectedIndex();
        showTable(indexTuan);
    }

    private void firstSetUp() {
        // nhung gi can cho lan setUp du lieu dau tien se them vao day
        setAllData();
        tblModel = (DefaultTableModel) tblLichDiemDanh.getModel();
        setTableProperties();
        indexTuan = -1;
        setUpCBHocKy();
        setUpCBTuan(hocKy);
        setCBMoiNhat();
    }
   
    private void setAllData() {
        // de cac ham theo dung thu tu nha :>
        setHocKy();
        setListLopHoc();
        setListBuoiHoc();
    }

    private void setHocKy() {
        // lay data cua hoc ky moi nhat gv day
        // lay hoc ky moi nhat, chinh la LastValue trong thongTinDay.dsHocKy
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_GV();
        int lastIndex = listHocKy.getListHocKy().size() - 1;
        hocKy = listHocKy.getListHocKy().get(lastIndex);
    }

    private void setListLopHoc() {
        // lay thong tin cac lopHoc hoc ma GV day <tai thoi diem moi nhat>
        this.listLopHoc = new ListLopHoc();
        int sttHocKy = hocKy.getSttHocKy();
        int namHoc = hocKy.getNamHoc();
        ArrayList<LopHoc> listLopHoc = KetNoiSQL_GV.getListLopHoc(sttHocKy, namHoc);
        if (!listLopHoc.isEmpty()) {
            this.listLopHoc.getListLopHoc().addAll(listLopHoc);
        }
    }

    private void setListBuoiHoc() {
        // moi lopHoc se lay data cac buoi hoc tuong ung da diem danh
        this.listBuoiHoc = new ListBuoiHoc();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<BuoiHoc> listBuoiHoc = KetNoiSQL_GV.getListBuoiHoc(lopHoc.getMaLopHoc());
            if (!listBuoiHoc.isEmpty()) {
                this.listBuoiHoc.getListBuoiHoc().addAll(listBuoiHoc);
            }

        }
    }

    private void setTableProperties() {
        tblLichDiemDanh.setDefaultRenderer(Object.class, new CalendarRenderer1(CalendarRenderer1.Mode.ROLLUPCALENDAR));
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(TimeTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        int selectIndexCBTuan = 0;
        HocKy hocKyMoiNhat = hocKy;
        Tuan tuanBDHocKyMoiNhat = hocKyMoiNhat.getDsTuan().get(0);
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
        LopHoc lopHoc = listLopHoc.getLopHoc(buoiHoc.getMaLopHoc());
        String s = lopHoc.getTenMonHoc() + "\n" + lopHoc.getLop() + "\n" + buoiHoc.getTenNhom();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTuanTruoc = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboTuan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLichDiemDanh = new javax.swing.JTable();
        btnTuanKe = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 204));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTuanTruoc.setBackground(new java.awt.Color(255, 255, 255));
        btnTuanTruoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTuanTruoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-First-icon-16.png"))); // NOI18N
        btnTuanTruoc.setText("Tuần Trước");
        btnTuanTruoc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTuanTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuanTruocActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboTuan, 0, 334, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboHocKy, 0, 240, Short.MAX_VALUE)
                        .addGap(104, 104, 104)))
                .addGap(113, 113, 113))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboHocKy))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboTuan))
                .addContainerGap())
        );

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
        if (tblLichDiemDanh.getColumnModel().getColumnCount() > 0) {
            tblLichDiemDanh.getColumnModel().getColumn(0).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(1).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(2).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(3).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(4).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(5).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(6).setResizable(false);
            tblLichDiemDanh.getColumnModel().getColumn(7).setResizable(false);
        }

        btnTuanKe.setBackground(new java.awt.Color(255, 255, 255));
        btnTuanKe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTuanKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-Last-icon-16.png"))); // NOI18N
        btnTuanKe.setText("Tuần Kế");
        btnTuanKe.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTuanKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuanKeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(220, 220, 220))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(472, 472, 472)
                .addComponent(btnTuanTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTuanKe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTuanKe, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTuanTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboTuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTuanActionPerformed
        indexTuan = comboTuan.getSelectedIndex();
        showTable(indexTuan);
    }//GEN-LAST:event_comboTuanActionPerformed

    private void btnTuanTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuanTruocActionPerformed
        if (indexTuan > 0) {
            indexTuan--;
            comboTuan.setSelectedIndex(indexTuan);
            //showTable(indexTuan);
        }
    }//GEN-LAST:event_btnTuanTruocActionPerformed

    private void btnTuanKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuanKeActionPerformed
        if (indexTuan < comboTuan.getItemCount() - 1) {
            indexTuan++;
            comboTuan.setSelectedIndex(indexTuan);
            //showTable(indexTuan);
        }
    }//GEN-LAST:event_btnTuanKeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTuanKe;
    private javax.swing.JButton btnTuanTruoc;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboTuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLichDiemDanh;
    // End of variables declaration//GEN-END:variables
}
