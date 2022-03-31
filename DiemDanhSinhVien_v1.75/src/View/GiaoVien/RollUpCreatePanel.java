/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import TableModel.GiaoVien.RollUpCalendarRenderer;
import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListLichDayChinh;
import Model.GiaoVien.ListLopHoc;
import Model.LichDayChinh;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.DiemDanhSV;
import Model.GiaoVien.LopHoc;
import Model.HocKy;
import Model.ListHocKy;
import Model.GiaoVien.ListBuoiHoc;
import Model.Tuan;
import Model.*;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chuon
 */
// chua them ngoai le: ngung cho phep diem danh neu hoc ky da het thoi han (So voi thoi gian thuc)
public class RollUpCreatePanel extends javax.swing.JPanel {

    private JFrame home;
    private HocKy hocKy; // lay lich cac tuan do nha truong sap xep
    private ListLopHoc listLopHoc;
    private ListBuoiHoc listBuoiHoc; // chua maso lop hoc + thong tin buoi hoc + diem danh
    private ListLichDayChinh listLichDay; // lich day chinh chi tiet cua cac lop hoc
    private DefaultTableModel tblModel;
    private int indexTuan; // cac index bat dau tu so 0
    private RollUpCalendarRenderer tblRenderer;
    
    // gia su tat ca thuoc tinh tren da co data roi
    // neu la buoi hoc da diem danh thi se co mau diem danh, con chua diem danh se co mau khac, nho de chu thich
    public RollUpCreatePanel(JFrame parent) {
        initComponents();
        home = parent;
        firstSetUp();
    }

    public ListBuoiHoc getListBuoiHoc() {
        return listBuoiHoc;
    }

    public void updateListBuoiHocAndShowTable(ListBuoiHoc listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
        tblRenderer = new RollUpCalendarRenderer(hocKy.getDsTuan().get(indexTuan), listLopHoc, listBuoiHoc);
        indexTuan = comboTuan.getSelectedIndex();
        showTable(indexTuan);
    }

    private void firstSetUp() {
        // nhung gi can cho lan setUp du lieu dau tien se them vao day
        setAllData();
        tblModel = (DefaultTableModel) tbLlichDay.getModel();
        setTableProperties();
        indexTuan = -1;
        setUpCBHocKy();
        setUpCBTuan(hocKy);
        setCBMoiNhat();
    }

    private void setAllData() {
        setHocKy();
        setListLopHoc();
        setListBuoiHoc();
        setListLichDay();
        tblRenderer = new RollUpCalendarRenderer(hocKy.getDsTuan().get(indexTuan), listLopHoc, listBuoiHoc);
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
        listLopHoc = KetNoiSQL_GV.getListLopHoc(sttHocKy, namHoc);
        this.listLopHoc.getListLopHoc().addAll(listLopHoc);
    }
    
