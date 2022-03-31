/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import UnusedModel.ObjectRenderer;
import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.LopHoc;
import Model.HocKy;
import Model.ListHocKy;
import Model.GiaoVien.ListBuoiHoc;
import TableModel.GiaoVien.RollUpListTableModel;
import Model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
public class RollUpListPanel extends javax.swing.JPanel {

    private MainFormGV home;
    private HocKy hocKy; // lay lich cac tuan do nha truong sap xep
    private ListLopHoc listLopHoc; // lay thong tin cac lopHoc hoc ma GV day + vai tro GV trong lopHoc
    private ListBuoiHoc listTongBuoiHoc; // chua maso lop hoc + thong tin diem danh    
    private ListBuoiHoc listBuoiHocTrinhChieu;
    private RollUpListTableModel tblModel;

    public RollUpListPanel(MainFormGV home) {
        initComponents();
        this.home = home;
        firstSetUp();
    }
    
    public void updateListBuoiHocAndShowTable(ListBuoiHoc listBuoiHoc) {
        this.listTongBuoiHoc = listBuoiHoc;
        this.listTongBuoiHoc.sortDescendingListBuoiHoc();
        setListBuoiHocTrinhChieu();
        // de phong truong hop truoc do da set ngay ko hop ly, 
        // ma luc update listBuoiHoc la no van lay nhung du lieu cu de setListBuoiHocTrinhChieu
        if (listBuoiHocTrinhChieu.getListBuoiHoc().isEmpty()) {
            setNgayTimKiem();
            setListBuoiHocTrinhChieu();
        }
        tblModel = new RollUpListTableModel(listLopHoc, listBuoiHocTrinhChieu);
        tblDSDiemDanh.setModel(tblModel);
        setTableProperties();
        filter();
    }
    
    private void firstSetUp() {
        // nhung gi can cho lan setUp du lieu dau tien se them vao day
        setAllData();
        tblModel = new RollUpListTableModel(listLopHoc, listBuoiHocTrinhChieu);
        tblDSDiemDanh.setModel(tblModel);
        setTableProperties();
        setUpCBMonHoc();
        setUpCBLop(comboLop.getSelectedItem().toString());
    }

    private void setAllData() {
        // set data cho cac thuoc tinh
        setHocKy();
        setListLopHoc();
        setListBuoiHoc();
        setNgayTimKiem();
        setListBuoiHocTrinhChieu();
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
        if (listLopHoc.size() != 0) {
            this.listLopHoc.getListLopHoc().addAll(listLopHoc);
        }
    }

