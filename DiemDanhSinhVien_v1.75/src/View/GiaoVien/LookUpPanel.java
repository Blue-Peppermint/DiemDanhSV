/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.GiaoVien.BuoiHoc;
import Model.HocKy;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.ListBuoiHoc;
import Model.ListHocKy;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.LopHoc;
import UnusedModel.ObjectRenderer;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
public class LookUpPanel extends javax.swing.JPanel {

    //private LookUpRollUpStudentDlg rollUpStudentDlg;
    //private RollUpStudenDeletetDlg rollUpStudenDeletetDlg;
    private Frame home;
    private ListHocKy listHocKy;
    // listLopHoc se thay doi tuy thuoc vao comBoHocKy, va listBuoiHoc thay doi dua vao listLopHoc
    private ListLopHoc listLopHoc; // lay thong tin cac lopHoc hoc ma GV day + vai tro GV trong lopHoc
    private static ListBuoiHoc listBuoiHoc; // chua maso lop hoc + thong tin diem danh   
    private DefaultTableModel tblModel;
    private static boolean listBuoiHocUpdated;
    /**
     * Creates new form NhomLopMH
     */
    public LookUpPanel(java.awt.Frame parent) {
        initComponents();
        home = parent;
        firstSetUp();
    }

    public ListBuoiHoc getListBuoiHoc() {
        return listBuoiHoc;
    }

    public void setListBuoiHoc(ListBuoiHoc listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
    }

    public static boolean isListBuoiHocUpdated() {
        return listBuoiHocUpdated;
    }

    public static void setListBuoiHocUpdated(boolean listBuoiHocUpdated) {
        LookUpPanel.listBuoiHocUpdated = listBuoiHocUpdated;
    }
    
    private void firstSetUp(){
        setAllData();
        tblModel = (DefaultTableModel) tbl.getModel();        
        setTableProperties();
        setUpCBHocKy(listHocKy);
        setUpCBMonHoc(listLopHoc);
        setUpCBLop(comboLop.getSelectedItem().toString());
        showTable();
        comboHocKy.setSelectedIndex(comboHocKy.getItemCount()-1);
    }
    
    public void updateListBuoiHocAndShowTable(ListBuoiHoc listBuoiHoc) {
        this.listBuoiHoc = listBuoiHoc;
        showTable();
        filter();
    }
    
    private void setAllData() {
        setListHocKy();
        setListLopHoc(null);
        setListBuoiHoc(listLopHoc);
    }

    private void setListHocKy() {
        listHocKy = new ListHocKy();
        listHocKy.getDataToanBo_GV();
    }

    private void setListLopHoc(HocKy hocKy) {
        // moi hoc ky GV co day hoc se lay data cac lop hoc tuong ung
        this.listLopHoc = new ListLopHoc();
        // neu CBHocKy chon None thi getToanBoDataListLopHoc
        if(hocKy == null){
            ArrayList<LopHoc> list = KetNoiSQL_GV.getListLopHoc();
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        }  
         // neu CBHocKy chon khac None thi chi get DataListLopHoc cua hocKy tuong ung
        else {
            ArrayList<LopHoc> list = KetNoiSQL_GV.getListLopHoc(hocKy.getSttHocKy(), hocKy.getNamHoc());
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        }
    }

