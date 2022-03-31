/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListNhomSV;
import Model.GiaoVien.NhomSV;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.DiemDanhSV;
import Model.HocKy;
import Model.GiaoVien.LopHoc;
import Model.ListHocKy;
import Model.GiaoVien.ListBuoiHoc;
import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chuon
 */
public class RollUpInformationDlg extends javax.swing.JDialog {

    private Frame home;
    private RollUpCreatePanel rollUpCreatePanel; // lay data listBuoiHoc nham kiem tra trung ngay diem danh
    private ListLopHoc listLopHoc; // lay thong tin cac lop hoc ma GV day + vai tro GV trong lop
    private ListNhomSV listNhomSV; // lay danh sach sinh vien hoc cac nhom   
    

    // con thieu setUp ngayDay trong firstSetUp()
    // van chua code nhung phan them ngay day
    // chua code kiem tra ngayDay <= ngayKetThuc HocKy
    public RollUpInformationDlg(java.awt.Frame parent, boolean modal, RollUpCreatePanel rollUpCreatePanel, BuoiHoc buoiHoc) {
        super(parent, modal);
        initComponents();
        setTitle("Thông Tin Buổi Điểm Danh");
        setLocationRelativeTo(null);
        home = parent;
        this.rollUpCreatePanel = rollUpCreatePanel;
        setAllData();
        firstSetUp(buoiHoc);
    }

    private void firstSetUp(BuoiHoc buoiHoc) {
        // lan set up dau tien dua vao buoiHoc da chon luc truoc       
        setUpCBTenMonHoc();
        setUpCBLop();
        setUpCBNhom();
        LopHoc lopHoc = listLopHoc.getLopHoc(buoiHoc.getMaLopHoc());
        // neu trong buoiHoc co chua thong tin lopHoc
        if (lopHoc != null) {
            String tenMonHoc = lopHoc.getTenMonHoc();
            String lop = lopHoc.getLop();
            comboTenMonHoc.setSelectedItem(tenMonHoc);
            comboLop.setSelectedItem(lop);
            comboNhom.setSelectedItem(buoiHoc.getTenNhom());
        }
        dateChooser.setDate(buoiHoc.getNgayHoc());
        if (buoiHoc.getCa() == 0) {
            //btnGrpCa.setSelected((ButtonModel) radioSang, rootPaneCheckingEnabled);
            radioSang.setSelected(true);
        } else {
            //btnGrpCa.setSelected((ButtonModel) radioChieu, rootPaneCheckingEnabled);
            radioChieu.setSelected(true);
        }
    }

    private void setAllData() {
        setListLopHoc();
        setListNhomSV();
    }

    private void setListLopHoc() {
        // lay data cua hoc ky moi nhat gv day
        // lay hoc ky moi nhat, chinh la LastValue trong dsHocKy
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_GV();
        int lastIndex = listHocKy.getListHocKy().size() - 1;
        HocKy hocKy = listHocKy.getListHocKy().get(lastIndex);
        // lay thong tin cac lopHoc hoc ma GV day <tai thoi diem moi nhat>
        this.listLopHoc = new ListLopHoc();
        int sttHocKy = hocKy.getSttHocKy();
        int namHoc = hocKy.getNamHoc();
        ArrayList<LopHoc> listLop = KetNoiSQL_GV.getListLopHoc(sttHocKy, namHoc);
        if (!listLop.isEmpty()) {
            this.listLopHoc.getListLopHoc().addAll(listLop);
        }
    }

