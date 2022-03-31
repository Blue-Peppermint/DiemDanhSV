/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author chuon
 */
public class InfoStudent {
    private String mssv;
    private String ho;
    private String ten;
    private String gioiTinh;
    private String lopNC;

    public InfoStudent() {
        this.mssv = null;
        this.ho = null;
        this.ten = null;
        this.gioiTinh = null;
        this.lopNC = null;
    }

    public InfoStudent(String mssv, String ho, String ten, String gioiTinh, String lopNC) {
        this.mssv = mssv;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.lopNC = lopNC;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getLopNC() {
        return lopNC;
    }

    public void setLopNC(String lopNC) {
        this.lopNC = lopNC;
    }
    public void getSqlData(String mssv){
        // SQL: lay tat ca du lieu con thieu tren SQL thong qua ma so SV
        
    } 

    @Override
    public String toString() {
        return "InfoStudent{" + "mssv=" + mssv + ", ho=" + ho + ", ten=" + ten + ", gioiTinh=" + gioiTinh + ", lopNC=" + lopNC + '}';
    }
    
}
