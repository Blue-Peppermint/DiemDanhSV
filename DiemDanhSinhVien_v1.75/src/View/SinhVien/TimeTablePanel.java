/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SinhVien;

import Model.CalendarRenderer1;
import Model.HocKy;
import Model.LichDayChinh;
import Model.ListHocKy;
import Model.SinhVien.KetNoiSQL_SV;
import Model.SinhVien.ListLichDayChinh;
import Model.SinhVien.ListLopHoc;
import Model.SinhVien.LopHoc;
import Model.Tuan;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chuon
 */
public class TimeTablePanel extends javax.swing.JPanel {

    private ListHocKy listHocKy; // lich tong quat nha truong xep theo tuan + hoc ky 
    private ListLopHoc listLopHoc; // chua cac lop hoc giao vien day
    private ListLichDayChinh listLichDay;
    private DefaultTableModel modelTbl;
    private int indexHocKy; // cac index bat dau tu so 0
    private int indexTuan;

    /**
     * Creates new form TimeTablePanel
     */
    public TimeTablePanel() {
        initComponents();
        firstSetUp();
    }

    private void firstSetUp() {
        // nhung gi can cho lan setUp du lieu dau tien se them vao day
        setAllData();
        modelTbl = (DefaultTableModel) tblThoiGianBieu.getModel();
        setTableProperties();
        if (listHocKy.getListHocKy().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sinh Viên Chưa Tham Gia Bất Kỳ Lớp Học Nào",
                     "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            indexHocKy = -1;
            indexTuan = -1;
            setUpCBHocKy(listHocKy);
            setUpCBTuan(indexHocKy);
            setCBMoiNhat();
        }
    }

    private void setAllData() {
        // nhung thuoc tinh can set Data thi them vao day
        // get hoc ky
        setListHocKy();
        // get lop hoc dua vao hoc ky + GV + idVaiTro
        // nhung data lien quan den GV + idVaiTro khong can quan tam, boi vi tren sql chi lay data lien quan
        // den GV + idVaiTro thoi
        setListLopHoc();
        // get lichDay dua vao nhung lop hoc da co
        setListLichDay();
    }

    private void setListHocKy() {
        listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_SV();
    }

    private void setListLopHoc() {
        // lay cac lop hoc GV day theo hoc ky + nam hoc
        this.listLopHoc = new ListLopHoc();
        for (HocKy hocKy : listHocKy.getListHocKy()) {
            int sttHocKy = hocKy.getSttHocKy();
            int namHoc = hocKy.getNamHoc();
            ArrayList<LopHoc> list = KetNoiSQL_SV.getListLopHoc(sttHocKy, namHoc);
            this.listLopHoc.getListLopHoc().addAll(list);
        }
    }

