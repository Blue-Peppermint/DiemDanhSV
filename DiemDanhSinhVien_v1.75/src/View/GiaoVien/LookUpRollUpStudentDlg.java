/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.BasicAttribute_Method;
import Model.GiaoVien.BuoiHoc;
import TableModel.GiaoVien.ComboBoxCellEditor_RollUp;
import Model.InfoStudent;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListInfoStudent;
import Model.GiaoVien.LopHoc;
import TableModel.GiaoVien.TblRendererLookUp;
import TableModel.GiaoVien.RollUpStudentTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;
import Model.MyEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author chuon
 */
// class nay duoc su dung cho 2 muc dinh
// 1. xem diem danh sinh vien (ko editable)
// 2. chinh sua diem danh sinh vien (editable)
// tuong ung voi 2 mode:
// mode 1: uneditable
// mode 2: editable + update
public class LookUpRollUpStudentDlg extends javax.swing.JDialog {

    // co 2 listBuoiHoc: listTongBuoiHoc = chua tat ca cac buoi hoc cua LopHoc + TenNhom ma GV do DiemDanh (du lieu goc)
    // listBuoiHocTrinhChieu = chua buoi hoc se show cho user (Phat sinh do chuc nang tim kiem theo ngay)
    private LopHoc lopHoc; // chua thong tin co ban ve lop hoc GV dang tra cuu
    private ListBuoiHoc listTongBuoiHoc;
    private ListBuoiHoc listBuoiHocTrinhChieu;
    private ListInfoStudent listInfoStudents;
    private RollUpStudentTableModel tblModel;
    private int mode;
    // boolean chinhSua = true neu cho phep column diem danh editable
    // boolean chinhSua = false neu khong cho phep editable
    private boolean chinhSua;
    private TblRendererLookUp objectLookUpCellRender;
    private ComboBoxCellEditor_RollUp comboLookUpCellEditor;

