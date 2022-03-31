/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SinhVien;

import Model.HocKy;
import Model.ListHocKy;
import UnusedModel.ObjectRenderer;
import Model.SinhVien.GVDayHoc;
import Model.SinhVien.InfoTeacher;
import Model.SinhVien.KetNoiSQL_SV;
import Model.SinhVien.ListInfoTeacher;
import Model.SinhVien.ListLopHoc;
import Model.SinhVien.ListMail;
import Model.SinhVien.LopHoc;
import Model.SinhVien.Mail;
import TableModel.SinhVien.MailBoxTableModel;
import Model.Tuan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
/**
 * Luu Y:
 * Cac ComboBox trong day se setUp Items phu thuoc vao cac comboBox o tren no (dua vao view, tu tren xuong, trai sang phai)
 */

public class MailPanel extends javax.swing.JPanel {

    private MainFormSV home;
    private MailBoxTableModel tblModel;
    private ListHocKy listHocKy;
    private ListLopHoc listLopHoc; // lay thong tin cac lop hoc ma GV day + nhom trong lop
    private ListInfoTeacher listInfoTeacher;
    private ListMail listTongMail; // lay thong tin cac thu SV nhan duoc tu GV
    private ListMail listMailTrinhChieu; // phat sinh do chuc nang tim kiem theo ngay

    public MailPanel() {
        initComponents();
        firstSetUp();
    }

    private void firstSetUp() {
        setAllData();
        if (listHocKy.getListHocKy().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Sinh Viên Chưa Tham Gia Bất Kỳ Lớp Học Nào",
//                    "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tblModel = new MailBoxTableModel(listLopHoc, listInfoTeacher, listTongMail);
            tblMailBox.setModel(tblModel);
            setUpCBHocKy(listHocKy);
            setUpCBTenMonHoc(listLopHoc);
            setUpCBLop((String) comboTenMonHoc.getSelectedItem());
            setUpCBGiaoVien((String) comboTenMonHoc.getSelectedItem(), (String) comboLop.getSelectedItem());
            setTableProperties();
            comboGiaoVien.setSelectedIndex(0);
            comboHocKy.setSelectedIndex(comboHocKy.getItemCount() - 1);
        }
    }

    private void setAllData() {
        setListHocKy();
        setListTongMail();
        setListLopHoc(null);
        setListInfoTeacher();
        setUpCBNgayTimKiem(null);
        setListMailTrinhChieu();
    }

    private void setListHocKy() {
        listHocKy = new ListHocKy();
        listHocKy.getDataToanBo_SV();
    }

    private void setListTongMail() {
        this.listTongMail = new ListMail();
        ArrayList<Mail> listMail = KetNoiSQL_SV.getListMail();
        if (!listMail.isEmpty()) {
            this.listTongMail.getListMail().addAll(listMail);
        }

    }

    private void setListLopHoc(HocKy hocKy) {
        // moi hoc ky GV co day hoc se lay data cac lop hoc tuong ung
        this.listLopHoc = new ListLopHoc();
        // neu CBHocKy chon None thi getToanBoDataListLopHoc
        if (hocKy == null) {
            ArrayList<LopHoc> list = KetNoiSQL_SV.getListLopHoc();
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        } // neu CBHocKy chon khac None thi chi get DataListLopHoc cua hocKy tuong ung
        else {
            ArrayList<LopHoc> list = KetNoiSQL_SV.getListLopHoc(hocKy.getSttHocKy(), hocKy.getNamHoc());
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        }
    }

    private void setListInfoTeacher() {
        this.listInfoTeacher = new ListInfoTeacher();
        for (LopHoc lop : listLopHoc.getListLopHoc()) {
            ArrayList<InfoTeacher> list = KetNoiSQL_SV.getListInfoTeacher(lop.getMaLopHoc());
            if (!list.isEmpty()) {
                for(InfoTeacher infoTeacher : list){
                    if(!checkExistInfoTeacher(infoTeacher.getMaGV())){
                        this.listInfoTeacher.getListInfoTeacher().add(infoTeacher);
                    }
                }             
            }
        }
    }

    private boolean checkExistInfoTeacher(String maGV){
        for(InfoTeacher infoTeacher : listInfoTeacher.getListInfoTeacher()){
            if(infoTeacher.getMaGV().equals(maGV)){
                return true;
            }
        }
        return false;
    }
    