    private void setListLichDay() {
        listLichDay = new ListLichDayChinh();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<LichDayChinh> lich = KetNoiSQL_SV.getLichDay(lopHoc);
            listLichDay.getListLichDay().addAll(lich);
        }
    }

    private void setTableProperties() {
        // set cac option lien quan den jtable
        tblThoiGianBieu.setDefaultRenderer(Object.class, new CalendarRenderer1(CalendarRenderer1.Mode.TIMETABLE));
        tblThoiGianBieu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblThoiGianBieu.setCellSelectionEnabled(true);
        tblThoiGianBieu.getTableHeader().setReorderingAllowed(false);
        if (tblThoiGianBieu.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < tblThoiGianBieu.getColumnModel().getColumnCount(); i++) {
                tblThoiGianBieu.getColumnModel().getColumn(i).setResizable(false);
            }
        }
    }

    private void setUpCBHocKy(ListHocKy listHocKy) {
        // chua kiem tra da co du du lieu chua
        for (HocKy tmp : listHocKy.getListHocKy()) {
            String s = "Học Kỳ " + String.valueOf(tmp.getSttHocKy());
            comboHocKy.addItem(s);
        }
        indexHocKy = comboHocKy.getItemCount() - 1;
    }

    private void setUpCBTuan(int indexHocKy) {
        // ung voi tung hoc ky, se dua ra du lieu tuan tuong ung
        // kiem tra hocKy da co du du lieu chua?
        HocKy hocKy = listHocKy.getListHocKy().get(indexHocKy);
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
        comboTuan.setSelectedIndex(indexTuan);
    }

    private void setCBMoiNhat() {
        // set selectedIndex cho cac comboBox. Chi chay method nay khi da setUpCBHocKy() + setUpCBTuan()
        // luon show hoc ky moi nhat
        // neu ngay hien tai chua den ngay trong khung gio lich day thi show tuan 1
        // neu ngay hien tai van nam trong khung gio lich day thi show tuan do
        // neu ngay hien tai da qua khung gio lich day thi show tuan cuoi cung
        int selectIndexCBHocKy = comboHocKy.getItemCount() - 1;
        comboHocKy.setSelectedIndex(selectIndexCBHocKy);
        indexHocKy = selectIndexCBHocKy;
        int selectIndexCBTuan = 0;
        int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
        HocKy hocKyMoiNhat = listHocKy.getListHocKy().get(lastIndexListHocKy);
        Tuan tuanBDHocKyMoiNhat = hocKyMoiNhat.getDsTuan().get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(TimeTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (tuanBDHocKyMoiNhat.getNgayBDTuan().compareTo(ngayHienTai) > 0) {
            indexTuan = selectIndexCBTuan;
            comboTuan.setSelectedIndex(indexTuan);
            return;
        }
        int lastIndexDSTuan = hocKyMoiNhat.getDsTuan().size() - 1;
        Tuan tuanKtHocKyMoiNhat = hocKyMoiNhat.getDsTuan().get(lastIndexDSTuan);
        if (tuanKtHocKyMoiNhat.getNgayKTTuan().compareTo(ngayHienTai) < 0) {
            selectIndexCBTuan = lastIndexDSTuan;
            indexTuan = selectIndexCBTuan;
            comboTuan.setSelectedIndex(indexTuan);
            return;
        }
        for (int indexTuan = 0; indexTuan < hocKyMoiNhat.getDsTuan().size(); indexTuan++) {
            Tuan tuan = hocKyMoiNhat.getDsTuan().get(indexTuan);
            if (tuan.getNgayBDTuan().compareTo(ngayHienTai) <= 0
                    && tuan.getNgayKTTuan().compareTo(ngayHienTai) >= 0) {
                selectIndexCBTuan = indexTuan;
            }
        }        
        indexTuan = selectIndexCBTuan;
        comboTuan.setSelectedIndex(indexTuan);
    }

    private void setLopHocOnTable(LichDayChinh lichDayChinh) {
        //show Lop Hoc len JTable mong muon
        int row = lichDayChinh.getCa();
        int column = lichDayChinh.getThuHoc() - 1;

        LopHoc lopHoc = listLopHoc.getLopHoc(lichDayChinh.getMaLopHoc());
        String s = lopHoc.getTenMonHoc() + "\n" + lopHoc.getLop() + "\n"
                + lichDayChinh.getTenNhom();
        modelTbl.setValueAt(s, row, column);
    }
    
    private void showTable(int indexHocKy, int indexTuan) {
        // show table cac lop hoc dua vao cac tuan trong hocKy
        // reset table
        modelTbl.setNumRows(0);
        // chi setValueAt duoc khi ma row do da ton tai
        modelTbl.addRow(new Object[]{
            "SÁNG"
        });
        modelTbl.addRow(new Object[]{
            "CHIỀU"
        });
        // duyet tung lop hoc so sanh ngay ket thuc mon hoc (listLichDay) voi ngay ket thuc tuan(DsHocKy)
        for (LichDayChinh lich : listLichDay.getListLichDay()) {
            HocKy hocKy = listHocKy.getListHocKy().get(indexHocKy);
            Date ngayBDTuan = hocKy.getDsTuan().get(indexTuan).getNgayBDTuan();
            Date ngayKTTuan = hocKy.getDsTuan().get(indexTuan).getNgayKTTuan();
            Date ngayKTMon = lich.getNgayKT();
            Date ngayBDMon = lich.getNgayBD();
            // neu nhu mon hoc van con day trong tuan sttTuan va hoc ky
            LopHoc lop = listLopHoc.getLopHoc(lich.getMaLopHoc());
//            if (ngayBDTuan.compareTo(ngayBDMon) >= 0 && ngayKTTuan.compareTo(ngayKTMon) <= 0
//                    && lop.getHocKy() == hocKy.getSttHocKy() && lop.getNamHoc() == hocKy.getNamHoc()) {
//                setLopHocOnTable(lich);
//            }
            if (ngayBDTuan.compareTo(ngayBDMon) >= 0 && ngayKTTuan.compareTo(ngayKTMon) <= 0) {
                setLopHocOnTable(lich);
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

        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThoiGianBieu = new javax.swing.JTable();
        btnTuanTruoc = new javax.swing.JButton();
        BtnTuanKe = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboTuan = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 204, 204));

        jLabel3.setBackground(new java.awt.Color(255, 153, 153));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("THÔNG TIN THỜI GIAN BIỂU");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel3.setOpaque(true);

        tblThoiGianBieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblThoiGianBieu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblThoiGianBieu.setModel(new javax.swing.table.DefaultTableModel(
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
        tblThoiGianBieu.setRowHeight(tblThoiGianBieu.getRowHeight()*5);
        tblThoiGianBieu.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblThoiGianBieu);

        btnTuanTruoc.setText("Tuần Trước");
        btnTuanTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuanTruocActionPerformed(evt);
            }
        });

        BtnTuanKe.setText("Tuần Kế");
        BtnTuanKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTuanKeActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Chọn học kỳ:");

        comboHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHocKyActionPerformed(evt);
            }
        });

        jLabel2.setText("Chọn tuần:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTuanTruoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnTuanKe, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 89, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTuanTruoc)
                        .addComponent(BtnTuanKe)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTuanTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuanTruocActionPerformed
        //rang buoc ko cho lui lien tuc
        if (indexTuan > 0) {
            indexTuan--;
            comboTuan.setSelectedIndex(indexTuan);
            showTable(indexHocKy, indexTuan);
        }
    }//GEN-LAST:event_btnTuanTruocActionPerformed

    private void BtnTuanKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTuanKeActionPerformed
        if (indexTuan < comboTuan.getItemCount() - 1) {
            indexTuan++;
            comboTuan.setSelectedIndex(indexTuan);
            showTable(indexHocKy, indexTuan);
        }
    }//GEN-LAST:event_BtnTuanKeActionPerformed

    private void comboHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHocKyActionPerformed
        indexHocKy = comboHocKy.getSelectedIndex();
        setUpCBTuan(indexHocKy);
        if (indexHocKy >= 0 && indexTuan >= 0) {           
            showTable(indexHocKy, indexTuan);
        }
    }//GEN-LAST:event_comboHocKyActionPerformed

    private void comboTuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTuanActionPerformed
        indexTuan = comboTuan.getSelectedIndex();
        if (indexHocKy >= 0 && indexTuan >= 0) {
            showTable(indexHocKy, indexTuan);
        }
    }//GEN-LAST:event_comboTuanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnTuanKe;
    private javax.swing.JButton btnTuanTruoc;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboTuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblThoiGianBieu;
    // End of variables declaration//GEN-END:variables
}
