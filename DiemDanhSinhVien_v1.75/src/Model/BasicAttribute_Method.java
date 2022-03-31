package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chuon
 */
public class BasicAttribute_Method {

    public static boolean kiemTraTonTaiTaiKhoan(String tenDangNhap, String matKhau) {
        // kiem tra Table TaiKhoan:
        // return false; neu sai ten dang nhap || matKhau
        // neu dung ca ten dang nhap && matKhau return true
        // nho dung prepareStatement de phong loi sql injection
        // Khi kiem tra ten dang nhap ko phan biet chu in hoa hay chu thuong (nhu trong UIS)
        // su dung ham String.equalsIgnoreCase(String str)
        boolean ketQua = false;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT COUNT(*) AS SL FROM dbo.TaiKhoan WHERE [User]=? AND Pass=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet resultSet = ps.executeQuery();
            int tmp = 0;
            while (resultSet.next()) {
                tmp = resultSet.getInt("SL");
            }
            if (tmp != 0) {
                ketQua = true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BasicAttribute_Method.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }

        }
        return ketQua;
    }
    
    public static int getNgoaiLeMatKhau(String matKhau) {
        if(matKhau.equals("")){
            return 1;
        }
        if (matKhau.length() > 20) {
            return 2;
        }
        return 0;
    }

    public static boolean doiMatKhau(String tenDangNhap, String matKhau) {
        // doi mat khau cua user thong qua mssv o tren
        // true: neu thanh cong
        boolean ketQua = false;
        String user = "sa";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CNPHANMEM3";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE dbo.TaiKhoan SET Pass =? WHERE [User]=?";
            statement = connection.prepareCall(sql);
            statement.setString(1, matKhau);
            statement.setString(2, tenDangNhap);
            statement.execute();
            ketQua = true;
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BasicAttribute_Method.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BasicAttribute_Method.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ketQua;
    }

    public static String convertSpecialString(String text) {
        // Tất cả các ký tự sau là kiểu meta + special nên khi chạy RowFilter nó sẽ bị lỗi
        // "\.[{(*+?^$|"
        String textModified = text.replace("\\", "\\\\");
        textModified = textModified.replace(".", "\\.");
        textModified = textModified.replace("[", "\\[");
        textModified = textModified.replace("{", "\\{");
        textModified = textModified.replace("(", "\\(");
        textModified = textModified.replace("*", "\\*");
        textModified = textModified.replace("+", "\\+");
        textModified = textModified.replace("?", "\\?");
        textModified = textModified.replaceAll("\\^", "\\\\^");
        textModified = textModified.replace("$", "\\$");
        textModified = textModified.replaceAll("\\|", "\\\\|");
        return textModified;
    }
}
