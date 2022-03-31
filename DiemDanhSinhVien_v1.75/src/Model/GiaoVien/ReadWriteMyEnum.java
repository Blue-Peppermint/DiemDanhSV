/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GiaoVien;

import Model.MyEnum;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chuon
 */
class NeccesaryInfoMyEnum implements Serializable{
    private String kyHieuExcel;
    private short backGroundColorKyHieuExcel;

    public NeccesaryInfoMyEnum(String kyHieuExcel, short backGroundColorKyHieuExcel) {
        this.kyHieuExcel = kyHieuExcel;
        this.backGroundColorKyHieuExcel = backGroundColorKyHieuExcel;
    }

    public String getKyHieuExcel() {
        return kyHieuExcel;
    }

    public short getBackGroundColorKyHieuExcel() {
        return backGroundColorKyHieuExcel;
    }

    @Override
    public String toString() {
        return "NeccesaryInfoMyEnum{" + "kyHieuExcel=" + kyHieuExcel + ", backGroundColorKyHieuExcel=" + backGroundColorKyHieuExcel + '}';
    }
   
}

public class ReadWriteMyEnum {
    
    public static boolean writeMyEnum(String tenDangNhap){
        try {
            FileOutputStream fos = new FileOutputStream(tenDangNhap+"_MyENUM.DAT");
            ObjectOutputStream oos = new ObjectOutputStream(fos);          
            MyEnum e = MyEnum.CO_MAT;
            NeccesaryInfoMyEnum enumInfo = new NeccesaryInfoMyEnum(e.getKyHieuExcel(),
                    e.getBackGroundColorKyHieuExcel());
            oos.writeObject(enumInfo);
            e = MyEnum.HOC_MUON;
            enumInfo = new NeccesaryInfoMyEnum(e.getKyHieuExcel(),
                    e.getBackGroundColorKyHieuExcel());
            oos.writeObject(enumInfo);
            e = MyEnum.VANG_KO_PHEP;
            enumInfo = new NeccesaryInfoMyEnum(e.getKyHieuExcel(),
                    e.getBackGroundColorKyHieuExcel());
            oos.writeObject(enumInfo);
            e = MyEnum.VANG_CO_PHEP;
            enumInfo = new NeccesaryInfoMyEnum(e.getKyHieuExcel(),
                    e.getBackGroundColorKyHieuExcel());
            oos.writeObject(enumInfo);                       
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteMyEnum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteMyEnum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean readMyEnum(String tenDangNhap){
        try {
            FileInputStream fis = new FileInputStream(tenDangNhap+"_MyENUM.DAT");
             ObjectInputStream ois = new ObjectInputStream(fis);
             ArrayList<NeccesaryInfoMyEnum> objes = new ArrayList<>();
             while(fis.available() > 0){
                 NeccesaryInfoMyEnum obj = (NeccesaryInfoMyEnum) ois.readObject();
                 objes.add(obj);
                 //System.out.println(obj);
             }
             int obj_i = 0;
             for(MyEnum e: MyEnum.values()){
                 e.setKyHieuExcel(objes.get(obj_i).getKyHieuExcel());
                 e.setBackGroundColorKyHieuExcel(objes.get(obj_i).getBackGroundColorKyHieuExcel());
                 obj_i++;
             }
             ois.close();
             fis.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ReadWriteMyEnum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteMyEnum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadWriteMyEnum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       return true;
    }
    
    
    public static void main(String[] args) {
//        writeMyEnum();
//        readMyEnum();
    }
}