    private void setListBuoiHoc(ListLopHoc listLopHoc) {
        // moi lopHoc se lay data cac buoi hoc tuong ung da diem danh
        this.listBuoiHoc = new ListBuoiHoc();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            ArrayList<BuoiHoc> listBuoiHoc = KetNoiSQL_GV.getListBuoiHoc(lopHoc.getMaLopHoc());
            if (listBuoiHoc.size() != 0) {
                this.listBuoiHoc.getListBuoiHoc().addAll(listBuoiHoc);
            }
        }
    }

    private void setTableProperties() {
        tbl.setDefaultRenderer(Object.class, new ObjectRenderer());
        tbl.setAutoCreateRowSorter(true);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.getTableHeader().setReorderingAllowed(false);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(75);
            tbl.getColumnModel().getColumn(0).setMaxWidth(75);
            tbl.getColumnModel().getColumn(1).setResizable(false);
            tbl.getColumnModel().getColumn(2).setResizable(false);
            tbl.getColumnModel().getColumn(3).setResizable(false);
            tbl.getColumnModel().getColumn(4).setResizable(false);
            tbl.getColumnModel().getColumn(5).setResizable(false);
            tbl.getColumnModel().getColumn(6).setResizable(false);
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

    private void setUpCBMonHoc(ListLopHoc listLopHoc) {
        comboTenMonHoc.removeAllItems();
        String item = "None"; // khong su dung chuc nang tim kiem trong comboBox
        comboTenMonHoc.addItem(item);
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            item = lopHoc.getTenMonHoc();
            if (!checkCBTenMonHocExist(item)) {
                comboTenMonHoc.addItem(item);
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

    private void filter() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
        String tenMonHoc = comboTenMonHoc.getSelectedItem().toString().trim();
        String lop = comboLop.getSelectedItem().toString().trim();

        if (!tenMonHoc.equals("None")) {
            filters.add(RowFilter.regexFilter(tenMonHoc, 2));
        }
        if (!lop.equals("None")) {
            filters.add(RowFilter.regexFilter(lop, 3));
        }
        int selectedIndexCBHocKy = comboHocKy.getSelectedIndex();
        if (selectedIndexCBHocKy != 0) {
            HocKy hocKy = listHocKy.getListHocKy().get(selectedIndexCBHocKy - 1);
            String namHocStr = "";
            if (hocKy.getSttHocKy() == 1) {
                String namBD = String.valueOf(hocKy.getNamHoc());
                String namKT = String.valueOf(hocKy.getNamHoc() + 1);
                namHocStr += namBD + " - " + namKT;
            } else {
                String namBD = String.valueOf(hocKy.getNamHoc() - 1);
                String namKT = String.valueOf(hocKy.getNamHoc());
                namHocStr += namBD + " - " + namKT;
            }
            filters.add(RowFilter.regexFilter(namHocStr, 5));
            String hocKyStr = String.valueOf(hocKy.getSttHocKy());
            filters.add(RowFilter.regexFilter(hocKyStr, 4));
        }
        RowFilter rf = RowFilter.andFilter(filters);
        TableRowSorter sorter = new TableRowSorter(tblModel);
        sorter.setRowFilter(rf);
        tbl.setRowSorter(sorter);
    }

    private boolean checkBuoiHocExistOnTable(BuoiHoc buoiHoc) {
        // kiem tra buoi hoc da duoc show tren table chua
        // bang cach kiem tra column MaLopMH + Nhom da xuat hien chua
        for (int row = 0; row < tblModel.getRowCount(); row++) {
            String maLopMH = (String) tblModel.getValueAt(row, 1);
            String tenNhom = (String) tblModel.getValueAt(row, 6);
            if (buoiHoc.getMaLopHoc().equals(maLopMH) && buoiHoc.getTenNhom().equals(tenNhom)) {
                return true;
            }
        }
        return false;
    }

    private void showTable() {
        // duyet tung buoiHoc va show data maLopHoc + tenNhom khac nhau
        // thu tu value xuat ra se phu thuoc vao ngay diem danh moi nhat (do listBuoiHoc.sortDescendingListBuoiHoc();)
        tblModel.setRowCount(0);
        int dem = 0;
        listBuoiHoc.sortDescendingListBuoiHoc();
        for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
            if (!checkBuoiHocExistOnTable(buoiHoc)) {
                String maLopHoc = buoiHoc.getMaLopHoc();
                LopHoc lopHoc = listLopHoc.getLopHoc(maLopHoc);
                Vector vt = new Vector();
                vt.add(++dem);
                vt.add(maLopHoc);
                vt.add(lopHoc.getTenMonHoc());
                vt.add(lopHoc.getLop());
                vt.add(lopHoc.getHocKy());
                String namHoc = "";
                if (lopHoc.getHocKy() == 1) {
                    String namBD = String.valueOf(lopHoc.getNamHoc());
                    String namKT = String.valueOf(lopHoc.getNamHoc() + 1);
                    namHoc += namBD + " - " + namKT;
                } else {
                    String namBD = String.valueOf(lopHoc.getNamHoc() - 1);
                    String namKT = String.valueOf(lopHoc.getNamHoc());
                    namHoc += namBD + " - " + namKT;
                }
                vt.add(namHoc);
                vt.add(buoiHoc.getTenNhom());
                tblModel.addRow(vt);
            }
        }
    }

//    private ListBuoiHoc getListBuoiHoc(String maLopHoc, String tenNhom) {
//        ListBuoiHoc listResult = new ListBuoiHoc();
//        for (BuoiHoc buoiHoc : listBuoiHoc.getListBuoiHoc()) {
//            if (buoiHoc.getMaLopHoc().equals(maLopHoc) && buoiHoc.getTenNhom().equals(tenNhom)) {
//                listResult.getListBuoiHoc().add(buoiHoc);
//            }
//        }
//        return listResult;
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTenMonHoc = new javax.swing.JComboBox<>();
        comboLop = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnXem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Lớp MH", "Tên MH", "Lớp", "Học Kỳ", "Năm Học", "Nhóm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(75);
            tbl.getColumnModel().getColumn(0).setMaxWidth(75);
            tbl.getColumnModel().getColumn(1).setResizable(false);
            tbl.getColumnModel().getColumn(2).setResizable(false);
            tbl.getColumnModel().getColumn(3).setResizable(false);
            tbl.getColumnModel().getColumn(4).setResizable(false);
            tbl.getColumnModel().getColumn(5).setResizable(false);
            tbl.getColumnModel().getColumn(6).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 73, 1310, 222));

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRA CỨU ĐIỂM DANH");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 54));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên môn học");
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

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Học Kỳ");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);

        comboHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHocKyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(409, 444, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(343, 343, 343))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboLop, comboTenMonHoc});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 1340, -1));

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));

        btnXem.setBackground(new java.awt.Color(255, 255, 255));
        btnXem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-24.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 255, 255));
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-edit-delete-icon-16.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 255, 255));
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-document-edit-icon-32.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(btnXem)
                .addGap(42, 42, 42)
                .addComponent(btnXoa)
                .addGap(36, 36, 36)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSua, btnXem, btnXoa});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 360, -1));

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1310, 10));
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Mục");
        } else {
            int modelRow = tbl.convertRowIndexToModel(row);
            String maLopHoc = (String) tblModel.getValueAt(modelRow, 1);
            String tenNhom = (String) tblModel.getValueAt(modelRow, 6);
            //ListBuoiHoc list = getListBuoiHoc(maLopHoc, tenNhom);
            ListBuoiHoc list = new ListBuoiHoc(listBuoiHoc.getListBuoiHoc(maLopHoc, tenNhom));
            if (!list.getListBuoiHoc().isEmpty()) {
                // luc truyen vao la lay du lieu goc 
                // nen khi nhan dong y de luu ben LookUpRollUpStudentDlg thi listBuoiHoc goc cung se bi thay doi
                LopHoc lopHoc = listLopHoc.getLopHoc(maLopHoc);
                // kiem tra lop hoc nay co dang nam trong hoc ky hien tai
                int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
                HocKy hocKyHienTai = listHocKy.getListHocKy().get(lastIndexListHocKy);
                if (lopHoc.getHocKy() != hocKyHienTai.getSttHocKy()
                        || lopHoc.getNamHoc() != hocKyHienTai.getNamHoc()) {
                    JOptionPane.showMessageDialog(this, "Không Cho Sửa Thông Tin Các Lớp Học Không Nằm Trong Học Kỳ Hiện Tại",
                            "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LookUpRollUpStudentDlg dlg = new LookUpRollUpStudentDlg(home, true, lopHoc, list, 2);
                    dlg.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Mục");
        } else {
            int modelRow = tbl.convertRowIndexToModel(row);
            String maLopHoc = (String) tblModel.getValueAt(modelRow, 1);
            String tenNhom = (String) tblModel.getValueAt(modelRow, 6);
            ListBuoiHoc list = new ListBuoiHoc(listBuoiHoc.getListBuoiHoc(maLopHoc, tenNhom));
            if (!list.getListBuoiHoc().isEmpty()) {
                // luc truyen vao la lay du lieu goc 
                // nen khi nhan dong y de luu ben LookUpRollUpStudentDlg thi listBuoiHoc goc cung se bi thay doi
                LopHoc lopHoc = listLopHoc.getLopHoc(maLopHoc);
                // kiem tra lop hoc nay co dang nam trong hoc ky hien tai
                int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
                HocKy hocKyHienTai = listHocKy.getListHocKy().get(lastIndexListHocKy);
                if (lopHoc.getHocKy() != hocKyHienTai.getSttHocKy()
                        || lopHoc.getNamHoc() != hocKyHienTai.getNamHoc()) {
                    JOptionPane.showMessageDialog(this, "Không Cho Xóa Thông Tin Các Lớp Học Không Nằm Trong Học Kỳ Hiện Tại",
                            "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LookUpRollUpStudenDeletetDlg dlg = new LookUpRollUpStudenDeletetDlg(home, true, this, lopHoc, list);
                    dlg.setVisible(true);
                    showTable();
                }
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Mục");
        } else {
            // ban chat cua filter do la sau khi filtering jtable, tblModel van ko thay doi.
            // Chi thay doi nhung gi minh thay ma thoi. Vay nen phai chay method convertRowIndexToModel
            int modelRow = tbl.convertRowIndexToModel(row);
            String maLopHoc = (String) tblModel.getValueAt(modelRow, 1);
            String tenNhom = (String) tblModel.getValueAt(modelRow, 6);
            //ListBuoiHoc list = getListBuoiHoc(maLopHoc, tenNhom);
            ListBuoiHoc list = new ListBuoiHoc(listBuoiHoc.getListBuoiHoc(maLopHoc, tenNhom));
            if (!list.getListBuoiHoc().isEmpty()) {
                LopHoc lopHoc = listLopHoc.getLopHoc(maLopHoc);
                new LookUpRollUpStudentDlg(home, true,lopHoc, list, 1).setVisible(true);
            }
        }
    }//GEN-LAST:event_btnXemActionPerformed

    private void comboHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHocKyActionPerformed
        HocKy hocKy = null;
        if(comboHocKy.getSelectedIndex() != 0){
            hocKy = listHocKy.getListHocKy().get(comboHocKy.getSelectedIndex()-1);
        }
        setListLopHoc(hocKy);
        setListBuoiHoc(listLopHoc);
        setUpCBMonHoc(listLopHoc);
        String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
        setUpCBLop(tenMonHoc);
        showTable();
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboHocKy.getItemCount() != 0) {
            filter();
        }
    }//GEN-LAST:event_comboHocKyActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboHocKy.getItemCount() != 0) {
            filter();
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void comboTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTenMonHocActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboHocKy.getItemCount() != 0) {
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            setUpCBLop(tenMonHoc);
            filter();
        }
    }//GEN-LAST:event_comboTenMonHocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboTenMonHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tbl;
    // End of variables declaration//GEN-END:variables
}