    private void setTableProperties() {
        // tuy chinh cac propeties linh tinh cua table
        tblMailBox.setDefaultRenderer(Object.class, new ObjectRenderer());
        tblMailBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblMailBox.getTableHeader().setReorderingAllowed(false);
        // tuy chinh chieu rong cua cac cot
        if (tblMailBox.getColumnModel().getColumnCount() > 0) {
            //STT
            tblMailBox.getColumnModel().getColumn(0).setMinWidth(75);
            tblMailBox.getColumnModel().getColumn(0).setMaxWidth(75);
            //TenMH
            tblMailBox.getColumnModel().getColumn(1).setResizable(false);
            //Lop
            tblMailBox.getColumnModel().getColumn(2).setMinWidth(100);
            tblMailBox.getColumnModel().getColumn(2).setMaxWidth(100);
            //GV Gui
            tblMailBox.getColumnModel().getColumn(3).setResizable(false);
            //Noi Dung
            tblMailBox.getColumnModel().getColumn(4).setResizable(false);
            //Thoi Gian Gui
            tblMailBox.getColumnModel().getColumn(5).setResizable(false);
        }
    }

    private void setUpCBHocKy(ListHocKy listHocKy) {
        comboTenMonHoc.removeAllItems();
        String item = "None"; // khong su dung chuc nang tim kiem trong comboBox
        comboHocKy.addItem(item);
        for (HocKy hocKy : listHocKy.getListHocKy()) {
            String newItem = "Học Kỳ " + hocKy.getSttHocKy() + " - Năm Học ";
            if (hocKy.getSttHocKy() == 1) {
                String namBD = String.valueOf(hocKy.getNamHoc());
                String namKT = String.valueOf(hocKy.getNamHoc() + 1);
                newItem += namBD + " - " + namKT;
            } else {
                String namBD = String.valueOf(hocKy.getNamHoc() - 1);
                String namKT = String.valueOf(hocKy.getNamHoc());
                newItem += namBD + " - " + namKT;
            }
            comboHocKy.addItem(newItem);
        }
        comboHocKy.setSelectedIndex(0);
    }

    private void setUpCBTenMonHoc(ListLopHoc listLopHoc) {
        comboTenMonHoc.removeAllItems();
        comboTenMonHoc.addItem("None");
        for (LopHoc lop : listLopHoc.getListLopHoc()) {
            if (!checkCBTenMonHocExist(lop.getTenMonHoc())) {
                comboTenMonHoc.addItem(lop.getTenMonHoc());
            }
        }
        comboTenMonHoc.setSelectedIndex(0);
    }

    private boolean checkCBTenMonHocExist(String tenMonHon) {
        // kiem tra ten mon hoc da ton tai trong combobox ten mon hoc chua?
        for (int i = 0; i < comboTenMonHoc.getItemCount(); i++) {
            String compareStr = comboTenMonHoc.getItemAt(i);
            if (tenMonHon.equals(compareStr)) {
                return true;
            }
        }
        return false;
    }

