/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.BasicAttribute_Method;
import Model.HocKy;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.ListHocKy;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.ListMailStudent;
import Model.GiaoVien.ListNhomSV;
import Model.GiaoVien.LopHoc;
import TableModel.GiaoVien.MailBoxTableModel;
import Model.GiaoVien.MailStudent;
import Model.GiaoVien.NhomSV;
import UnusedModel.ObjectRenderer;
import Model.Tuan;
import java.awt.Component;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author chuon
 */
/**
 * Luu Y: Cac ComboBox trong day se setUp Items phu thuoc vao cac comboBox o
 * tren no (dua vao view, tu tren xuong, trai sang phai)
 */
public class MailPanel extends javax.swing.JPanel {

    private Frame home;
    private MailBoxTableModel tblModel;
    private ListHocKy listHocKy;
    private ListLopHoc listLopHoc; // lay thong tin cac lop hoc ma GV day + vai tro GV trong lop
    private ListNhomSV listNhomSV; // lay danh sach sinh vien hoc cac nhom
    private ListMailStudent listTongMailStudent; // lay thong tin cac thu GV da gui cho SV
    private ListMailStudent listMailStudentTrinhChieu; // phat sinh do chuc nang tim kiem theo ngay
    // nho reset listMSSVChosen moi khi chon noi gui khac
    private ArrayList<String> listMSSVChosen;

    public MailPanel(Frame parent) {
        initComponents();
        home = parent;
        firstSetUp();
        resizeColumnWidth(tblMailBox);
          tblMailBox.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public ListMailStudent getListTongMailStudent() {
        return listTongMailStudent;
    }
public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 150; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}
    private void firstSetUp() {
        setAllData();
        tblModel = new MailBoxTableModel(listLopHoc, listNhomSV, listMailStudentTrinhChieu);
        tblMailBox.setModel(tblModel);
        setUpCBHocKy(listHocKy);
        setUpCBTenMonHoc(listLopHoc);
        setUpCBLop((String) comboTenMonHoc.getSelectedItem());
        setUpCBNhom((String) comboTenMonHoc.getSelectedItem(), (String) comboLop.getSelectedItem());
        setTableProperties();
        radioNhom.setSelected(true);
        comboNhom.setSelectedIndex(0);
        comboHocKy.setSelectedIndex(comboHocKy.getItemCount() - 1);
    }

    private void setAllData() {
        setListHocKy();
        setListTongMail();
        setListLopHoc(null);
        setListNhomSV();
        setUpCBNgayTimKiem(null);
        setListMailStudentTrinhChieu();
        listMSSVChosen = null;
    }

    private void setListHocKy() {
        listHocKy = new ListHocKy();
        listHocKy.getDataToanBo_GV();
    }

    private void setListTongMail() {
        this.listTongMailStudent = new ListMailStudent();
        ArrayList<MailStudent> listMail = KetNoiSQL_GV.getListMail();
        if (!listMail.isEmpty()) {
            this.listTongMailStudent.getListMail().addAll(listMail);
        }

    }

    private void setListLopHoc(HocKy hocKy) {
        // moi hoc ky GV co day hoc se lay data cac lop hoc tuong ung
        this.listLopHoc = new ListLopHoc();
        // neu CBHocKy chon None thi getToanBoDataListLopHoc
        if (hocKy == null) {
            ArrayList<LopHoc> list = KetNoiSQL_GV.getListLopHoc();
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        } // neu CBHocKy chon khac None thi chi get DataListLopHoc cua hocKy tuong ung
        else {
            ArrayList<LopHoc> list = KetNoiSQL_GV.getListLopHoc(hocKy.getSttHocKy(), hocKy.getNamHoc());
            if (!list.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(list);
            }
        }
    }