    public LookUpRollUpStudentDlg(java.awt.Frame parent, boolean modal, LopHoc lopHoc, ListBuoiHoc listTongBuoiHoc, int mode) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Tra Cứu Buổi Điểm Danh");
        this.lopHoc = lopHoc;
        this.listTongBuoiHoc = listTongBuoiHoc;
        this.listTongBuoiHoc.sortAscendingListBuoiHoc();
        this.mode = mode;
        firstSetUp();
    }

    public JPanel getPanelTimKiemThem() {
        return panelTimKiemThem;
    }

    private void firstSetUp() {
        setAllData();
        showInfoClass();
        setTableProperties();
        tblModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                setSpinnersUpperBound(tblModel);
                tblStudents.repaint();
            }
        });
    }

    private void setAllData() {
        if (mode == 1) {
            this.chinhSua = false;
            btnLuuChinhSua.setVisible(false);
            btnKetXuat.setVisible(true);
        } else {
            this.chinhSua = true;
            btnLuuChinhSua.setVisible(true);
            btnKetXuat.setVisible(false);
        }
        setListStudents();
        setNgayTimKiem();
        // ham duoi day lay data null
        setListBuoiHocTrinhChieu();
        setTblModel(listBuoiHocTrinhChieu);
        setTblRenderer(tblModel);
        setTblEditor();
        setSpinnersUpperBound(tblModel);
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
        BuoiHoc buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(0);
        Date ngayDiemDanhDauTien = buoiHoc.getNgayHoc();
        int lastIndexListBuoiHoc = listTongBuoiHoc.getListBuoiHoc().size() - 1;
        buoiHoc = listTongBuoiHoc.getListBuoiHoc().get(lastIndexListBuoiHoc);
        Date ngayDiemDanhGanNhat = buoiHoc.getNgayHoc();
        dateChooserFrom.setDate(ngayDiemDanhDauTien);
        dateChooserTo.setDate(ngayDiemDanhGanNhat);
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
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // no cell Selection Mode
        //tblStudents.setCellSelectionEnabled(true);
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
        tblModel = new RollUpStudentTableModel(listBuoiHocTrinhChieu, listInfoStudents, chinhSua);
        tblStudents.setModel(tblModel);
    }

    private void setTblEditor() {
        comboLookUpCellEditor = new ComboBoxCellEditor_RollUp();
        for (int i = 6; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            tblStudents.getColumnModel().getColumn(i).setCellEditor(comboLookUpCellEditor);
        }
    }

    private void setTblRenderer(RollUpStudentTableModel tblModel) {
        // update data cho tblRenderer dua vao listBuoiHoc trong tblModel
        ListBuoiHoc listBuoiHocInTblModel = new ListBuoiHoc(tblModel.getListBuoiHoc());
        objectLookUpCellRender = new TblRendererLookUp(listBuoiHocInTblModel,
                (int) spinnerHoc.getValue(), (int) spinnerHocMuon.getValue(),
                (int) spinnerVangKoPhep.getValue(), (int) spinnerVangCoPhep.getValue());
        for (int i = 0; i < tblStudents.getColumnModel().getColumnCount(); i++) {
            tblStudents.getColumnModel().getColumn(i).setCellRenderer(objectLookUpCellRender);
        }
    }

    private void setSpinnersUpperBound(RollUpStudentTableModel tblModel) {
        // set maximum value cho cac spinnerS dua vao listBuoiHocTrinhChieu
        //  Maximum value của 1 spinner riêng biệt được xác định trong điều kiện tối ưu nhất
        int lowerBound = -1;
        int incrementBy = 1;
        int initialSpinnerValue = (int) spinnerHoc.getValue();
        ListBuoiHoc listBuoiHocTmp = new ListBuoiHoc(tblModel.getListBuoiHoc());
        int upperBound = listBuoiHocTmp.tinhSoLuongMaximum1SVHoc();
        if(initialSpinnerValue > upperBound){
            initialSpinnerValue = upperBound;
        }
        spinnerHoc.setModel(new SpinnerNumberModel(initialSpinnerValue,
                lowerBound, upperBound, incrementBy));
        // spinnerHocMuon
        initialSpinnerValue = (int) spinnerHocMuon.getValue();
        upperBound = listBuoiHocTmp.tinhSoLuongMaximum1SVHocMuon();
        if(initialSpinnerValue > upperBound){
            initialSpinnerValue = upperBound;
        }
        spinnerHocMuon.setModel(new SpinnerNumberModel(initialSpinnerValue,
                lowerBound, upperBound, incrementBy));
        // spinnerVangKoPhep
        initialSpinnerValue = (int) spinnerVangKoPhep.getValue();
        upperBound = listBuoiHocTmp.tinhSoLuongMaximum1SVVangKoPhep();
        if(initialSpinnerValue > upperBound){
            initialSpinnerValue = upperBound;
        }
        spinnerVangKoPhep.setModel(new SpinnerNumberModel(initialSpinnerValue,
                lowerBound, upperBound, incrementBy));
        // spinnerVangCoPhep
        initialSpinnerValue = (int) spinnerVangCoPhep.getValue();
        upperBound = listBuoiHocTmp.tinhSoLuongMaximum1SVVangCoPhep();
        if(initialSpinnerValue > upperBound){
            initialSpinnerValue = upperBound;
        }
        spinnerVangCoPhep.setModel(new SpinnerNumberModel(initialSpinnerValue,
                lowerBound, upperBound, incrementBy));
        objectLookUpCellRender.setSoBuoiHoc((int) spinnerHoc.getValue());
        objectLookUpCellRender.setSoBuoiHocMuon((int) spinnerHocMuon.getValue());
        objectLookUpCellRender.setSoBuoiVangKoPhep((int) spinnerVangKoPhep.getValue());
        objectLookUpCellRender.setSoBuoiVangCoPhep((int) spinnerVangCoPhep.getValue());
    }

    private void resetSpinnersValue() {
        // reset gia tri chon cua cac Spinner
        spinnerHoc.setValue(-1);
        spinnerHocMuon.setValue(-1);
        spinnerVangKoPhep.setValue(-1);
        spinnerVangCoPhep.setValue(-1);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnDongY = new javax.swing.JButton();
        BtnThoat = new javax.swing.JButton();
        panelTimKiemThem = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dateChooserFrom = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateChooserTo = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        spinnerVangKoPhep = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        spinnerHoc = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        spinnerHocMuon = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        spinnerVangCoPhep = new javax.swing.JSpinner();
        btnResetSpinners = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMSSV = new javax.swing.JTextField();
        txtLop = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btnLuuChinhSua = new javax.swing.JButton();
        btnKetXuat = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        lblTenMonHoc = new javax.swing.JLabel();
        lblTenLop = new javax.swing.JLabel();
        lblTenNhom = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH ĐIỂM DANH");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 817, 50));

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
        tblStudents.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            tblStudents.getColumnModel().getColumn(0).setMinWidth(75);
            tblStudents.getColumnModel().getColumn(0).setMaxWidth(75);
            tblStudents.getColumnModel().getColumn(1).setResizable(false);
            tblStudents.getColumnModel().getColumn(2).setResizable(false);
            tblStudents.getColumnModel().getColumn(3).setResizable(false);
            tblStudents.getColumnModel().getColumn(4).setResizable(false);
            tblStudents.getColumnModel().getColumn(5).setResizable(false);
            tblStudents.getColumnModel().getColumn(6).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 156, 797, 155));

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 797, 10));

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
        getContentPane().add(btnDongY, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 700, 90, 30));

        BtnThoat.setBackground(new java.awt.Color(255, 255, 255));
        BtnThoat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BtnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Actions-edit-delete-icon-16.png"))); // NOI18N
        BtnThoat.setText("Thoát");
        BtnThoat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThoatActionPerformed(evt);
            }
        });
        getContentPane().add(BtnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 700, 85, 30));

        panelTimKiemThem.setBackground(new java.awt.Color(255, 153, 153));
        panelTimKiemThem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTimKiemThem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Từ Ngày");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        panelTimKiemThem.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, 20));

        dateChooserFrom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelTimKiemThem.add(dateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 210, -1));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Đến");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);
        panelTimKiemThem.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 30, 20));

        dateChooserTo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelTimKiemThem.add(dateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 210, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(MyEnum.VANG_KO_PHEP.getIcon());
        jLabel7.setText("Số Buổi SV Vắng Không Phép");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTimKiemThem.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 150, 30));

        spinnerVangKoPhep.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        spinnerVangKoPhep.setModel(new javax.swing.SpinnerNumberModel(-1, -1, null, 1));
        spinnerVangKoPhep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        spinnerVangKoPhep.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerVangKoPhepStateChanged(evt);
            }
        });
        panelTimKiemThem.add(spinnerVangKoPhep, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 178, 70, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(MyEnum.CO_MAT.getIcon());
        jLabel8.setText("Số Buổi SV Học");
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTimKiemThem.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 150, 30));

        spinnerHoc.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        spinnerHoc.setModel(new javax.swing.SpinnerNumberModel(-1, -1, null, 1));
        spinnerHoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        spinnerHoc.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerHocStateChanged(evt);
            }
        });
        panelTimKiemThem.add(spinnerHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 90, 70, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(MyEnum.HOC_MUON.getIcon());
        jLabel9.setText("Số Buổi SV Học Muộn");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTimKiemThem.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 150, 30));

        spinnerHocMuon.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        spinnerHocMuon.setModel(new javax.swing.SpinnerNumberModel(-1, -1, null, 1));
        spinnerHocMuon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        spinnerHocMuon.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerHocMuonStateChanged(evt);
            }
        });
        panelTimKiemThem.add(spinnerHocMuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 128, 70, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(MyEnum.VANG_CO_PHEP.getIcon());
        jLabel10.setText("Số Buổi SV Vắng Có Phép");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTimKiemThem.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 230, 150, 30));

        spinnerVangCoPhep.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        spinnerVangCoPhep.setModel(new javax.swing.SpinnerNumberModel(-1, -1, null, 1));
        spinnerVangCoPhep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        spinnerVangCoPhep.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerVangCoPhepStateChanged(evt);
            }
        });
        panelTimKiemThem.add(spinnerVangCoPhep, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 228, 70, 30));

        btnResetSpinners.setBackground(new java.awt.Color(255, 255, 255));
        btnResetSpinners.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnResetSpinners.setText("Reset");
        btnResetSpinners.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnResetSpinners.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSpinnersActionPerformed(evt);
            }
        });
        panelTimKiemThem.add(btnResetSpinners, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 49, 127, -1));

        txtTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });
        panelTimKiemThem.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 150, 20));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        panelTimKiemThem.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 90, 40, 20));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MSSV");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        panelTimKiemThem.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 130, 40, 20));

        txtMSSV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMSSV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMSSVKeyReleased(evt);
            }
        });
        panelTimKiemThem.add(txtMSSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 150, 20));

        txtLop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtLop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLopKeyReleased(evt);
            }
        });
        panelTimKiemThem.add(txtLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 150, 20));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lớp");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        panelTimKiemThem.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 40, 20));

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/search-icon-32.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        panelTimKiemThem.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 130, 40));

        getContentPane().add(panelTimKiemThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, -1, 280));

        btnLuuChinhSua.setBackground(new java.awt.Color(255, 255, 255));
        btnLuuChinhSua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLuuChinhSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Save-icon.png"))); // NOI18N
        btnLuuChinhSua.setText("Lưu Chỉnh Sửa");
        btnLuuChinhSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuChinhSuaActionPerformed(evt);
            }
        });
        getContentPane().add(btnLuuChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 330, -1, -1));

        btnKetXuat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnKetXuat.setText("Kết Xuất Excel");
        btnKetXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetXuatActionPerformed(evt);
            }
        });
        getContentPane().add(btnKetXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 330, -1, -1));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tên Nhóm:");
        jLabel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 66, -1));

        lblTenMonHoc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenMonHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenMonHoc.setText("jLabel8");
        lblTenMonHoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 347, -1));

        lblTenLop.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenLop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenLop.setText("jLabel9");
        lblTenLop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 347, -1));

        lblTenNhom.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTenNhom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNhom.setText("jLabel10");
        lblTenNhom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblTenNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 347, -1));

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Tên Môn Học:");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel14.setBackground(new java.awt.Color(204, 204, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Tên Lớp:");
        jLabel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel14.setOpaque(true);
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 66, -1));

        jLabel11.setBackground(new java.awt.Color(255, 153, 153));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Tìm SV Theo Số Buổi");
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, -1, -1));

        jLabel15.setBackground(new java.awt.Color(255, 204, 204));
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 820, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
//        // lay listTongBuoiHoc da thay doi trong tblModel va add them vao SQL
//        // mode 1: uneditable
//        // mode 2: editable + update      
//        ArrayList<BuoiHoc> listBuoiHocTblModel = tblModel.getListBuoiHoc();
//        if (mode == 2 && tblModel.isDataChanged()) {
//            // luu tat ca buoiHoc tren SQL, dong thoi khoi tao list Boolean da luu thanh cong hay chua
//            ArrayList<Boolean> isUpdatedBuoiHoc = new ArrayList<>();
//            for (BuoiHoc buoiHoc : listBuoiHocTblModel) {
//                boolean isUpdated = KetNoiSQL_GV.updateBuoiHoc(buoiHoc);
//                isUpdatedBuoiHoc.add(isUpdated);
//            }            
//            // sau khi luu tren SQL thi update data listBuoiHoc goc
//            for (int i = 0; i < listTongBuoiHoc.getListBuoiHoc().size(); i++) {
//                BuoiHoc buoiHocOriginal = listTongBuoiHoc.getListBuoiHoc().get(i);
//                for (BuoiHoc buoiHoc : listBuoiHocTblModel) {
//                    if (buoiHocOriginal.equals(buoiHoc)) {
//                        buoiHocOriginal.setListDiemDanhSV(buoiHoc.getListDiemDanhSV());
//                    }
//                }
//            }            
//            // kiem tra xem tat ca buoiHoc da duoc update chua
//            for (int update_i = 0; update_i < isUpdatedBuoiHoc.size(); update_i++) {
//                if (update_i == isUpdatedBuoiHoc.size() - 1) {
//                    JOptionPane.showMessageDialog(this, "Lưu Thành Công");
//                    LookUpPanel.setListBuoiHocUpdated(true);
//                    this.dispose();
//                }
//                if (isUpdatedBuoiHoc.get(update_i) == false) {
//                    JOptionPane.showMessageDialog(this, "Lưu Thất Bại");
//                }
//            }
//        } else {
//            this.dispose();
//        }
        this.dispose();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void BtnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnThoatActionPerformed

    private void btnKetXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetXuatActionPerformed
        if (!tblModel.getListBuoiHoc().isEmpty()) {
            exportExcel();
        } else {
            JOptionPane.showMessageDialog(this, "Không Có Buổi Điểm Danh Nào Để Kết Xuất",
                    "Thông Báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnKetXuatActionPerformed

    private void btnLuuChinhSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuChinhSuaActionPerformed
        // lay listTongBuoiHoc da thay doi trong tblModel va add them vao SQL
        // mode 1: uneditable
        // mode 2: editable + update
        int result = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Chỉnh Sửa?",
            "Cảnh Báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            ArrayList<BuoiHoc> listBuoiHocTblModel = tblModel.getListBuoiHoc();
            if (mode == 2 && tblModel.isDataChanged()) {
                // luu tat ca buoiHoc tren SQL, dong thoi khoi tao list Boolean da luu thanh cong hay chua
                ArrayList<Boolean> isUpdatedBuoiHoc = new ArrayList<>();
                for (BuoiHoc buoiHoc : listBuoiHocTblModel) {
                    boolean isUpdated = KetNoiSQL_GV.updateBuoiHoc(buoiHoc);
                    isUpdatedBuoiHoc.add(isUpdated);
                }
                boolean isUpdatedAll = false;
                // kiem tra xem tat ca buoiHoc da duoc update chua
                for (int update_i = 0; update_i < isUpdatedBuoiHoc.size(); update_i++) {
                    if (update_i == isUpdatedBuoiHoc.size() - 1) {
                        JOptionPane.showMessageDialog(this, "Lưu Thành Công");
                        LookUpPanel.setListBuoiHocUpdated(true);
                        isUpdatedAll = true;
                    }
                    if (isUpdatedBuoiHoc.get(update_i) == false) {
                        JOptionPane.showMessageDialog(this, "Lưu Thất Bại");
                        break;
                    }
                }
                // neu luu thanh cong tren SQL thi update data listBuoiHoc goc
                if (isUpdatedAll) {
                    for (int i = 0; i < listTongBuoiHoc.getListBuoiHoc().size(); i++) {
                        BuoiHoc buoiHocOriginal = listTongBuoiHoc.getListBuoiHoc().get(i);
                        for (BuoiHoc buoiHoc : listBuoiHocTblModel) {
                            if (buoiHocOriginal.equals(buoiHoc)) {
                                buoiHocOriginal.setListDiemDanhSV(buoiHoc.getListDiemDanhSV());
                            }
                        }
                    }
                    LookUpPanel.setListBuoiHocUpdated(true);
                }
            }
        }
    }//GEN-LAST:event_btnLuuChinhSuaActionPerformed

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
        resetSpinnersValue();
        setSpinnersUpperBound(tblModel);
        setTblRenderer(tblModel);
        setTblEditor();
        setTableProperties();
        tblModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                setSpinnersUpperBound(tblModel);
                tblStudents.repaint();
            }
        });
        filter();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtLopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLopKeyReleased
        filter();
    }//GEN-LAST:event_txtLopKeyReleased

    private void txtMSSVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMSSVKeyReleased
        filter();
    }//GEN-LAST:event_txtMSSVKeyReleased

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        filter();
    }//GEN-LAST:event_txtTenKeyReleased

    private void btnResetSpinnersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSpinnersActionPerformed
        resetSpinnersValue();
        objectLookUpCellRender.setSoBuoiHoc(-1);
        objectLookUpCellRender.setSoBuoiHocMuon(-1);
        objectLookUpCellRender.setSoBuoiVangKoPhep(-1);
        objectLookUpCellRender.setSoBuoiVangCoPhep(-1);
        tblStudents.repaint();
    }//GEN-LAST:event_btnResetSpinnersActionPerformed

    private void spinnerVangCoPhepStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerVangCoPhepStateChanged
        objectLookUpCellRender.setSoBuoiVangCoPhep((int) spinnerVangCoPhep.getValue());
        tblStudents.repaint();
    }//GEN-LAST:event_spinnerVangCoPhepStateChanged

    private void spinnerHocMuonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerHocMuonStateChanged
        objectLookUpCellRender.setSoBuoiHocMuon((int) spinnerHocMuon.getValue());
        tblStudents.repaint();
    }//GEN-LAST:event_spinnerHocMuonStateChanged

    private void spinnerHocStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerHocStateChanged
        objectLookUpCellRender.setSoBuoiHoc((int) spinnerHoc.getValue());
        tblStudents.repaint();
    }//GEN-LAST:event_spinnerHocStateChanged

    private void spinnerVangKoPhepStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerVangKoPhepStateChanged
        objectLookUpCellRender.setSoBuoiVangKoPhep((int) spinnerVangKoPhep.getValue());
        tblStudents.repaint();
    }//GEN-LAST:event_spinnerVangKoPhepStateChanged

    private void exportExcel() {
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\chuon\\OneDrive\\Máy tính\\");
        //Dialog box title
        excelFileChooser.setDialogTitle("Save As");
        //Filter only xls, xlsx, xlsm files
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel Files (*.xls;*.xlsx;*.xlsm)", "xls", "xlsx", "xlsm");
        //Setting extension for selected file names
        excelFileChooser.setFileFilter(fnef);
        int chooser = excelFileChooser.showSaveDialog(null);
        // nếu đồng ý lưu và kiểm tra ngoại lệ tên file
        if (chooser == JFileChooser.APPROVE_OPTION && checkValidFileName(excelFileChooser.getSelectedFile().getName())) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Điểm Danh SV");
            // thêm thông tin lớp mà GV điểm danh
            int rowNumLopDiemDanh = 0;
            int columnNumLopDiemDanh = 1;
            int widthLopDiemDanh = 2; // so luong cac column se merge them cho thongTinDiemDanh
            int heightLopDiemDanh = 3; // so luong cac rowN se merge them cho thongTinDiemDanh
            Row mergeRow = sheet.createRow(rowNumLopDiemDanh);
            Cell cellLopDiemDanh = mergeRow.createCell(columnNumLopDiemDanh);
            Font fontLopDiemDanh = workbook.createFont();
            fontLopDiemDanh.setFontName("Arial");
            fontLopDiemDanh.setBold(true);
            fontLopDiemDanh.setFontHeightInPoints((short) 13);
            fontLopDiemDanh.setColor(IndexedColors.DARK_TEAL.index);
            CellStyle cellStyleLopDiemDanh = workbook.createCellStyle();
            cellStyleLopDiemDanh.setFont(fontLopDiemDanh);
            cellStyleLopDiemDanh.setWrapText(true);
            cellStyleLopDiemDanh.setFillBackgroundColor(IndexedColors.WHITE.index);
//            // set mau fill backGround
//            cellStyleLopDiemDanh.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
//            cellStyleLopDiemDanh.setFillPattern(FillPatternType.SOLID_FOREGROUND);           
            cellLopDiemDanh.setCellStyle(cellStyleLopDiemDanh);
            BuoiHoc buoiHoc = tblModel.getListBuoiHoc().get(0);
            String lopDiemDanhStr = lopHoc.getTenMonHoc() + "\nLớp: " + lopHoc.getLop() + "\nNhóm: " + buoiHoc.getTenNhom();
            cellLopDiemDanh.setCellValue(lopDiemDanhStr);
            // thêm thông tin những ngày điểm danh GV chọn để xuất excel
            int columnNumNgayDiemDanh = columnNumLopDiemDanh + widthLopDiemDanh + 3;
            int rowNumNgayDiemDanh = rowNumLopDiemDanh + 2;
            Row rowNgayDiemDanh = sheet.createRow(rowNumNgayDiemDanh);
            Cell cellNgayDiemDanh = rowNgayDiemDanh.createCell(columnNumNgayDiemDanh);
            Font fontNgayDiemDanh = workbook.createFont();
            fontNgayDiemDanh.setFontName("Arial");
            fontNgayDiemDanh.setFontHeightInPoints((short) 12);
            fontNgayDiemDanh.setColor(IndexedColors.BLACK.index);
            CellStyle cellStyleNgayDiemDanh = workbook.createCellStyle();
            cellStyleNgayDiemDanh.setFont(fontNgayDiemDanh);
            cellNgayDiemDanh.setCellStyle(cellStyleNgayDiemDanh);
            String ngayDiemDanhStr = "Ngày Điểm Danh: ";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            BuoiHoc buoiDiemDanhBDXuatFile = tblModel.getListBuoiHoc().get(0);
            BuoiHoc buoiDiemDanhKTXuatFile = tblModel.getListBuoiHoc().get(tblModel.getListBuoiHoc().size() - 1);
            if (buoiDiemDanhBDXuatFile.equals(buoiDiemDanhKTXuatFile)) {
                String ngayXuatFileDiemDanh = simpleDateFormat.format(buoiDiemDanhBDXuatFile.getNgayHoc());
                if (buoiDiemDanhBDXuatFile.getCa() == 0) {
                    ngayXuatFileDiemDanh += " SÁNG";
                } else {
                    ngayXuatFileDiemDanh += " CHIỀU";
                }
                ngayDiemDanhStr += ngayXuatFileDiemDanh;
            } else {
                String ngayBDXuatFileDiemDanh = simpleDateFormat.format(buoiDiemDanhBDXuatFile.getNgayHoc());
                String ngayKTXuatFileDiemDanh = simpleDateFormat.format(buoiDiemDanhKTXuatFile.getNgayHoc());
                if (buoiDiemDanhBDXuatFile.getCa() == 0) {
                    ngayBDXuatFileDiemDanh += " SÁNG";
                } else {
                    ngayBDXuatFileDiemDanh += " CHIỀU";
                }
                if (buoiDiemDanhKTXuatFile.getCa() == 0) {
                    ngayKTXuatFileDiemDanh += " SÁNG";
                } else {
                    ngayKTXuatFileDiemDanh += " CHIỀU";
                }
                ngayDiemDanhStr += ngayBDXuatFileDiemDanh + " - " + ngayKTXuatFileDiemDanh;
            }
            cellNgayDiemDanh.setCellValue(ngayDiemDanhStr);
            CellRangeAddress cellMerge = new CellRangeAddress(rowNumNgayDiemDanh,
                    rowNumNgayDiemDanh, columnNumNgayDiemDanh, columnNumNgayDiemDanh + 3);
            sheet.addMergedRegion(cellMerge);
            setMerge(sheet, rowNumLopDiemDanh, rowNumLopDiemDanh + heightLopDiemDanh,
                    columnNumLopDiemDanh, columnNumLopDiemDanh + widthLopDiemDanh, true);
            // Các Header Column của JTABLE
            Font headerFont = workbook.createFont();
            //headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.RED.getIndex());
            // tạo cellStyle cho header column
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            // export data header column
            Row rowExportHeaderColumn = sheet.createRow(columnNumLopDiemDanh + heightLopDiemDanh + 2);
            for (int headerColumn_index = 0; headerColumn_index < tblModel.getColumnCount(); headerColumn_index++) {
                Cell cell = rowExportHeaderColumn.createCell(columnNumLopDiemDanh + headerColumn_index);
                cell.setCellValue(tblModel.getColumnName(headerColumn_index));
                cell.setCellStyle(headerCellStyle);
            }
            // export data Row của JTABLE
            int rowNum = rowExportHeaderColumn.getRowNum() + 1;
            for (int row_index = 0; row_index < tblStudents.getRowCount(); row_index++) {
                Row rowExportInfo = sheet.createRow(rowNum++);
                for (int col_index = 0; col_index < tblStudents.getColumnCount(); col_index++) {
                    int col_index_NgayDiemDanh = 6;
                    if (col_index < col_index_NgayDiemDanh) {
                        int col_index_STT = 0;
                        if (col_index == col_index_STT) { // Integer Column
                            if (tblModel.getValueAt(row_index, col_index) != null) {
                                rowExportInfo.createCell(columnNumLopDiemDanh + col_index, CellType.NUMERIC).setCellValue(row_index + 1);
                            }
                        } else { // String Column
                            rowExportInfo.createCell(columnNumLopDiemDanh + col_index).setCellValue(
                                    (String) tblModel.getValueAt(tblStudents.convertRowIndexToModel(row_index), col_index));
                        }
                    } else { // cac column ngay diem danh
                        Cell cellDiemDanh = rowExportInfo.createCell(columnNumLopDiemDanh + col_index);
                        int idDiemDanh = (int) tblModel.getValueAt(
                                tblStudents.convertRowIndexToModel(row_index), col_index);
                        cellDiemDanh.setCellValue(MyEnum.getKyHieuExcel(idDiemDanh));
                        CellStyle cellStyleDiemDanh = workbook.createCellStyle();
                        cellStyleDiemDanh.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleDiemDanh.setFillForegroundColor(MyEnum.getBackGroundColorKyHieuExcel(idDiemDanh));
                        cellStyleDiemDanh.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        // border
                        cellStyleDiemDanh.setBorderBottom(BorderStyle.THIN);
                        cellStyleDiemDanh.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        cellStyleDiemDanh.setBorderLeft(BorderStyle.THIN);
                        cellStyleDiemDanh.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        cellStyleDiemDanh.setBorderRight(BorderStyle.THIN);
                        cellStyleDiemDanh.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        cellStyleDiemDanh.setBorderTop(BorderStyle.THIN);
                        cellStyleDiemDanh.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        cellDiemDanh.setCellStyle(cellStyleDiemDanh);
                    }
                }
            }
//<editor-fold defaultstate="folded" desc="export Color">
//            int maximumIn1Row = 10;
//            int columnNow = 0;
//            int rowN = 15;
//            Row rowNow = sheet.createRow(rowN);
//            for(IndexedColors color : IndexedColors.values()){
//                if(columnNow == maximumIn1Row){
//                    rowN ++;
//                    columnNow = 0;
//                    rowNow = sheet.createRow(rowN);
//                    CellStyle st2 = workbook.createCellStyle();
//                    st2.setWrapText(true);
//                    rowNow.setRowStyle(st2);
//                }
//                Font f = workbook.createFont();
//                f.setBold(true);
//                f.setFontHeightInPoints((short) 13);
//                f.setColor(color.index);
//                CellStyle st = workbook.createCellStyle();
//                st.setFont(f);
//                st.setWrapText(true);
//                Cell c = rowNow.createCell(columnNow);
//                c.setCellStyle(st);
//                c.setCellValue(color.toString());
//                columnNow++;
//            }
//</editor-fold>               

            // thêm chú thích ký hiệu điểm danh
            int columnNumChuThich = columnNumLopDiemDanh + tblStudents.getColumnCount() - 1;
            int rowNumChuThich = rowExportHeaderColumn.getRowNum() + tblStudents.getRowCount() + 2;
            Row rowChuThich = sheet.createRow(rowNumChuThich);
            rowChuThich.createCell(columnNumChuThich).setCellValue("Chú Thích");
            int columnNumChiThichNow = columnNumChuThich + 1;
            for (MyEnum e : MyEnum.values()) {
                rowChuThich.createCell(columnNumChiThichNow).setCellValue(e.getChuThichEditorComBoBox());
                columnNumChiThichNow++;
            }
            // set style + value cho cac cell chu thich chi tiet điểm danh
            Row rowChuThich_ChiTiet = sheet.createRow(rowNumChuThich + 1);
            int columnNum_ChiTietNow = columnNumChuThich + 1;
            for (MyEnum e : MyEnum.values()) {
                CellStyle style = workbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setFillForegroundColor(e.getBackGroundColorKyHieuExcel());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setBorderLeft(BorderStyle.THIN);
                style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setBorderRight(BorderStyle.THIN);
                style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setBorderTop(BorderStyle.THIN);
                style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
                Cell cell_ChiTiet = rowChuThich_ChiTiet.createCell(columnNum_ChiTietNow);
                cell_ChiTiet.setCellStyle(style);
                cell_ChiTiet.setCellValue(e.getKyHieuExcel());
                columnNum_ChiTietNow++;
            }
            // Tự động căn chỉnh các cột
            for (int i = columnNumLopDiemDanh; i < columnNumChuThich + 5; i++) {
                // van on
                sheet.autoSizeColumn(i);
                //sheet.autoSizeColumn(i, true);
            }
            FileOutputStream fileOut = null;
            try {
                String selectedFileStr = excelFileChooser.getSelectedFile().toString();
                if (selectedFileStr.contains(".xlsx")) {
                    selectedFileStr = selectedFileStr.replaceFirst(".xlsx", "");
                }
                File newSelectedFile = new File(selectedFileStr);
                fileOut = new FileOutputStream(newSelectedFile + ".xlsx");
                JOptionPane.showMessageDialog(this, "Kết Xuất Thành Công!",
                        "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Kết Xuất Thất Bại",
                        "Thông Báo", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                workbook.write(fileOut);
                fileOut.close();
                // Closing the workbook  
                workbook.close();
            } catch (IOException ex) {
                Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setMerge(Sheet sheet, int numRow, int untilRow, int numCol, int untilCol, boolean border) {
        CellRangeAddress cellMerge = new CellRangeAddress(numRow, untilRow, numCol, untilCol);
        sheet.addMergedRegion(cellMerge);
        if (border) {
            setBordersToMergedCells(sheet, cellMerge);
        }

    }

    private void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress) {
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
    }

    private boolean checkValidFileName(String fileName) {
        if (fileName.contains("\\") || fileName.contains("/") || fileName.contains(":")
                || fileName.contains("*") || fileName.contains("?") || fileName.contains("\"")
                || fileName.contains("<") || fileName.contains(">") || fileName.contains("|")) {
            JOptionPane.showMessageDialog(this, "Tên File Không Thể Chứa Những Ký Tự Đặc Biệt Sau:  \\/:*?\"<>|",
                    "Thông Báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

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
//            java.util.logging.Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LookUpRollUpStudentDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                LookUpRollUpStudentDlg dialog = new LookUpRollUpStudentDlg(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton BtnThoat;
    private javax.swing.JButton btnDongY;
    private javax.swing.JButton btnKetXuat;
    private javax.swing.JButton btnLuuChinhSua;
    private javax.swing.JButton btnResetSpinners;
    private javax.swing.JButton btnTimKiem;
    private com.toedter.calendar.JDateChooser dateChooserFrom;
    private com.toedter.calendar.JDateChooser dateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel panelTimKiemThem;
    private javax.swing.JSpinner spinnerHoc;
    private javax.swing.JSpinner spinnerHocMuon;
    private javax.swing.JSpinner spinnerVangCoPhep;
    private javax.swing.JSpinner spinnerVangKoPhep;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMSSV;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
