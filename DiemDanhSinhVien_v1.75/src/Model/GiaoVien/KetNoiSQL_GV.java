/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.LichDayChinh;
import Model.InfoStudent;
import Model.HocKy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chuon
 */
public class KetNoiSQL_GV {

    private static String maGV;

    public static String getMaGV() {
        return maGV;
    }

    public static void setMaGV(String maGV) {
        KetNoiSQL_GV.maGV =  maGV.toUpperCase();
    }

    //LƯU Ý: TẤT CẢ CÁC METHOD DƯỚI ĐÂY ĐỀU LẤY NHỮNG THÔNG TIN TRONG PHẠM VI LIÊN QUAN ĐẾN GIÁO VIÊN ĐÓ
    // NGHĨA LÀ TẤT CẢ METHOD DƯỚI ĐÂY ĐỀU CÓ THÊM THAM SỐ LÀ 
    // String maGV + ArrayList<Integer> idVaiTro CỦA TỪNG LỚP GV Dạy
    // DAY LA LY DO String maGV la thuoc tinh static
    // ĐỪNG LẤY DỮ LIỆU BỊ TRÙNG NHÁ
    // 2 ham getNgayBDHocKy + getNgayKTHocKy duoi day co the se ko su dung
    public static Date getNgayBDHocKy(int sttHocKy, int namHoc) {
        // SQL: tra ve ngay dau tien GV day cua Nam Hoc = ? and Hoc Ky = ?
        Date ketQua = null;
        String user = "sa";
        String pass = "123";
        //String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM";
        String url = "jdbc:sqlserver://;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Ket Noi sql thanh cong");
            String sql = "select dbo.F_getNgayBD(?,?,?) as NgayBD";
            //statement= connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ps.setInt(2, namHoc);
            ps.setInt(3, sttHocKy);
            ResultSet resultSet = ps.executeQuery();
            //resultSet = statemen1.executeQuery(sql);
            while (resultSet.next()) {
                Date tmp;
                tmp = resultSet.getDate("NgayBD");
                ketQua = tmp;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            System.out.println("Ket Noi sql khong duoc");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static Date getNgayKTHocKy(int sttHocKy, int namHoc) {
        // SQL: tra ve ngay cuoi cung GV day cua Nam Hoc = ? and Hoc Ky = ?
        Date ketQua = null;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select dbo.F_getNgayKT('" + maGV + "'," + namHoc + "," + sttHocKy + ") as NgayBD ";
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Date tmp;
                tmp = resultSet.getDate("NgayBD");
                ketQua = tmp;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static Date getNgayBDHocKyAll(int sttHocKy, int namHoc) {
        // SQL: tra ve ngay dau tien GV day cua Nam Hoc = ? and Hoc Ky = ?
        Date ketQua = null;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select dbo.F_getNgayBDHocKy(?,?) as NgayBD ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, namHoc);
            ps.setInt(2, sttHocKy);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ketQua = resultSet.getDate("NgayBD");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static Date getNgayKTHocKyAll(int sttHocKy, int namHoc) {
        // SQL: tra ve ngay cuoi cung GV day cua Nam Hoc = ? and Hoc Ky = ?
        Date ketQua = null;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select dbo.F_getNgayKTHocKy(?,?) as NgayKT ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, namHoc);
            ps.setInt(2, sttHocKy);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ketQua = resultSet.getDate("NgayKT");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return ketQua;
    }

    public static HocKy getHocKyMoiNhat() {
        HocKy ketQua = new HocKy();
        // SQL: lay data sttHocKy + namHoc cua lopMonHoc ma GV day muon nhat (ko can them dsTuan)
        // cai nay phai xem xet lai boi vi de khoi tao HocKy can chay 1 ham o trong ThongTinDay

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statemen1 = null;
        Statement statemen2 = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select dbo.F_maxNamHoc() as MaxNH ";
            String sql2 = "select dbo.F_maxHocKi() as MaxHK ";
            statemen1 = connection.createStatement();
            statemen2 = connection.createStatement();
//            statemen1 = connection.prepareStatement(sql);
//            statemen2 = connection.prepareStatement(sql3);
//            statemen1.setString(1, maGV);
//            statemen2.setString(1, maGV);
            ResultSet resultSet = statemen1.executeQuery(sql);

//            resultSet = statemen1.executeQuery(sql);
//            resultSet2 = statemen2.executeQuery(sql3);
            while (resultSet.next()) {
                int tmp;
                tmp = resultSet.getInt("MaxNH");
                ketQua.setNamHoc(tmp);
            }
            ResultSet resultSet2 = statemen2.executeQuery(sql2);
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
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statemen1 != null) {
                try {
                    statemen1.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statemen2 != null) {
                try {
                    statemen1.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketQua;
    }

    public static HocKy getHocKyLauNhat() {
        HocKy ketQua = new HocKy();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;
        Statement statement2 = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select dbo.F_minNamHoc() as MinNH ";
            String sql2 = "select dbo.F_minHocKi() as MinHK ";
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            ResultSet resultSet, resultSet2;
            resultSet = statement.executeQuery(sql);
            resultSet2 = statement2.executeQuery(sql2);
            while (resultSet.next()) {
                int tmp;
                tmp = resultSet.getInt("MinNH");
                ketQua.setNamHoc(tmp);
            }
            while (resultSet2.next()) {
                int tmp;
                tmp = resultSet2.getInt("MinHK");
                ketQua.setSttHocKy(tmp);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement2 != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketQua;
    }

    public static ArrayList<LichDayChinh> getDSLichDay(ArrayList<LopHoc> dsLopHoc) {
        // SQL: bo sung toan bo data trong mang LichDayChinh, 
        //tuong ung voi moi phan tu trong dsLopHoc se co nhung lich day chi tiet do nha truong sap xep
        ArrayList<LichDayChinh> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            for (LopHoc lh : dsLopHoc) {
                String maLH = lh.getMaLopHoc();
                String sql = "select * from F_getLichDayChinh(?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, maLH);
                ResultSet resultSet;
                resultSet = ps.executeQuery();
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
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (ketQua.size() == 0) {
            return null;
        } else {
            return ketQua;
        }
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
        // SQl: lay toan bo cac lop hoc GV do da va dang day
        ArrayList<LopHoc> tmp = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select * from F_getListLopMonHoc('" + maGV + "')";
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maLopMonHoc = resultSet.getString("MaLopMH");
                int hocKy = resultSet.getInt("HocKy");
                int namHoc = resultSet.getInt("NamHoc");
                String tenMH = resultSet.getString("TenMH");
                String lop = resultSet.getString("Lop");
                LopHoc lh = new LopHoc(maLopMonHoc, hocKy, namHoc, tenMH, lop);
                int LMHDaTonTai = LopMonHocDaTonTai(tmp, maLopMonHoc, namHoc, hocKy);

                if (LMHDaTonTai > -1) {
                    ArrayList<Integer> vt = tmp.get(LMHDaTonTai).getIdVaiTro();
                    int vaitro = resultSet.getInt("IDVaiTro");
                    vt.add(vaitro);
                    lh.setIdVaiTro(vt);
                    tmp.set(LMHDaTonTai, lh);
                } else {
                    int vaitro = resultSet.getInt("IDVaiTro");
                    ArrayList<Integer> vt = new ArrayList<>();
                    vt.add(vaitro);
                    lh.setIdVaiTro(vt);
                    tmp.add(lh);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return tmp;
    }

    public static ArrayList<LopHoc> getListLopHoc(int sttHocKy, int namHoc) {
        // SQL: bo sung toan bo data trong voi input tren
        ArrayList<LopHoc> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select * from F_getListLMHTheoHocKy(?,?,?)";
            //statement= connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ps.setInt(2, namHoc);
            ps.setInt(3, sttHocKy);
            ResultSet resultSet = ps.executeQuery();
            //resultSet = statemen1.executeQuery(sql);
            while (resultSet.next()) {
                String maLopMonHoc = resultSet.getString("MaLopMH");
                int hocKy = resultSet.getInt("HocKy");
                int tmpNamHoc = resultSet.getInt("NamHoc");
                String maMH = resultSet.getString("TenMH");
                String lop = resultSet.getString("Lop");
                LopHoc lh = new LopHoc(maLopMonHoc, hocKy, tmpNamHoc, maMH, lop);
                int LMHDaTonTai = LopMonHocDaTonTai(ketQua, maLopMonHoc, tmpNamHoc, hocKy);

                if (LMHDaTonTai > -1) {
                    ArrayList<Integer> vt = ketQua.get(LMHDaTonTai).getIdVaiTro();
                    int vaitro = resultSet.getInt("IDVaiTro");
                    vt.add(vaitro);
                    lh.setIdVaiTro(vt);
                    ketQua.set(LMHDaTonTai, lh);
                } else {
                    int vaitro = resultSet.getInt("IDVaiTro");
                    ArrayList<Integer> vt = new ArrayList<>();
                    vt.add(vaitro);
                    lh.setIdVaiTro(vt);
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
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static ArrayList<BuoiHoc> getListBuoiHoc(String maLopHoc) {
        //SQL: bo sung toan bo data ArrayList <BuoiHoc> voi input tren
        // lay buoi hoc voi DiemDanhSV duoc sap xep theo mssv
        ArrayList<BuoiHoc> ketqua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select * from F_getListBuoiHoc(?,?)";
            //statement= connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ps.setString(2, maLopHoc);

            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String maBH = resultSet.getString("MaBH");
                String tenNhom = resultSet.getString("ChuThich");
                Date ngay = resultSet.getDate("Ngay");
                int ca = resultSet.getInt("Ca");
                String sql2 = "select * from F_getListSVDD(?)";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1, maBH);
                ResultSet rs = ps2.executeQuery();
                ArrayList<DiemDanhSV> listDDSV = new ArrayList<>();
                while (rs.next()) {
                    DiemDanhSV ddSV = new DiemDanhSV(rs.getString("MSSV"), rs.getInt("IDDiemDanh"));
                    listDDSV.add(ddSV);
                }
                BuoiHoc bh = new BuoiHoc(maLopHoc, tenNhom, ngay, ca, listDDSV);
                ketqua.add(bh);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketqua;

    }

    public static ArrayList<NhomSV> getListNhomSV(String maLopHoc) {
        ArrayList<NhomSV> list = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);

            String sql = "select * from F_getListVaiTroGV(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ps.setString(2, maLopHoc);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Integer> listVaiTro = new ArrayList<>();
            while (resultSet.next()) {
                listVaiTro.add(resultSet.getInt("IDVaiTro"));
            }
            for (int i : listVaiTro) {
                if (i == 0) {
                    String sql1 = "select * from F_getListSVLMHTheoNhom(?,?)";
                    PreparedStatement ps1 = connection.prepareStatement(sql1);
                    ps1.setString(1, maLopHoc);
                    ps1.setInt(2, i);
                    ResultSet rs = ps1.executeQuery();
                    NhomSV nSV = new NhomSV();
                    nSV.setMaLopMH(maLopHoc);
                    ArrayList<String> lSV = new ArrayList<>();
                    String tenNhom = "";
                    while (rs.next()) {
                        lSV.add(rs.getString("MSSV"));
                        tenNhom = rs.getString("ChuThich");
                    }
                    nSV.setListMSSV(lSV);
                    nSV.setTenNhom(tenNhom);
                    list.add(nSV);
                }
                if (i == 1) {
                    String sql2 = "select Max(IDNhomTH) as MAXID from F_getListSVLopMonHoc(?) ";
                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ps2.setString(1, maLopHoc);
                    ResultSet rs2 = ps2.executeQuery();
                    int maxIDNhomTH = 1;
                    while (rs2.next()) {
                        maxIDNhomTH = rs2.getInt("MAXID");
                    }
                    for (int j = 1; j <= maxIDNhomTH; j++) {
                        String sql3 = "select * from F_getListSVLMHTheoNhom(?,?)";
                        PreparedStatement ps3 = connection.prepareStatement(sql3);
                        ps3.setString(1, maLopHoc);
                        ps3.setInt(2, j);
                        ResultSet rs3 = ps3.executeQuery();
                        NhomSV nSV = new NhomSV();
                        nSV.setMaLopMH(maLopHoc);
                        String tenNhom = "";
                        ArrayList<String> lSV = new ArrayList<>();
                        while (rs3.next()) {
                            lSV.add(rs3.getString("MSSV"));
                            // cai gi day ?? dau co tac dung gi dau
                            tenNhom = rs3.getString("ChuThich");
                        }
                        nSV.setListMSSV(lSV);
                        nSV.setTenNhom(tenNhom);
                        list.add(nSV);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return list;
    }

    public static ArrayList<MailStudent> getListMail() {
        // lay toan bo Mail GV gui cho SV
        ArrayList<MailStudent> list = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "select * from F_getListGVThongBao(?) ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String maLopHoc = resultSet.getString("MaLopMH");

                Date ngay = new Date(resultSet.getTimestamp("ThoiGian").getTime());

                String noiDung = resultSet.getString("NoiDung");
                String maTB = resultSet.getString("MaTB");
                boolean thongBaoTuDo = resultSet.getBoolean("ThongBaoTuDo");
                ArrayList<String> listSV = new ArrayList<>();
                String sql1 = "select * from dbo.GVThongBaoSV as gvtbsv where gvtbsv.MaTB = ?  ";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setString(1, maTB);
                ResultSet rs = ps1.executeQuery();
                while (rs.next()) {
                    listSV.add(rs.getString("MSSV"));
                }
                MailStudent mail = new MailStudent(maLopHoc, ngay, noiDung, listSV, thongBaoTuDo);
                list.add(mail);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return list;
    }

    public static boolean saveMail(MailStudent mail) {
        boolean isSave = false;

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "INSERT INTO dbo.GVThongBao(MaTB,MaLopMH,MaGV,IDVaiTro,ThongBaoTuDo)VALUES(?,?,?,?,? ) "
                    + "INSERT INTO dbo.NDThongBao(MaTB,ThoiGian,NoiDung)VALUES(?,?,? )";

            String sql1 = "SELECT MIN(IDVaiTro) as MINID FROM dbo.F_getListVaiTroGV(?,?)";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setString(1, maGV);
            ps1.setString(2, mail.getMaLopHoc());
            int idVaiTro = 0;
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                idVaiTro = rs.getInt("MINID");
            }
            String sql2 = "Select * from dbo.GVThongBao";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<Integer> listMaBH = new ArrayList<>();
            while (rs2.next()) {
                listMaBH.add(Integer.parseInt(rs2.getString("MaTB").substring(rs2.getString("MaTB").lastIndexOf("_") + 1)));
            }
            int maxMaTB = 0;
            if (listMaBH.size() != 0) {
                for (int i : listMaBH) {
                    if (i > maxMaTB) {
                        maxMaTB = i;
                    }
                }
            }
            maxMaTB += 1;
            String maTB = "TB_" + maxMaTB;
            ps = connection.prepareCall(sql);
            ps.setString(1, maTB);
            ps.setString(2, mail.getMaLopHoc());
            ps.setString(3, maGV);
            ps.setInt(4, idVaiTro);
            ps.setInt(5, mail.isThongBaoTuDo() ? 1 : 0);
            ps.setString(6, maTB);

//            java.sql.Date ngay = new java.sql.Date(mail.getNgayGui().getTime());
//            statemen1.setDate(7, ngay);
            java.sql.Timestamp ngay = new java.sql.Timestamp(mail.getNgayGui().getTime());
            ps.setTimestamp(7, ngay);
            ps.setNString(8, mail.getNoiDung());
            ps.execute();

            for (String mssv : mail.getListMSSV()) {
                String sql3 = "INSERT INTO dbo.GVThongBaoSV(MaTB,MSSV)VALUES(?,? )";
                PreparedStatement ps3 = connection.prepareCall(sql3);
                ps3.setString(1, maTB);
                ps3.setString(2, mssv);
                ps3.execute();
            }

            isSave = true;
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return isSave;
    }

    public static boolean saveBuoiHoc(BuoiHoc buoiHoc) {
        // save toan bo thuoc tinh trong buoiHoc len tren cac bang SQL
        boolean isSave = false;

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "INSERT INTO dbo.BuoiHoc( MaBH,MaLopMH,MaGV,IDVaiTro,Ngay,Ca) VALUES(?,?,?,?,?,? ) "
                    + "INSERT INTO dbo.NhomTHBuoiHoc(MaBH,MaLopMH,IDNhomTH) VALUES (?,?,?)";
            String sqlNew = "SELECT * FROM dbo.LoaiNhomTH AS lnth WHERE lnth.ChuThich=?";
            PreparedStatement psNew = connection.prepareStatement(sqlNew);
            psNew.setNString(1, buoiHoc.getTenNhom());
            int idNhomTH = 0;
            ResultSet rs = psNew.executeQuery();
            while (rs.next()) {
                idNhomTH = rs.getInt("IDNhomTH");
            }

            statement = connection.prepareCall(sql);
            String maLMH = buoiHoc.getMaLopHoc();
            String magv = maGV;
            int vaitro = 0;
            if (idNhomTH == 0) {
                vaitro = 0;
            } else {
                vaitro = 1;
            }
            java.sql.Date ngay = new java.sql.Date(buoiHoc.getNgayHoc().getTime());
            int ca = buoiHoc.getCa();
            String maBH = magv + "_" + ngay.toString() + "_" + ca;
            statement.setString(1, maBH);
            statement.setString(2, maLMH);
            statement.setString(3, magv);
            statement.setInt(4, vaitro);
            statement.setDate(5, ngay);
            statement.setInt(6, ca);
            statement.setString(7, maBH);
            statement.setString(8, maLMH);
            statement.setInt(9, idNhomTH);
            statement.execute();

            for (DiemDanhSV ddsv : buoiHoc.getListDiemDanhSV()) {
                String sql1 = "INSERT INTO dbo.SVDiemDanh (MaBH,MSSV,IDDiemDanh) VALUES(?,?,?)";
                PreparedStatement ps = connection.prepareCall(sql1);
                ps.setString(1, maBH);
                ps.setString(2, ddsv.getMaSoSV());
                ps.setInt(3, ddsv.getiDdiemDanh());
                ps.execute();
            }
            isSave = true;
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return isSave;
    }

    public static boolean updateBuoiHoc(BuoiHoc buoiHoc) {
        // update toan bo thuoc tinh trong buoiHoc len tren cac bang SQL
        boolean isUpdate = false;

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM dbo.BuoiHoc AS bh WHERE bh.MaLopMH=? AND bh.MaGV=? AND bh.Ngay=? AND bh.Ca=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, buoiHoc.getMaLopHoc());
            ps.setString(2, maGV);
            java.sql.Date ngay = new java.sql.Date(buoiHoc.getNgayHoc().getTime());
            ps.setDate(3, ngay);
            ps.setInt(4, buoiHoc.getCa());
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            String maBH = "";
            while (resultSet.next()) {
                maBH = resultSet.getString("MaBH");
            }
            for (DiemDanhSV ddsv : buoiHoc.getListDiemDanhSV()) {
                String sql1 = "UPDATE dbo.SVDiemDanh SET MaBH=?,MSSV=?,IDDiemDanh=? WHERE MaBH=? and MSSV=?";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setString(1, maBH);
                ps1.setString(2, ddsv.getMaSoSV());
                ps1.setInt(3, ddsv.getiDdiemDanh());
                ps1.setString(4, maBH);
                ps1.setString(5, ddsv.getMaSoSV());
                ps1.execute();
            }
            isUpdate = true;
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return isUpdate;
    }

    public static boolean deleteBuoiHoc(BuoiHoc buoiHoc) {
        // delete toan bo thuoc tinh trong buoiHoc len tren cac bang SQL
        boolean isDeleated = false;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM dbo.BuoiHoc AS bh WHERE bh.MaLopMH=? AND bh.MaGV=? AND bh.Ngay=? AND bh.Ca=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, buoiHoc.getMaLopHoc());
            ps.setString(2, maGV);
            java.sql.Date ngay = new java.sql.Date(buoiHoc.getNgayHoc().getTime());
            ps.setDate(3, ngay);
            ps.setInt(4, buoiHoc.getCa());
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            String maBH = "";
            while (resultSet.next()) {
                maBH = resultSet.getString("MaBH");
            }
            for (DiemDanhSV ddsv : buoiHoc.getListDiemDanhSV()) {
                String sql1 = "delete dbo.SVDiemDanh WHERE MaBH=? and MSSV=?";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setString(1, maBH);
                ps1.setString(2, ddsv.getMaSoSV());
                ps1.execute();
            }           
            String sql2 = "delete dbo.NhomTHBuoiHoc WHERE MaBH=?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setString(1, maBH);
            ps2.execute();            
            String sql3 = "delete dbo.BuoiHoc WHERE MaBH=?";
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            ps3.setString(1, maBH);
            ps3.execute();
            isDeleated = true;
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return isDeleated;
    }

    public static ArrayList<LichDayChinh> getLichDay(LopHoc lopHoc) {
        // nhớ là chỉ lấy dữ liệu liên quan đến lớp học + trong phạm vi idVaiTro của GV đó
        ArrayList<LichDayChinh> ketQua = new ArrayList<>();

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            for (int i : lopHoc.getIdVaiTro()) {
                String maLH = lopHoc.getMaLopHoc();
                String sql = "";
                if (i == 0) {
                    sql = "SELECT ld.*,lnth.ChuThich FROM dbo.LichDay AS ld,dbo.LoaiNhomTH AS lnth WHERE ld.MaLopMH=? AND ld.IDNhomTH=0 AND ld.IDNhomTH=lnth.IDNhomTH";
                } else {
                    sql = "SELECT ld.*,lnth.ChuThich FROM dbo.LichDay AS ld,dbo.LoaiNhomTH AS lnth WHERE ld.MaLopMH=? AND ld.IDNhomTH>0 AND ld.IDNhomTH=lnth.IDNhomTH";
                }
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, maLH);
                ResultSet resultSet;
                resultSet = ps.executeQuery();
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
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static ArrayList<InfoStudent> getListInfoStudent(String maLopHoc) {
        // lay data sap xep theo mssv
        ArrayList<InfoStudent> list = new ArrayList<>();
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT sv.* FROM dbo.SVHocLopMonHoc AS svhlmh,dbo.SinhVien AS sv "
                    + "WHERE svhlmh.MSSV=sv.MSSV AND svhlmh.IDNhomTH=0 AND svhlmh.MaLopMH=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maLopHoc);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String hoTen = resultSet.getNString("HoTen");
                String ten = hoTen.substring(hoTen.lastIndexOf(" ") + 1);
                String ho = hoTen.substring(0, hoTen.lastIndexOf(" "));
                InfoStudent student = new InfoStudent(resultSet.getString("MSSV"), ho, ten, resultSet.getNString("GioiTinh"),
                        resultSet.getString("MaLopNC"));
                list.add(student);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return list;

    }

    public static boolean kiemTraGVDayHoc(int sttHocKy, int namHoc) {
        boolean ketQua = false;

        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT dbo.F_slLopMonHocGVDay(?,?,?) AS SL";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGV);
            ps.setInt(2, namHoc);
            ps.setInt(3, sttHocKy);
            ResultSet resultSet;
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ketQua = (resultSet.getInt("SL") > 0) ? true : false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KetNoiSQL_GV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ketQua;
    }
    
}
