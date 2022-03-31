/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.ImageIcons.ICON_HOC_MUON;
import static Model.ImageIcons.ICON_VANG_CO_PHEP;
import static Model.ImageIcons.ICON_VANG_KO_PHEP;
import java.awt.Color;
import java.awt.Image;
import java.io.FileOutputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author chuon
 */
class ImageIcons {

    final static int iconHeight = 24; // iconHeight phải = RowHeight của JTable
//    final static String FILE_PATH_ICON_COMAT = "C:\\Users\\chuon\\OneDrive\\Máy tính\\Study\\HuongDoiTuong\\DiemDanhSinhVien_v1.5\\src\\Resources\\check.png";
//    final static String FILE_PATH_ICON_HOC_MUON = "C:\\Users\\chuon\\OneDrive\\Máy tính\\Study\\HuongDoiTuong\\DiemDanhSinhVien_v1.5\\src\\Resources\\away.png";
//    final static String FILE_PATH_ICON_VANG_KO_PHEP = "C:\\Users\\chuon\\OneDrive\\Máy tính\\Study\\HuongDoiTuong\\DiemDanhSinhVien_v1.5\\src\\Resources\\error.png";
//    final static String FILE_PATH_ICON_VANG_CO_PHEP = "C:\\Users\\chuon\\OneDrive\\Máy tính\\Study\\HuongDoiTuong\\DiemDanhSinhVien_v1.5\\src\\Resources\\absence.png";
    final static String FILE_PATH_ICON_COMAT = "src\\Resources\\check.png";
    final static String FILE_PATH_ICON_HOC_MUON = "src\\Resources\\away.png";
    final static String FILE_PATH_ICON_VANG_KO_PHEP = "src\\Resources\\error.png";
    final static String FILE_PATH_ICON_VANG_CO_PHEP = "src\\Resources\\absence.png";
    static ImageIcon ICON_COMAT;
    static ImageIcon ICON_HOC_MUON;
    static ImageIcon ICON_VANG_KO_PHEP;
    static ImageIcon ICON_VANG_CO_PHEP;

    static {
        ICON_COMAT = new ImageIcon(new ImageIcon(FILE_PATH_ICON_COMAT).getImage()
                .getScaledInstance(iconHeight, iconHeight, Image.SCALE_SMOOTH));
        ICON_HOC_MUON = new ImageIcon(new ImageIcon(FILE_PATH_ICON_HOC_MUON).getImage()
                .getScaledInstance(iconHeight, iconHeight, Image.SCALE_SMOOTH));
        ICON_VANG_KO_PHEP = new ImageIcon(new ImageIcon(FILE_PATH_ICON_VANG_KO_PHEP).getImage()
                .getScaledInstance(iconHeight, iconHeight, Image.SCALE_SMOOTH));
        ICON_VANG_CO_PHEP = new ImageIcon(new ImageIcon(FILE_PATH_ICON_VANG_CO_PHEP).getImage()
                .getScaledInstance(iconHeight, iconHeight, Image.SCALE_SMOOTH));
    }

}

public enum MyEnum implements Serializable{

    CO_MAT(0, "Có Mặt", ImageIcons.ICON_COMAT, new Color(51, 204, 51), "X", IndexedColors.WHITE.index),
    HOC_MUON(1, "Học Muộn", ImageIcons.ICON_HOC_MUON, new Color(77, 210, 255), "M", IndexedColors.WHITE.index),
    VANG_KO_PHEP(2, "Vắng Không Phép", ImageIcons.ICON_VANG_KO_PHEP, new Color(255, 77, 77), "K.P", IndexedColors.WHITE.index),
    VANG_CO_PHEP(3, "Vắng Có Phép", ImageIcons.ICON_VANG_CO_PHEP, new Color(153, 153, 153), "P", IndexedColors.WHITE.index);
    
    private int idDiemDanh;
    private String chuThichEditorComBoBox;
    private ImageIcon icon;
    private Color colorDaiDien;
    private String kyHieuExcel;
    private short backGroundColorKyHieuExcel;
    
    private MyEnum(int idDiemDanh, String chuThichEditorComBoBox, ImageIcon icon,
            Color colorDaiDien, String kyHieuExcel, short backGroundColorKyHieuExcel) {
        this.idDiemDanh = idDiemDanh;
        this.chuThichEditorComBoBox = chuThichEditorComBoBox;
        this.icon = icon;
        this.colorDaiDien = colorDaiDien;
        this.kyHieuExcel = kyHieuExcel;
        this.backGroundColorKyHieuExcel = backGroundColorKyHieuExcel;
    }
    

    public int getIdDiemDanh() {
        return idDiemDanh;
    }

    public String getChuThichEditorComBoBox() {
        return chuThichEditorComBoBox;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Color getColorDaiDien() {
        return colorDaiDien;
    }

    public String getKyHieuExcel() {
        return kyHieuExcel;
    }

    public void setKyHieuExcel(String kyHieuExcel) {
        this.kyHieuExcel = kyHieuExcel;
    }

    public short getBackGroundColorKyHieuExcel() {
        return backGroundColorKyHieuExcel;
    }

    public void setBackGroundColorKyHieuExcel(short backGroundColorKyHieuExcel) {
        this.backGroundColorKyHieuExcel = backGroundColorKyHieuExcel;
    }    

    @Override
    public String toString() {
        return "MyEnum{" + "idDiemDanh=" + idDiemDanh + ", chuThichEditorComBoBox=" + chuThichEditorComBoBox + ", icon=" + icon + ", colorDaiDien=" + colorDaiDien + ", kyHieuExcel=" + kyHieuExcel + ", backGroundColorKyHieuExcel=" + backGroundColorKyHieuExcel + '}';
    }
    
    public static int getIDDiemDanh(String chuThich) {
        int idDiemDanh = -1;
        for (MyEnum e : MyEnum.values()) {
            if (e.chuThichEditorComBoBox.equals(chuThich)) {
                idDiemDanh = e.idDiemDanh;
            }
        }
        return idDiemDanh;
    }

    public static String getChuThichEditorComBoBox(int idDiemDanh) {
        String chuThich = null;
        for (MyEnum e : MyEnum.values()) {
            if (e.idDiemDanh == idDiemDanh) {
                chuThich = e.chuThichEditorComBoBox;
            }
        }
        return chuThich;
    }

    public static ImageIcon getIcon(int idDiemDanh) {
        ImageIcon icon = null;
        for (MyEnum e : MyEnum.values()) {
            if (e.idDiemDanh == idDiemDanh) {
                icon = e.icon;
            }
        }
        return icon;
    }
    
    public static Color getColorDaiDien(int idDiemDanh){
        Color color = null;
        for (MyEnum e : MyEnum.values()) {
            if (e.idDiemDanh == idDiemDanh) {
                color = e.colorDaiDien;
            }
        }
        return color;
    }
    
    public static String getKyHieuExcel(int idDiemDanh) {
        String kyHieuExcel = null;
        for (MyEnum e : MyEnum.values()) {
            if (e.idDiemDanh == idDiemDanh) {
                kyHieuExcel = e.kyHieuExcel;
            }
        }
        return kyHieuExcel;
    }
    
    public static short getBackGroundColorKyHieuExcel(int idDiemDanh) {
        short backGroundColorKyHieuExcel = -1;
        for (MyEnum e : MyEnum.values()) {
            if (e.idDiemDanh == idDiemDanh) {
                backGroundColorKyHieuExcel = e.backGroundColorKyHieuExcel;
            }
        }
        return backGroundColorKyHieuExcel;
    }      
    
}