    private void setListBuoiHoc() {
        // moi lopHoc se lay data cac buoi hoc tuong ung da diem danh
        this.listBuoiHoc = new ListBuoiHoc();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<BuoiHoc> listBuoiHoc = KetNoiSQL_GV.getListBuoiHoc(lopHoc.getMaLopHoc());
            this.listBuoiHoc.getListBuoiHoc().addAll(listBuoiHoc);
        }
    }

    private void setListLichDay() {
        // moi lopHoc se lay data lich day tuong ung
        listLichDay = new ListLichDayChinh();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<LichDayChinh> lich = KetNoiSQL_GV.getLichDay(lopHoc);
            listLichDay.getListLichDay().addAll(lich);
        }
    }

    private void setTableProperties() {
        tbLlichDay.setDefaultRenderer(Object.class,tblRenderer);
        tbLlichDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbLlichDay.setCellSelectionEnabled(true);
        tbLlichDay.getTableHeader().setReorderingAllowed(false);
        if (tbLlichDay.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < tbLlichDay.getColumnModel().getColumnCount(); i++) {
                tbLlichDay.getColumnModel().getColumn(i).setResizable(false);
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
        for (Tuan tmp : hocKy.getDsTuan()) {
            int sttTuan = tmp.getSttTuan();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayBDTuan = simpleDateFormat.format(tmp.getNgayBDTuan());
            String ngayKTTuan = simpleDateFormat.format(tmp.getNgayKTTuan());
            String s = "Tuần " + sttTuan + " [Từ " + ngayBDTuan + " -- Đến "
                    + ngayKTTuan + "]";
            comboTuan.addItem(s);
        }
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

    private void setLopHocOnTable(LichDayChinh lichDayChinh) {
        //show Lop Hoc len JTable mong muon
        int row = lichDayChinh.getCa();
        int column = lichDayChinh.getThuHoc() - 1;
        LopHoc lopHoc = listLopHoc.getLopHoc(lichDayChinh.getMaLopHoc());
        String s = lopHoc.getTenMonHoc() + "\n" + lopHoc.getLop() + "\n"
                + lichDayChinh.getTenNhom();
        tblModel.setValueAt(s, row, column);
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
        // show ten mon hoc + lop + ten nhom
        // show table cac lop hoc dua vao cac tuan trong hocKy
        // reset table
        //old way: tblModel.setNumRows(0);      
        tblModel.getDataVector().removeAllElements();
        tblModel.fireTableDataChanged();
        // chi setValueAt duoc khi ma row do da ton tai
        tblModel.addRow(new Object[]{
            "SÁNG"
        });
        tblModel.addRow(new Object[]{
            "CHIỀU"
        });
        // duyet tung lop hoc so sanh ngay ket thuc mon hoc (listLichDay) voi ngay ket thuc tuan(DsHocKy)
        for (LichDayChinh lich : listLichDay.getListLichDay()) {
            Date ngayBDTuan = hocKy.getDsTuan().get(indexTuan).getNgayBDTuan();
            Date ngayKTTuan = hocKy.getDsTuan().get(indexTuan).getNgayKTTuan();
            Date ngayKTMon = lich.getNgayKT();
            Date ngayBDMon = lich.getNgayBD();
            // neu nhu mon hoc van con day trong tuan sttTuan
            if (ngayBDTuan.compareTo(ngayBDMon) >= 0 && ngayKTTuan.compareTo(ngayKTMon) <= 0) {
                setLopHocOnTable(lich);
            }
        }

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

    private String[] getStrings(String BuoiHoc) {
        // tach lay thong tin can thiet trong chuoi lay tu table
        // vd Format:
        // Trí tuệ nhân tạo
        // D18CQCN01-N
        // Thực hành 1
        String[] ketQua = new String[3];
        for (int i = 0; i < ketQua.length; i++) {
            ketQua[i] = "";
        }
        int index = 0;
        for (int i = 0; i < BuoiHoc.length(); i++) {
            if (BuoiHoc.charAt(i) == '\n') {
                index++;
            } else {
                ketQua[index] += BuoiHoc.charAt(i);
            }
        }
        return ketQua;
    }

    private Date calDateChoosenOnTable() {
        // tra ve ngay cua buoi hoc da chon tren table
        Date ketQua = null;
        long ngayBDTuanInMiliS = hocKy.getDsTuan().get(indexTuan).getNgayBDTuan().getTime();
        long oneDayinMiliS = 24 * 60 * 60 * 1000;
        int nowDate = tbLlichDay.getSelectedColumn() + 1;
        int distant = nowDate - 2;
        ketQua = new Date(ngayBDTuanInMiliS + distant * oneDayinMiliS);
        return ketQua;
    }

    private BuoiHoc getBuoiHocOnTable() {
        // tra ve gia tri buoi hoc dua vao object chon tu table
        BuoiHoc buoiHoc = null;
        int row = tbLlichDay.getSelectedRow();
        int column = tbLlichDay.getSelectedColumn();
        // kiem tra xem da chon hay chua?
        if (row >= 0 && column > 0) {
            Object selectedValue = tbLlichDay.getValueAt(row, column);
            // neu nhu chon 1 lich hoc dung
            if (selectedValue != null) {
                String buoiHocString = selectedValue.toString();
                String[] buoiHocArray = getStrings(buoiHocString);
                String tenMonHoc = buoiHocArray[0];
                String lop = buoiHocArray[1];
                String tenNhom = buoiHocArray[2];
                String maLopHoc = listLopHoc.getLopHoc(tenMonHoc, lop).getMaLopHoc();
                Date ngayHoc = calDateChoosenOnTable();
                int ca = tbLlichDay.getSelectedRow();
                ArrayList<DiemDanhSV> listDiemDanhSV = new ArrayList<>();
                buoiHoc = new BuoiHoc(maLopHoc, tenNhom, ngayHoc, ca, listDiemDanhSV);
            } else if (column > 0) { // neu chon 1 buoi hoc tu do, thi chi lay ngayHoc + ca thoi
                Date ngayHoc = calDateChoosenOnTable();
                int ca = tbLlichDay.getSelectedRow();
                buoiHoc = new BuoiHoc();
                buoiHoc.setNgayHoc(ngayHoc);
                buoiHoc.setCa(ca);
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
        if (checkThoiGianDiemDanhNamTrongHocKy(ngayDiemDanh)) {
            if (!checkThoiGianDiemDanhDaQua(ngayDiemDanh)) {
                if (!checkThoiGianDiemDanhLaNgayChuNhat(ngayDiemDanh)) {
                    if (!checkTrungThoiGianDiemDanh(buoiHocKiemTra)) {
                        return 0;
                    } else {
                        return 4;
                    }
                } else {
                    return 3;
                }
            } else {
                return 2;
            }
        } else {
            return 1;
        }

    }

    private boolean checkThoiGianDiemDanhNamTrongHocKy(Date ngayDiemDanh) {
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_GV();
        int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
        HocKy hocKy = listHocKy.getListHocKy().get(lastIndexListHocKy);
        Date ngayBDHocKy = hocKy.getDsTuan().get(0).getNgayBDTuan();
        int lastIndexhocKy = hocKy.getDsTuan().size() - 1;
        Date ngayKTHocKY = hocKy.getDsTuan().get(lastIndexhocKy).getNgayKTTuan();
        if (ngayDiemDanh.compareTo(ngayBDHocKy) >= 0 && ngayDiemDanh.compareTo(ngayKTHocKY) < 0) {
            return true;
        } else {
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
        //ListBuoiHoc listTongBuoiHoc = rollUpCreatePanel.getListBuoiHoc();
        if (!listBuoiHoc.getListBuoiHoc().isEmpty()) {
            for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
                if (buoiHoc.getNgayHoc().equals(buoiHocKiemTra.getNgayHoc())
                        && buoiHoc.getCa() == buoiHocKiemTra.getCa()) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTuanTruoc = new javax.swing.JButton();
        btnTuanKe = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboTuan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLlichDay = new javax.swing.JTable();
        btnDiemDanh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Chọn học kỳ:");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);

        comboHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHocKyActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
                .addContainerGap(195, Short.MAX_VALUE))
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

        tbLlichDay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbLlichDay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbLlichDay.setModel(new javax.swing.table.DefaultTableModel(
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
        tbLlichDay.setRowHeight(tbLlichDay.getRowHeight() + 50);
        tbLlichDay.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbLlichDay);
        if (tbLlichDay.getColumnModel().getColumnCount() > 0) {
            tbLlichDay.getColumnModel().getColumn(0).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(1).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(2).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(3).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(4).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(5).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(6).setResizable(false);
            tbLlichDay.getColumnModel().getColumn(7).setResizable(false);
        }

        btnDiemDanh.setBackground(new java.awt.Color(255, 255, 255));
        btnDiemDanh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiemDanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/diemdanh.jpg"))); // NOI18N
        btnDiemDanh.setText("Điểm Danh");
        btnDiemDanh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDiemDanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiemDanhActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new Color(255, 214, 153));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new Color(255, 214, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel3.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(255, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Đã Điểm Danh");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);

        jLabel5.setBackground(Color.CYAN);
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(Color.CYAN);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("jLabel5");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel5.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(0, 204, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Chưa Điểm Danh");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addComponent(btnDiemDanh, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnTuanTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTuanKe, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDiemDanh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTuanKe, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnTuanTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHocKyActionPerformed
        // nothing :)
    }//GEN-LAST:event_comboHocKyActionPerformed

    private void comboTuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTuanActionPerformed
        if(tblRenderer != null){
            indexTuan = comboTuan.getSelectedIndex();
            tblRenderer.setTuan(hocKy.getDsTuan().get(indexTuan));
            showTable(indexTuan);
        }        
    }//GEN-LAST:event_comboTuanActionPerformed

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

    private void btnDiemDanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiemDanhActionPerformed
        // Dlg dau tien hien thi cac thong tin lien quan den BuoiHoc de GV xac nhan
        // kiem tra co duoc phep diem danh SV hay ko
        // reset lua chon moi lan nhan nut
        if (tbLlichDay.getSelectedColumn() > 0) {
            //int valueClickBtnDiemDanh = checkValidBtnDiemDanh();
            BuoiHoc buoiHoc = getBuoiHocOnTable();
            int checkedValue = checkHopLeThoiGianDiemDanh(buoiHoc);
            if (checkedValue == 1) {
                JOptionPane.showMessageDialog(this, "Ngày Điểm Danh Không Nằm Trong Học Kỳ Này",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 2) {
                JOptionPane.showMessageDialog(this, "Không Cho Điểm Danh Buổi Dạy Đã Qua",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 3) {
                JOptionPane.showMessageDialog(this, "Không Cho Điểm Danh Vào Ngày Chủ Nhật",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 4) {
                JOptionPane.showMessageDialog(this, "Trùng Buổi Điểm Danh Đã Tạo",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (checkedValue == 0) {
                if (buoiHoc != null) {
                    RollUpInformationDlg dlg = new RollUpInformationDlg(home, true, this, buoiHoc);
                    dlg.setVisible(true);
                    // reset row, column da chon trong jtable
                    showTable(indexTuan);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Buổi Dạy Nếu Muốn Điểm Danh");
        }
        tbLlichDay.clearSelection();
    }//GEN-LAST:event_btnDiemDanhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiemDanh;
    private javax.swing.JButton btnTuanKe;
    private javax.swing.JButton btnTuanTruoc;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboTuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tbLlichDay;
    // End of variables declaration//GEN-END:variables
}