    private void setListNhomSV() {
        this.listNhomSV = new ListNhomSV();
        for (LopHoc lop : listLopHoc.getListLopHoc()) {
            ArrayList<NhomSV> list = KetNoiSQL_GV.getListNhomSV(lop.getMaLopHoc());
            if (!list.isEmpty()) {
                this.listNhomSV.getListNhomSV().addAll(list);
            }
        }

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
            //MaLop
            tblMailBox.getColumnModel().getColumn(1).setMinWidth(150);
            tblMailBox.getColumnModel().getColumn(1).setMaxWidth(150);
            //TenMH
            tblMailBox.getColumnModel().getColumn(2).setMinWidth(300);
            tblMailBox.getColumnModel().getColumn(2).setMaxWidth(300);
            //Lop
            tblMailBox.getColumnModel().getColumn(3).setMinWidth(100);
            tblMailBox.getColumnModel().getColumn(3).setMaxWidth(100);
            //Gui Den
            tblMailBox.getColumnModel().getColumn(4).setMinWidth(150);
            tblMailBox.getColumnModel().getColumn(4).setMaxWidth(150);
            //Noi Dung
            tblMailBox.getColumnModel().getColumn(5).setResizable(false);
            //Thoi Gian Gui
            tblMailBox.getColumnModel().getColumn(6).setMinWidth(350);
            tblMailBox.getColumnModel().getColumn(6).setMaxWidth(350);
        }
    }

    private void setUpCBHocKy(ListHocKy listHocKy) {
        comboTenMonHoc.removeAllItems();
        if (!listHocKy.getListHocKy().isEmpty()) {
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
    }

    private void setUpCBTenMonHoc(ListLopHoc listLopHoc) {
        comboTenMonHoc.removeAllItems();
        if (!listLopHoc.getListLopHoc().isEmpty()) {
            comboTenMonHoc.addItem("None");
            for (LopHoc lop : listLopHoc.getListLopHoc()) {
                if (!checkCBTenMonHocExist(lop.getTenMonHoc())) {
                    comboTenMonHoc.addItem(lop.getTenMonHoc());
                }
            }
            comboTenMonHoc.setSelectedIndex(0);
        }
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
        if (!listLopHoc.getListLopHoc().isEmpty()) {
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

    private void setUpCBNhom(String tenMonHoc, String lop) {
        comboNhom.removeAllItems();
        comboNhom.addItem("None");
        LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
        if (lopHoc != null) {
            ArrayList<NhomSV> listNhom = listNhomSV.getListNhomSV(lopHoc.getMaLopHoc());
            if (!listNhom.isEmpty()) {
                for (NhomSV nhom : listNhom) {
                    comboNhom.addItem(nhom.getTenNhom());
                }
            }
        }
        comboNhom.setSelectedIndex(0);

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
        if (radioNhom.isSelected()) {
            String groupName = comboNhom.getSelectedItem().toString().trim();
            if (!groupName.equals("None")) {
                filters.add(RowFilter.regexFilter(groupName, 4));
            }
        } else {
            if (listMSSVChosen != null) {
                for (String s : listMSSVChosen) {
                    filters.add(RowFilter.regexFilter(s, 4));
                }
            }
        }
        filters.add(RowFilter.regexFilter(BasicAttribute_Method.convertSpecialString(txtAreaNoiDung.getText()), 5));
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

    private void setListMailStudentTrinhChieu() {
        // set data dua vao hocKy + cac ComboBoxDate tim kiem theo ngay
        listMailStudentTrinhChieu = new ListMailStudent();
        if (comboHocKy.getItemCount() > 0) {
            if (comboHocKy.getSelectedIndex() > 0) { // neu nhu comboHocKy chon item != None
                if (comboDateFrom.getSelectedIndex() <= comboDateTo.getSelectedIndex()) {
                    HocKy hocKy = listHocKy.getListHocKy().get(comboHocKy.getSelectedIndex() - 1);
                    Tuan tuanBD = hocKy.getDsTuan().get(comboDateFrom.getSelectedIndex());
                    Tuan tuanKT = hocKy.getDsTuan().get(comboDateTo.getSelectedIndex());
                    Date ngayBD = tuanBD.getNgayBDTuan();
                    Date ngayKT = tuanKT.getNgayKTTuan();
                    listMailStudentTrinhChieu.setListMail(
                            listTongMailStudent.getListMail(ngayBD, ngayKT));
                }
            } else { // neu nhu comboHocKy chon item == None
                HocKy hocKyDau = listHocKy.getListHocKy().get(0);
                Date ngayBD = hocKyDau.getDsTuan().get(0).getNgayBDTuan();
                int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
                HocKy hocKyCuoi = listHocKy.getListHocKy().get(lastIndexListHocKy);
                int lastIndexDSTuan = hocKyCuoi.getDsTuan().size() - 1;
                Date ngayKT = hocKyCuoi.getDsTuan().get(lastIndexDSTuan).getNgayKTTuan();
                listMailStudentTrinhChieu.setListMail(
                        listTongMailStudent.getListMail(ngayBD, ngayKT));
            }
        }
    }

    private boolean checkValidBtnSoanThu() {
        // kiem tra quyen soan Thu, dua vao ngayHienTai so voi ngayBD,KT hocKy moi nhat
        // chua toi ngay bat dau hoc ky (đã có dữ liệu học kỳ mới) ko duoc gui thu
        // da qua ngay cuoi cung cua hoc ky ko duoc gui thu
        if (!listHocKy.getListHocKy().isEmpty()) {
            int lastIndexListHocKy = listHocKy.getListHocKy().size() - 1;
            HocKy hocKy = listHocKy.getListHocKy().get(lastIndexListHocKy);
            Date ngayHienTai = new Date();
            Date ngayBDHocKy = hocKy.getDsTuan().get(0).getNgayBDTuan();
            int lastIndexDSTuan = hocKy.getDsTuan().size() - 1;
            Date ngayKTHocKy = hocKy.getDsTuan().get(lastIndexDSTuan).getNgayKTTuan();
            if (ngayHienTai.compareTo(ngayBDHocKy) >= 0 && ngayHienTai.compareTo(ngayKTHocKy) <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void updateShowUpTable() {
        // chay ham update nay sau khi da setUpCBNgayTimKiem theo y muon
        // muc dich ban dau tao ra la de update table sau khi gui thu thanh cong trong SendMailDlg
        setListMailStudentTrinhChieu();
        tblModel = new MailBoxTableModel(listLopHoc, listNhomSV, listMailStudentTrinhChieu);
        tblMailBox.setModel(tblModel);
        setTableProperties();
        filter();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        btnSoanThu = new javax.swing.JButton();
        btnXem = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMailBox = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboTenMonHoc = new javax.swing.JComboBox<>();
        comboLop = new javax.swing.JComboBox<>();
        comboNhom = new javax.swing.JComboBox<>();
        radioNhom = new javax.swing.JRadioButton();
        radioCaNhan = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaNoiDung = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboHocKy = new javax.swing.JComboBox<>();
        comboDateFrom = new javax.swing.JComboBox<>();
        comboDateTo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HỘP THƯ");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 30));

        btnSoanThu.setBackground(new java.awt.Color(255, 255, 255));
        btnSoanThu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSoanThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Circle-icons-mail.svg.png"))); // NOI18N
        btnSoanThu.setText("Soạn Thư");
        btnSoanThu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSoanThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoanThuActionPerformed(evt);
            }
        });
        add(btnSoanThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 260, 110, 40));

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
        add(btnXem, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, 110, 40));

        tblMailBox.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblMailBox.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Lớp MH", "Tên MH", "Lớp", "Gửi Đến", "Nội dung", "Thời gian gửi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMailBox.setDoubleBuffered(true);
        tblMailBox.setDragEnabled(true);
        tblMailBox.setFillsViewportHeight(true);
        tblMailBox.setFocusCycleRoot(true);
        tblMailBox.setFocusTraversalPolicyProvider(true);
        tblMailBox.setInheritsPopupMenu(true);
        tblMailBox.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tblMailBox);
        tblMailBox.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jScrollPane3.setViewportView(jScrollPane1);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1260, 200));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setText("Tên Môn Học");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 87, 70, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Lớp");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 127, 30, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText("Thời Gian Gửi Từ");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 249, -1, -1));

        comboTenMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTenMonHocActionPerformed(evt);
            }
        });
        jPanel1.add(comboTenMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 83, 143, -1));

        comboLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLopActionPerformed(evt);
            }
        });
        jPanel1.add(comboLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 123, 143, -1));

        comboNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNhomActionPerformed(evt);
            }
        });
        jPanel1.add(comboNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 163, 149, -1));

        radioNhom.setBackground(new java.awt.Color(255, 255, 255));
        radioNhom.setText("Nhóm");
        radioNhom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        radioNhom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        radioNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNhomActionPerformed(evt);
            }
        });
        jPanel1.add(radioNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 163, 95, -1));

        radioCaNhan.setBackground(new java.awt.Color(255, 255, 255));
        radioCaNhan.setText("Cá Nhân");
        radioCaNhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        radioCaNhan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        radioCaNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCaNhanActionPerformed(evt);
            }
        });
        jPanel1.add(radioCaNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 204, 95, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("Nội Dung");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 50, -1));

        txtAreaNoiDung.setColumns(20);
        txtAreaNoiDung.setRows(5);
        txtAreaNoiDung.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtAreaNoiDung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaNoiDungKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaNoiDung);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 335, 560, 192));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setText("Gửi Đến");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 163, -1, -1));

        jLabel8.setBackground(new java.awt.Color(255, 153, 153));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("TÌM KIẾM");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setOpaque(true);
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setText("Đến");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 289, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setText("Học Kỳ");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 51, -1, -1));

        comboHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHocKyActionPerformed(evt);
            }
        });
        jPanel1.add(comboHocKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 43, 272, -1));

        comboDateFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateFromActionPerformed(evt);
            }
        });
        jPanel1.add(comboDateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 245, 299, -1));

        comboDateTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateToActionPerformed(evt);
            }
        });
        jPanel1.add(comboDateTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 285, 299, -1));

        jLabel11.setBackground(new java.awt.Color(255, 204, 204));
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setOpaque(true);
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 760, 520));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, -1, 570));

        jLabel10.setBackground(new java.awt.Color(255, 204, 255));
        jLabel10.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel10.setOpaque(true);
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1270, 910));
    }// </editor-fold>//GEN-END:initComponents

    private void btnSoanThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoanThuActionPerformed
        if (!checkValidBtnSoanThu()) {
            JOptionPane.showMessageDialog(this, "Ngoai Thoi Gian Cho Phep Soan Thu");
        } else {
            SendMailDlg sendMailDlg = new SendMailDlg(home, true, this);
            sendMailDlg.setVisible(true);

        }
    }//GEN-LAST:event_btnSoanThuActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        int row = tblMailBox.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phai chon vao 1 muc");
        } else {
            int rowModel = tblMailBox.convertRowIndexToModel(row);
            String ngayGui = (String) tblModel.getValueAt(rowModel, 6);
            MailStudent mail = listMailStudentTrinhChieu.getMailStudent(ngayGui);
            if (mail != null) {
                LopHoc lopHoc = listLopHoc.getLopHoc(mail.getMaLopHoc());
                ReadMailDlg dlg = new ReadMailDlg(home, true, lopHoc, mail);
                dlg.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnXemActionPerformed

    private void comboTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTenMonHocActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            setUpCBLop(tenMonHoc);
            String lop = (String) comboLop.getSelectedItem();
            // chay cac method sau kieu gi cung se ra 1 ket qua, muc dich la: Update comboNhom ve Null
            setUpCBNhom(tenMonHoc, lop);
            radioNhom.setSelected(true);
            comboNhom.setSelectedIndex(0);
            filter();
        }
    }//GEN-LAST:event_comboTenMonHocActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            String lop = (String) comboLop.getSelectedItem();
            // comboNhom chi duoc phep update ngoai Null khi du ca tenMonHoc + Lop
            setUpCBNhom(tenMonHoc, lop);
            radioNhom.setSelected(true);
            comboNhom.setSelectedIndex(0);
            filter();
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void comboNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNhomActionPerformed
        // neu radioNhom duoc chon thi moi chay
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0) {
            if (radioNhom.isSelected()) {
                filter();
            }
        }
    }//GEN-LAST:event_comboNhomActionPerformed

    private void radioNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNhomActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0) {
            listMSSVChosen = null;
            comboNhom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_radioNhomActionPerformed

    private void radioCaNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCaNhanActionPerformed
        // chi hiển thị danh sách những sinh viên nếu tên môn học + lớp dầy đủ
        // nếu GV dạy lớp LT hay TH thì cũng đều xuất hết toàn bộ SV, chẳng qua nếu như vậy thì:
        // với GV có dạy lớp LT thì cứ lấy hết mssv trong đó
        // nhưng với GV chỉ dạy mỗi lớp TH thì phải duyệt hết toàn bộ các nhóm TH để lấy hết mssv trong đó
        // những SV chọn để gửi thư sau khi đóng DLG sẽ trả về danh sách mssv tương ứng
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0) {
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            String lop = comboLop.getSelectedItem().toString();
            LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
            if (lopHoc != null) {
                String maLopHoc = lopHoc.getMaLopHoc();
                ChooseStudentsDlg dlg = new ChooseStudentsDlg(home, true, lopHoc, listNhomSV);
                dlg.setVisible(true);
                listMSSVChosen = dlg.getListMSSVChoosen();
                //thiet lap quay tro lai radioNhom neu ko chon SV nao trong radioCaNhan
                if (listMSSVChosen == null) {
                    comboNhom.setSelectedIndex(0);
                    radioNhom.setSelected(true);
                } else {
                    comboNhom.setSelectedIndex(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Phai chon tenMonHoc + Lop da");
                radioNhom.setSelected(true);
            }
            filter();
        }
    }//GEN-LAST:event_radioCaNhanActionPerformed

    private void txtAreaNoiDungKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaNoiDungKeyReleased
        filter();
    }//GEN-LAST:event_txtAreaNoiDungKeyReleased

    private void comboHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHocKyActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            HocKy hocKy = null;
            int selectedIndexCBHocKY = comboHocKy.getSelectedIndex();
            if (selectedIndexCBHocKY > 0) {
                hocKy = listHocKy.getListHocKy().get(selectedIndexCBHocKY - 1);
            }
            setListLopHoc(hocKy);
            setUpCBTenMonHoc(listLopHoc);
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            setUpCBLop(tenMonHoc);
            String lop = comboLop.getSelectedItem().toString();
            setUpCBNhom(tenMonHoc, lop);
            setUpCBNgayTimKiem(hocKy);
            txtAreaNoiDung.setText("");
            comboDateFrom.setSelectedIndex(0);
            comboDateTo.setSelectedIndex(comboDateTo.getItemCount() - 1);
            setListMailStudentTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc, listNhomSV, listMailStudentTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
            filter();
        }
    }//GEN-LAST:event_comboHocKyActionPerformed

    private void comboDateFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateFromActionPerformed
        if (comboDateFrom.getItemCount() > 0 && comboDateTo.getItemCount() > 0
            && comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            if(comboDateFrom.getSelectedIndex() > comboDateTo.getSelectedIndex()){
                JOptionPane.showMessageDialog(this, "Lỗi Tìm Kiếm Thời Gian Gửi Thư");
                comboDateFrom.setSelectedIndex(0);
                comboDateTo.setSelectedIndex(comboDateTo.getItemCount() -1);
            }
            setListMailStudentTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc, listNhomSV, listMailStudentTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
            filter();
        }
    }//GEN-LAST:event_comboDateFromActionPerformed

    private void comboDateToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateToActionPerformed
        if (comboDateFrom.getItemCount() > 0 && comboDateTo.getItemCount() > 0
            && comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
            && comboNhom.getItemCount() != 0 && comboHocKy.getItemCount() != 0) {
            if(comboDateFrom.getSelectedIndex() > comboDateTo.getSelectedIndex()){
                JOptionPane.showMessageDialog(this, "Lỗi Tìm Kiếm Thời Gian Gửi Thư");
                comboDateFrom.setSelectedIndex(0);
                comboDateTo.setSelectedIndex(comboDateTo.getItemCount() -1);
            }
            setListMailStudentTrinhChieu();
            tblModel = new MailBoxTableModel(listLopHoc, listNhomSV, listMailStudentTrinhChieu);
            tblMailBox.setModel(tblModel);
            setTableProperties();
            filter();
        }
    }//GEN-LAST:event_comboDateToActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSoanThu;
    private javax.swing.JButton btnXem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboDateFrom;
    private javax.swing.JComboBox<String> comboDateTo;
    private javax.swing.JComboBox<String> comboHocKy;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboNhom;
    private javax.swing.JComboBox<String> comboTenMonHoc;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton radioCaNhan;
    private javax.swing.JRadioButton radioNhom;
    private javax.swing.JTable tblMailBox;
    private javax.swing.JTextArea txtAreaNoiDung;
    // End of variables declaration//GEN-END:variables
}
