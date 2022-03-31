/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.SinhVien;

import Model.HocKy;
import Model.LichDayChinh;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chuon
 */
public class KetNoiSQL_SV {

    private static String mssv;

    public static String getMssv() {
        return mssv;
    }

    public static void setMssv(String mssv) {
        KetNoiSQL_SV.mssv = mssv.toUpperCase();
    }

    private static int LopMonHocDaTonTai(ArrayList<LopHoc> listLH, String maLMH, int nam, int hocKy) {
        if (listLH.size() == 0) {
            return -1;
        }
        for (int i = 0; i < listLH.size(); i++) {
            LopHoc lh = listLH.get(i);
            if ((lh.getMaLopHoc().equals(maLMH)) && (lh.getNamHoc() == nam) && (lh.getHocKy() == hocKy)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<LopHoc> getListLopHoc() {
        // SQl: lay toan bo cac lop hoc SV do da va dang hoc
        ArrayList<LopHoc> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM FSV_getListLopHoc(?)";
            //statement= connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mssv);

            ResultSet resultSet = ps.executeQuery();
            //resultSet = statemen1.executeQuery(sql);
            while (resultSet.next()) {
                String maLopMonHoc = resultSet.getString("MaLopMH");
                int hocKy = resultSet.getInt("HocKy");
                int tmpNamHoc = resultSet.getInt("NamHoc");
                String maMH = resultSet.getString("TenMH");
                String lop = resultSet.getString("Lop");
                int LMHDaTonTai = LopMonHocDaTonTai(ketQua, maLopMonHoc, tmpNamHoc, hocKy);

                if (LMHDaTonTai > -1) {
                    ListGVDayHoc gv = ketQua.get(LMHDaTonTai).getListGVDayHoc();
                    String tenNhom = resultSet.getNString("ChuThich");
                    String maGV = resultSet.getString("MaGV");
                    gv.addGV(new GVDayHoc(maLopMonHoc, tenNhom, maGV));
                    LopHoc lh = new LopHoc(maLopMonHoc, hocKy, tmpNamHoc, maMH, lop, gv);
                    ketQua.set(LMHDaTonTai, lh);
                } else {
                    String tenNhom = resultSet.getNString("ChuThich");
                    String maGV = resultSet.getString("MaGV");
                    ListGVDayHoc gv = new ListGVDayHoc();
                    gv.addGV(new GVDayHoc(maLopMonHoc, tenNhom, maGV));
                    LopHoc lh = new LopHoc(maLopMonHoc, hocKy, tmpNamHoc, maMH, lop, gv);
                    ketQua.add(lh);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketQua;
    }

    public static ArrayList<LopHoc> getListLopHoc(int sttHocKy, int namHoc) {
        // SQl: lay toan bo cac lop hoc SV do hoc trong sttHocKy + namHoc
        ArrayList<LopHoc> ketQua = new ArrayList<>();
        ArrayList<LopHoc> listLH = getListLopHoc();
        for (LopHoc lh : listLH) {
            if ((lh.getHocKy() == sttHocKy) && (lh.getNamHoc() == namHoc)) {
                ketQua.add(lh);
            }
        }
        return ketQua;
    }

    public static ArrayList<LichDayChinh> getLichDay(LopHoc lopHoc) {
        // nhá»› lĂ  chá»‰ láº¥y dá»¯ liá»‡u liĂªn quan Ä‘áº¿n lá»›p há»�c + trong pháº¡m vi cac nhom SV do hoc
        ArrayList<LichDayChinh> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM dbo.LichDay AS ld, dbo.LoaiNhomTH AS lnth\n"
                    + "WHERE ld.IDNhomTH=lnth.IDNhomTH AND ld.MaLopMH=? AND lnth.ChuThich =?";
            for (GVDayHoc gv : lopHoc.getListGVDayHoc().getListGVDayHoc()) {
                String tenNhom = gv.getTenNhom();
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, lopHoc.getMaLopHoc());
                ps.setNString(2, tenNhom);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    LichDayChinh tmp = new LichDayChinh(resultSet.getString("MaLopMH"), resultSet.getInt("BuoiHoc"), resultSet.getInt("Ca"),
                            resultSet.getDate("NgayBD"), resultSet.getDate("NgayKT"), resultSet.getString("ChuThich"));
                    ketQua.add(tmp);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return ketQua;
    }
    
    public static ArrayList<BuoiHoc> getListBuoiHoc(String maLopHoc) {
        //SQL: bo sung toan bo data ArrayList <BuoiHoc> voi input tren
        ArrayList<BuoiHoc> ketqua = new ArrayList<>();

        LopHoc lopHoc = new LopHoc();
        ArrayList<LopHoc> listLH = getListLopHoc();
        for (LopHoc lh : listLH) {
            if (lh.getMaLopHoc().equals(maLopHoc)) {
                lopHoc = lh;
            }
        }

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM FSV_getListBuoiHoc(?,?,?,?) ";
            for (GVDayHoc gv : lopHoc.getListGVDayHoc().getListGVDayHoc()) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, mssv);
                ps.setString(2, gv.getMaLopHoc());
                ps.setString(3, gv.getTenNhom());
                ps.setString(4, gv.getMaGV());
                ResultSet resultSet;
                resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Date ngay = resultSet.getDate("Ngay");
                    int ca = resultSet.getInt("Ca");
                    BuoiHoc bh = new BuoiHoc(gv, ngay, ca, resultSet.getInt("IDDiemDanh"));
                    ketqua.add(bh);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return ketqua;

    }

    public static ArrayList<Mail> getListMail() {
        // lay toan bo Mail GV gui cho SV
        ArrayList<Mail> ketQua = new ArrayList<>();
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM dbo.GVThongBao AS gvtb,dbo.GVThongBaoSV AS tbsv,dbo.NDThongBao AS ndtb\n"
                    + "WHERE gvtb.MaTB=tbsv.MaTB AND gvtb.MaTB=ndtb.MaTB AND tbsv.MSSV = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mssv);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String maLopHoc = resultSet.getString("MaLopMH");

                Date ngay = new Date(resultSet.getTimestamp("ThoiGian").getTime());

                String noiDung = resultSet.getString("NoiDung");
                String maGV = resultSet.getString("MaGV");
                ketQua.add(new Mail(maLopHoc, maGV, ngay, noiDung));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    private static boolean tonTaiGV(ArrayList<InfoTeacher> list, String maGV) {
        if (list.size() == 0) {
            return false;
        }
        for (InfoTeacher info : list) {
            if (info.getMaGV().equals(maGV)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<InfoTeacher> getListInfoTeacher(String maLopHoc) {
        // lay toan bo info ve GV day trong maLopHoc do, ko lay trung nha(lay luon nhom Ly Thuyet luon)
        ArrayList<InfoTeacher> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT gvdlmh.*,gv.HoTenGV FROM dbo.GVDayLopMonHoc AS gvdlmh,dbo.GiaoVien AS gv\n"
                    + "WHERE gvdlmh.MaLopMH=? AND gv.MaGV=gvdlmh.MaGV";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maLopHoc);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String maGV = resultSet.getString("MaGV");
                if (!tonTaiGV(ketQua, maGV)) {
                    ketQua.add(new InfoTeacher(maGV, resultSet.getNString("HoTenGV")));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketQua;
    }

    public static HocKy getHocKyMoiNhat() {
        // SQL: lay data sttHocKy + namHoc cua lopMonHoc ma SV hoc gan day nhat (ko can them dsTuan)
        HocKy ketQua = new HocKy();
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT dbo.FSV_maxNamHoc(?) as MaxNH ";
            String sql2 = "SELECT dbo.FSV_maxHocKi(?) as MaxHK ";
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps.setString(1, mssv);
            ps2.setString(1, mssv);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int tmp;
                tmp = resultSet.getInt("MaxNH");
                ketQua.setNamHoc(tmp);
            }
            ResultSet resultSet2 = ps2.executeQuery();
            while (resultSet2.next()) {
                int tmp;
                tmp = resultSet2.getInt("MaxHK");
                ketQua.setSttHocKy(tmp);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_SV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return ketQua;
    }

    public static boolean kiemTraSVHoc(int sttHocKy, int namHoc) {
        // SQL: kiem tra xem SV co hoc mon nao trong sttHocKy + namHoc ko?
        // true: co hoc it nhat 1 mon
        // false: ko hoc mon nao ca
        boolean ketQua = false;
        ArrayList<LopHoc> listLH = getListLopHoc();
        for (LopHoc lh : listLH) {
            if ((lh.getHocKy() == sttHocKy) && (lh.getNamHoc() == namHoc)) {
                ketQua = true;
            }
        }
        return ketQua;
    }

}
