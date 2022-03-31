/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.MyEnum;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chuon
 */

public class BuoiHoc {
    private String maLopHoc;
    private String tenNhom;
    private Date ngayHoc;
    private int ca; // ca sang hoac chieu 0 -> 1
    private ArrayList<DiemDanhSV> listDiemDanhSV;

    public BuoiHoc() {
        this.maLopHoc = "";
        this.tenNhom = "";
        this.ngayHoc = null;
        this.ca = -1;
        this.listDiemDanhSV = new ArrayList<>();
    }

    public BuoiHoc(String maLopHoc, String tenNhom, Date ngayHoc, int ca, ArrayList<DiemDanhSV> listDiemDanhSV) {
        this.maLopHoc = maLopHoc;
        this.tenNhom = tenNhom;
        this.ngayHoc = ngayHoc;
        this.ca = ca;
        this.listDiemDanhSV = listDiemDanhSV;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public Date getNgayHoc() {
        return ngayHoc;
    }

    public void setNgayHoc(Date ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public ArrayList<DiemDanhSV> getListDiemDanhSV() {
        return listDiemDanhSV;
    }

    public void setListDiemDanhSV(ArrayList<DiemDanhSV> listDiemDanhSV) {
        this.listDiemDanhSV = listDiemDanhSV;
    }

    @Override
    public boolean equals(Object o) {
        // so sanh cac thuoc tinh cua 1 buoiHoc, tru thong tin diemDanhSV
        BuoiHoc buoiHocSoSanh = (BuoiHoc) o;
        if(maLopHoc.equals(buoiHocSoSanh.getMaLopHoc()) 
                && ca == buoiHocSoSanh.getCa() && tenNhom.equals(buoiHocSoSanh.getTenNhom())
                && ngayHoc.equals(buoiHocSoSanh.getNgayHoc())){
            return true;
        }
        return false;
    }
    
    
    public void khoiTaolistDiemDanhSV(ArrayList<String> listMSSV){
        listDiemDanhSV = new ArrayList<>();
        for(String s : listMSSV){
            DiemDanhSV tmp = new DiemDanhSV(s, MyEnum.VANG_KO_PHEP.getIdDiemDanh());
            listDiemDanhSV.add(tmp);
        }
    }
    
    public BuoiHoc clone(){
        BuoiHoc newBuoiHoc = new BuoiHoc();
        newBuoiHoc.maLopHoc = this.maLopHoc;
        newBuoiHoc.ca = this.ca;
        newBuoiHoc.tenNhom = this.tenNhom;
        newBuoiHoc.ngayHoc = (Date) this.ngayHoc.clone();
        newBuoiHoc.listDiemDanhSV = new ArrayList<>();
        for(DiemDanhSV diemDanhSV : listDiemDanhSV){
            newBuoiHoc.listDiemDanhSV.add(new DiemDanhSV(diemDanhSV.getMaSoSV(), diemDanhSV.getiDdiemDanh()));
        }
        return newBuoiHoc;
    }
    
    public DiemDanhSV getSVDiemDanh(String mssv){
        for(DiemDanhSV sv : listDiemDanhSV){
            if(mssv.equals(sv.getMaSoSV())){
                return sv;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BuoiHoc{" + "maLopHoc=" + maLopHoc + ", tenNhom=" + tenNhom + ", ngayHoc=" + ngayHoc + ", ca=" + ca + ", listDiemDanhSV=" + listDiemDanhSV.toString() ;
    }
    
    public boolean isExistSVHoc() {
        // kiem tra co ton tai SV di hoc trong buoiHoc nay hay ko?
        for (DiemDanhSV diemDanhSV : listDiemDanhSV) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.CO_MAT.getIdDiemDanh()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isExistSVHocMuon() {
        // kiem tra co ton tai SV hoc muon trong buoiHoc nay hay ko?
        for (DiemDanhSV diemDanhSV : listDiemDanhSV) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.HOC_MUON.getIdDiemDanh()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isExistSVVangKoPhep() {
        // kiem tra co ton tai SV vang hoc khong phep trong buoiHoc nay hay ko?
        for (DiemDanhSV diemDanhSV : listDiemDanhSV) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_KO_PHEP.getIdDiemDanh()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isExistSVVangCoPhep() {
        // kiem tra co ton tai SV vang hoc co phep trong buoiHoc nay hay ko?
        for (DiemDanhSV diemDanhSV : listDiemDanhSV) {
            if (diemDanhSV.getiDdiemDanh() == MyEnum.VANG_CO_PHEP.getIdDiemDanh()) {
                return true;
            }
        }
        return false;
    }
    
    
    
    // hinh nhu ham nay bi thua
    public int getIDNhom(){
        int id = -1;
        
        if(maLopHoc.equals("") || tenNhom.equals("")){
            return id;
        }
        else {
                    String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql="select IDNhomTH from F_getIDNhom(?, ?) ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,maLopHoc);     
            ps.setString(2, tenNhom);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {                              
                id = resultSet.getInt("IDNhomTH");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }finally{
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }           
        }
        return id;
        }      
    }
}