    private void setUpCBLop(String tenMonHoc) {
        comboLop.removeAllItems();
        String item = "None"; // khong su dung chuc nang tim kiem trong comboBox
        comboLop.addItem(item);
        // neu khong co ten mon hoc se hien thi toan bo lop hoc gv do da day
        if (tenMonHoc.equals("None")) {
            for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
                item = lopHoc.getLop();
                if (!checkCBLopExist(item)) {
                    comboLop.addItem(item);
                }
            }
        } // neu co ten mon hoc thi hien thi nhung lop tuong ung
        else {
            for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
                item = lopHoc.getLop();
                if (lopHoc.getTenMonHoc().equals(tenMonHoc) && !checkCBLopExist(item)) {
                    comboLop.addItem(item);
                }
            }
        }
        comboLop.setSelectedIndex(0);
    }

    private boolean checkCBLopExist(String lop) {
        // kiem tra lop da ton tai trong combobox lop chua?
        for (int i = 0; i < comboLop.getItemCount(); i++) {
            String lopKTra = comboLop.getItemAt(i);
            if (lop.equals(lopKTra)) {
                return true;
            }
        }
        return false;
    }

    private void setUpCBGiaoVien(String tenMonHoc, String lop) {
        comboGiaoVien.removeAllItems();
        comboGiaoVien.addItem("None");
        LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
        if (lopHoc != null) {
            ArrayList<GVDayHoc> listGVDayHoc = lopHoc.getListGVDayHoc().getListGVDayHoc();
            if (!listGVDayHoc.isEmpty()) {
                for(GVDayHoc gvDayHoc: listGVDayHoc){
                    String maGV = gvDayHoc.getMaGV();
                    String hoTen = listInfoTeacher.getInfoTeacher(maGV).getHoTen();
                    if(!checkCBGiaoVienExist(maGV)){
                        // do co the cac GV trung ho ten nen phai xuat luon ca maGV
                        String item = hoTen + " - " + maGV;
                        comboGiaoVien.addItem(item);
                    }                    
                }
            }
        }
        comboGiaoVien.setSelectedIndex(0);
    }

    private boolean checkCBGiaoVienExist(String maGV) {
        // kiem tra ma GV da ton tai trong combobox GV chua?
        for (int i = 0; i < comboGiaoVien.getItemCount(); i++) {
            String gvKTra = comboGiaoVien.getItemAt(i);
            if (maGV.equals(gvKTra)) {
                return true;
            }
        }
        return false;
    }

    private void filter() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
        String tenMonHoc = comboTenMonHoc.getSelectedItem().toString().trim();
        String lop = comboLop.getSelectedItem().toString().trim();
        String hoTenGV = comboGiaoVien.getSelectedItem().toString().trim();
        if (!tenMonHoc.equals("None")) {
            filters.add(RowFilter.regexFilter(tenMonHoc, 1));
        }
        if (!lop.equals("None")) {
            filters.add(RowFilter.regexFilter(lop, 2));
        }
        if (!hoTenGV.equals("None")) {
            filters.add(RowFilter.regexFilter(hoTenGV, 3));
        }      
        filters.add(RowFilter.regexFilter(txtAreaNoiDung.getText(), 4));
        RowFilter rf = RowFilter.andFilter(filters);
        TableRowSorter sorter = new TableRowSorter(tblModel);
        sorter.setRowFilter(rf);
        tblMailBox.setRowSorter(sorter);
    }

    private void setUpCBNgayTimKiem(HocKy hocKy) {
        // set data theo hocKy
        comboDateFrom.removeAllItems();
        comboDateTo.removeAllItems();
        if (!listHocKy.getListHocKy().isEmpty()) {
            if (hocKy == null) { // setUp từ ngày BD học kỳ đầu tiên -> ngày KT học kỳ cuối cùng
                // them comboBoxDateFrom: add thong tin tuan dau tien cua hoc ky dau tien
                HocKy hocKyDau = listHocKy.getListHocKy().get(0);
                Tuan tuanDauTien = hocKyDau.getDsTuan().get(0);
                int sttTuan = tuanDauTien.getSttTuan();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayBDTuan = simpleDateFormat.format(tuanDauTien.getNgayBDTuan());
                String ngayKTTuan = simpleDateFormat.format(tuanDauTien.getNgayKTTuan());
                String s = "Tuần " + sttTuan + " [Từ " + ngayBDTuan + " -- Đến "
                        + ngayKTTuan + "]";
                comboDateFrom.addItem(s);
                // them comboBoxDateTo: add thong tin tuan cuoi cung cua hoc ky cuoi cung
                int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
                HocKy hocKyCuoi = listHocKy.getListHocKy().get(lastIndexListHocKy);
                int lastIndexDSTuan = hocKyCuoi.getDsTuan().size() - 1;
                Tuan tuanCuoiCung = hocKyCuoi.getDsTuan().get(lastIndexDSTuan);
                sttTuan = tuanCuoiCung.getSttTuan();
                ngayBDTuan = simpleDateFormat.format(tuanCuoiCung.getNgayBDTuan());
                ngayKTTuan = simpleDateFormat.format(tuanCuoiCung.getNgayKTTuan());
                s = "Tuần " + sttTuan + " [Từ " + ngayBDTuan + " -- Đến "
                        + ngayKTTuan + "]";
                comboDateTo.addItem(s);
            } else { // setUp dua vao hocKy cu the da chon
                for (Tuan tmp : hocKy.getDsTuan()) {
                    int sttTuan = tmp.getSttTuan();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String ngayBDTuan = simpleDateFormat.format(tmp.getNgayBDTuan());
                    String ngayKTTuan = simpleDateFormat.format(tmp.getNgayKTTuan());
                    String s = "Tuần " + sttTuan + " [Từ " + ngayBDTuan + " -- Đến "
                            + ngayKTTuan + "]";
                    comboDateFrom.addItem(s);
                    comboDateTo.addItem(s);
                }
            }
        }
    }

    private void setListMailTrinhChieu() {
        // set data dua vao hocKy + cac ComboBoxDate tim kiem theo ngay
        listMailTrinhChieu = new ListMail();
        if (comboHocKy.getSelectedIndex() > 0) { // neu nhu comboHocKy chon item != None
            if (comboDateFrom.getSelectedIndex() <= comboDateTo.getSelectedIndex()) {
                HocKy hocKy = listHocKy.getListHocKy().get(comboHocKy.getSelectedIndex() - 1);
                Tuan tuanBD = hocKy.getDsTuan().get(comboDateFrom.getSelectedIndex());
                Tuan tuanKT = hocKy.getDsTuan().get(comboDateTo.getSelectedIndex());
                Date ngayBD = tuanBD.getNgayBDTuan();
                Date ngayKT = tuanKT.getNgayKTTuan();
                listMailTrinhChieu.setListMail(
                        listTongMail.getListMail(ngayBD, ngayKT));
            }
        } else if (comboHocKy.getSelectedIndex() == 0) { // neu nhu comboHocKy chon item == None
            HocKy hocKyDau = listHocKy.getListHocKy().get(0);
            Date ngayBD = hocKyDau.getDsTuan().get(0).getNgayBDTuan();
            int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
            HocKy hocKyCuoi = listHocKy.getListHocKy().get(lastIndexListHocKy);
            int lastIndexDSTuan = hocKyCuoi.getDsTuan().size() - 1;
            Date ngayKT = hocKyCuoi.getDsTuan().get(lastIndexDSTuan).getNgayKTTuan();
            listMailTrinhChieu.setListMail(
                    listTongMail.getListMail(ngayBD, ngayKT));
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
        tblMailBox = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTenMonHoc = new javax.swing.JComboBox<>();
        comboLop = new javax.swing.JComboBox<>();
        comboGiaoVien = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaNoiDung = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboDateFrom = new javax.swing.JComboBox<>();
        comboDateTo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnXem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 204));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblMailBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMailBox.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên MH", "Lớp", "Giáo Viên Gửi", "Nội dung", "Thời gian gửi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMailBox);

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("                                                                                HỘP THƯ");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên Môn Học");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Lớp");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);

        comboTenMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTenMonHocActionPerformed(evt);
            }
        });

        comboLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLopActionPerformed(evt);
            }
        });

        comboGiaoVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGiaoVienActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nội Dung");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);

        txtAreaNoiDung.setColumns(20);
        txtAreaNoiDung.setRows(5);
        txtAreaNoiDung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaNoiDungKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaNoiDung);

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Giáo Viên Gửi");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Học Kỳ");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);

        comboHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHocKyActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Thời Gian Gửi Từ");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Đến");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);

        comboDateFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateFromActionPerformed(evt);
            }
        });

        comboDateTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateToActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("TÌM KIẾM");
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel8.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboGiaoVien, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(369, 369, 369)
                        .addComponent(jLabel8)))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboGiaoVien, comboLop, comboTenMonHoc});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboGiaoVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        btnXem.setBackground(new java.awt.Color(255, 255, 255));
        btnXem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-24.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLamMoi))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE))))
                .addGap(116, 116, 116))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTenMonHocActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            setUpCBLop(tenMonHoc);
            String lop = (String) comboLop.getSelectedItem();
            // chay cac method sau kieu gi cung se ra 1 ket qua, muc dich la: Update comboGiaoVien ve Null
            setUpCBGiaoVien(tenMonHoc, lop);
            comboGiaoVien.setSelectedIndex(0);
            filter();
        }
    }//GEN-LAST:event_comboTenMonHocActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            String lop = (String) comboLop.getSelectedItem();
            // comboGiaoVien chi duoc phep update ngoai Null khi du ca tenMonHoc + Lop
            setUpCBGiaoVien(tenMonHoc, lop);
            comboGiaoVien.setSelectedIndex(0);
            filter();
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void comboGiaoVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGiaoVienActionPerformed
        // neu radioNhom duoc chon thi moi chay
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0) {
                filter();
        }
    }//GEN-LAST:event_comboGiaoVienActionPerformed

    private void txtAreaNoiDungKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaNoiDungKeyReleased
        filter();
    }//GEN-LAST:event_txtAreaNoiDungKeyReleased

    private void comboHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHocKyActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            HocKy hocKy = null;
            int selectedIndexCBHocKY = comboHocKy.getSelectedIndex();
            if (selectedIndexCBHocKY != 0) {
                hocKy = listHocKy.getListHocKy().get(selectedIndexCBHocKY - 1);
            }
            setListLopHoc(hocKy);
            setUpCBTenMonHoc(listLopHoc);
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            setUpCBLop(tenMonHoc);
            String lop = comboLop.getSelectedItem().toString();
            setUpCBGiaoVien(tenMonHoc, lop);
            setUpCBNgayTimKiem(hocKy);
            comboDateFrom.setSelectedIndex(0);
            comboDateTo.setSelectedIndex(comboDateTo.getItemCount() - 1);
            txtAreaNoiDung.setText("");
            setListMailTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc, listInfoTeacher, listMailTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
            filter();
        }
    }//GEN-LAST:event_comboHocKyActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        int row = tblMailBox.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phai chon vao 1 muc");
        } else {
            int rowModel = tblMailBox.convertRowIndexToModel(row);
            String ngayGui = (String) tblModel.getValueAt(rowModel, 5);
            Mail mail = listMailTrinhChieu.getMail(ngayGui);
            if (mail != null) {
                LopHoc lopHoc = listLopHoc.getLopHoc(mail.getMaLopHoc());
                InfoTeacher infoTeacher = listInfoTeacher.getInfoTeacher(mail.getMaGV());
                ReadMailDlg dlg = new ReadMailDlg(home, true, lopHoc, infoTeacher, mail);
                dlg.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnXemActionPerformed

    private void comboDateFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateFromActionPerformed
        if (comboDateFrom.getItemCount() > 0 && comboDateTo.getItemCount() > 0
            && comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            if(comboDateFrom.getSelectedIndex() > comboDateTo.getSelectedIndex()){
                JOptionPane.showMessageDialog(this, "Lỗi Tìm Kiếm Thời Gian Gửi Thư");
                comboDateFrom.setSelectedIndex(0);
                comboDateTo.setSelectedIndex(comboDateTo.getItemCount() -1);
            }
            setListMailTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc,listInfoTeacher, listMailTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
            filter();
        }
    }//GEN-LAST:event_comboDateFromActionPerformed

    private void comboDateToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateToActionPerformed
        if (comboDateFrom.getItemCount() > 0 && comboDateTo.getItemCount() > 0
            && comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboGiaoVien.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            if(comboDateFrom.getSelectedIndex() > comboDateTo.getSelectedIndex()){
                JOptionPane.showMessageDialog(this, "Lỗi Tìm Kiếm Thời Gian Gửi Thư");
                comboDateFrom.setSelectedIndex(0);
                comboDateTo.setSelectedIndex(comboDateTo.getItemCount() -1);
            }
            setListMailTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc,listInfoTeacher, listMailTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
        }
    }//GEN-LAST:event_comboDateToActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // refresh giao dien khi GV gửi thư mới
        setListTongMail();
        if (!listTongMail.getListMail().isEmpty()) {
            comboHocKy.setSelectedIndex(comboHocKy.getItemCount() - 1);
        }
    }//GEN-LAST:event_btnLamMoiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnXem;
    private javax.swing.JComboBox<String> comboDateFrom;
    private javax.swing.JComboBox<String> comboDateTo;
    private javax.swing.JComboBox<String> comboGiaoVien;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboTenMonHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblMailBox;
    private javax.swing.JTextArea txtAreaNoiDung;
    // End of variables declaration//GEN-END:variables
}
