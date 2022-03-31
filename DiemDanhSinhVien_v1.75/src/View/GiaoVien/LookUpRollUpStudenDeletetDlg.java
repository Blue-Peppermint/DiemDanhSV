/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.BasicAttribute_Method;
import Model.GiaoVien.BuoiHoc;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListInfoStudent;
import Model.GiaoVien.LopHoc;
import Model.InfoStudent;
import TableModel.GiaoVien.RollUpStudentTableModel;
import TableModel.GiaoVien.TblRendererLookUp;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
public class LookUpRollUpStudenDeletetDlg extends javax.swing.JDialog {

    private LopHoc lopHoc;
    private ListBuoiHoc listTongBuoiHoc;
    private ListBuoiHoc listBuoiHocTrinhChieu;
    private ListInfoStudent listInfoStudents;
    private RollUpStudentTableModel tblModel;
    private TblRendererLookUp objectLookUpCellRender;
    private LookUpPanel lookUpPanel;
    

    public LookUpRollUpStudenDeletetDlg(java.awt.Frame parent, boolean modal, LookUpPanel lookUpPanel,LopHoc lopHoc, ListBuoiHoc listTongBuoiHoc) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle("Xóa Buổi Điểm Danh");
        this.lopHoc = lopHoc;
        this.listTongBuoiHoc = listTongBuoiHoc;
        this.listTongBuoiHoc.sortAscendingListBuoiHoc();
        this.lookUpPanel = lookUpPanel;
        firstSetUp();
    }

    private void firstSetUp() {
        setAllData();
        showInfoClass();
        setTableProperties();
        tblModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                tblStudents.repaint();
            }
        });
    }

    private void setAllData() {
        setListStudents();
        setNgayTimKiem();
        // ham duoi day lay data null
        setListBuoiHocTrinhChieu();
        setTblModel(listBuoiHocTrinhChieu);
        setTblRenderer(tblModel);
    }

    private void setListStudents() {
        listInfoStudents = new ListInfoStudent();
        BuoiHoc buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(0);
        ArrayList<InfoStudent> list = KetNoiSQL_GV.getListInfoStudent(buoiHoc.getMaLopHoc());
        if (!list.isEmpty()) {
            listInfoStudents.getListInfoStudent().addAll(list);
        }

    }

    private void setNgayTimKiem() {
        this.listTongBuoiHoc.sortAscendingListBuoiHoc();
        if (!listTongBuoiHoc.getListBuoiHoc().isEmpty()) {
            BuoiHoc buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(0);
            Date ngayDiemDanhDauTien = buoiHoc.getNgayHoc();
            int lastIndexListBuoiHoc = listTongBuoiHoc.getListBuoiHoc().size() - 1;
            buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(lastIndexListBuoiHoc);
            Date ngayDiemDanhGanNhat = buoiHoc.getNgayHoc();
            dateChooserFrom.setDate(ngayDiemDanhDauTien);
            dateChooserTo.setDate(ngayDiemDanhGanNhat);
        }
    }

    private void setListBuoiHocTrinhChieu() {
        Date ngayBD = dateChooserFrom.getDate();
        Date ngayKT = dateChooserTo.getDate();
        listBuoiHocTrinhChieu = new ListBuoiHoc(listTongBuoiHoc.getListBuoiHoc(ngayBD, ngayKT));
    }

    private void showInfoClass(){
        lblTenMonHoc.setText(lopHoc.getTenMonHoc());
        lblTenLop.setText(lopHoc.getLop());
        lblTenNhom.setText(listTongBuoiHoc.getListBuoiHoc().get(0).getTenNhom());
    }
    
    private void setTableProperties() {
        // tuy chinh cac propeties linh tinh cua table
        tblStudents.setRowHeight(22);
        tblStudents.setAutoCreateRowSorter(true);
        tblStudents.setColumnSelectionAllowed(true);
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStudents.setRowSelectionAllowed(false);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        // tuy chinh chieu rong cua cac cot
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            //STT
            tblStudents.getColumnModel().getColumn(0).setMinWidth(50);
            tblStudents.getColumnModel().getColumn(0).setMaxWidth(50);
            // Ho
            tblStudents.getColumnModel().getColumn(1).setResizable(true);
            // Ten
            tblStudents.getColumnModel().getColumn(2).setMinWidth(75);
            tblStudents.getColumnModel().getColumn(2).setMaxWidth(75);
            // Gioi Tinh
            tblStudents.getColumnModel().getColumn(3).setMinWidth(75);
            tblStudents.getColumnModel().getColumn(3).setMaxWidth(75);
            // MSSV
            tblStudents.getColumnModel().getColumn(4).setResizable(false);
            // Lop
            tblStudents.getColumnModel().getColumn(5).setResizable(false);
            // ke tu cot diem danh thu 6
            for (int i = 6; i < tblStudents.getColumnModel().getColumnCount(); i++) {
                tblStudents.getColumnModel().getColumn(i).setResizable(false);
            }
        }
        tblStudents.repaint();
    }

    private void setTblModel(ListBuoiHoc listBuoiHocTrinhChieu) {
        // update data cho tblModel dua vao listBuoiHocTrinhChieu
        tblModel = new RollUpStudentTableModel(listBuoiHocTrinhChieu, listInfoStudents, false);
        tblStudents.setModel(tblModel);
    }

    private void setTblRenderer(RollUpStudentTableModel tblModel) {
        // update data cho tblRenderer dua vao listBuoiHoc trong tblModel
        ListBuoiHoc listBuoiHocInTblModel = new ListBuoiHoc(tblModel.getListBuoiHoc());
        objectLookUpCellRender = new TblRendererLookUp(listBuoiHocInTblModel);
        for (int i = 0; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            tblStudents.getColumnModel().getColumn(i).setCellRenderer(objectLookUpCellRender);
        }
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

    private boolean checkValidRollUpDateWillDelete(Date ngayDiemDanh) {
        // kiem tra hop le ngay diem danh se xoa
        // ko cho xoa neu ngay diem danh < ngay Hien tai
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayHienTai = null;
        try {
            ngayHienTai = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(LookUpRollUpStudenDeletetDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ngayDiemDanh.compareTo(ngayHienTai) < 0) {
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        btnDongY = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        txtMSSV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dateChooserFrom = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateChooserTo = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblTenMonHoc = new javax.swing.JLabel();
        lblTenLop = new javax.swing.JLabel();
        lblTenNhom = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 126, 1151, 10));

        btnDongY.setBackground(new java.awt.Color(255, 255, 255));
        btnDongY.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/ok.png"))); // NOI18N
        btnDongY.setText("Đồng Ý");
        btnDongY.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });
        getContentPane().add(btnDongY, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 551, 90, 40));

        btnHuy.setBackground(new java.awt.Color(255, 255, 255));
        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-Close-icon-16.png"))); // NOI18N
        btnHuy.setText("Hủy Bỏ");
        btnHuy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        getContentPane().add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 551, 90, 40));

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Xóa Buổi Điểm Danh");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, -1));

        btnXoa.setBackground(new java.awt.Color(255, 255, 255));
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-edit-delete-icon-16.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        getContentPane().add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, 100, 30));

        txtMSSV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMSSVKeyReleased(evt);
            }
        });
        getContentPane().add(txtMSSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, 133, -1));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lớp");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 50, 20));

        txtLop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLopKeyReleased(evt);
            }
        });
        getContentPane().add(txtLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 430, 133, -1));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 50, 20));

        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });
        getContentPane().add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 134, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MSSV");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, 50, 20));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Từ Ngày");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, -1, 20));

        dateChooserFrom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(dateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 160, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Đến");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, -1, 20));

        dateChooserTo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(dateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, 150, 30));

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-24.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        getContentPane().add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 410, 100, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tên Nhóm:");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 95, 72, -1));

        lblTenMonHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenMonHoc.setText("jLabel8");
        lblTenMonHoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 53, 240, -1));

        lblTenLop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenLop.setText("jLabel9");
        lblTenLop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 75, 240, -1));

        lblTenNhom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNhom.setText("jLabel10");
        lblTenNhom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 95, 240, -1));

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tên Môn Học:");
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 52, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tên Lớp:");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 75, 72, -1));

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Họ", "Tên", "Giới Tính", "MSSV", "Lớp", "Ngày diem danh/ Ca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.setColumnSelectionAllowed(true);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudents);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 1140, 170));

        jLabel10.setBackground(new java.awt.Color(255, 204, 204));
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1160, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMSSVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMSSVKeyReleased
        filter();
    }//GEN-LAST:event_txtMSSVKeyReleased

    private void txtLopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLopKeyReleased
        filter();
    }//GEN-LAST:event_txtLopKeyReleased

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        filter();
    }//GEN-LAST:event_txtTenKeyReleased

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        setListBuoiHocTrinhChieu();
        if (listBuoiHocTrinhChieu.getListBuoiHoc().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không Có Buổi Điểm Danh Nào Trong Khung Thời Gian Này");
            setNgayTimKiem();
            setListBuoiHocTrinhChieu();
        }
        setTblModel(listBuoiHocTrinhChieu);
        txtTen.setText("");
        txtLop.setText("");
        txtMSSV.setText("");
        setTblRenderer(tblModel);
        setTableProperties();
        tblModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                tblStudents.repaint();
            }
        });
        filter();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int column = tblStudents.getSelectedColumn();
        int columnDiemDanh = 6;
        if (column == -1) {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Mục");
        } else if (column < columnDiemDanh) {
            JOptionPane.showMessageDialog(this, "Phải Chọn Vào 1 Buổi Điểm Danh");
        } else {
            ArrayList<BuoiHoc> listBuoiHocModel = tblModel.getListBuoiHoc();
            int columnChoosen = tblStudents.getSelectedColumn();
            int indexBuoiHoc = columnChoosen - columnDiemDanh;
            // delete BuoiHoc tren sql
            BuoiHoc buoiHocModelWillDelete = listBuoiHocModel.get(indexBuoiHoc);
            if (!checkValidRollUpDateWillDelete(buoiHocModelWillDelete.getNgayHoc())) {
                JOptionPane.showMessageDialog(this, "Không Cho Xóa Buổi Điểm Danh Đã Qua");
            } else {
                int result = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Xóa Không?",
                        "Cảnh Báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    boolean isDeleted = KetNoiSQL_GV.deleteBuoiHoc(buoiHocModelWillDelete);
                    // sau khi delete tren SQL thi remove data listBuoiHoc goc va update giao dien
                    if (isDeleted) {
                        BuoiHoc buoiHocOriginalWillDelete = listTongBuoiHoc.getBuoiHoc(
                                buoiHocModelWillDelete.getMaLopHoc(), buoiHocModelWillDelete.getNgayHoc(),
                                buoiHocModelWillDelete.getCa());
                        ListBuoiHoc listBuoiHocOriginal = lookUpPanel.getListBuoiHoc();
                        listBuoiHocOriginal.getListBuoiHoc().remove(buoiHocOriginalWillDelete);
                        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
                        LookUpPanel.setListBuoiHocUpdated(true);
                        // cap nhat lai data dang hien thi tren giao dien
                        listTongBuoiHoc.getListBuoiHoc().remove(buoiHocOriginalWillDelete);
                        setListBuoiHocTrinhChieu();
                        setTblModel(listBuoiHocTrinhChieu);
                        setTblRenderer(tblModel);
                        setTableProperties();
                        filter();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa Thất Bại");
                    }
                }
            }
            tblStudents.clearSelection();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
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
//            java.util.logging.Logger.getLogger(LookUpRollUpStudenDeletetDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudenDeletetDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudenDeletetDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudenDeletetDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                LookUpRollUpStudenDeletetDlg dialog = new LookUpRollUpStudenDeletetDlg(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private com.toedter.calendar.JDateChooser dateChooserFrom;
    private com.toedter.calendar.JDateChooser dateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTenLop;
    private javax.swing.JLabel lblTenMonHoc;
    private javax.swing.JLabel lblTenNhom;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMSSV;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
