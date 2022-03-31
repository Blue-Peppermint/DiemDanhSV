/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GiaoVien;

import Model.HocKy;
import Model.GiaoVien.KetNoiSQL_GV;
import Model.ListHocKy;
import Model.GiaoVien.ListLopHoc;
import Model.GiaoVien.ListNhomSV;
import Model.GiaoVien.LopHoc;
import Model.GiaoVien.MailStudent;
import Model.GiaoVien.NhomSV;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chuon
 */
public class SendMailDlg extends javax.swing.JDialog {
    // chi cho phep GV gui thu nhung lop hoc dang day hien tai
    private MailPanel mailPanel;
    private ListLopHoc listLopHoc; // lay thong tin cac lop hoc ma GV day + vai tro GV trong lop
    private ListNhomSV listNhomSV; // lay danh sach sinh vien hoc cac nhom    
    private ArrayList<String> listMSSVSent;

    public SendMailDlg(java.awt.Frame parent, boolean modal, MailPanel mailPanel) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gửi Thư");
        this.mailPanel = mailPanel;
        setAllData();
        setUpCBTenMonHoc();
        setUpCBLop((String) comboTenMonHoc.getSelectedItem());
        setUpCBNhom((String) comboTenMonHoc.getSelectedItem(),(String)comboLop.getSelectedItem());
        firstShowUp();
    }

    private void firstShowUp(){
        radioNhom.setSelected(true);
        comboNhom.setSelectedIndex(0);
    }
    private void setAllData() {
        // de cac ham theo dung thu tu nha :>
        setListLopHoc();
        setListNhomSV();
    }

    private HocKy getHocKy() {
        // lay data cua hoc ky moi nhat gv day
        // lay hoc ky moi nhat, chinh la LastValue trong thongTinDay.dsHocKy
        HocKy hocKy = null;
        ListHocKy listHocKy = new ListHocKy();
        listHocKy.getDataKhoaMoiNhat_GV();
        if (!listHocKy.getListHocKy().isEmpty()) {
            hocKy = new HocKy();
            int lastIndex = listHocKy.getListHocKy().size() - 1;
            hocKy = listHocKy.getListHocKy().get(lastIndex);
        }
        return hocKy;
    }

    private void setListLopHoc() {
        // lay thong tin cac lopHoc hoc ma GV day <tai thoi diem moi nhat>
        HocKy hocKy = getHocKy();
        if (hocKy != null) {
            this.listLopHoc = new ListLopHoc();
            int sttHocKy = hocKy.getSttHocKy();
            int namHoc = hocKy.getNamHoc();
            ArrayList<LopHoc> listLopHoc = KetNoiSQL_GV.getListLopHoc(sttHocKy, namHoc);
            if (!listLopHoc.isEmpty()) {
                this.listLopHoc.getListLopHoc().addAll(listLopHoc);
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

    private void setUpCBTenMonHoc() {
        comboTenMonHoc.removeAllItems();
        for (LopHoc lop : listLopHoc.getListLopHoc()) {
            if (!checkCBTenMonHocExist(lop.getTenMonHoc())) {
                comboTenMonHoc.addItem(lop.getTenMonHoc());
            }
        }
        comboTenMonHoc.setSelectedIndex(0);
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

    private void setUpCBLop(String tenMonHoc) {
        // hien thi lop dua vao ten mon hoc
        comboLop.removeAllItems();
        for (LopHoc lopHoc : listLopHoc.getListLopHoc()) {
            String lop = lopHoc.getLop();
            if (lopHoc.getTenMonHoc().equals(tenMonHoc) && !checkCBLopExist(lop)) {
                comboLop.addItem(lop);
            }
        }
    }

    private void setUpCBNhom(String tenMonHoc, String lop) {
        comboNhom.removeAllItems();
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

    private MailStudent createMail(){
        MailStudent mail = null;        
        LopHoc lopHoc = listLopHoc.getLopHoc((String)comboTenMonHoc.getSelectedItem()
                    ,(String)comboLop.getSelectedItem());
            if(lopHoc != null){
                String maLopHoc = lopHoc.getMaLopHoc();
                Date ngayGui = new Date();
                String noiDung = txtAreaNoiDung.getText();
                boolean thongBaoTuDo = radioCaNhan.isSelected();
                mail = new MailStudent(maLopHoc, ngayGui, noiDung, listMSSVSent, thongBaoTuDo);
            }
            else{
                System.out.println("Loi khong tim thay lop hoc");
            }
            return mail;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTenMonHoc = new javax.swing.JComboBox<>();
        comboLop = new javax.swing.JComboBox<>();
        comboNhom = new javax.swing.JComboBox<>();
        radioNhom = new javax.swing.JRadioButton();
        radioCaNhan = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaNoiDung = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        thoatBtn = new javax.swing.JButton();
        guiBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SOẠN THƯ");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        comboNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNhomActionPerformed(evt);
            }
        });

        radioNhom.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radioNhom);
        radioNhom.setText("Nhóm");
        radioNhom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        radioNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNhomActionPerformed(evt);
            }
        });

        radioCaNhan.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radioCaNhan);
        radioCaNhan.setText("Cá Nhân");
        radioCaNhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        radioCaNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCaNhanActionPerformed(evt);
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
        txtAreaNoiDung.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtAreaNoiDung);

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Gửi Đến");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioCaNhan)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(radioNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioNhom)
                            .addComponent(comboNhom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(radioCaNhan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        thoatBtn.setBackground(new java.awt.Color(255, 255, 255));
        thoatBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        thoatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/Button-Close-icon-16.png"))); // NOI18N
        thoatBtn.setText("Thoát");
        thoatBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        thoatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thoatBtnActionPerformed(evt);
            }
        });

        guiBtn.setBackground(new java.awt.Color(255, 255, 255));
        guiBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        guiBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconimage/mail.jpg"))); // NOI18N
        guiBtn.setText("Gửi");
        guiBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        guiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(guiBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150)
                .addComponent(thoatBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guiBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(thoatBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addGap(506, 506, 506))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTenMonHocActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
                && comboNhom.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            setUpCBLop(tenMonHoc);           
            comboLop.setSelectedIndex(0);                        
            String lop = (String) comboLop.getSelectedItem();
            setUpCBNhom(tenMonHoc, lop);
            radioNhom.setSelected(true);
            comboNhom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_comboTenMonHocActionPerformed

    private void comboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLopActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
                && comboNhom.getItemCount() != 0) {
            String tenMonHoc = (String) comboTenMonHoc.getSelectedItem();
            String lop = (String) comboLop.getSelectedItem();
            setUpCBNhom(tenMonHoc, lop);
            comboNhom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_comboLopActionPerformed

    private void radioNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNhomActionPerformed
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
                && comboNhom.getItemCount() != 0) {
            listMSSVSent = null;
            comboNhom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_radioNhomActionPerformed

    private void radioCaNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCaNhanActionPerformed
        // chi hiển thị danh sách những sinh viên nếu tên môn học + lớp dầy đủ
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
                && comboNhom.getItemCount() != 0) {
            String tenMonHoc = comboTenMonHoc.getSelectedItem().toString();
            String lop = comboLop.getSelectedItem().toString();
            LopHoc lopHoc = listLopHoc.getLopHoc(tenMonHoc, lop);
            if (lopHoc != null) {
                String maLopHoc = lopHoc.getMaLopHoc();
                ChooseStudentsDlg dlg = new ChooseStudentsDlg((Frame) this.getParent(),
                        true, lopHoc, listNhomSV);
                dlg.setVisible(true);
                listMSSVSent = dlg.getListMSSVChoosen();
                //thiet lap quay tro lai radioNhom neu ko chon SV nao trong radioCaNhan
                if (listMSSVSent == null) {
                    comboNhom.setSelectedIndex(0);
                    radioNhom.setSelected(true);
                }
                else{
                    comboNhom.setSelectedIndex(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Phai chon tenMonHoc + Lop da");
                radioNhom.setSelected(true);
            }
        }
    }//GEN-LAST:event_radioCaNhanActionPerformed

    private void guiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiBtnActionPerformed
        // kiem tra cac ngoai le, cho phep gui thu khi nao?
        // update SQL data
        MailStudent mail = createMail();
        if(mail != null){
            // ngan ngua spamming btn gui thu -> loi get ngay tren table cua nhung ban ghi mail se bi trung
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendMailDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(KetNoiSQL_GV.saveMail(mail)){
                mailPanel.getListTongMailStudent().getListMail().add(mail);
                mailPanel.updateShowUpTable();
                JOptionPane.showMessageDialog(this, "Gui Thu Thanh Cong");
            }
            else{
                JOptionPane.showMessageDialog(this, "Gui Thu That Bai");
            }            
        }
        else{
            JOptionPane.showMessageDialog(this, "Thu Bi Loi Khong The Gui Duoc. Xin Thu Lai");
        }
    }//GEN-LAST:event_guiBtnActionPerformed

    private void thoatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thoatBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_thoatBtnActionPerformed

    private void comboNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNhomActionPerformed
        // neu radioNhom duoc chon thi moi chay
        if (comboTenMonHoc.getItemCount() != 0 && comboLop.getItemCount() != 0
                && comboNhom.getItemCount() != 0) {
        if(radioNhom.isSelected()){
            LopHoc lopHoc = listLopHoc.getLopHoc((String)comboTenMonHoc.getSelectedItem()
                    ,(String)comboLop.getSelectedItem());
            if(lopHoc != null){
                ArrayList<NhomSV> listNhom = listNhomSV.getListNhomSV(lopHoc.getMaLopHoc());
                for(NhomSV nhom : listNhom){
                    listMSSVSent = new ArrayList<>();
                    // duyet trong listNhom cua lop hoc vua chon, tim kiem nhom can thiet de get mssv
                    if(nhom.getTenNhom().equals((String)comboNhom.getSelectedItem())){
                        listMSSVSent.addAll(nhom.getListMSSV());                        
                    }
                }
            }
            else{
                System.out.println("Loi khong tim thay lop hoc");
            }

        }
        }
    }//GEN-LAST:event_comboNhomActionPerformed

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
//            java.util.logging.Logger.getLogger(SendMailDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SendMailDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SendMailDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SendMailDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SendMailDlg dialog = new SendMailDlg(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboLop;
    private javax.swing.JComboBox<String> comboNhom;
    private javax.swing.JComboBox<String> comboTenMonHoc;
    private javax.swing.JButton guiBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radioCaNhan;
    private javax.swing.JRadioButton radioNhom;
    private javax.swing.JButton thoatBtn;
    private javax.swing.JTextArea txtAreaNoiDung;
    // End of variables declaration//GEN-END:variables
}