    private void setListNhomSV() {
        // lay data theo tung lop hoc
        this.listNhomSV = new ListNhomSV();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<NhomSV> listNhom = KetNoiSQL_GV.getListNhomSV(lopHoc.getMaLopHoc());
            if (!listNhom.isEmpty()) {
                this.listNhomSV.getListNhomSV().addAll(listNhom);
            }
        }
    }

    private boolean checkCBTenMonHocExist(String tenMonHoc) {
        // kiem tra ten mon hoc da ton tai trong comboBox chua?
        for (int i = 0; i < comboTenMonHoc.getItemCount(); i++) {
            String tenSoSanh = comboTenMonHoc.getItemAt(i);
            if (tenMonHoc.equals(tenSoSanh)) {
                return true;
            }
        }
        return false;
    }

    public void setUpCBTenMonHoc() {
        comboTenMonHoc.removeAllItems();
        for (LopHoc lop : listLopHoc.getListLopHoc()) {
            if (!checkCBTenMonHocExist(lop.getTenMonHoc())) {
                comboTenMonHoc.addItem(lop.getTenMonHoc());
            }
        }
    }

    private boolean checkCBLopExist(String lop) {
        // kiem tra lopHoc da ton tai trong combobox lopHoc chua?
        for (int i = 0; i < comboLop.getItemCount(); i++) {
            String lopSoSanh = comboLop.getItemAt(i);
            if (lop.equals(lopSoSanh)) {
                return true;
            }
        }
        return false;
    }

    private void setUpCBLop() {
        comboLop.removeAllItems();
        String tenMonHoc = "";
        if (comboTenMonHoc.getItemCount() > 0) {
            tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
        }        
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            if (tenMonHoc.equals(lopHoc.getTenMonHoc()) && !checkCBLopExist(lopHoc.getLop())) {
                comboLop.addItem(lopHoc.getLop());
            }
        }
    }

    private void setUpCBNhom() {
        comboNhom.removeAllItems();
        String tenMonHoc = "";
        String lop = "";
        if (comboTenMonHoc.getItemCount() > 0 && comboLop.getItemCount() > 0) {
            tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            lop = comboLop.getSelectedItem().toString();
        }
        LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
        if (lopHoc != null) {
            ArrayList<NhomSV> listNhom = listNhomSV.getListNhomSV(lopHoc.getMaLopHoc());
            if (!listNhom.isEmpty()) {
                for (NhomSV nhom : listNhom) {
                    comboNhom.addItem(nhom.getTenNhom());
                }
            }
        }
    }

    private BuoiHoc khoiTaoBuoiHocBangThongTinDien() {
        BuoiHoc buoiHoc = null;
        if (comboTenMonHoc.getItemCount() > 0 && comboLop.getItemCount() > 0) {
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            String lop = comboLop.getSelectedItem().toString();
            LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
            if (lopHoc != null) {
                String maLopHoc = lopHoc.getMaLopHoc();
                String tenNhom = comboNhom.getSelectedItem().toString();
                Date ngayHoc = dateChooser.getDate();
                int ca = -1;
                if (radioSang.isSelected()) {
                    ca = 0;
                } else {
                    ca = 1;
                }
                ArrayList<DiemDanhSV> listDiemDanhSV = null;
                buoiHoc = new BuoiHoc(maLopHoc, tenNhom, ngayHoc, ca, listDiemDanhSV);
                ArrayList<String> listMSSV = listNhomSV.getNhomSV(maLopHoc, tenNhom).getListMSSV();
                buoiHoc.khoiTaolistDiemDanhSV(listMSSV);
            }
        }
        return buoiHoc;
    }

    private int checkHopLeThoiGianDiemDanh(BuoiHoc buoiHocKiemTra) {
        // kiem tra ngay diem danh co con trong khung thoi gian day hoc khong?/ return 1
        // ngay diem danh ko duoc nam trong nhung ngay da qua/2
        // ngay diem danh ko duoc la Chu Nhat/3
        // ngay diem danh ko duoc phep trung voi buoi diem danh khac/4
        Date ngayDiemDanh = buoiHocKiemTra.getNgayHoc();
        if(checkThoiGianDiemDanhNamTrongHocKy(ngayDiemDanh)){
            if(!checkThoiGianDiemDanhDaQua(ngayDiemDanh)){
                if(!checkThoiGianDiemDanhLaNgayChuNhat(ngayDiemDanh)){
                    if(!checkTrungThoiGianDiemDanh(buoiHocKiemTra)){
                        return 0;
                    }
                    else{
                        return 4;
                    }
                }
                else{
                    return 3;
                }
            }
            else{
                return 2;
            }
        }
        else {
            return 1;
        }
        
    }

    private boolean checkThoiGianDiemDanhNamTrongHocKy(Date ngayDiemDanh){
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_GV();
        int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
        HocKy hocKy = listHocKy.getListHocKy().get(lastIndexListHocKy);
        Date ngayBDHocKy = hocKy.getDsTuan().get(0).getNgayBDTuan();
        int lastIndexhocKy = hocKy.getDsTuan().size() - 1;
        Date ngayKTHocKY = hocKy.getDsTuan().get(lastIndexhocKy).getNgayKTTuan();
         if (ngayDiemDanh.compareTo(ngayBDHocKy) >= 0 && ngayDiemDanh.compareTo(ngayKTHocKY) < 0) {
             return true;
         }else{
             return false;
         }
    }

    private boolean checkThoiGianDiemDanhDaQua(Date ngayDiemDanh) {
        // chi cho phep diem danh ke tu ngay hien tai tro ve sau
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(RollUpInformationDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ngayDiemDanh.compareTo(ngayHienTai) >= 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkThoiGianDiemDanhLaNgayChuNhat(Date ngayDiemDanh) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayDiemDanh);
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkTrungThoiGianDiemDanh(BuoiHoc buoiHocKiemTra) {
        // kiem tra thoi gian diem danh co bi trung voi buoi diem danh khac khong?
        ListBuoiHoc listTongBuoiHoc = rollUpCreatePanel.getListBuoiHoc();
        if (!listTongBuoiHoc.getListBuoiHoc().isEmpty()) {
            for (BuoiHoc buoiHoc : listTongBuoiHoc.getListBuoiHoc()) {
                if (buoiHoc.getNgayHoc().equals(buoiHocKiemTra.getNgayHoc())
                        && buoiHoc.getCa() == buoiHocKiemTra.getCa()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetDateChooser() {
        // set dateChooser ve ngay hien tai
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(RollUpInformationDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
        dateChooser.setDate(ngayHienTai);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpCa = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboTenMonHoc = new javax.swing.JComboBox<>();
        radioSang = new javax.swing.JRadioButton();
        radioChieu = new javax.swing.JRadioButton();
        comboNhom = new javax.swing.JComboBox<>();
        btnDiemDanh = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        comboLop = new javax.swing.JComboBox<>();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tên Môn Học");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ngày Dạy");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 70, 20));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ca");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 150, 70, 20));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nhóm");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 70, 30));

        comboTenMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTenMonHocActionPerformed(evt);
            }
        });
        getContentPane().add(comboTenMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 172, -1));

        radioSang.setBackground(new java.awt.Color(255, 255, 255));
        btnGrpCa.add(radioSang);
        radioSang.setText("SÁNG");
        radioSang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(radioSang, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        radioChieu.setBackground(new java.awt.Color(255, 255, 255));
        btnGrpCa.add(radioChieu);
        radioChieu.setText("CHIỀU");
        radioChieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(radioChieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, -1, -1));
        getContentPane().add(comboNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 172, -1));

        btnDiemDanh.setBackground(new java.awt.Color(255, 255, 255));
        btnDiemDanh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDiemDanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/ok.png"))); // NOI18N
        btnDiemDanh.setText("Điểm Danh");
        btnDiemDanh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDiemDanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiemDanhActionPerformed(evt);
            }
        });
        getContentPane().add(btnDiemDanh, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 100, 34));

        btnHuy.setBackground(new java.awt.Color(255, 255, 255));
        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-edit-delete-icon-16.png"))); // NOI18N
        btnHuy.setText("Hủy Bỏ");
        btnHuy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        getContentPane().add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 90, 34));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Lớp");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        comboLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLopActionPerformed(evt);
            }
        });
        getContentPane().add(comboLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 172, -1));
        getContentPane().add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 172, -1));

        jLabel6.setBackground(new java.awt.Color(255, 153, 153));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Thông Tin Buổi Điểm Danh");
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 374, 31));

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 350, 10));

        jLabel7.setBackground(new java.awt.Color(255, 204, 204));
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel7.setOpaque(true);
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 370, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTenMonHocActionPerformed
        if (comboTenMonHoc.getItemCount() != 0
                && comboLop.getItemCount() != 0 && comboNhom.getItemCount() != 0) {
            setUpCBLop();
            setUpCBNhom();
        }
    }//GEN-LAST:event_comboTenMonHocActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboTenMonHoc.getItemCount() != 0
                && comboLop.getItemCount() != 0 && comboNhom.getItemCount() != 0) {
            setUpCBNhom();
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void btnDiemDanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiemDanhActionPerformed
        // khoi tao buoiHoc theo nhung thong so da dien
        // khoi tao listDiemDanhSV luon
        // neu dong y nhung buoi hoc da ton tai vay thi xuat thong bao, ko cho diem danh
        BuoiHoc buoiHoc = khoiTaoBuoiHocBangThongTinDien();
        if (buoiHoc == null) {
            JOptionPane.showMessageDialog(this, "Không Đủ Thông Tin Để Tạo Buổi Điểm Danh",
                        "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int checkedValue = checkHopLeThoiGianDiemDanh(buoiHoc);
            if (checkedValue == 1) {
                JOptionPane.showMessageDialog(this, "Ngày Điểm Danh Không Nằm Trong Học Kỳ Này",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 2) {
                JOptionPane.showMessageDialog(this, "Không Cho Điểm Danh Buổi Dạy Đã Qua",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                resetDateChooser();
            } else if (checkedValue == 3) {
                JOptionPane.showMessageDialog(this, "Không Cho Điểm Danh Vào Ngày Chủ Nhật",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 4) {
                JOptionPane.showMessageDialog(this, "Trùng Buổi Điểm Danh Đã Tạo",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                // tao DiaLog moi
                ListBuoiHoc listBuoiHoc = new ListBuoiHoc();
                listBuoiHoc.getListBuoiHoc().add(buoiHoc);
                LopHoc lopHoc = listLopHoc.getLopHoc(listBuoiHoc.getListBuoiHoc().get(0).getMaLopHoc());
                RollUpStudentCreateDlg dlg = new RollUpStudentCreateDlg(home, true, rollUpCreatePanel,lopHoc, listBuoiHoc);
                dlg.setVisible(true);
                // neu da luu thanh cong thi dong cac Dlg
                if (dlg.isSaved()) {
                    dlg.dispose();
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_btnDiemDanhActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

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
//            java.util.logging.Logger.getLogger(RollUpInformationDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RollUpInformationDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RollUpInformationDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RollUpInformationDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                
//                RollUpInformationDlg dialog = new RollUpInformationDlg(new javax.swing.JFrame(),rootPaneCheckingEnabled, buoiHoc);
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
    private javax.swing.JButton btnDiemDanh;
    private javax.swing.ButtonGroup btnGrpCa;
    private javax.swing.JButton btnHuy;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboNhom;
    private javax.swing.JComboBox<String> comboTenMonHoc;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radioChieu;
    private javax.swing.JRadioButton radioSang;
    // End of variables declaration//GEN-END:variables
}