    private void setListBuoiHoc() {
        // moi lopHoc se lay data cac buoi hoc tuong ung da diem danh
        this.listTongBuoiHoc = new ListBuoiHoc();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<BuoiHoc> listBuoiHoc = KetNoiSQL_GV.getListBuoiHoc(lopHoc.getMaLopHoc());
            if (listBuoiHoc.size() != 0) {
                this.listTongBuoiHoc.getListBuoiHoc().addAll(listBuoiHoc);
            }
        }
        this.listTongBuoiHoc.sortDescendingListBuoiHoc();
    }

    private void setNgayTimKiem() {
        // set ngay tim kiem theo ngayBDHocKy + ngayKTHocKy
        //BuoiHoc buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(0);
        Date ngayBDHocKy = hocKy.getDsTuan().get(0).getNgayBDTuan();
        int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
        Date ngayKTHocKy = hocKy.getDsTuan().get(lastIndexDSTuan).getNgayKTTuan();
        dateChooserFrom.setDate(ngayBDHocKy);
        dateChooserTo.setDate(ngayKTHocKy);

    }

    private void setListBuoiHocTrinhChieu() {
        Date ngayBD = dateChooserFrom.getDate();
        Date ngayKT = dateChooserTo.getDate();
        listBuoiHocTrinhChieu = new ListBuoiHoc();
        for (BuoiHoc buoiHoc : listTongBuoiHoc.getListBuoiHoc()) {
            if (buoiHoc.getNgayHoc().compareTo(ngayBD) >= 0
                    && buoiHoc.getNgayHoc().compareTo(ngayKT) <= 0) {
                listBuoiHocTrinhChieu.getListBuoiHoc().add(buoiHoc);
            }
        }
    }

    private void setTableProperties() {
        tblDSDiemDanh.setDefaultRenderer(Object.class, new ObjectRenderer());
        tblDSDiemDanh.setAutoCreateRowSorter(true);
        tblDSDiemDanh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDSDiemDanh.getTableHeader().setReorderingAllowed(false);
        if (tblDSDiemDanh.getColumnModel().getColumnCount() > 0) {
            tblDSDiemDanh.getColumnModel().getColumn(0).setMinWidth(75);
            tblDSDiemDanh.getColumnModel().getColumn(0).setMaxWidth(75);
            tblDSDiemDanh.getColumnModel().getColumn(1).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(2).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(3).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(4).setMinWidth(125);
            tblDSDiemDanh.getColumnModel().getColumn(4).setMaxWidth(125);
            tblDSDiemDanh.getColumnModel().getColumn(5).setMinWidth(200);
            tblDSDiemDanh.getColumnModel().getColumn(5).setMaxWidth(200);
            tblDSDiemDanh.getColumnModel().getColumn(6).setMinWidth(125);
            tblDSDiemDanh.getColumnModel().getColumn(6).setMaxWidth(125);
        }
    }

    private void setUpCBMonHoc() {
        String item = "None"; // khong su dung chuc nang tim kiem trong comboBox
        comboMonHoc.addItem(item);
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            item = lopHoc.getTenMonHoc();
            if (!checkCBTenMonHocExist(item)) {
                comboMonHoc.addItem(item);
            }
        }
    }

    private boolean checkCBTenMonHocExist(String tenMonHon) {
        // kiem tra ten mon hoc da ton tai trong combobox ten mon hoc chua?
        for (int i = 0; i < comboMonHoc.getItemCount(); i++) {
            String compareStr = comboMonHoc.getItemAt(i);
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

    private void filter() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
        String tenMonHoc = comboMonHoc.getSelectedItem().toString().trim();
        String lop = comboLop.getSelectedItem().toString().trim();

        if (!tenMonHoc.equals("None")) {
            filters.add(RowFilter.regexFilter(tenMonHoc, 2));
        }
        if (!lop.equals("None")) {
            filters.add(RowFilter.regexFilter(lop, 3));
        }
        RowFilter rf = RowFilter.andFilter(filters);
        TableRowSorter sorter = new TableRowSorter(tblModel);
        sorter.setRowFilter(rf);
        tblDSDiemDanh.setRowSorter(sorter);
    }

    private ListBuoiHoc getListBuoiHocOnTable(String maLopHoc, String tenNhom, String ngayHocStr, String caStr) {
        Date ngayHoc = null;
        try {
            ngayHoc = new SimpleDateFormat("EEEE, dd/MM/yyyy").parse(ngayHocStr);
        } catch (ParseException ex) {
            Logger.getLogger(RollUpListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        int ca = (caStr.equalsIgnoreCase("Sáng") ? 0 : 1);
        ListBuoiHoc listResult = new ListBuoiHoc();
        for (BuoiHoc buoiHoc : listTongBuoiHoc.getListBuoiHoc()) {
            if (buoiHoc.getMaLopHoc().equals(maLopHoc) && buoiHoc.getTenNhom().equals(tenNhom)
                    && buoiHoc.getNgayHoc().equals(ngayHoc) && buoiHoc.getCa() == ca) {
                listResult.getListBuoiHoc().add(buoiHoc);
            }
        }
        return listResult;
    }

//<editor-fold defaultstate="collapsed">
//    private void setBuoiHocOnTable(BuoiHoc buoiHoc, int indexRow) {
//        int stt = indexRow;
//        String maLopHoc = buoiHoc.getMaLopHoc();
//        // ham getLopHoc(maLopHoc) se o trong class fullData
//        String tenMonHoc = listLopHoc.getLopHoc(maLopHoc).getTenMonHoc();
//        String lop = listLopHoc.getLopHoc(maLopHoc).getLop();
//        String nhom = buoiHoc.getTenNhom();
//        String ngayHoc = new SimpleDateFormat("dd/MM/yyyy").format(buoiHoc.getNgayHoc());        
//        String ca = null;
//        if (buoiHoc.getCa() == 0) {
//            ca = "SÁNG";
//        } else {
//            ca = "CHIỀU";
//        }
//        Vector vt = new Vector();
//        vt.add(stt);
//        vt.add(maLopHoc);
//        vt.add(tenMonHoc);
//        vt.add(lop);
//        vt.add(nhom);
//        vt.add(ngayHoc);
//        vt.add(ca);
//        tblModel.addRow(vt);
//    }
//
//    private void showTable() {
//        // show table tuong ung voi ten mon hoc + lop + ngay thang hop le
//        // reset table
//        tblModel.setRowCount(0);
//        int stt = 1;       
//        for (BuoiHoc buoiHoc : listTongBuoiHoc.getListBuoiHocOnTable()) {
//            // chi cho phep nhung ngay hop le trong tim kiem
//            System.out.println(buoiHoc.getNgayHoc());
//             System.out.println(dateChooserFrom.getDate());
//             
//            if (buoiHoc.getNgayHoc().compareTo(dateChooserFrom.getDate()) >= 0
//                    && buoiHoc.getNgayHoc().compareTo(dateChooserTo.getDate()) <= 0) {
//                setBuoiHocOnTable(buoiHoc, stt++);
//            }
//        }
//    }
//</editor-fold>
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDSDiemDanh = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboMonHoc = new javax.swing.JComboBox<>();
        comboLop = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        dateChooserFrom = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dateChooserTo = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        btnXem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 204));

        tblDSDiemDanh.setAutoCreateRowSorter(true);
        tblDSDiemDanh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblDSDiemDanh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblDSDiemDanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"11111", null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Lớp MH", "Tên MH", "Lớp", "Nhóm", "Ngày", "Ca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSDiemDanh.setRowHeight(20);
        tblDSDiemDanh.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDSDiemDanh);
        if (tblDSDiemDanh.getColumnModel().getColumnCount() > 0) {
            tblDSDiemDanh.getColumnModel().getColumn(0).setMinWidth(100);
            tblDSDiemDanh.getColumnModel().getColumn(0).setMaxWidth(100);
            tblDSDiemDanh.getColumnModel().getColumn(2).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(3).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(4).setResizable(false);
            tblDSDiemDanh.getColumnModel().getColumn(5).setMinWidth(150);
            tblDSDiemDanh.getColumnModel().getColumn(5).setMaxWidth(150);
            tblDSDiemDanh.getColumnModel().getColumn(6).setMinWidth(100);
            tblDSDiemDanh.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tên Môn Học");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Lớp");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Từ Ngày");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);

        comboMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMonHocActionPerformed(evt);
            }
        });

        comboLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLopActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dateChooserFrom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Đến");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);

        dateChooserTo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-32.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnXem.setBackground(new java.awt.Color(255, 255, 255));
        btnXem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-24.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboMonHoc, 0, 199, Short.MAX_VALUE)
                                    .addComponent(comboLop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateChooserFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboLop, comboMonHoc});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comboMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(dateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateChooserTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMonHocActionPerformed
        String tenMonHoc = comboMonHoc.getSelectedItem().toString();
        setUpCBLop(tenMonHoc);
        if (comboMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0) {
            filter();
        }
    }//GEN-LAST:event_comboMonHocActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0) {
            filter();
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        setListBuoiHocTrinhChieu();
        if (listBuoiHocTrinhChieu.getListBuoiHoc().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không Có Buổi Điểm Danh Nào Trong Khung Thời Gian Này");
            setNgayTimKiem();
            setListBuoiHocTrinhChieu();
        }
        tblModel = new RollUpListTableModel( listLopHoc, listBuoiHocTrinhChieu);
        tblDSDiemDanh.setModel(tblModel);
        setTableProperties();
        filter();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        int row = tblDSDiemDanh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phai chon vao 1 muc");
        } else {
            // ban chat cua filter do la sau khi filtering jtable, tblModel van ko thay doi. 
            // Chi thay doi nhung gi minh thay ma thoi. Vay nen phai chay method convertRowIndexToModel
            int modelRow = tblDSDiemDanh.convertRowIndexToModel(row);
            String maLopHoc = (String) tblModel.getValueAt(modelRow, 1);
            String tenNhom = (String) tblModel.getValueAt(modelRow, 4);
            String ngayHocStr = (String) tblModel.getValueAt(modelRow, 5);
            String caStr = (String) tblModel.getValueAt(modelRow, 6);
            ListBuoiHoc list = getListBuoiHocOnTable(maLopHoc, tenNhom, ngayHocStr, caStr);
            if (!list.getListBuoiHoc().isEmpty()) {
                LopHoc lopHoc = listLopHoc.getLopHoc(maLopHoc);
                LookUpRollUpStudentDlg dlg = new LookUpRollUpStudentDlg(home, true,lopHoc, list, 1);
                dlg.getPanelTimKiemThem().setVisible(false);
                dlg.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnXemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXem;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboMonHoc;
    private com.toedter.calendar.JDateChooser dateChooserFrom;
    private com.toedter.calendar.JDateChooser dateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblDSDiemDanh;
    // End of variables declaration//GEN-END:variables
}
